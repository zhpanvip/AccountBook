package project.graduate.lele.accountbook.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.activity.InfoActivity;
import project.graduate.lele.accountbook.activity.LoginActivity;
import project.graduate.lele.accountbook.activity.PayDetailsActivity;
import project.graduate.lele.accountbook.activity.RecordActivity;
import project.graduate.lele.accountbook.bean.AccountBean;
import project.graduate.lele.accountbook.event.MsgEvent;
import project.graduate.lele.accountbook.utils.DateUtils;
import project.graduate.lele.accountbook.utils.SharedPreferencesUtils;
import project.graduate.lele.accountbook.utils.UserTools;

/**
 * Created by zhpan on 2017/1/8.
 * 记账页面
 */

public class AccountFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private TextView mTvIncome;
    private TextView mTvPay;
    private TextView mTvChazhi;
    private LinearLayout mLlAccount;
    private ImageView mIvPayToday;
    private RelativeLayout mRlPay;
    private ImageView mIvGainToday;
    private RelativeLayout mRlIncome;
    private ImageView mIvNews;
    private RelativeLayout mRlInfo;
    private ImageView mIvNew;
    private Button mBtnRecord;
    private TextView mTvPayToday;
    private TextView mTvIncomeToday;

    private double pay;         //  本月收入
    private double income;      //  本月指出
    private double payToday;    //  今日指出
    private double incomeToday; //  今日收入

    /**
     * 记账Fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_account, null);

        initView(mView);
        initData();
        setData();
        setListener();

        return mView;
    }

    private void initData() {
        //  注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {   //  Fragment销毁时的生命周期
        super.onDestroyView();
        //  注销EventBus
        EventBus.getDefault().unregister(this);
    }

    private void setData() {
        /**
         *  将收支的成员变量置0 避免第二次调用setData时数据错乱
         */
        pay=0;
        incomeToday=0;
        income=0;
        payToday=0;
        String username = UserTools.getUsername(getContext());

        //  获取当前日期 格式yyyy-MM-dd
        String date = DateUtils.getDate();
        //  从数据库中查询所有支出的数据集合（根据用户名、月份、和收支类别[支出]）
        List<AccountBean> accountPayBeen = DataSupport.where("accountClass=1 and username=? and month=?",username,DateUtils.getMonth()).find(AccountBean.class);
        //  从数据库中查询所有收入的数据集合（根据用户名、月份、和收支类别[收入]）
        List<AccountBean> accountIncomeBeen = DataSupport.where("accountClass=2 and username=? and month=?",username,DateUtils.getMonth()).find(AccountBean.class);
        //  从数据库中查询所有今日支出的数据集合（根据用户名、日期、和收支类别[支出]）
        List<AccountBean> accountPayToday= DataSupport.where("accountClass=1 and date=? and username=?", date,username).find(AccountBean.class);
        //  从数据库中查询所有今日收入的数据集合（根据用户名、月份、和收支类别[收入出]）
        List<AccountBean> accountIncomeToday = DataSupport.where("accountClass=2 and date=? and username=?", date,username).find(AccountBean.class);

        //  遍历支出集合 得到本月支出总和
        for(int i=0;i<accountPayBeen.size();i++){
            pay+=accountPayBeen.get(i).getAccount();
        }
        //  遍历收入集合 得到本月收入总和
        for(int i=0;i<accountIncomeBeen.size();i++){
            income+=accountIncomeBeen.get(i).getAccount();
        }
        //  遍历今日支出集合 得到今日支出总和
        for(int i=0;i<accountPayToday.size();i++){
            payToday+=accountPayToday.get(i).getAccount();
        }
        //  遍历今日收入集合 得到今日收入总和
        for(int i=0;i<accountIncomeToday.size();i++){
            incomeToday+=accountIncomeToday.get(i).getAccount();
        }

        SharedPreferencesUtils.saveYuE(getContext(), (float) (income-pay));

        //  设置本月支出
        mTvPay.setText("￥"+pay);
        //  设置本月收入
        mTvIncome.setText("￥"+income);
        //  设置本月差值
        mTvChazhi.setText("￥"+(income-pay));

        //  如果今日支出大于0 设置今日支出
        if(payToday>0){
            mTvPayToday.setText("￥"+payToday);
            mTvPayToday.setTextColor(Color.parseColor("#F4606A"));
        }else if(payToday==0){
            mTvPayToday.setText("暂无支出");
            mTvPayToday.setTextColor(Color.parseColor("#999999"));
        }
        //  如果今日收入大于0 设置今日收入
        if(incomeToday>0){
            mTvIncomeToday.setText("￥"+incomeToday);
            mTvIncomeToday.setTextColor(Color.parseColor("#F4606A"));
        }else if(incomeToday==0){
            mTvIncomeToday.setText("暂无收入");
            mTvIncomeToday.setTextColor(Color.parseColor("#999999"));
        }
    }

    private void setListener() {

        //  记一笔按钮监听事件
        mBtnRecord.setOnClickListener(this);

        mRlPay.setOnClickListener(this);
        mRlIncome.setOnClickListener(this);
        mRlInfo.setOnClickListener(this);
    }

    private void initView(View mView) {
        mTvIncome = (TextView) mView.findViewById(R.id.tv_income);
        mTvPay = (TextView) mView.findViewById(R.id.tv_pay);
        mTvChazhi = (TextView) mView.findViewById(R.id.tv_chazhi);
        mLlAccount = (LinearLayout) mView.findViewById(R.id.ll_account);
        mIvPayToday = (ImageView) mView.findViewById(R.id.iv_pay_today);
        mRlPay = (RelativeLayout) mView.findViewById(R.id.rl_pay);
        mIvGainToday = (ImageView) mView.findViewById(R.id.iv_gain_today);
        mRlIncome = (RelativeLayout) mView.findViewById(R.id.rl_income);
        mIvNews = (ImageView) mView.findViewById(R.id.iv_news);
        mRlInfo = (RelativeLayout) mView.findViewById(R.id.rl_info);
        mIvNew = (ImageView) mView.findViewById(R.id.iv_new);
        mBtnRecord = (Button) mView.findViewById(R.id.btn_record);
        mTvIncomeToday= (TextView) mView.findViewById(R.id.tv_income_today);
        mTvPayToday= (TextView) mView.findViewById(R.id.tv_pay_today);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_record:   //  记一笔按钮
                if(SharedPreferencesUtils.getLoginStatus(getContext())){    //如果登陆了则跳转到记一笔
                    RecordActivity.start(getContext());
                }else {     //  没有登陆则跳转到登陆页面
                    LoginActivity.start(getContext());
                }
                break;
            case R.id.rl_pay:   //  今日支出
                PayDetailsActivity.start(getContext(),1);  //  跳转到指出明细
                break;
            case R.id.rl_income:    //  今日收入
                //IncomActivity.start(getContext());
                PayDetailsActivity.start(getContext(),2);  //  跳转到收入明细
                break;
            case R.id.rl_info:  //  理财咨询
                InfoActivity.start(getContext());   //跳转到理财咨询
                break;
        }
    }

    /**
     *  RecordActivity 页面保存数据成功并通过EventBus发送Post后执行该方法
     * @param save
     */
    @Subscribe
    public void saveAccountSuccess(RecordActivity.SaveAccountSuccess save){
        //  调用该方法重新查询数据库刷新页面
        setData();
    }

    /**
     * 接收到EventBus发送的登陆成功的消息后调用这个方法
     * @param msgEvent
     */
    @Subscribe
    public void loginSuccess(MsgEvent msgEvent){

        //  调用该方法重新查询数据库刷新页面
        setData();
    }

}

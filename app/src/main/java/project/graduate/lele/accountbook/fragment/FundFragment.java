package project.graduate.lele.accountbook.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.activity.AddPayWayActivity;
import project.graduate.lele.accountbook.activity.FundActivity;
import project.graduate.lele.accountbook.adapter.MyPayAdapter;
import project.graduate.lele.accountbook.bean.AccountBean;
import project.graduate.lele.accountbook.bean.PayBean;
import project.graduate.lele.accountbook.utils.DateUtils;
import project.graduate.lele.accountbook.utils.SharedPreferencesUtils;
import project.graduate.lele.accountbook.utils.UserTools;

/**
 * Created by zhpan on 2017/1/8.
 * 资金
 */

public class FundFragment extends Fragment implements View.OnClickListener{
    private View mView;
    private TextView mTvTitle;
    private ImageView mIvRight;
    //private ImageView mIvLeft;
    private ListView mListView;
    private TextView mTvYuE;
    private ImageView mIvEye;
    private List<PayBean> mList;
    private MyPayAdapter mAdapter;
    private boolean isOpen;
    private double pay;         //  本月收入
    private double income;      //  本月指出
    private double yuE;         //  结余

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_fund,null);

        initView();
        initData();
        setData();
        setListener();
        return mView;
    }

    private void initData() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void setListener() {
        mIvEye.setOnClickListener(this);
        mIvRight.setOnClickListener(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyPayAdapter adapter = (MyPayAdapter) parent.getAdapter();
                List<PayBean> payBeen = adapter.getmList();
                FundActivity.start(getContext(),payBeen.get(position));
            }
        });
    }

    private void setData() {
        mTvTitle.setText("我的资金");
        mTvTitle.setTextColor(Color.parseColor("#FFFFFF"));
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setImageResource(R.drawable.btn_add_account);
        //mIvLeft.setVisibility(View.GONE);

        //  从数据库中查询所有支出的数据集合（根据用户名、月份、和收支类别[支出]）
        List<AccountBean> accountPayBeen = DataSupport.where("accountClass=1 and username=? and month=?", UserTools.getUsername(getContext()), DateUtils.getMonth()).find(AccountBean.class);
        //  从数据库中查询所有收入的数据集合（根据用户名、月份、和收支类别[收入]）
        List<AccountBean> accountIncomeBeen = DataSupport.where("accountClass=2 and username=? and month=?",UserTools.getUsername(getContext()),DateUtils.getMonth()).find(AccountBean.class);

        //  遍历支出集合 得到本月支出总和
        for(int i=0;i<accountPayBeen.size();i++){
            pay+=accountPayBeen.get(i).getAccount();
        }
        //  遍历收入集合 得到本月收入总和
        for(int i=0;i<accountIncomeBeen.size();i++){
            income+=accountIncomeBeen.get(i).getAccount();
        }

         yuE=income-pay;

        isOpen = SharedPreferencesUtils.isOpen(getContext());

        if(isOpen){
            mIvEye.setImageResource(R.drawable.eye_open);
            mTvYuE.setText("￥"+yuE+"");
        }else {
            mIvEye.setImageResource(R.drawable.eye_closed);
            mTvYuE.setText("*****");
        }

        //  2.给集合填充数据
        mList=DataSupport.findAll(PayBean.class);

        //  3.创建Adapter
        mAdapter=new MyPayAdapter(getContext());

        //  4.把集合的数据传递到Adapter
        mAdapter.setmList(mList);

        //  5.给ListView设置Adapter
        mListView.setAdapter(mAdapter);

    }

    private void initView() {
        mTvTitle= (TextView) mView.findViewById(R.id.tv_title);
        mIvRight= (ImageView) mView.findViewById(R.id.iv_right);
        //mIvLeft= (ImageView) mView.findViewById(R.id.iv_left);

        //  1.先获取ListView控件
        mListView= (ListView) mView.findViewById(R.id.lv_my_pay);
        mTvYuE= (TextView) mView.findViewById(R.id.tv_account);
        mIvEye= (ImageView) mView.findViewById(R.id.iv_eye);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_eye:
                boolean open = SharedPreferencesUtils.isOpen(getContext());
                if(open){
                    mIvEye.setImageResource(R.drawable.eye_closed);
                    SharedPreferencesUtils.setISOpen(getContext(),false);
                    mTvYuE.setText("*****");
                }else {
                    mIvEye.setImageResource(R.drawable.eye_open);
                    SharedPreferencesUtils.setISOpen(getContext(),true);
                    mTvYuE.setText(yuE+"");
                }

                break;
            case R.id.iv_right:
                Intent intent=new Intent(getContext(), AddPayWayActivity.class);
                startActivity(intent);

                break;
        }
    }
    @Subscribe
    public void addWaySuccess( AddPayWayActivity.AddWaySuccess addWaySuccess){
        mList=DataSupport.findAll(PayBean.class);
        mAdapter.setmList(mList);
        mAdapter.notifyDataSetChanged();
    }


}

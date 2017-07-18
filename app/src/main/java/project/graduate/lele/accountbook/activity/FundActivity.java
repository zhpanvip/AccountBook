package project.graduate.lele.accountbook.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import org.litepal.crud.DataSupport;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.adapter.PayDetailAdapter;
import project.graduate.lele.accountbook.bean.AccountBean;
import project.graduate.lele.accountbook.bean.PayBean;
import project.graduate.lele.accountbook.utils.UserTools;

/**
 *  支付方式详情页面
 */
public class FundActivity extends BaseActivity {

    private TextView mTvPay;
    private LinearLayout mLlPay;
    private TextView mTvIncome;
    private LinearLayout mLlIncome;
    private LinearLayout mLlFund;
    private ListView mLvFund;
    private PayDetailAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund);

        initView();
        setData();
    }

    private void setData() {
        //  获取资金页面传递来的数据
        Intent intent = getIntent();
        PayBean payBean = (PayBean) intent.getSerializableExtra("payBean");
        tvTitle.setText(payBean.getWay());
        tvTitle.setTextColor(Color.parseColor("#FFFFFF"));
        int color = Color.parseColor(payBean.getBackground());
        //  设置背景色
        mRlTitleBar.setBackgroundColor(color);
        mLlFund.setBackgroundColor(color);
        mLlIncome.setBackgroundColor(color);
        mLlPay.setBackgroundColor(color);
        //  给状态栏设置背景色
        setStatusBarColor(this,color);
       // List<AccountGainClassBean> accountGainClassBeen = DataSupport.findAll(AccountGainClassBean.class);
        //  从数据查询用户某个支付方式的收入数据
        List<AccountBean> accountIncomeBeen = DataSupport
                .where("accountClass=2 and payWay=? and username=?",payBean.getWay(), UserTools.getUsername(this))
                .find(AccountBean.class);

        //  从数据查询用户某个收入方式的收入数据
        List<AccountBean> accountPayBeen = DataSupport
                .where("accountClass=1 and payWay=? and username=?",payBean.getWay(),UserTools.getUsername(this))
                .find(AccountBean.class);

        double income=0;    //  累计支出
        double pay=0;       //  累计收入
        //  遍历支出集合计算出累计支出
        for(int i=0; i<accountIncomeBeen.size();i++){
            income+=accountIncomeBeen.get(i).getAccount();
        }
        //  遍历收入集合计算出累计收入
        for(int i=0;i<accountPayBeen.size();i++){
            pay+=accountPayBeen.get(i).getAccount();
        }

        mTvIncome.setText("￥"+income);
        mTvPay.setText("￥"+pay);

        //  1.实例化ListView的适配器
        mAdapter=new PayDetailAdapter(this);
        //  2.查询数据库获取用户该支付方式的所有数据得到List集合
        List<AccountBean> accountBeen = DataSupport
                .where("payWay=? and username=?", payBean.getWay(),UserTools.getUsername(this))
                .find(AccountBean.class);
        //  3.将集合数据设置到Adapter
        mAdapter.setmList(accountBeen);
        //  4.给ListView适配Adapter
        mLvFund.setAdapter(mAdapter);
    }

    public static void start(Context context, PayBean payBean) {
        Intent intent = new Intent(context, FundActivity.class);
        intent.putExtra("payBean", payBean);
        context.startActivity(intent);
    }

    private void initView() {
        mTvPay = (TextView) findViewById(R.id.tv_pay);
        mLlPay = (LinearLayout) findViewById(R.id.ll_pay);
        mTvIncome = (TextView) findViewById(R.id.tv_income);
        mLlIncome = (LinearLayout) findViewById(R.id.ll_income);
        mLlFund = (LinearLayout) findViewById(R.id.ll_fund);
        mLvFund = (ListView) findViewById(R.id.lv_fund);
    }
}

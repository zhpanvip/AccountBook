package project.graduate.lele.accountbook.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.fragment.AccountFragment;
import project.graduate.lele.accountbook.fragment.ChartFragment;
import project.graduate.lele.accountbook.fragment.FundFragment;
import project.graduate.lele.accountbook.fragment.MoreFragment;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    private RadioGroup mRadioGroup; //  底部导航栏的四个按钮的RadioGroup
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private long mExitTime; //  第一次按返回键的时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(this,true);
        setContentView(R.layout.activity_main);

        initView();
        setData();
        setListener();
    }

    private void setListener() {
        //  给RadioGroup设置选择改变的监听事件 用来切换页面
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction;
                switch (checkedId) {
                    case R.id.rb_tab1:  //  点击记账时切换到记账页面AccountFragment
                        //  开启事务
                        transaction = mFragmentManager.beginTransaction();
                        //  调用replace方法将当前的Fragment替换成记账的Fragment
                        transaction.replace(R.id.rl_tab, new AccountFragment());
                        //  提交事务
                        transaction.commit();
                        break;
                    case R.id.rb_tab2:  //  点击报表时跳转到报表页面ChartFragment
                        transaction = mFragmentManager.beginTransaction();
                        //  调用replace方法将当前的Fragment替换成报表的Fragment
                        transaction.replace(R.id.rl_tab, new ChartFragment());
                        transaction.commit();
                        break;
                    case R.id.rb_tab3:  //  点击资金时切换到资金页面FundFragment
                        transaction = mFragmentManager.beginTransaction();
                        //  调用replace方法将当前的Fragment替换成资金的Fragment
                        transaction.replace(R.id.rl_tab, new FundFragment());
                        transaction.commit();
                        break;
                    case R.id.rb_tab4:  //  点击更多时切换更多页面MoreFragment
                        transaction = mFragmentManager.beginTransaction();
                        //  调用replace方法将当前的Fragment替换成更多的Fragment
                        transaction.replace(R.id.rl_tab, new MoreFragment());
                        transaction.commit();
                        break;
                }
            }
        });
    }

    private void setData() {
        //  开启事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        //  启动程序时默认加载记账页面AccountFragment
        transaction.replace(R.id.rl_tab, new AccountFragment());
        transaction.commit();
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_tab);
    }

    /**
     * 返回键的监听方法 实现按两次退出程序
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //  如果按得是返回键
            //  如果第二次按返回键和第一次俺的间隔小于2秒
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //  记录第一次按返回键的时间
                mExitTime = System.currentTimeMillis();
            } else {    //  如果两次间隔在2秒内则退出程序
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //  设置状态栏透明
    private void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }



}

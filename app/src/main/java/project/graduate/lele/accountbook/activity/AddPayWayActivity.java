package project.graduate.lele.accountbook.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import org.greenrobot.eventbus.EventBus;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.bean.PayBean;
import project.graduate.lele.accountbook.utils.EditTextCashUtil;

/**
 * 添加支付方式
 */
public class AddPayWayActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtWay;
    private EditText mEtYue;
    private Button mBtnSave;
    private LinearLayout mActivityAddPayWay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pay_way);
        initView();

        setData();
    }

    private void setData() {
        EditTextCashUtil.setPricePoint(mEtYue);
        tvTitle.setText("添加支付方式");
        tvTitle.setTextColor(Color.parseColor("#FFFFFF"));
    }

    private void initView() {
        mEtWay = (EditText) findViewById(R.id.et_way);
        mEtYue = (EditText) findViewById(R.id.et_yue);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mActivityAddPayWay = (LinearLayout) findViewById(R.id.activity_add_pay_way);

        mBtnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                save();
                break;
        }
    }

    private void save() {
        String way = mEtWay.getText().toString().trim();
        if (TextUtils.isEmpty(way)) {
            Toast.makeText(this, "输入支付方式", Toast.LENGTH_SHORT).show();
            return;
        }

        String yue = mEtYue.getText().toString().trim();
        //  构造支付方式实体类
        PayBean payBean = new PayBean();
        payBean.setWay(way);   //   支付方式名称
        payBean.setPicSrc(R.drawable.as_ico_add_auto_bind); //  图标
        payBean.setBackground("#5A98DE");   //  背景色
        if (TextUtils.isEmpty(yue)) {
            payBean.setBanlance(0);
        } else {
            payBean.setBanlance(Double.parseDouble(yue));
        }

        if (payBean.save()) { //  保存支付方式到数据库成功
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            //  保存支付方式到数据库成功 发送广播通知成员弹窗列表更新数据
            EventBus.getDefault().post(new AddWaySuccess());
            //EventBus.getDefault().post(new AddPopDataActivity.SavePopSuccess(flag));
            finish();
        } else {//  保存支付方式失败
            Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AddPayWayActivity.class);
        context.startActivity(intent);
    }

    public class AddWaySuccess {

    }
}

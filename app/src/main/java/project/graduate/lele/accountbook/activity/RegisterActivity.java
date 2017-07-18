package project.graduate.lele.accountbook.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.bean.UserBean;

/**
 * 注册Activity
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mIvLeft;
    private TextView tvTitle;
    private TextView tvRight;
    private ImageView ivRight;
    private RelativeLayout mRlTitleBar;
    private TextView mTvForgetPsw;
    private Button mBtnRegister;
    private EditText mEtNumber;
    private EditText mEtPassword;
    private EditText mEtConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setListener();
        setData();

    }

    private void setListener() {
        mIvLeft.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);

    }

    private void initView() {
        mIvLeft = (ImageView) findViewById(R.id.iv_left);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRight = (TextView) findViewById(R.id.tv_right);
        ivRight = (ImageView) findViewById(R.id.iv_right);
        mRlTitleBar = (RelativeLayout) findViewById(R.id.rl_title_bar);
        mTvForgetPsw = (TextView) findViewById(R.id.tv_forget_psw);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mEtNumber = (EditText) findViewById(R.id.et_number);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);
    }

    private void setData() {
        //mRlTitleBar.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        mRlTitleBar.getBackground().setAlpha(0);
        tvTitle.setText("用户注册");
        tvTitle.setTextColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.btn_register:
                submit();
                break;
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
    //  点击注册按钮的时候执行
    private void submit() {
        String username = mEtNumber.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        if(username.length()!=11){
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length()<6){
            Toast.makeText(this, "密码长度不能小于6位", Toast.LENGTH_SHORT).show();
            return;
        }

        String confirmPsw= mEtConfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(confirmPsw)) {
            Toast.makeText(this, "请确认密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(confirmPsw)){
            Toast.makeText(this, "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }

        //  创建用户实体类并写入数据库完成注册
        UserBean userBean=new UserBean();
        userBean.setUsername(username);
        userBean.setPassword(password);
        //  设置邮箱
        userBean.setEmail(username+"@henu.edu.com");
        //  设置头像链接
        userBean.setHeadPic("http://www.qqtu8.net/f/20120824130944.jpg");
        //  设置积分
        userBean.setGrade(19);

        if(userBean.save()){
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
        }
    }
}

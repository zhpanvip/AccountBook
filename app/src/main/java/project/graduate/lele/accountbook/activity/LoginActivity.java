package project.graduate.lele.accountbook.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.bean.UserBean;
import project.graduate.lele.accountbook.event.MsgEvent;
import project.graduate.lele.accountbook.fragment.MoreFragment;
import project.graduate.lele.accountbook.utils.SharedPreferencesUtils;

/**
 * 登录Activity
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mIvLeft;
    private TextView tvTitle;
    private TextView tvRight;
    private ImageView ivRight;
    private RelativeLayout mRlTitleBar;
    private TextView mTvForgetPsw;
    private Button mBtnRegister;
    private Button mBtnLogin;
    private EditText mEditTextNo;
    private EditText mEditTextPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        setData();
        setListener();

    }

    private void setListener() {
        mTvForgetPsw.setOnClickListener(this);
        mIvLeft.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

    private void initView() {
        mIvLeft= (ImageView) findViewById(R.id.iv_left);
        tvTitle= (TextView) findViewById(R.id.tv_title);
        tvRight= (TextView) findViewById(R.id.tv_right);
        ivRight= (ImageView) findViewById(R.id.iv_right);
        mRlTitleBar= (RelativeLayout) findViewById(R.id.rl_title_bar);
        mTvForgetPsw= (TextView) findViewById(R.id.tv_forget_psw);
        mBtnRegister= (Button) findViewById(R.id.btn_register);
        mEditTextNo= (EditText) findViewById(R.id.et_number);
        mEditTextPsw= (EditText) findViewById(R.id.et_password);
        mBtnLogin= (Button) findViewById(R.id.btn_login);
    }

    private void setData() {
        //  将TitleBar设置为透明 前两个字符为00时表示全透明 为ff时不透明
        mRlTitleBar.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        //mRlTitleBar.getBackground().setAlpha(0);
        tvTitle.setText("登陆");
        tvTitle.setTextColor(Color.parseColor("#FFFFFF"));
    }

    public static void  start(Context context){
        Intent intent=new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_forget_psw:

                break;
            case R.id.iv_left:  //  返回
                finish();
                break;
            case R.id.btn_register: //跳转到注册
                RegisterActivity.start(this);

                break;
            case R.id.btn_login:   //   登陆
                login();
                break;
        }

    }
    //  登陆
    private void login() {
        //  获取EditText中输入的用户名和密码
        String username = mEditTextNo.getText().toString().trim();
        String password = mEditTextPsw.getText().toString().trim();

        if(TextUtils.isEmpty(username)){    //  判断用户名是否为空
            Toast.makeText(this, "账号不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        if(username.length()!=11){  //  验证手机号是否合法
            Toast.makeText(this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){    //  验证密码是否为空
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        //  实例化用户对象
        UserBean userBean=new UserBean();
        //  将密码和用户名设置到用户对象
        userBean.setPassword(password);
        userBean.setUsername(username);

        //  查询根据账号和密码数据库 看数据库是否能匹配到用户名和密码
        List<UserBean> userBeen = DataSupport
                .where("username=? and password=?", username, password)
                .find(UserBean.class);

        if(userBeen.size()>0){  //  如果集合的size大于0说明用户名密码正确 登陆成功
            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            //  登陆成功的状态保存到SharedPreferences中
            SharedPreferencesUtils.saveLoginStatus(this,true);
            //  将用户信息保存到SharedPreferences中 方便以后取用
            SharedPreferencesUtils.saveUserInfo(this,userBean);
            //  发送EventBus登陆成功的消息 相当于发送广播
            EventBus.getDefault().post(new MsgEvent(MoreFragment.LOGIN));
            finish();
        }else {
            Toast.makeText(this, "登陆失败，请检查用户名和密码是否正确", Toast.LENGTH_SHORT).show();
        }
    }




}

package project.graduate.lele.accountbook.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.activity.AboutUsActivity;
import project.graduate.lele.accountbook.activity.UserInfoActivity;
import project.graduate.lele.accountbook.activity.LoginActivity;
import project.graduate.lele.accountbook.bean.SignBean;
import project.graduate.lele.accountbook.bean.UserBean;
import project.graduate.lele.accountbook.event.MsgEvent;
import project.graduate.lele.accountbook.utils.DateUtils;
import project.graduate.lele.accountbook.utils.SharedPreferencesUtils;
import project.graduate.lele.accountbook.utils.UserTools;

/**
 * Created by lunlele on 2017/1/8.
 * 更多页面
 */

public class MoreFragment extends Fragment implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    private View mView;
    private LinearLayout mLlLogin;
    private LinearLayout mLlSign;
    private RelativeLayout mRlAccount;
    private RelativeLayout mRlShare;
    private RelativeLayout mRlAboutUs;
    private RelativeLayout mRlUpdate;
    private ToggleButton toggleButton;
    private TextView mTvVersion;
    private TextView mTvUserName;
    private TextView mTvLevel;
    private ImageView mIvHead;
    private TextView mTvSign;

    public static final int EXIT=-1;    //  未登录（退出状态）
    public static final int LOGIN=1;    //  已登陆（登陆状态）
    private boolean isSign; //  今天是否签到
    private int status; //登陆状态 1代表已登陆状态 2代表退出状态

    //  ImageLoader加载图片的配置
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_touxiang)    //   正在加载时显示改图片
            .showImageOnFail(R.drawable.ic_touxiang)       //   加载失败时显示改图片
            .cacheInMemory(true)    //  把图片缓存到内存
            .cacheOnDisk(true)      //  把图片缓存到磁盘
            .bitmapConfig(Bitmap.Config.RGB_565)    //  设置加载图片的格式
            .displayer(new RoundedBitmapDisplayer(150)) //  设置图片圆角 150超出图片的宽度故显示为圆形图片
            .build();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_more, null);

        initView();
        setListener();
        initData();
        setData();
        return mView;
    }

    private void initData() {
        //  注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //  注销EventBus
        EventBus.getDefault().unregister(this);
    }

    private void setData() {
        mTvVersion.setText("v"+getVersionName());
        if(SharedPreferencesUtils.getLoginStatus(getContext())){    //已登陆
            setUserHeadPic();   //  加载用户头像
            setUserInfo();      //  设置用户信息
        }

        if(SharedPreferencesUtils.getRemindStatus(getContext())){   //  记账提醒开启
            toggleButton.setChecked(true);  //把记账提醒的按钮设置为开启状态
        }else { //  记账提醒未开启
            toggleButton.setChecked(false); //把记账提醒的按钮设置为关闭状态
        }
        //  根据用户名和日期查询签到数据库
        List<SignBean> signBeen = DataSupport.
                where("username=? and signDate=?",UserTools.getUsername(getContext()), DateUtils.getDate())
                .find(SignBean.class);

        if(signBeen.size()>0){  //如果查到有签到信息说明该用户今天已经签到
            isSign=true;   //   签到状态设置为true
            mTvSign.setText("已签到"); //  设置已签到
        }else { //如果没有查到签到信息说明该用户今天已经签到
            isSign=false;   //   签到状态设置为false
            mTvSign.setText("签到");//  设置未签到
        }
    }

    private void setListener() {
        mLlLogin.setOnClickListener(this);
        mLlSign.setOnClickListener(this);
        mRlAccount.setOnClickListener(this);
        mRlAboutUs.setOnClickListener(this);
        mRlShare.setOnClickListener(this);
        mRlUpdate.setOnClickListener(this);
        //  记账提醒按钮的点击事件
        toggleButton.setOnCheckedChangeListener(this);
    }
    //  获取程序当前版本号
    private String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getContext().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getContext().getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    private void initView() {
        mLlLogin = (LinearLayout) mView.findViewById(R.id.ll_login);
        mLlSign = (LinearLayout) mView.findViewById(R.id.ll_sign);
        mRlAccount = (RelativeLayout) mView.findViewById(R.id.rl_account);
        mRlShare = (RelativeLayout) mView.findViewById(R.id.rl_share);
        mRlAboutUs = (RelativeLayout) mView.findViewById(R.id.rl_about_us);
        mRlUpdate = (RelativeLayout) mView.findViewById(R.id.rl_update);
        toggleButton= (ToggleButton) mView.findViewById(R.id.tb_remind);
        mTvVersion= (TextView) mView.findViewById(R.id.tv_version);
        mTvUserName= (TextView) mView.findViewById(R.id.tv_username);
        mTvLevel= (TextView) mView.findViewById(R.id.tv_level);
        mIvHead= (ImageView) mView.findViewById(R.id.iv_head_pic);
        mTvSign= (TextView) mView.findViewById(R.id.tv_sign);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_login: //  用户头像部分点击事件
                if(SharedPreferencesUtils.getLoginStatus(getContext())){    //  判断是否登陆
                    UserInfoActivity.start(getContext());   //  如果已登陆则跳转到个人中心
                }else {
                    LoginActivity.start(getContext());  //  未登录则跳转到登陆页面
                }
                break;
            case R.id.ll_sign:  //  签到点击事件
                //  判断是否登陆  登陆了才可以签到
                if(SharedPreferencesUtils.getLoginStatus(getContext())){     //  已经登陆
                    //  是否签过到
                    if(isSign){  //  已签到
                        Toast.makeText(getContext(), "今天已经签过到了", Toast.LENGTH_SHORT).show();
                    }else { //  没有签到
                        //  签到
                        sign();
                    }
                }else { //  没有登陆
                    Toast.makeText(getContext(), "您还没有登陆，登陆后才能签到", Toast.LENGTH_SHORT).show();
                }
                //SignActivity.start(getContext());
                break;
            case R.id.rl_account:
                if(SharedPreferencesUtils.getLoginStatus(getContext())){
                    UserInfoActivity.start(getContext());
                }else {
                    Toast.makeText(getContext(), "您还没有登陆!", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.rl_share:
                //ShareActivity.start(getContext());
                Toast.makeText(getContext(), "敬请期待", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rl_about_us:
                AboutUsActivity.start(getContext());
                break;

            case R.id.rl_update:
                //UpdateActivity.start(getContext());
                Toast.makeText(getContext(), "已经是最新版本", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    //  签到并写入数据库
    private void sign() {
        //  获取用户签到积分
        int grade = UserTools.getGrade(getContext());

        //  签到积分加1
        grade++;
        //  查询数据库得到当前登陆用户的实体类并修改用户积分
        UserBean userBean = UserTools.getUserBean(getContext());
        userBean.setGrade(grade);

        //  将修改后的用户信息保存到数据库
        if(userBean.save()){    //  保存成功则签到成功
            Toast.makeText(getContext(), "签到成功", Toast.LENGTH_SHORT).show();
            mTvSign.setText("已签到");

            SignBean signBean=new SignBean();
            signBean.setSign(true);
            signBean.setSignDate(DateUtils.getDate());
            signBean.setUsername(UserTools.getUsername(getContext()));
            signBean.save();
        }else { //  保存失败 签到失败
            Toast.makeText(getContext(), "签到失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 记账提醒按钮的监听方法
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.tb_remind:
                //  将是否提醒保存到SharedPreference
                SharedPreferencesUtils.saveRemindStatus(getContext(),isChecked);
                break;
        }
    }

    /**
     * 接收到EventBus发送的登陆/退出成功的消息后调用这个方法
     * @param msgEvent
     */
    @Subscribe
    public void loginSuccess(MsgEvent msgEvent){
        status = msgEvent.status;
        if(status==LOGIN){   //登陆成功 更新用户头像和信息
            setUserInfo();
            setUserHeadPic();
            mTvLevel.setVisibility(View.VISIBLE);   //  显示用户等级
        }else if(status==EXIT){     //  退出成功 将头像和信息设置为默认值
            mIvHead.setImageResource(R.drawable.ic_touxiang);
            mTvUserName.setText("点击登陆");
            mTvSign.setText("签到");
            mTvLevel.setVisibility(View.GONE);  //  隐藏用户等级
        }

    }
    //  设置用户头像
    private void setUserHeadPic() {
        //  获取头像链接地址
        String imageUrl=UserTools.getHeadPic(getContext());
        //  调用ImageLoader下载图片并给mIvHead显示
        ImageLoader.getInstance().displayImage(imageUrl, mIvHead, options);
    }

    private void setUserInfo(){
        //  设置用户名
        mTvUserName.setText(UserTools.getUsername(getContext()));
        //  设置已签到
        if(isSign){
            mTvSign.setText("已签到");
        }
        //  通过工具类获取用户签到积分
        int grade = UserTools.getGrade(getContext());
        String levelStr;
        //  根据签到积分换算成相应的等级
        switch (grade/10){  // 0-10分
            case 0:
                levelStr="等级：种子";
                break;
            case 1:
                levelStr="等级：树苗";   //10-20分
                break;
            case 2:
                levelStr="等级：大树";   //20-30分
                break;
            default:
                levelStr="等级：大树";   //大于等于30分
                break;
        }

        //  设置等级
        mTvLevel.setVisibility(View.VISIBLE);
        mTvLevel.setText(levelStr);
    }
}

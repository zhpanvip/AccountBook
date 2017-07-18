package project.graduate.lele.accountbook.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import org.greenrobot.eventbus.EventBus;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.event.MsgEvent;
import project.graduate.lele.accountbook.fragment.MoreFragment;
import project.graduate.lele.accountbook.utils.SharedPreferencesUtils;
import project.graduate.lele.accountbook.utils.UserTools;

/**
 *  个人中心
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvAccountRight;
    private ImageView mIvEmailRight;
    private ImageView mIvPswRight;
    private LinearLayout mActivityAccount;
    private TextView mTvTel;
    private TextView mTvEmail;
    private Button btn_exit;
    private ImageView mIvHeadPic;

    //  显示图片的配置参数
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_touxiang)
            .showImageOnFail(R.drawable.ic_touxiang)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .displayer(new RoundedBitmapDisplayer(90))
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initView();
        setData();
    }

    private void setData() {
        mIvLeft.setImageResource(R.drawable.back_black);
        tvTitle.setText("个人中心");
        loadHeadPic();

        mTvTel.setText(UserTools.getUsername(this));
        mTvEmail.setText(UserTools.getEmail(this));

    }
    public static void start(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        mIvAccountRight = (ImageView) findViewById(R.id.iv_account_right);
        mIvEmailRight = (ImageView) findViewById(R.id.iv_email_right);
        mIvPswRight = (ImageView) findViewById(R.id.iv_psw_right);
        mActivityAccount = (LinearLayout) findViewById(R.id.activity_account);
        mTvTel = (TextView) findViewById(R.id.tv_tel);
        mTvTel.setOnClickListener(this);
        mTvEmail = (TextView) findViewById(R.id.tv_email);
        mTvEmail.setOnClickListener(this);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(this);
        mIvHeadPic= (ImageView) findViewById(R.id.iv_head_pic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                EventBus.getDefault().post(new MsgEvent(MoreFragment.EXIT));
                //  清空缓存数据
                SharedPreferencesUtils.clearData(this);

                SharedPreferencesUtils.saveLoginStatus(this,false);

                finish();
                break;
        }
    }
    //  载入头像
    public void loadHeadPic(){
        String imageUrl= UserTools.getHeadPic(this);
        ImageLoader.getInstance().displayImage(imageUrl, mIvHeadPic, options);
    }
}

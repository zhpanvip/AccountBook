package project.graduate.lele.accountbook.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import project.graduate.lele.accountbook.R;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setData();
    }

    private void setData() {
        //  设置TitleBar背景颜色
        mRlTitleBar.setBackgroundColor(Color.parseColor("#ffffff"));
        tvTitle.setText("关于我们");
        mIvLeft.setImageResource(R.drawable.back_black);
    }

    public static void  start(Context context){
        Intent intent=new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }
}

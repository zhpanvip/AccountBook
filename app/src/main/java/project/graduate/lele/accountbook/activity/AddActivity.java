package project.graduate.lele.accountbook.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import org.greenrobot.eventbus.EventBus;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.adapter.GvClassAdapter;
import project.graduate.lele.accountbook.bean.AccountClassBean;
import project.graduate.lele.accountbook.bean.AccountGainClassBean;

/**
 * 添加自定义记账种类页面
 */
public class AddActivity extends BaseActivity implements View.OnClickListener {
    private GridView mGridView;
    private int picSrc=R.drawable.x1;
    private ImageView mIvPic;
    private EditText mEditText;
    private int accountClass;
    private AccountClassBean mAccountClassBean;
    private AccountGainClassBean mAccountGainClassBean;
    private int[] picSrcs = {R.drawable.x1, R.drawable.x2, R.drawable.x3, R.drawable.x4, R.drawable.x5,
            R.drawable.x6, R.drawable.x7, R.drawable.x8, R.drawable.x9, R.drawable.x10, R.drawable.x11, R.drawable.x12, R.drawable.x13,
            R.drawable.x14, R.drawable.x15, R.drawable.x16, R.drawable.x17, R.drawable.x18, R.drawable.x19, R.drawable.x20, R.drawable.x21,
            R.drawable.x22, R.drawable.x23, R.drawable.x24, R.drawable.x25, R.drawable.x26, R.drawable.x27, R.drawable.x28, R.drawable.x29,
            R.drawable.x30, R.drawable.x31, R.drawable.x32, R.drawable.x33, R.drawable.x34, R.drawable.x35, R.drawable.x36,
            R.drawable.x37, R.drawable.x38, R.drawable.x39, R.drawable.x40,
    };
    private GvClassAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initView();
        setData();
        setListener();
    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.gv_add_class);
        mIvPic = (ImageView) findViewById(R.id.iv_pic);
        mEditText = (EditText) findViewById(R.id.et_class);
    }

    private void setData() {
        mIvLeft.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        accountClass = intent.getIntExtra("accountClass",0);
        if (accountClass==1) {
            tvTitle.setText("新增支出类别");
        } else if (accountClass==2) {
            tvTitle.setText("新增收入类别");
        }
        tvTitle.setTextColor(Color.parseColor("#FFFFFF"));

        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("完成");
        tvRight.setTextColor(Color.parseColor("#FFFFFF"));

        mAccountClassBean = new AccountClassBean();
        mAccountGainClassBean = new AccountGainClassBean();

        adapter = new GvClassAdapter(this);
        adapter.setPicSrcs(picSrcs);
        mGridView.setAdapter(adapter);
    }

    private void setListener() {
        tvRight.setOnClickListener(this);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GvClassAdapter adapter = (GvClassAdapter) parent.getAdapter();
                int[] picSrcs = adapter.getPicSrcs();
                picSrc = picSrcs[position];
                mIvPic.setImageResource(picSrc);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                String customClass = mEditText.getText().toString().trim();
                if (TextUtils.isEmpty(customClass)) {
                    Toast.makeText(this, "请输入类别", Toast.LENGTH_SHORT).show();
                } else {
                    if(accountClass==2){
                        mAccountGainClassBean.setPicId(picSrc);
                        mAccountGainClassBean.setGainName(customClass);
                        mAccountGainClassBean.setBackground("#9DE7EB");

                        if(mAccountGainClassBean.save()){
                            finish();
                            saveSuccess();
                        }else {
                            Toast.makeText(this, "数据保存失败", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }else if(accountClass==1){
                        mAccountClassBean.setPicId(picSrc);
                        mAccountClassBean.setPayName(customClass);
                        mAccountClassBean.setBackground("#FF836A");

                        if( mAccountClassBean.save()){
                            finish();
                            saveSuccess();
                        }else {
                            Toast.makeText(this, "数据保存失败", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
                break;
        }
    }

    private void saveSuccess(){
        EventBus.getDefault().post(new SaveCustomSuccess(accountClass));
    }

    public class SaveCustomSuccess{
        public  int accountClass;

        public SaveCustomSuccess(int accountClass) {
            this.accountClass = accountClass;
        }
    }
}

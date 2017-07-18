package project.graduate.lele.accountbook.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.greenrobot.eventbus.EventBus;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.bean.MemberBean;

/**
 * 添加成员
 */
public class AddPopDataActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEditText;
    //private int flag;
    //private PayBean payBean;
    private MemberBean memberBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pop_data);

        initView();
        setData();
        setListener();
    }

    private void setListener(){
        tvRight.setOnClickListener(this);
    }

    private void initView(){
        mEditText= (EditText) findViewById(R.id.et_add);
    }

    private void setData() {

        //Intent intent = getIntent();
        //flag = intent.getIntExtra("flag",0);

        mIvLeft.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("确定");
        tvRight.setTextColor(Color.parseColor("#FFFFFF"));
        tvTitle.setText("新增成员");
        mEditText.setHint("请输入成员名称");
        //payBean=new PayBean();
        memberBean=new MemberBean();
        /*if(flag==RecordActivity.WAY){
            tvTitle.setText("新增支付方式");
            mEditText.setHint("请输入支付方式");
        }else if(flag==RecordActivity.MEMBER){
            tvTitle.setText("新增成员");
            mEditText.setHint("请输入成员名称");
        }*/


    }

    public static void start(Context context){
        Intent intent=new Intent(context,AddPopDataActivity.class);
        //intent.putExtra("flag",flag);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_right: //  保存按钮
                submit();
                break;
        }
    }




    private void submit(){
        //  获取输入框内容
        String name = mEditText.getText().toString().trim();
        if(TextUtils.isEmpty(name)){    //  判断输入框是否为空
            Toast.makeText(this, "请输入名称", Toast.LENGTH_SHORT).show();
        }else { //  添加成员
            memberBean.setMember(name);
            if(memberBean.save()){
                //  保存成员到数据库成功 发送广播通知成员弹窗列表更新数据
                EventBus.getDefault().post(new SavePopSuccess());
                finish();
            }else {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
                finish();
            }
            //  添加支付方式该换到了AddPayWayActivity
            /*if(flag==RecordActivity.WAY){
                payBean.setWay(name);

                if(payBean.save()){
                    EventBus.getDefault().post(new SavePopSuccess(flag));
                    finish();
                }else {
                    Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }else if(flag==RecordActivity.MEMBER){
                memberBean.setMember(name);
                if(memberBean.save()){
                    EventBus.getDefault().post(new SavePopSuccess(flag));
                    finish();
                }else {
                    Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }*/
        }
    }

    public class SavePopSuccess{
        /*public int flag;

        public SavePopSuccess(int flag) {
            this.flag = flag;
        }*/
    }

}

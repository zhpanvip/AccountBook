package project.graduate.lele.accountbook.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.adapter.PayDetailAdapter;
import project.graduate.lele.accountbook.bean.AccountBean;
import project.graduate.lele.accountbook.utils.UserTools;

/**
 * 收支明细
 */
public class PayDetailsActivity extends BaseActivity {
    private ListView mListView;
    private PayDetailAdapter mAdapter;
    private List<AccountBean> mList;
    private TextView mTvNoData;
    private Intent intent;
    private int  flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        initData();
        setListener();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setListener(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PayDetailAdapter adapter = (PayDetailAdapter) parent.getAdapter();
                List<AccountBean> accountBeen = adapter.getmList();
                AccountBean accountBean = accountBeen.get(position);
                RecordActivity.start(PayDetailsActivity.this,accountBean);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PayDetailAdapter adapter = (PayDetailAdapter) parent.getAdapter();
                List<AccountBean> accountBeen = adapter.getmList();
                AccountBean accountBean = accountBeen.get(position);
                showAlertDialog(accountBean);
                return false;
            }
        });
    }

    //  弹出对话框
    private void showAlertDialog(final AccountBean accountBean) {
        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要删除该条目？");

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem(accountBean);
            }
        });
        builder.show();
    }

    private void deleteItem(AccountBean accountBean) {
        int delete = DataSupport.delete(AccountBean.class, accountBean.getId());
        if(delete>0){
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(new RecordActivity.SaveAccountSuccess());
        }else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
        getData();
        mAdapter.setmList(mList);
        mAdapter.notifyDataSetChanged();

    }


    private void initData() {



        EventBus.getDefault().register(this);
        intent = getIntent();
         flag = intent.getIntExtra("flag", 0);

        getData();


        tvTitle.setTextColor(Color.parseColor("#FFFFFF"));


        if(mList.size()==0){
            mTvNoData.setVisibility(View.VISIBLE);
        }else {
            mTvNoData.setVisibility(View.GONE);
        }
        mAdapter=new PayDetailAdapter(this);
        mAdapter.setmList(mList);
        mListView.setAdapter(mAdapter);
    }

    private void initView(){
        mListView= (ListView) findViewById(R.id.lv_pay);
        mTvNoData= (TextView) findViewById(R.id.tv_no_data);
    }
    public static void  start(Context context,int flag){
        Intent intent=new Intent(context, PayDetailsActivity.class);
        intent.putExtra("flag",flag);
        context.startActivity(intent);
    }

    public static void  start(Context context,int flag,String payName){
        Intent intent=new Intent(context, PayDetailsActivity.class);
        intent.putExtra("flag",flag);
        intent.putExtra("payName",payName);
        context.startActivity(intent);
    }

    public void getData() {
        if(flag==1){
            //  从数据库中查询所有支出的数据集合
            mList = DataSupport.where("accountClass=1 and username=?", UserTools.getUsername(this)).find(AccountBean.class);
            tvTitle.setText("支出明细");
        }else if(flag==2){
            //  从数据库中查询所有收入的数据集合
            mList = DataSupport.where("accountClass=2 and username=?", UserTools.getUsername(this)).find(AccountBean.class);
            tvTitle.setText("收入明细");
        }else if(flag==3){
            String payName = intent.getStringExtra("payName");
            tvTitle.setText(payName);
            mList = DataSupport.where("username=? and payClass=?", UserTools.getUsername(this),payName).find(AccountBean.class);
        }
    }

    /**
     *  RecordActivity 页面保存数据成功并通过EventBus发送Post后执行该方法
     * @param save
     */
    @Subscribe
    public void saveAccountSuccess(RecordActivity.SaveAccountSuccess save){
        //  调用该方法重新查询数据库刷新页面
        getData();
        mAdapter.setmList(mList);
        mAdapter.notifyDataSetChanged();
    }


}

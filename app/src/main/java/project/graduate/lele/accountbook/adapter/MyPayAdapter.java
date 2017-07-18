package project.graduate.lele.accountbook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.bean.PayBean;

/**
 * Created by zhpan on 2017/1/14.
 * 资金页面ListView的适配器
 */
public class MyPayAdapter extends BaseAdapter {
    private Context mContext;
    private List<PayBean> mList;

    public List<PayBean> getmList() {
        return mList;
    }

    public void setmList(List<PayBean> mList) {
        this.mList = mList;
    }

    public MyPayAdapter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 返回的是列表的条目数
     * @return
     */
    @Override
    public int getCount() {
        return mList.size();
    }

    /**
     * 数据填充在此方法中完成（每个实体类对应一个条目，实体类的数据填充到条目中）
     * @param position  条目的位置
     * @param convertView   关联条目布局的view
     * @param parent
     * @return  关联条目布局的view
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyPayViewHolder holder;
        if(convertView==null){
            holder=new MyPayViewHolder();
            convertView=View.inflate(mContext, R.layout.item_my_pay,null);
            holder.imageView= (ImageView) convertView.findViewById(R.id.iv_logo);
            holder.mTvName= (TextView) convertView.findViewById(R.id.tv_name);
            holder.mTvAccount= (TextView) convertView.findViewById(R.id.tv_account);
            holder.mTvEDu= (TextView) convertView.findViewById(R.id.tv_e_du);

            convertView.setTag(holder);
        }else {
            holder= (MyPayViewHolder) convertView.getTag();
        }

        PayBean payBean = mList.get(position);

        holder.mTvAccount.setText(payBean.getBanlance()+"");
        holder.imageView.setImageResource(payBean.getPicSrc());
        holder.mTvName.setText(payBean.getWay());

        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FundActivity.start(mContext);
            }
        });*/

        return convertView;
    }
    //  ViewHolder中的每个成员变量对应条目布局中的一个view
    static class MyPayViewHolder{
        private ImageView imageView;
        private TextView mTvName;
        private TextView mTvAccount;
        private TextView mTvEDu;
    }




    /************************************************************************************************/
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}

package project.graduate.lele.accountbook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.bean.TotalPayBean;

/**
 * Created by zhpan on 2017/1/19.
 * 报表页面图ViewPager中的Fragment中的ListView的适配器
 */

public class PayListAdapter extends BaseAdapter {
    List<TotalPayBean> mList;
    private Context mContext;

    public PayListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<TotalPayBean> getmList() {
        return mList;
    }

    public void setmList(List<TotalPayBean> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.item_pay_list,null);

            holder.mImageView= (ImageView) convertView.findViewById(R.id.iv_pay);
            holder.mTvType= (TextView) convertView.findViewById(R.id.tv_type);
            holder.mTvPercent= (TextView) convertView.findViewById(R.id.tv_percent);
            holder.mTvAccount= (TextView) convertView.findViewById(R.id.tv_account);

            convertView.setTag(holder);
        }else {
          holder= (ViewHolder) convertView.getTag();
        }
        TotalPayBean totalPayBean = mList.get(position);
        holder.mImageView.setImageResource(totalPayBean.getPicSrc());
        holder.mTvType.setText(totalPayBean.getName());
        DecimalFormat format = new DecimalFormat("#.00");
        String percent = format.format((totalPayBean.getPercent()*100));
        holder.mTvPercent.setText(percent+"%");
        holder.mTvAccount.setText(totalPayBean.getTotal()+"");

        return convertView;
    }

    static class ViewHolder{
        private ImageView mImageView;
        private TextView mTvType;
        private TextView mTvPercent;
        private TextView mTvAccount;

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}

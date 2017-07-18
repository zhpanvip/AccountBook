package project.graduate.lele.accountbook.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.bean.AccountBean;

/**
 * Created by zhpan on 2017/2/16.
 *  资金详情页面ListView对应的适配器
 */

public class PayDetailAdapter extends BaseAdapter {
    private List<AccountBean> mList;
    private Context context;

    public List<AccountBean> getmList() {
        return mList;
    }

    public void setmList(List<AccountBean> mList) {
        this.mList = mList;
    }

    public PayDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.item_pay_list, null);
            holder=new ViewHolder(convertView);

            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        AccountBean accountBean = mList.get(position);
        holder.mIvPay.setImageResource(accountBean.getPicSrc());
        holder.mTvType.setText(accountBean.getPayClass());
        holder.mTvPercent.setText(accountBean.getDate());
        double accountClass = accountBean.getAccountClass();
        if(accountClass==1){
            holder.mTvAccount.setTextColor(Color.parseColor("#6AC239"));
            holder.mTvAccount.setText("-"+accountBean.getAccount());
        }else if(accountClass==2){
            holder.mTvAccount.setTextColor(Color.parseColor("#FC7A60"));
            holder.mTvAccount.setText(accountBean.getAccount()+"");
        }

        return convertView;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView mIvPay;
        public TextView mTvType;
        public TextView mTvPercent;
        public TextView mTvAccount;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mIvPay = (ImageView) rootView.findViewById(R.id.iv_pay);
            this.mTvType = (TextView) rootView.findViewById(R.id.tv_type);
            this.mTvPercent = (TextView) rootView.findViewById(R.id.tv_percent);
            this.mTvAccount = (TextView) rootView.findViewById(R.id.tv_account);
        }

    }
}

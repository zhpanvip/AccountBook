package project.graduate.lele.accountbook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.litepal.crud.DataSupport;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.activity.RecordActivity;
import project.graduate.lele.accountbook.bean.AccountClassBean;
import project.graduate.lele.accountbook.bean.AccountGainClassBean;

/**
 * Created by lunlele on 2017/1/30.
 * 记一笔页面记账类别的适配器
 */
public class GridViewAdapter extends BaseAdapter {
    private List<? extends DataSupport> mList;
    private Context context;
    private int flag;

    public GridViewAdapter(Context context) {
        this.context = context;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<? extends DataSupport> getmList() {
        return mList;
    }

    public void setmList(List<? extends DataSupport> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = View.inflate(context, R.layout.item_gridview, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        if(flag==RecordActivity.PAY){
            AccountClassBean accountClassBean = (AccountClassBean) mList.get(position);
            holder.mIvClass.setImageResource(accountClassBean.getPicId());
            holder.mTvClass.setText(accountClassBean.getPayName());
        }else if(flag==RecordActivity.INCOME) {
            AccountGainClassBean accountGainClassBean = (AccountGainClassBean) mList.get(position);
            holder.mIvClass.setImageResource(accountGainClassBean.getPicId());
            holder.mTvClass.setText(accountGainClassBean.getGainName());
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
        public ImageView mIvClass;
        public TextView mTvClass;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mIvClass = (ImageView) rootView.findViewById(R.id.iv_class);
            this.mTvClass = (TextView) rootView.findViewById(R.id.tv_class);
        }

    }
}

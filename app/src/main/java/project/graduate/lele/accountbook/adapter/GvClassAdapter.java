package project.graduate.lele.accountbook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import project.graduate.lele.accountbook.R;

/**
 * Created by lunlele on 2017/2/14.
 * 添加自定义类别页面的GridView的适配器
 */
public class GvClassAdapter extends BaseAdapter {
    private int[] picSrcs;
    private Context context;

    public GvClassAdapter(Context context) {
        this.context = context;
    }

    public int[] getPicSrcs() {
        return picSrcs;
    }

    public void setPicSrcs(int[] picSrcs) {
        this.picSrcs = picSrcs;
    }

    @Override
    public int getCount() {
        return picSrcs.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null){
            convertView = View.inflate(context, R.layout.item_gridview, null);
            holder=new ViewHolder(convertView);

            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        holder.mIvClass.setImageResource(picSrcs[position]);
        holder.mTvClass.setVisibility(View.GONE);

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

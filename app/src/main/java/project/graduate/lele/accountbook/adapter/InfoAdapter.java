package project.graduate.lele.accountbook.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.bean.InfoBean;

/**
 * Created by zhpan on 2017/2/17.
 * 资讯页面ListView的适配器
 */
public class InfoAdapter extends BaseAdapter {
    private List<InfoBean.DataBean.NormalArticlesBean> mList;

    private Context context;

    //  显示图片的配置
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.bbs_weibo_default)
            .showImageOnFail(R.drawable.bbs_weibo_default)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            //.displayer(new RoundedBitmapDisplayer(150))
            .build();

    public InfoAdapter(Context context) {
        this.context = context;
    }

    public List<InfoBean.DataBean.NormalArticlesBean> getmList() {
        return mList;
    }

    public void setmList(List<InfoBean.DataBean.NormalArticlesBean> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_info, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        //  得到文章实体类的对象
        InfoBean.DataBean.NormalArticlesBean normalArticlesBean = mList.get(position);
        //  给条目设置内容
        String sub_title = normalArticlesBean.getArticle().getSub_title();
        String title = normalArticlesBean.getArticle().getTitle();
        normalArticlesBean.getArticle();
        holder.mTvSubTitle.setText(sub_title);
        holder.mTvTitle.setText(title);

        String thumb_path = normalArticlesBean.getArticle().getThumb_path();
        List<String> thumb_images = normalArticlesBean.getArticle().getThumb_images();

        //  如果图片的集合大于0则条目有图片 为条目设置图片
        if(thumb_images.size()>0){
            String picUrl=thumb_path+thumb_images.get(0);
            //  让ImageView可见
             holder.mIvInfo.setVisibility(View.VISIBLE);
            //  用ImageLoader下载图片并设置到ImageView
            ImageLoader.getInstance().displayImage(picUrl, holder.mIvInfo, options);
        }else {//   图片集合为0
            //  隐藏ImageView
             holder.mIvInfo.setVisibility(View.GONE);
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
        public ImageView mIvInfo;
        public TextView mTvSubTitle;
        public TextView mTvTitle;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mIvInfo = (ImageView) rootView.findViewById(R.id.iv_info);
            this.mTvSubTitle = (TextView) rootView.findViewById(R.id.tv_title);
            this.mTvTitle = (TextView) rootView.findViewById(R.id.tv_content);
        }
    }
}

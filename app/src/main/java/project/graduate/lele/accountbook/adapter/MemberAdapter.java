package project.graduate.lele.accountbook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.litepal.crud.DataSupport;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.activity.RecordActivity;
import project.graduate.lele.accountbook.bean.MemberBean;
import project.graduate.lele.accountbook.bean.PayBean;

/**
 * Created by zhpan on 2017/2/1.
 * 记一笔弹窗页面ListView的适配器（成员支付方式公用）
 */
public class MemberAdapter extends BaseAdapter {
    private Context context;
    private List<? extends DataSupport> mList;
    private int flag;

    public int getFlag() {
        return flag;
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

    public MemberAdapter(Context context) {
        this.context=context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_member,null);
            holder=new ViewHolder();
            holder.mTextView= (TextView) convertView.findViewById(R.id.tv_members);

            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        if(flag== RecordActivity.MEMBER){    //  家庭成员
            MemberBean memberBean = (MemberBean) mList.get(position);
            holder.mTextView.setText(memberBean.getMember());
        }else if(flag==RecordActivity.WAY){  //  支付方式
            PayBean pay = (PayBean) mList.get(position);
            holder.mTextView.setText(pay.getWay());
        }
        return convertView;
    }

    static class ViewHolder{
       private TextView mTextView;
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

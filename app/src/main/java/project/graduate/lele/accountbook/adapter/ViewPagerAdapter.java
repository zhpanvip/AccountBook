package project.graduate.lele.accountbook.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;
import project.graduate.lele.accountbook.fragment.PayFragment;

/**
 * Created by zhpan on 2017/1/15.
 * 报表页面ViewPager的适配器
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<PayFragment> mList;

    public List<PayFragment> getmList() {
        return mList;
    }

    public void setmList(List<PayFragment> mList) {
        this.mList = mList;
    }


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}

package project.graduate.lele.accountbook.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.adapter.ViewPagerAdapter;

/**
 * Created by zhpan on 2017/1/8.
 * 报表页面
 */

public class ChartFragment extends Fragment implements View.OnClickListener{
    private View mView;
    private PayFragment mPayFragment;
    private PayFragment mGainFragment;
    //private PayFragment mOverPlusFragment;
    private List<PayFragment> mList;
    private ViewPager mViewpager;
    private ViewPagerAdapter mAdapter;
    private TextView mTvPay;
    private TextView mTvGain;
    //private TextView mTvOverPlus;
    private List<TextView> mTvList;
    private View mViewPay;
    private View mViewGain;
    //private View mViewOverPlus;
    private List<View> mViewList;
    private LinearLayout mLlPay;
    private LinearLayout mLlGain;
    //private LinearLayout mLlOverPlus;
    private int prePosition;

   // private ImageView mIvLeft;
    private TextView mTvTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_chart,null);

        initView();
        setData();
        setListener();

        return mView;
    }

    private void setListener() {
        mLlPay.setOnClickListener(this);
        mLlGain.setOnClickListener(this);
        //mLlOverPlus.setOnClickListener(this);

        /**
         *  ViewPager页面滑动的监听事件
         */
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             *  ViewPager页面滚动的时候调用这个方法
              * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 页面滑动完成，切换到了另一个Fragment之后调用这个方法
             * @param position    滑动之后的position
             */
            @Override
            public void onPageSelected(int position) {
                mTvList.get(prePosition).setTextColor(Color.parseColor("#666666"));
                mViewList.get(prePosition).setBackgroundColor(Color.parseColor("#FFFFFF"));

                mTvList.get(position).setTextColor(Color.parseColor("#F4606A"));
                mViewList.get(position).setBackgroundColor(Color.parseColor("#F4606A"));
                prePosition=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setData() {
        //mIvLeft.setVisibility(View.GONE);
        mTvTitle.setText("报表");
        mTvTitle.setTextColor(Color.parseColor("#FFFFFF"));


        mTvList=new ArrayList<>();
        Collections.addAll(mTvList,mTvPay,mTvGain);
        mViewList=new ArrayList<>();
        Collections.addAll(mViewList,mViewPay,mViewGain);


        mPayFragment=PayFragment.getInstance(1);

        mGainFragment=PayFragment.getInstance(2);
       // mOverPlusFragment=new PayFragment();
        mList=new ArrayList<>();

        mList.add(mPayFragment);
        mList.add(mGainFragment);
        //mList.add(mOverPlusFragment);

        mAdapter=new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        mAdapter.setmList(mList);
        mViewpager.setAdapter(mAdapter);

    }

    private void initView() {
        mViewpager= (ViewPager) mView.findViewById(R.id.vp_my_count);

        mTvPay= (TextView) mView.findViewById(R.id.tv_pay);
        mTvGain= (TextView) mView.findViewById(R.id.tv_gain);
        //mTvOverPlus= (TextView) mView.findViewById(R.id.tv_overplus);

        mViewPay=mView.findViewById(R.id.view_pay);
        mViewGain=mView.findViewById(R.id.view_gain);
        //mViewOverPlus=mView.findViewById(R.id.view_overplus);

        mLlPay= (LinearLayout) mView.findViewById(R.id.ll_pay);
        mLlGain= (LinearLayout) mView.findViewById(R.id.ll_gain);
       // mLlOverPlus= (LinearLayout) mView.findViewById(R.id.ll_overplus);

        mTvTitle= (TextView) mView.findViewById(R.id.tv_title);
        //mIvLeft= (ImageView) mView.findViewById(R.id.iv_left);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_pay:   //  点击了支出
                if(prePosition!=0){
                    mViewpager.setCurrentItem(0);
                }
                break;

            case R.id.ll_gain:  //  点击了收入
                if(prePosition!=1){
                    mViewpager.setCurrentItem(1);
                }
                break;

            /*case R.id.ll_overplus:  //  点击了结余
                if(prePosition!=2){
                    mViewpager.setCurrentItem(2);
                }
                break;*/
        }
    }
}

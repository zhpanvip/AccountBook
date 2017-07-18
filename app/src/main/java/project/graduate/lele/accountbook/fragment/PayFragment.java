package project.graduate.lele.accountbook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lantouzi.wheelview.WheelView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.activity.PayDetailsActivity;
import project.graduate.lele.accountbook.activity.RecordActivity;
import project.graduate.lele.accountbook.adapter.PayListAdapter;
import project.graduate.lele.accountbook.bean.AccountBean;
import project.graduate.lele.accountbook.bean.AccountClassBean;
import project.graduate.lele.accountbook.bean.AccountGainClassBean;
import project.graduate.lele.accountbook.bean.TotalPayBean;
import project.graduate.lele.accountbook.utils.DateUtils;
import project.graduate.lele.accountbook.utils.UserTools;
import project.graduate.lele.accountbook.view.ListViewForScrollView;
import project.graduate.lele.accountbook.view.PieChartView;

/**
 * Created by lunlele on 2017/1/15.
 * 报表页面中ViewPager中嵌套的Fragment
 */

public class PayFragment extends Fragment {
    private View mView;
    //  选择月份的滑动条
    private WheelView wheelView;
    //  扇形图
    private PieChartView mPieChartView;
    private ListViewForScrollView mListView;
    private PayListAdapter mAdapter;
    //   支出类别的集合
    private List<AccountClassBean> payList;
    //  收入类别的集合
    private List<AccountGainClassBean> incomeList;
    //  收入或者这出的标志 1表示支出 2表示收入
    private int accountClass;
    //  账单的集合
    private List<List<AccountBean>> mAccountBeenList;
    //  填充扇形图和ListView的数据集合
    private List<TotalPayBean> totalPayBeanList;
    //  月份集合 从2016年1月到当前月
    private List<String> monthList;
    //  暂无数据的TextView
    private TextView mTvNoData;
    //  包含扇形图和ListView的布局
    private LinearLayout mLlChart;
    //  滑动月份滚动条时选中的月份
    private String month = "";

    //  通过调用该方式获取PayFragment的对象
    public static PayFragment getInstance(int accountClass) {
        Bundle bundle = new Bundle();
        //  实例化PayFragment时将收支类别传递进来
        bundle.putInt("accountClass", accountClass);
        PayFragment payFragment = new PayFragment();
        payFragment.setArguments(bundle);
        return payFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.pay_fragment, null);

        initView();
        initData();
        setData();
        setListener();

        return mView;
    }

    private void initData() {
        totalPayBeanList = new ArrayList<>();
        mAccountBeenList = new ArrayList<>();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void setListener() {

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PayListAdapter adapter = (PayListAdapter) parent.getAdapter();
                List<TotalPayBean> totalPayBeen = adapter.getmList();
                TotalPayBean totalPayBean = totalPayBeen.get(position);
                PayDetailsActivity.start(getContext(), 3, totalPayBean.getName());
            }
        });

        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onWheelItemChanged(WheelView wheelView, int position) {
            }

            /**
             * 月份的滑动条选中某个月份时的监听方法
             * @param wheelView
             * @param position
             */
            @Override
            public void onWheelItemSelected(WheelView wheelView, int position) {
                //  获取月份集合
                List<String> items = wheelView.getItems();
                //  通过月份查询数据库得到扇形图和ListView中的数据
                getData(items.get(position));
                //  重新给ListView的Adapter设置数据
                mAdapter.setmList(totalPayBeanList);
                //  通知ListView数据改变了 刷新ListView页面
                mAdapter.notifyDataSetChanged();


                mPieChartView.midString = "";
                // 给扇形图重新设置数据
                mPieChartView.setPieItems(totalPayBeanList);
                // 扇形图开启动画
                mPieChartView.AnimalXY(2000, 2000, new DecelerateInterpolator());
            }
        });
    }

    private void initView() {
        wheelView = (WheelView) mView.findViewById(R.id.wheel_view);
        mPieChartView = (PieChartView) mView.findViewById(R.id.pie_chart_view);
        //  1.通过findViewById找到ListView
        mListView = (ListViewForScrollView) mView.findViewById(R.id.lv_list);
        mTvNoData = (TextView) mView.findViewById(R.id.tv_no_data);
        mLlChart = (LinearLayout) mView.findViewById(R.id.ll_chart);

    }

    /**
     * PayDetailsActivity 页面删除数据成功并通过EventBus发送Post后执行该方法
     *
     * @param save
     */
    @Subscribe
    public void deleteSuccess(RecordActivity.SaveAccountSuccess save) {
        //  调用该方法重新查询数据库刷新页面
        getData(month);
    }



    /**
     * 下边的代码目的是为了从数据库中取出扇形图和ListView对应的数据集合totalPayBeanList，
     * 逻辑比较复杂。真实项目开发中这些数据都是请求服务器得到的不必写此逻辑查询数据库。
     *
     * */
    private void setData() {
        Bundle bundle = getArguments();
        accountClass = bundle.getInt("accountClass");

        //  获取月份集合并填充到滑动条
        //  滑动条开始时间的字符串
        String start = "2016-01-01";
        //  开始日期
        Date startDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //  将日期格式化成yyyy-MM-dd形式
            startDate = format.parse(start);
            //  通过封装的方法获取月份的集合列表
            monthList = DateUtils.getMonthBetweenTwoDate(startDate, new Date());
            //  给滑动条填充月份
            wheelView.setItems(monthList);
            //  滑动条默认选中最后一个
            wheelView.selectIndex(monthList.size() - 1);
            //month = monthList.get(monthList.size() - 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //  获取所有的支出类别
        payList = DataSupport.findAll(AccountClassBean.class);
        //  获取所有的收入类别
        incomeList = DataSupport.findAll(AccountGainClassBean.class);
        //  获取集合中最后一个月份的字符串 格式yyyy-MM 根据该月份查询数据
        month = monthList.get(monthList.size() - 1);
        //  查询数据库获取到扇形图的数据
        getData(month);

        mPieChartView.midString = "";
        mPieChartView.setPieItems(totalPayBeanList);

        mPieChartView.AnimalXY(2000, 2000, new DecelerateInterpolator());


        //  3.实例化Adapter
        mAdapter = new PayListAdapter(getContext());
        mAdapter.setmList(totalPayBeanList);

        //  4.给ListView去设置Adapter
        mListView.setAdapter(mAdapter);

    }

    //  查询数据库并对数据进行分类汇总得到汇总后的List集合
    private void getData(String month) {
        //  清空账单的集合
        mAccountBeenList.clear();
        int listSize = 0;   //  集合的size
        if (accountClass == 1) {    //  类别为指出
            //  获取支出集合的size
            listSize = payList.size();
        } else if (accountClass == 2) {//   类别为收入
            //  获取收入集合的size
            listSize = incomeList.size();
        }
        //  遍历（收入/支出）类别集合 查询每种分类的所有数据
        for (int i = 0; i < listSize; i++) {
            List<AccountBean> accountBeen = new ArrayList<>();
            if (accountClass == 1) {    //  查询支出
                //  查询某用户某月某种支出类别的集合
                accountBeen = DataSupport.where("username=? and month=? and accountClass=? and payClass=?",
                        UserTools.getUsername(getContext()), month, accountClass + "", payList.get(i).getPayName())
                        .find(AccountBean.class);
            } else if (accountClass == 2) { //  查询收入
                //  查询某用户某月某种收入类别的集合
                accountBeen = DataSupport.where("username=? and month=? and accountClass=? and payClass=?",
                        UserTools.getUsername(getContext()), month, accountClass + "", incomeList.get(i).getGainName())
                        .find(AccountBean.class);
            }
            //  如果查询到的集合size大于0则说明该分类有中数据
            if (accountBeen.size() != 0) {
                //  将其添加到AccountBeenList
                mAccountBeenList.add(accountBeen);
            }
        }


        //  从数据库中查询所有支出的数据集合（根据用户名、月份、和收支类别[支出]）
        List<AccountBean> accountPayBeen = DataSupport
                .where("accountClass=1 and username=? and month=?", UserTools.getUsername(getContext()), month)
                .find(AccountBean.class);
        List<AccountBean> accountIncomeBeen = DataSupport
                .where("accountClass=2 and username=? and month=?", UserTools.getUsername(getContext()), month)
                .find(AccountBean.class);
        double totalPay = 0;
        double totalIncome = 0;
        //  遍历支出集合 得到本月支出总和
        for (int i = 0; i < accountPayBeen.size(); i++) {
            totalPay += accountPayBeen.get(i).getAccount();
        }

        //  遍历收入集合 得到本月收入总和
        for (int i = 0; i < accountIncomeBeen.size(); i++) {
            totalIncome += accountIncomeBeen.get(i).getAccount();
        }
        //  清空集合数据 方式多次调用getData方法时多次填充
        totalPayBeanList.clear();

        //  根据查询到的数据分类汇总
        for (int i = 0; i < mAccountBeenList.size(); i++) {
            //  获取某个分类的集合 （例如所有购物的账单集合）
            List<AccountBean> accountBeen = mAccountBeenList.get(i);
            //  ListView对应的实体类
            TotalPayBean totalPayBean = new TotalPayBean();
            //  该类的支出/收入总和
            double total = 0;
            //  遍历分类集合得到收入/支出总和
            for (int j = 0; j < accountBeen.size(); j++) {
                total += accountBeen.get(j).getAccount();
            }


            totalPayBean.setTotal(total);
            //  获取账单集合的第一个数据
            AccountBean accountBean = accountBeen.get(0);
            //取出名称和图片id设置给实体类 集合中所有数据都有相同的payClass(例如购物)和PicSrc（购物对应的图标）
            totalPayBean.setName(accountBean.getPayClass());
            totalPayBean.setPicSrc(accountBean.getPicSrc());

            if (accountClass == 1) {    //  如果是支出类别
                //  根据名称获取到支出类别的集合
                List<AccountClassBean> accountClassBeen = DataSupport
                        .where("payName=?", accountBean.getPayClass())
                        .find(AccountClassBean.class);

                totalPayBean.setColor(accountClassBeen.get(0).getBackground());
                totalPayBean.setTotalPay(totalPay);
                totalPayBean.setPercent((total / totalPay));
            } else if (accountClass == 2) {//  如果是收入类别
                List<AccountGainClassBean> accountClassBeen = DataSupport
                        .where("gainName=?", accountBean.getPayClass())
                        .find(AccountGainClassBean.class);

                totalPayBean.setColor(accountClassBeen.get(0).getBackground());
                totalPayBean.setTotalPay(totalIncome);
                totalPayBean.setPercent((total / totalIncome));
            }
            totalPayBeanList.add(totalPayBean);
        }

        if (totalPayBeanList.size() == 0) {
            mTvNoData.setVisibility(View.VISIBLE);
            mLlChart.setVisibility(View.GONE);
        } else {
            mTvNoData.setVisibility(View.GONE);
            mLlChart.setVisibility(View.VISIBLE);
        }
    }
}

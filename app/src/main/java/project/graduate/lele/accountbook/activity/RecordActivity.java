package project.graduate.lele.accountbook.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.crud.DataSupport;
import java.util.Calendar;
import java.util.List;
import project.graduate.lele.accountbook.app.MainApplication;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.adapter.GridViewAdapter;
import project.graduate.lele.accountbook.adapter.MemberAdapter;
import project.graduate.lele.accountbook.bean.AccountBean;
import project.graduate.lele.accountbook.bean.AccountClassBean;
import project.graduate.lele.accountbook.bean.AccountGainClassBean;
import project.graduate.lele.accountbook.bean.MemberBean;
import project.graduate.lele.accountbook.bean.PayBean;
import project.graduate.lele.accountbook.service.LocationService;
import project.graduate.lele.accountbook.utils.DateUtils;
import project.graduate.lele.accountbook.utils.EditTextCashUtil;
import project.graduate.lele.accountbook.utils.UserTools;
import project.graduate.lele.accountbook.view.GridViewForScrollView;

/**
 * 记一笔Activity
 * 该页面主要功能是将填写、选择的数据保存到数据库
 * 是该程序的核心页面
 */
public class RecordActivity extends BaseActivity implements View.OnClickListener {

    public static final int PAY = 1;    //  支出
    public static final int INCOME = 2; //  收入
    private ImageView mIvClass; //  收支类别图标
    private TextView mTvClass;  //  收支名称
    private EditText mEtMoney;  //  金额输入框
    private Button mBtnSave;    //  保存按钮
    private GridViewForScrollView mGdClass; //  类别的GridView
    private LinearLayout mRlClass;
    private TextView mTvDate;  //   日期
    private TextView mTvLocation;   //  位置
    private TextView mTvMembers;    //  成员
    private TextView mTvPayWay;     //  支付方式
    private TextView mTvNote;       //  备注
    private GridViewAdapter mAdapter;
    private int mYear, mMonth, mDay;    //  年月日
    private RelativeLayout mRlDate;
    private RelativeLayout mRlMembers;
    private RelativeLayout mRlPayWay;

    private final int DATE_DIALOG = 1;
    //  获取位置的服务
    private LocationService locationService;
    private FrameLayout frameLayout;
    //  成员和支付方式弹窗
    private PopupWindow popupWindow;
    //  弹窗布局的View
    private View popView;
    //  弹窗标题
    private TextView popTitle;
    //  弹窗右上添加
    private TextView tvAdd;
    //  弹窗中的列表
    private ListView mListView;
    //  成员的List集合数据
    private List<MemberBean> mListMember;
    //  支付方式的集合数据
    private List<PayBean> mListPay;
    //  支付方式的实体类
    private PayBean payBean;
    // private List<AccountClassBean> mList;
    private List<AccountGainClassBean> mListGain;
    //  成员标记
    public static final int MEMBER = 1;
    //  支付方式标记
    public static final int WAY = 2;
    //  标记是成员还是支付方式
    private int flag;

    //  记账实体类
    private AccountBean accountBean;
    //  所选类别的图标，给默认值
    private int picSrc = R.drawable.x2;
    //  所选类别的名称，默认餐饮
    private String payClass = "餐饮";
    //  日期 默认今日
    private String date = DateUtils.getDate();
    //  月份 默认本月
    private String month = DateUtils.getMonth();
    //  年份 默认今年
    private String yearStr = DateUtils.getYear();
    //  位置
    private String address;
    //  成员 默认自己
    private String member="自己";
    //  支付方式 默认现金
    private String payWay="现金";
    //  金额
    private double account;
    //  收支类别 默认1 即支出
    private int accountClass = PAY;   //  1.支出  2.收入
    //  时间戳
    private long timeStamp;

    //  成员列表的适配器
    private MemberAdapter memberAdapter;

    //  所有支出类别的集合
    private List<AccountClassBean> mListPayClass;

    //  从修改页面传来的实体类
    private AccountBean accountBeforBean;

    /**
     * 选择日期的监听事件
     */
    private DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {


        /**
         * 在弹出的对话框中点击确定时回掉（执行）这个方法
         * @param view
         * @param year  选择日期的年份
         * @param monthOfYear 选择日期的月份（要加1）
         * @param dayOfMonth 选择日期
         */
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear + 1;
            mDay = dayOfMonth;

            //  如果年份月份小于10 则在前边加0
            String monthStr = (mMonth < 10 ? "0" + mMonth : "" + mMonth);
            String mDayStr = (mDay < 10 ? "0" + mDay : mDay + "");

            //  要保存到数据库的日期字符串
            date = mYear + "-" + monthStr + "-" + mDayStr;
            mTvDate.setText(date);

            //  要保存到数据库的月份
            month=mYear+"-"+monthStr;
            //  要保存到数据库的年份
            yearStr=year+"";
            //  保存到数据库的时间戳
            timeStamp = System.currentTimeMillis();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        initView();
        initData();
        setData();
        initPopWindows();
        setListener();
    }

    /**
     * 监听返回键
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (popupWindow.isShowing()) {  //  按返回键时检查弹出框是否显示
                popupWindow.dismiss();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }

        }
        return super.onKeyDown(keyCode, event);
    }


    private void initData() {
        //  注册EventBus
        EventBus.getDefault().register(this);
        //  初始化记账实体类
        accountBean = new AccountBean();

        //  给日期一行默认设置为今日
        mTvDate.setText(DateUtils.getDate());

        //  处理修改记账数据
        ModifyData();
    }
    //  处理修改记账数据
    private void ModifyData() {
        Intent intent = getIntent();
        //  获取上个页面传过来的记账实体类
        accountBeforBean = (AccountBean) intent.getSerializableExtra("accountBean");
        //  如果实体类不为空说明为修改数据 将页面数据初始化为之前的数据
        if (accountBeforBean != null) {
            picSrc = accountBeforBean.getPicSrc();
            mIvClass.setImageResource(picSrc);
            payClass = accountBeforBean.getPayClass();
            mTvClass.setText(payClass);
            date = accountBeforBean.getDate();
            mTvDate.setText(date);
            member = accountBeforBean.getMember();
            mTvMembers.setText(member);
            payWay = accountBeforBean.getPayWay();
            mTvPayWay.setText(payWay);
            account=accountBeforBean.getAccount();
            accountClass= (int) accountBeforBean.getAccountClass();
            //mEtMoney.setText(accountBeforBean.getAccount()+"");
            //  给输入框设置hint为金额
            mEtMoney.setHint(account+"");

            //  从数据库中查找到该条数据并赋值给accountBean这样保存就不会产生新的数据而是修改老的数据
            accountBean=DataSupport.find(AccountBean.class,accountBeforBean.getId());

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  注销EventBus
        EventBus.getDefault().unregister(this);
    }

    /**
     * 创建选择日期的对话框
     *
     * @param id
     * @return
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mDateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private void setListener() {
        //  保存按钮监听
        mBtnSave.setOnClickListener(this);
        //  类别的GridView的条目点击监听事件
        mGdClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (accountClass == PAY) {  //   记账类别为支出
                    if (position != mListPayClass.size() - 1) { //  如果点击的不是最后一个条目
                        GridViewAdapter adapter = (GridViewAdapter) parent.getAdapter();
                        List<AccountClassBean> accountClassBeen = (List<AccountClassBean>) adapter.getmList();
                        mIvClass.setImageResource(accountClassBeen.get(position).getPicId());
                        mTvClass.setText(accountClassBeen.get(position).getPayName());
                        mRlClass.setBackgroundColor(Color.parseColor(accountClassBeen.get(position).getBackground()));

                        picSrc = accountClassBeen.get(position).getPicId();
                        payClass = accountClassBeen.get(position).getPayName();
                    } else {    //  点击最后一个条目 即点击自定义

                        //  跳转到添加自定义类别页面
                        startAddActivity(accountClass);
                    }
                } else if (accountClass == INCOME) {    //   记账类别为收入
                    if (position != mListGain.size() - 1) {
                        GridViewAdapter adapter = (GridViewAdapter) parent.getAdapter();
                        List<AccountGainClassBean> accountClassBeen = (List<AccountGainClassBean>) adapter.getmList();
                        mIvClass.setImageResource(accountClassBeen.get(position).getPicId());
                        mTvClass.setText(accountClassBeen.get(position).getGainName());
                        mRlClass.setBackgroundColor(Color.parseColor(accountClassBeen.get(position).getBackground()));

                        picSrc = accountClassBeen.get(position).getPicId();
                        payClass = accountClassBeen.get(position).getGainName();
                    } else {
                        startAddActivity(accountClass);
                    }
                }
            }
        });

        //  弹窗ListView的条目点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MemberAdapter adapter = (MemberAdapter) parent.getAdapter();
                List<DataSupport> popBeen = (List<DataSupport>) adapter.getmList();
                if (flag == WAY) {  //  如果flag是支付方式则取支付方式数据并将选中的数据设置给支付方式
                    payBean = (PayBean) popBeen.get(position);
                    mTvPayWay.setText(payBean.getWay());
                    payWay = payBean.getWay();
                } else if (flag == MEMBER) {//  如果flag是成员则取成员数据并将选中的数据设置给成员
                    MemberBean memberBean = (MemberBean) popBeen.get(position);
                    mTvMembers.setText(memberBean.getMember());
                    member = memberBean.getMember();
                }
                popupWindow.dismiss();

            }
        });
        //  TitleBar中收入和指出的RadioButton的选中状态改变的监听事件
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {    //  左边被选中
                    case R.id.rb_left:
                        //  给GridView填充支出类别 并刷新GridView
                        mAdapter.setmList(mListPayClass);
                        mAdapter.setFlag(PAY);
                        mAdapter.notifyDataSetChanged();
                        mRbLeft.setTextColor(Color.parseColor("#FFC12F"));
                        mRbRight.setTextColor(Color.parseColor("#FFFFFF"));
                        accountClass = PAY; //将accountClass设置为PAY
                        break;
                    case R.id.rb_right: //  右边被选中
                        //  给GridView填充收入类别 并刷新GridView
                        mAdapter.setmList(mListGain);
                        mAdapter.setFlag(INCOME);
                        mAdapter.notifyDataSetChanged();
                        mRbRight.setTextColor(Color.parseColor("#FFC12F"));
                        mRbLeft.setTextColor(Color.parseColor("#FFFFFF"));
                        accountClass = INCOME;//将accountClass设置为INCOME
                        break;
                }
            }
        });
    }

    private void startAddActivity(int accountClass) {
        Intent intent = new Intent(RecordActivity.this, AddActivity.class);
        intent.putExtra("accountClass", accountClass);
        startActivity(intent);
    }

    private void setData() {
        tvTitle.setVisibility(View.GONE);
        mRadioGroup.setVisibility(View.VISIBLE);

        //  格式化金额输入框
        EditTextCashUtil.setPricePoint(mEtMoney);

        //  通过日历类获取当前年月日
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);


        //  查询数据库得到支出类别的集合
        mListPayClass = DataSupport.findAll(AccountClassBean.class);
        //  构造支出实体类放置到集合最后作为添加自定义类别的入口
        AccountClassBean accountClassBean = new AccountClassBean();
        accountClassBean.setPicId(R.drawable.category_edit);
        accountClassBean.setPayName("自定义");
        mListPayClass.add(accountClassBean);

        //  实例化GridView适配器
        mAdapter = new GridViewAdapter(this);
        //  将查询到的数据适配到GradView中
        mAdapter.setmList(mListPayClass);
        //  给适配器设置flag代表是支出类别
        mAdapter.setFlag(PAY);
        //  给GridView适配Adapter
        mGdClass.setAdapter(mAdapter);

        //  查询数据库得到收入类别的集合
        mListGain = DataSupport.findAll(AccountGainClassBean.class);
        //  构造收入实体类添加到到集合最后作为添加自定义类别的入口
        AccountGainClassBean gainBean = new AccountGainClassBean();
        gainBean.setPicId(R.drawable.category_edit);
        gainBean.setGainName("自定义");
        mListGain.add(gainBean);


        //mListMember=new ArrayList<>();
        mListMember = DataSupport.findAll(MemberBean.class);

        mListPay = DataSupport.findAll(PayBean.class);
        payBean = mListPay.get(0);
    }

    //  Activity跳转的方法
    public static void start(Context context) {
        Intent intent = new Intent(context, RecordActivity.class);
        context.startActivity(intent);
    }

    //  Activity跳转的方法
    public static void start(Context context, AccountBean accountBean) {
        Intent intent = new Intent(context, RecordActivity.class);
        intent.putExtra("accountBean", accountBean);
        context.startActivity(intent);
    }

    private void initView() {
        mIvClass = (ImageView) findViewById(R.id.iv_class);
        mTvClass = (TextView) findViewById(R.id.tv_class);
        mEtMoney = (EditText) findViewById(R.id.tv_money);
        mGdClass = (GridViewForScrollView) findViewById(R.id.gd_class);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvLocation = (TextView) findViewById(R.id.tv_location);
        mTvMembers = (TextView) findViewById(R.id.tv_members);
        mTvPayWay = (TextView) findViewById(R.id.tv_pay_way);
        mTvNote = (TextView) findViewById(R.id.tv_note);
        mRlClass = (LinearLayout) findViewById(R.id.rl_class);
        mRlDate = (RelativeLayout) findViewById(R.id.rl_date);
        mRlDate.setOnClickListener(this);
        mRlMembers = (RelativeLayout) findViewById(R.id.rl_members);
        mRlMembers.setOnClickListener(this);
        mRlPayWay = (RelativeLayout) findViewById(R.id.rl_pay_way);
        mRlPayWay.setOnClickListener(this);
        frameLayout = (FrameLayout) findViewById(R.id.fr_record);
        mBtnSave = (Button) findViewById(R.id.btn_save);

    }

    //  保存按钮的提交方法
    private void submit() {
        String money = mEtMoney.getText().toString().trim();
        if (TextUtils.isEmpty(money)&&accountBeforBean==null) {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!TextUtils.isEmpty(money)){
            account=Double.parseDouble(money);
        }
        //  将页面的数据设置到accountBean
        accountBean.setPicSrc(picSrc);
        accountBean.setPayClass(payClass);
        accountBean.setAccount(account);
        accountBean.setDate(date);
        accountBean.setMonth(month);
        accountBean.setYear(yearStr);
        accountBean.setTimeStamp(timeStamp);
        accountBean.setMember(member);
        accountBean.setPayWay(payWay);
        accountBean.setNote(mTvNote.getText().toString());
        accountBean.setAccountClass(accountClass);
        accountBean.setUsername(UserTools.getUsername(this));
        accountBean.setAddress(address);
        //  将数据保存到数据库
        if (accountBean.save()) {   //  保存成功则更新支付方式余额
            double yuE = 0;     //  支付方式中的余额
            if (accountClass == PAY) {    //  支出
                yuE = payBean.getBanlance() - account;
            } else if (accountClass == INCOME) {
                yuE = payBean.getBanlance() + account;
            }
            payBean.setBanlance(yuE);
            payBean.save();
            EventBus.getDefault().post(new SaveAccountSuccess());
            finish();
        } else {
            Toast.makeText(this, "数据保存失败", Toast.LENGTH_SHORT).show();
        }

    }

    //  初始化选择成员和支付方式的弹出框
    private void initPopWindows() {
        popView = View.inflate(this, R.layout.popup_window, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //  给弹出框设置默认动画
        popupWindow.setAnimationStyle(-1);
        //  点击其他地方取消popWindows
        popupWindow.setFocusable(true);
         popTitle = (TextView) popView.findViewById(R.id.tv_pop_title);
        TextView tvAdd = (TextView) popView.findViewById(R.id.tv_add);
        LinearLayout linearLayout = (LinearLayout) popView.findViewById(R.id.ll_pop_window);
        RelativeLayout relativeLayout = (RelativeLayout) popView.findViewById(R.id.rl_pop_window);
        linearLayout.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
        tvAdd.setOnClickListener(this);

        mListView = (ListView) popView.findViewById(R.id.lv_member);
        memberAdapter = new MemberAdapter(this);
        memberAdapter.setmList(mListMember);
        mListView.setAdapter(memberAdapter);
    }

    @Subscribe
    public void saveCustomSuccess(AddActivity.SaveCustomSuccess saveCustomSuccess) {
        int accountClass = saveCustomSuccess.accountClass;
        if (accountClass==INCOME) {
            mListGain = DataSupport.findAll(AccountGainClassBean.class);
            AccountGainClassBean accountClassBean = new AccountGainClassBean();
            accountClassBean.setPicId(R.drawable.category_edit);
            accountClassBean.setGainName("自定义");
            mListGain.add(accountClassBean);
            mAdapter.setmList(mListGain);
        } else if (accountClass==PAY) {
            mListPayClass = DataSupport.findAll(AccountClassBean.class);
            AccountClassBean accountClassBean = new AccountClassBean();
            accountClassBean.setPicId(R.drawable.category_edit);
            accountClassBean.setPayName("自定义");
            mListPayClass.add(accountClassBean);
            mAdapter.setmList(mListPayClass);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 添加支成员成功后调用该方法刷新成员列表
     * @param savePopSuccess
     */
    @Subscribe
    public void savePopSuccess(AddPopDataActivity.SavePopSuccess savePopSuccess) {

        mListMember = DataSupport.findAll(MemberBean.class);
        memberAdapter.setmList(mListMember);
        memberAdapter.notifyDataSetChanged();

        /*int flag = savePopSuccess.flag;
        if (flag == WAY) {
            mListPay = DataSupport.findAll(PayBean.class);
            memberAdapter.setmList(mListPay);

        } else if (flag == MEMBER) {
            mListMember = DataSupport.findAll(MemberBean.class);
            memberAdapter.setmList(mListMember);
        }*/
    }


    /**
     * 添加支付方式成功后调用该方法刷新支付方式列表
     * @param addWaySuccess
     */
    @Subscribe
    public void addWaySuccess( AddPayWayActivity.AddWaySuccess addWaySuccess){
        mListPay = DataSupport.findAll(PayBean.class);
        memberAdapter.setmList(mListPay);
        memberAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_date:  //  选择时间的条目
                //  弹出时间选择对话框
                showDialog(DATE_DIALOG);
                break;
            case R.id.ll_pop_window:
                popupWindow.dismiss();
                break;
            case R.id.rl_members:   //点击选择成员时
                popTitle.setText("请选择成员");
                //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
                popupWindow.showAtLocation(frameLayout, Gravity.CENTER, 0, 0);
                memberAdapter.setmList(mListMember);
                memberAdapter.setFlag(MEMBER);
                //  通知adapter集合的数据改变了，刷新ListView
                memberAdapter.notifyDataSetChanged();
                flag = MEMBER;  //  将flag置为MEMBER
                break;
            case R.id.rl_pay_way:   //点击选择支付方式时
                popTitle.setText("请选择支付方式");
                //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
                popupWindow.showAtLocation(frameLayout, Gravity.CENTER, 0, 0);
                memberAdapter.setmList(mListPay);
                memberAdapter.setFlag(WAY);
                //  通知adapter集合的数据改变了，刷新ListView
                memberAdapter.notifyDataSetChanged();
                flag = WAY; //  将flag置为WAY
                break;
            case R.id.tv_add:
                if(flag==WAY){
                    AddPayWayActivity.start(this);
                }else {
                    AddPopDataActivity.start(this);
                }

                break;
            case R.id.btn_save:
                submit();
                break;
        }
    }

    public static class SaveAccountSuccess {

    }

    /**********************************************获取位置************************************************************************/

    /**
     * Activity停止时注销获取位置的service并停止获取位置
     */
    @Override
    protected void onStop() {
        super.onStop();
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop();
    }

    /**
     * Activity启动时注册获取位置的service
     */
    @Override
    protected void onStart() {
        super.onStart();
        locationService = ((MainApplication) getApplication()).locationService;
        //  获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }

        //  开启定位
        locationService.start();// 定位SDK

    }


    /*****
     * 定位结果回调，重写onReceiveLocation方法（获取位置成功之后调用这个方法）
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                try {   //  给TextView设置获取到的位置信息
                    if (mTvLocation != null)
                        mTvLocation.setText(location.getAddrStr());
                    address = location.getAddrStr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    };

/**********************************************获取位置结束************************************************************************/




}

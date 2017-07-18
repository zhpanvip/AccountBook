package project.graduate.lele.accountbook.app;

import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.bean.AccountClassBean;
import project.graduate.lele.accountbook.bean.AccountGainClassBean;
import project.graduate.lele.accountbook.bean.MemberBean;
import project.graduate.lele.accountbook.bean.PayBean;
import project.graduate.lele.accountbook.bean.UserBean;

/**
 * Created by zhpan on 2017/2/11.
 * 初始化数据库，第一次运行时向数据库中写入数据
 *
 */

public class InitData {
    //  初始化用户到本地数据库
    public static  void initUser(){

        UserBean userBean1=new UserBean();
        userBean1.setUsername("15736883178");
        userBean1.setPassword("123123");
        userBean1.setEmail("lunlele@outlook.com");
        userBean1.setLevel(3);
        userBean1.setHeadPic("http://depot.nipic.com/file/20160929/13244350_17022066241.jpg");
        userBean1.setGrade(31);
        userBean1.save();

        UserBean userBean2=new UserBean();
        userBean2.setUsername("15736883179");
        userBean2.setPassword("123123");
        userBean2.setEmail("xuxiaoxing@outlook.com");
        userBean2.setLevel(2);
        userBean2.setGrade(16);
        userBean2.setHeadPic("http://e.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=44348f00357adab43d851347bee49f2a/cc11728b4710b91274d1cad6c0fdfc0392452281.jpg");
        userBean2.save();
        UserBean userBean3=new UserBean();
        userBean3.setUsername("15736883180");
        userBean3.setPassword("123123");
        userBean3.setEmail("zhpan@outlook.com");
        userBean3.setLevel(1);
        userBean3.setGrade(9);
        userBean3.setHeadPic("http://imgsrc.baidu.com/forum/w%3D580/sign=10fe9aef014f78f0800b9afb49300a83/daef1a4c510fd9f93c3951d2272dd42a2834a472.jpg");
        userBean3.save();
    }

    //  初始化记账类别（支出）
    public static void initPayClassData(){

        AccountClassBean bean1 = new AccountClassBean();
        bean1.setPicId(R.drawable.x2);
        bean1.setPayName("餐饮");
        bean1.setBackground("#F26D49");
        bean1.save();

        AccountClassBean bean2 = new AccountClassBean();
        bean2.setPicId(R.drawable.x24);
        bean2.setPayName("购物");
        bean2.setBackground("#9FDE5B");
        bean2.save();

        AccountClassBean bean3 = new AccountClassBean();
        bean3.setPicId(R.drawable.x5);
        bean3.setPayName("服饰");
        bean3.setBackground("#9FDE5B");
        bean3.save();

        AccountClassBean bean4 = new AccountClassBean();
        bean4.setPicId(R.drawable.x6);
        bean4.setPayName("日用品");
        bean4.setBackground("#9DE7EB");
        bean4.save();

        AccountClassBean bean5 = new AccountClassBean();
        bean5.setPicId(R.drawable.x4);
        bean5.setPayName("交通");
        bean5.setBackground("#A2D4C3");
        bean5.save();

        AccountClassBean bean7 = new AccountClassBean();
        bean7.setPicId(R.drawable.x66);
        bean7.setPayName("话费");
        bean7.setBackground("#408637");
        bean7.save();

        AccountClassBean bean6 = new AccountClassBean();
        bean6.setPicId(R.drawable.x31);
        bean6.setPayName("红包");
        bean6.setBackground("#BEB3AC");
        bean6.save();


        AccountClassBean bean8 = new AccountClassBean();
        bean8.setPicId(R.drawable.x22);
        bean8.setPayName("护肤美妆");
        bean8.setBackground("#27A26F");
        bean8.save();

        AccountClassBean bean9 = new AccountClassBean();
        bean9.setPicId(R.drawable.x9);
        bean9.setPayName("住房");
        bean9.setBackground("#D969BA");
        bean9.save();

        AccountClassBean bean10 = new AccountClassBean();
        bean10.setPicId(R.drawable.x12);
        bean10.setPayName("医疗");
        bean10.setBackground("#347ABD");
        bean10.save();

        AccountClassBean bean11 = new AccountClassBean();
        bean11.setPicId(R.drawable.x7);
        bean11.setPayName("水电煤");
        bean11.setBackground("#928FA4");
        bean11.save();

        AccountClassBean bean12 = new AccountClassBean();
        bean12.setPicId(R.drawable.x38);
        bean12.setPayName("机票");
        bean12.setBackground("#3ED2B1");
        bean12.save();
    }


    //  初始化记账类别（收入）
    public static void initGainClass(){
        AccountGainClassBean bean1 = new AccountGainClassBean();
        bean1.setPicId(R.drawable.x10001);
        bean1.setGainName("工资");
        bean1.setBackground("#FF836A");
        bean1.save();

        AccountGainClassBean bean2 = new AccountGainClassBean();
        bean2.setPicId(R.drawable.x10014);
        bean2.setGainName("奖金福利");
        bean2.setBackground("#008AD4");
        bean2.save();

        AccountGainClassBean bean3 = new AccountGainClassBean();
        bean3.setPicId(R.drawable.x10005);
        bean3.setGainName("红包");
        bean3.setBackground("#9FDE5B");
        bean3.save();

        AccountGainClassBean bean4= new AccountGainClassBean();
        bean4.setPicId(R.drawable.x10003);
        bean4.setGainName("兼职外快");
        bean4.setBackground("#9DE7EB");
        bean4.save();

        AccountGainClassBean bean5 = new AccountGainClassBean();
        bean5.setPicId(R.drawable.x10004);
        bean5.setGainName("投资收入");
        bean5.setBackground("#A2D4C3");
        bean5.save();

        AccountGainClassBean bean7 = new AccountGainClassBean();
        bean7.setPicId(R.drawable.x20003);
        bean7.setGainName("租金");
        bean7.setBackground("#408637");
        bean7.save();
    }

    //  初始化成员
    public static void initMember(){
        MemberBean memberBean0=new MemberBean();
        memberBean0.setMember("自己");
        memberBean0.save();
        MemberBean memberBean=new MemberBean();
        memberBean.setMember("爱人");
        memberBean.save();
        MemberBean memberBean2=new MemberBean();
        memberBean2.setMember("孩子");
        memberBean2.save();
        MemberBean memberBean3=new MemberBean();
        memberBean3.setMember("父母");
        memberBean3.save();
        MemberBean memberBean4=new MemberBean();
        memberBean4.setMember("朋友");
        memberBean4.save();
    }

    //  初始化支付方式
    public static void initPay(){
        PayBean payBean=new PayBean();
        payBean.setWay("现金");
        payBean.setBanlance(0);
        payBean.setPicSrc(R.drawable.ico_cash);
        payBean.setBackground("#FC7A60");
        payBean.save();
        PayBean payBean2=new PayBean();
        payBean2.setWay("支付宝");
        payBean2.setPicSrc(R.drawable.as_ico_addp2p);
        payBean2.setBackground("#B1C23E");
        payBean2.save();

        PayBean payBean3=new PayBean();
        payBean3.setWay("微信");
        payBean3.setPicSrc(R.drawable.as_ico_addp2p);
        payBean3.setBackground("#5A98DE");
        payBean3.save();
        PayBean payBean4=new PayBean();
        payBean4.setWay("存储卡");
        payBean4.setPicSrc(R.drawable.icon_add_1);
        payBean4.setBackground("#FAB059");
        payBean4.save();
        PayBean payBean5=new PayBean();
        payBean5.setWay("信用卡");
        payBean5.setBackground("#EF6161");
        payBean5.setPicSrc(R.drawable.huankuan);
        payBean5.save();
    }



}

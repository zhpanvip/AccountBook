package project.graduate.lele.accountbook.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;
import java.io.Serializable;

/**
 * Created by zhpan on 2017/1/19.
 * 账单实体类 记一笔页面对应的实体类
 */

public class AccountBean extends DataSupport implements Serializable {
    @Column(unique = true, defaultValue = "unknown")
    private long id;    //  账单id
    private int type;// type==1 红包 type=2时 表示晚餐
    private double account; //  账单金额
    private int accountClass;    //1. 支出 2.收入
    private int picSrc;     //  账单对应的图片id
    private String payClass;    //

    private String address; //  位置
    private String member;  //  成员
    private String payWay;  //  支付方式
    private String note;    //  备注
    private long timeStamp; //  保存时的时间戳
    private String username;  //    用户
    private String date;    //  日期 2017-02-17
    private String month;   //  月份 2017-02
    private String year;    //  年份 2017

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getAccountClass() {
        return accountClass;
    }

    public void setAccountClass(int accountClass) {
        this.accountClass = accountClass;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(int picSrc) {
        this.picSrc = picSrc;
    }

    public String getPayClass() {
        return payClass;
    }

    public void setPayClass(String payClass) {
        this.payClass = payClass;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

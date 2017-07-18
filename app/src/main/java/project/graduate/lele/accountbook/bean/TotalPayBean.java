package project.graduate.lele.accountbook.bean;

/**
 * Created by zhpan on 2017/2/19.
 * 报表页面ListView对应的实体类
 */

public class TotalPayBean {
    private int picSrc; //  图片id
    private String name; // 类别名称
    private double percent; //  所占本月总支出/收入的百分比（percent=totalPay/total）
    private double total;   //  本月总支出
    private String color;   //  颜色（设置背景色）
    private double totalPay;//  该类的总支出


    public double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(double totalPay) {
        this.totalPay = totalPay;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(int picSrc) {
        this.picSrc = picSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

package project.graduate.lele.accountbook.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by zhpan on 2017/1/30.
 * 支出类别的实体类
 */

public class AccountClassBean extends DataSupport {
    @Column(unique = true, defaultValue = "unknown")
    private String payName;
    private int picId;  //  R.drawable.x5
    private String background;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }
}

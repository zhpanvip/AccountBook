package project.graduate.lele.accountbook.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by zhpan on 2017/2/12.
 * 支出类别的实体类
 */

public class AccountGainClassBean extends DataSupport {

    private int picId;//  R.drawable.x5
    @Column(unique = true, defaultValue = "unknown")
    private String gainName;
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

    public String getGainName() {
        return gainName;
    }

    public void setGainName(String gainName) {
        this.gainName = gainName;
    }
}

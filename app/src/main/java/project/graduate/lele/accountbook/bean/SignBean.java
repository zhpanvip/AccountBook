package project.graduate.lele.accountbook.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by zhpan on 2017/2/12.
 * 签到实体类
 */

public class SignBean extends DataSupport {
    @Column(unique = true, defaultValue = "unknown")
    private String signDate;    //  签到日期
    private boolean isSign;     //  今日是否签到
    private String username;    //  用户名

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public boolean isSign() {
        return isSign;
    }

    public void setSign(boolean sign) {
        isSign = sign;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

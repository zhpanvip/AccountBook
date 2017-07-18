package project.graduate.lele.accountbook.bean;

/**
 * Created by zhpan on 2017/1/14.
 *
 */

public class MyPayBean {
    private int type;
    private double account;
    private double eDu;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double geteDu() {
        return eDu;
    }

    public void seteDu(double eDu) {
        this.eDu = eDu;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }
}

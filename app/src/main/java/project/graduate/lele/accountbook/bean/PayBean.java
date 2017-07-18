package project.graduate.lele.accountbook.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;
import java.io.Serializable;

/**
 * Created by zhpan on 2017/2/1.
 *
 */

public class PayBean extends DataSupport implements Serializable {
    @Column(unique = true, defaultValue = "unknown")
    private String way;
    private double banlance;
    private int picSrc;
    private String background;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }



    public int getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(int picSrc) {
        this.picSrc = picSrc;
    }

    public double getBanlance() {
        return banlance;
    }

    public void setBanlance(double banlance) {
        this.banlance = banlance;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }
}

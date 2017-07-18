package project.graduate.lele.accountbook.bean;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by zhpan on 2017/2/11.
 * 用户实体类
 */
public class UserBean extends DataSupport {
    @Column(unique = true, defaultValue = "unknown")
    private String username;  //    用户名
    private String password;    //  密码
    private String email;   //  邮箱
    private String headPic; //  头像链接
    private int level;  //  等级
    private int grade;  //  积分

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

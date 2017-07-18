package project.graduate.lele.accountbook.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by zhpan on 2017/2/1.
 * 成员实体类
 */

public class MemberBean extends DataSupport {
    private String member;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }
}

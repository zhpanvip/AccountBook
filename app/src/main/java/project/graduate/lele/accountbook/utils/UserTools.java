package project.graduate.lele.accountbook.utils;

import android.content.Context;
import org.litepal.crud.DataSupport;
import java.util.List;
import project.graduate.lele.accountbook.bean.UserBean;

/**
 * Created by zhpan on 2017/2/11.
 */

public class UserTools {

    /**
     * 获取用户名
     *
     * @param context
     * @return
     */
    public static String getUsername(Context context) {

        UserBean userInfo = SharedPreferencesUtils.getUserInfo(context);
        return userInfo.getUsername();
    }

    /**
     * 获取用户密码
     *
     * @param context
     * @return
     */
    public static String getPassword(Context context) {
        UserBean userInfo = SharedPreferencesUtils.getUserInfo(context);
        return userInfo.getPassword();
    }

    //  获取用户实体类
    public static UserBean getUserBean(Context context) {
        String username = getUsername(context);
        List<UserBean> userBeen = DataSupport.where("username=?", username).find(UserBean.class);
        UserBean userBean = userBeen.get(0);

        return userBean;
    }

    /*public static int getLevel(Context context){
        UserBean userBean = getUserBean(context);
        return userBean.getLevel();
    }*/
    //  获取用户邮箱
    public static String getEmail(Context context) {
        UserBean userBean = getUserBean(context);
        return userBean.getEmail();
    }

    //  获取用户头像链接
    public static String getHeadPic(Context context) {
        UserBean userBean = getUserBean(context);
        return userBean.getHeadPic();
    }

    //  获取用户签到积分
    public static int getGrade(Context context) {
        UserBean userBean = getUserBean(context);
        return userBean.getGrade();
    }

}

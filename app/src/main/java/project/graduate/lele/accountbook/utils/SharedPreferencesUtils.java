package project.graduate.lele.accountbook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import project.graduate.lele.accountbook.bean.UserBean;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by lunlele on 2017/2/11.
 * SharedPreferences工具类
 */

public class SharedPreferencesUtils {
    /**
     * 保存登陆状态
     * @param context   调用这个方法的context
     * @param isLogin   是否登录
     */
    public static void saveLoginStatus(Context context,boolean isLogin){
        SharedPreferences sp=context.getSharedPreferences("accountBook",MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("isLogin",isLogin);
        edit.commit();
    }

    /**
     * 获取登陆状态
     */
    public static boolean getLoginStatus(Context context){
        SharedPreferences sp=context.getSharedPreferences("accountBook",MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin", false);
        return isLogin;
    }

    /**
     *  获取是否是第一次运行
     */
    public static boolean isFirst(Context context){
        SharedPreferences sp=context.getSharedPreferences("isFirst",MODE_PRIVATE);
        boolean isFirst = sp.getBoolean("isFirst",true);
        return isFirst;
    }
    /**
     *  设置为不是第一次运行
     */
    public static void setFirst(Context context){
        SharedPreferences sp=context.getSharedPreferences("isFirst",MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("isFirst",false);
        edit.commit();
    }

    /**
     * 保存用户信息
     * @param context
     * @param userBean  用户实体类
     */
    public static void saveUserInfo(Context context,UserBean userBean){
        SharedPreferences sp=context.getSharedPreferences("accountBook",MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();

        edit.putString("username",userBean.getUsername());
        edit.putString("password",userBean.getPassword());
        edit.commit();
    }

    /**
     * 获取用户信息
     * @param context
     * @return
     */
    public static UserBean getUserInfo(Context context){

        SharedPreferences sp=context.getSharedPreferences("accountBook",MODE_PRIVATE);
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");

        UserBean userBean=new UserBean();

        userBean.setUsername(username);
        userBean.setPassword(password);
        return userBean;
    }

    /**
     *     保存是否开启记账提醒
     */
    public static void saveRemindStatus(Context context,boolean isRemind){
        SharedPreferences sp=context.getSharedPreferences("remindStatus",MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("isRemind",isRemind);
        edit.commit();
    }
    /**
     *   获取是否开启记账提醒
     */
    public static boolean getRemindStatus(Context context){
        SharedPreferences sp=context.getSharedPreferences("accountBook",MODE_PRIVATE);
        boolean isRemind = sp.getBoolean("isRemind", false);
        return isRemind;
    }

    public static void saveYuE(Context context,float yuE){
        SharedPreferences sp=context.getSharedPreferences("accountBook",MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat("yuE",  yuE);
        edit.commit();
    }

    public static float getYuE(Context context){
        SharedPreferences sp=context.getSharedPreferences("accountBook",MODE_PRIVATE);
        float yuE = sp.getFloat("yuE", 0);
        return yuE;
    }

    public static void setISOpen(Context context,boolean isOpen){
        SharedPreferences sp=context.getSharedPreferences("accountBook",MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("isOpen",  isOpen);
        edit.commit();
    }

    public static boolean isOpen(Context context){
        SharedPreferences sp=context.getSharedPreferences("accountBook",MODE_PRIVATE);
        boolean isOpen = sp.getBoolean("isOpen", false);
        return isOpen;
    }

    //  退出登陆时清除SharedPreferences中有关用户的缓存数据（即保存到SharedPreferences时为名字为accountBook的缓存）
    public static void clearData(Context context){
        SharedPreferences sp=context.getSharedPreferences("accountBook",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

}

package project.graduate.lele.accountbook.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by zhpan on 2017/2/12.
 * 日期工具类
 */

public class DateUtils {
    /**
     * 获取年月日 格式yyyy/MM/dd
     */
    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 获取年月日 格式yyyy/MM/dd
     */
    public static String getDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 获取月份 yyyy-MM格式
     * @return
     */
    public static String getMonth() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 获取月份 格式yyyy-MM
     * @param date
     * @return
     */
    public static String getMonth(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     *  获取年份
     */
    public static String getYear() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    /**
     * 获取年月日时分 格式yyyy-MM-dd HH:mm
     */
    public static String timeStampToExactlyDate(long timeStamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date d = new Date(timeStamp);
        return simpleDateFormat.format(d);
    }

    /**
     * 根据开始时间和结束时间返回时间段内的月份集合
     *
     * @param beginDate
     * @param endDate
     * @return List
     */
    public static List<String> getMonthBetweenTwoDate(Date beginDate, Date endDate) {
        List<String> dateStrList=new ArrayList<>();
        dateStrList.add(getMonth(beginDate));

        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                Date time = cal.getTime();
                dateStrList.add(getMonth(time));
            } else {
                break;
            }
        }
        return dateStrList;
    }


}

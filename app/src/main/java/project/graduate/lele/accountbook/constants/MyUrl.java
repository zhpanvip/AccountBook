package project.graduate.lele.accountbook.constants;

/**
 * Created by zhpan on 2017/2/17.
 * 存放项目接口（链接）的类
 */

public class MyUrl {
    //  理财资讯页面请求网络的接口
    public static String INFO_URL="http://api.caijingmobile.com/news/article?last_id=&column_id=327";

    //  文章详情接口 参数id 从上个页面数据中获取
    public static String ACTICLE_URL="http://api.caijingmobile.com/api/2.0/cms_articles.php?id=324048&action=detail&appVer=62&bundleId=cn.com.caijing.android";

}

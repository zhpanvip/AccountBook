package project.graduate.lele.accountbook.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import java.util.List;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.bean.InfoBean;
import project.graduate.lele.accountbook.utils.DateUtils;

/**
 * 理财资讯文章详情
 */
public class ArticleActivity extends BaseActivity {

    private TextView mTvTitle;
    private TextView mTvDate;
    private TextView mTvContent;
    private InfoBean.DataBean.NormalArticlesBean.ArticleBeanX article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();

        setData();
    }

    private void setData() {
        Intent intent = getIntent();
        //  从Intent中获取资讯列表页面传递过来的文章实体类
        article = (InfoBean.DataBean.NormalArticlesBean.ArticleBeanX) intent.getSerializableExtra("article");
        mTvTitle.setText(article.getTitle());

        tvTitle.setText("资讯详情");
        tvTitle.setTextColor(Color.parseColor("#FFFFFF"));
        //  文章日期
        String date = DateUtils.timeStampToExactlyDate(article.getUpdated_time()*1000);
        mTvDate.setText(date);
        List<String> audio_content = article.getAudio_content();
        String content="";
        //  将文章拼接成带有p标签的段落的html格式
        for(int i=0;i<audio_content.size();i++){
            content+="<p>"+audio_content.get(i)+"</p>";
        }
        //  将文章内容转换成android分段
        mTvContent.setText(Html.fromHtml(content));
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvContent = (TextView) findViewById(R.id.tv_content);
    }
}

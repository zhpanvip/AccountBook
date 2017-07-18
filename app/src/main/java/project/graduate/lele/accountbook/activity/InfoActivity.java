package project.graduate.lele.accountbook.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.List;
import okhttp3.Call;
import project.graduate.lele.accountbook.R;
import project.graduate.lele.accountbook.adapter.InfoAdapter;
import project.graduate.lele.accountbook.bean.InfoBean;
import project.graduate.lele.accountbook.constants.MyUrl;

/**
 *  理财资讯
 */
public class InfoActivity extends BaseActivity {
    private ListView mListView;
    private InfoAdapter mAdapter;
    private InfoBean infoBean;
    private TextView mTvLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();
        setData();
        setListener();
    }

    private void setListener(){
        //  ListView条目点击的监听事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  通过parent获取到adapter
                InfoAdapter  adapter = (InfoAdapter) parent.getAdapter();
                //  从adapter中获取集合数据normalArticlesBeen数据内容等同于normal_articles集合中的数据
                List<InfoBean.DataBean.NormalArticlesBean> normalArticlesBeen = adapter.getmList();

                //  获取文章内容的实体类
                InfoBean.DataBean.NormalArticlesBean normalArticlesBean = normalArticlesBeen.get(position);
                InfoBean.DataBean.NormalArticlesBean.ArticleBeanX article = normalArticlesBean.getArticle();

                //  跳转到文章详情页面，并将文章实体类传递过去
                Intent intent=new Intent(InfoActivity.this,ArticleActivity.class);
                intent.putExtra("article",article);
                startActivity(intent);
            }
        });
    }

    private void setData() {
        tvTitle.setText("理财资讯");
        tvTitle.setTextColor(Color.parseColor("#FFFFFF"));
        //  刚进入时显示正在加载
        mTvLoading.setVisibility(View.VISIBLE);
        mTvLoading.setText("正在加载...");

        //  通过OkhttpUtils框架请求网络数据
        OkHttpUtils
                .get()
                .url(MyUrl.INFO_URL)
                .build()
                .execute(new StringCallback()
                {
                    /**
                     * 访问网络失败
                     */
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();//  打印错误信息
                        //  网络链接失败提示错误信息
                        mTvLoading.setText("网络链接错误，请稍后再试！");
                    }

                    /**
                     * 访问网络成功
                     */
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("Test",response);
                        //  获取数据成功隐藏正在加载
                        mTvLoading.setVisibility(View.GONE);
                        //  用Gson解析获取到的json字符串response
                        Gson gson=new Gson();
                        //  json字符串response解析成实体类
                        infoBean = gson.fromJson(response,InfoBean.class);
                        InfoBean.DataBean data = infoBean.getData();

                        //  1.获取到文章列表的集合数据
                        List<InfoBean.DataBean.NormalArticlesBean> normal_articles = data.getNormal_articles();
                        //  2.创建新闻列表的Adapter
                        mAdapter=new InfoAdapter(InfoActivity.this);
                        //  3.给Adapter设置数据
                        mAdapter.setmList(normal_articles);
                        //  4.给ListView设置Adapter
                        mListView.setAdapter(mAdapter);
                    }
                });

    }

    private  void initView(){
        mListView= (ListView) findViewById(R.id.lv_info);
        mTvLoading= (TextView) findViewById(R.id.tv_no_data);
    }

    public static void  start(Context context){
        Intent intent=new Intent(context, InfoActivity.class);
        context.startActivity(intent);
    }
}

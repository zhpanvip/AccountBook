package project.graduate.lele.accountbook.app;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhy.http.okhttp.OkHttpUtils;
import org.litepal.LitePal;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import project.graduate.lele.accountbook.service.LocationService;

/**
 * Created by zhpan on 2017/1/31.
 */

public class MainApplication extends Application {
    public LocationService locationService;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        //  初始化数据框架
        LitePal.initialize(this);
        //  初始化图片加载ImageLoader框架
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
              //  .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //  其他配置
                .build();
        //  初始化网络请求框架
        OkHttpUtils.initClient(okHttpClient);

        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
    }
}

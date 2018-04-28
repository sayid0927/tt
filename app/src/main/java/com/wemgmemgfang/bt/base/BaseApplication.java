package com.wemgmemgfang.bt.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Config;

import com.blankj.utilcode.utils.ThreadPoolUtils;
import com.blankj.utilcode.utils.Utils;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.bean.DownVideoBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerAppComponent;
import com.wemgmemgfang.bt.module.ApiModule;
import com.wemgmemgfang.bt.module.AppModule;
import com.wemgmemgfang.bt.utils.AppUtils;
import com.wemgmemgfang.bt.utils.CrashHandler;
import com.wemgmemgfang.bt.utils.GreenDaoUtil;
import com.wemgmemgfang.bt.utils.PreferUtil;
import com.wemgmemgfang.bt.utils.UmengUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseApplication extends Application {

    public  static BaseApplication baseApplication;

    private static AppComponent appComponent;

    public  static ThreadPoolUtils MAIN_EXECUTOR =   new ThreadPoolUtils(ThreadPoolUtils.Type.FixedThread,5);

    public  static List<DownVideoBean> downVideoBeanList = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        //将我们自己的MyApplication中的所有逻辑放在这里，例如初始化一些第三方
        initCompoent();
        Utils.init(this);
        AppUtils.init(this);
        PreferUtil.getInstance().init(this);
        UmengUtil.UmengUtilInit(this);
        UMShareAPI.get(this);//初始化sdk
        GreenDaoUtil.initDataBase(getApplicationContext());


//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "5abf0596b27b0a5f4600021a");
//        UMGameAgent.init( this );
//        MobclickAgent.setScenarioType(this ,  MobclickAgent.EScenarioType.E_UM_NORMAL);
//        UMConfigure.setLogEnabled(true);

//        CrashHandler.getInstance(this).init();

//        UMShareHelper.init(this);
//        UmengUtil.UmengUtilInit(this);
//        UmengUtil.onEvent("phoneInfo");


    }

    private void initCompoent() {
        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .appModule(new AppModule(this))
                .build();
    }

    //各个平台的配置
    static {
        //微信
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //新浪微博(第三个参数为回调地址)
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com/sina2/callback");
//        //QQ
//        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }


    /**
     * 获取BaseApplication实例
     * @return
     */

    public static BaseApplication getBaseApplication(){
        return baseApplication;
    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

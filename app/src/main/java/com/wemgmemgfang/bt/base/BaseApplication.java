package com.wemgmemgfang.bt.base;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.utils.ThreadPoolUtils;
import com.blankj.utilcode.utils.Utils;
import com.sh.zsh.code.umeng_sdk.UMShareHelper;
import com.wemgmemgfang.bt.bean.DownVideoBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerAppComponent;
import com.wemgmemgfang.bt.module.ApiModule;
import com.wemgmemgfang.bt.module.AppModule;
import com.wemgmemgfang.bt.utils.AppUtils;
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
        GreenDaoUtil.initDataBase(getApplicationContext());
        UMShareHelper.init(this);
//        UmengUtil.UmengUtilInit(this);
//        UmengUtil.onEvent("phoneInfo");
    }




    private void initCompoent() {
        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .appModule(new AppModule(this))
                .build();
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

}

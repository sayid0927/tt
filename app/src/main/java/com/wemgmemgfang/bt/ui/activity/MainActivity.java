package com.wemgmemgfang.bt.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.utils.AppUtils;
import com.blankj.utilcode.utils.LocationUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.TimeUtils;
import com.pgyersdk.update.PgyUpdateManager;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.RequestBody.AppInfoRequest;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.base.BaseFragmentPageAdapter;
import com.wemgmemgfang.bt.bean.Apk_UpdateBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.database.UserInfoDao;
import com.wemgmemgfang.bt.entity.DownVideoInfo;
import com.wemgmemgfang.bt.entity.UserInfo;
import com.wemgmemgfang.bt.presenter.contract.MainContract;
import com.wemgmemgfang.bt.presenter.impl.MainActivityPresenter;
import com.wemgmemgfang.bt.ui.fragment.DownRankingFragment;
import com.wemgmemgfang.bt.ui.fragment.FilmFragment;
import com.wemgmemgfang.bt.ui.fragment.HomeFragment;
import com.wemgmemgfang.bt.ui.fragment.MeFragment;
import com.wemgmemgfang.bt.utils.DownLoadHelper;
import com.wemgmemgfang.bt.utils.GreenDaoUtil;
import com.wemgmemgfang.bt.utils.PreferUtil;
import com.wemgmemgfang.bt.utils.RandomUtils;
import com.wemgmemgfang.bt.utils.UmengUtil;

import org.greenrobot.greendao.rx.RxDao;

import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.wemgmemgfang.bt.utils.DeviceUtils.ReadTxtFiles;

public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject
    MainActivityPresenter mPresenter;


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.vp)
    ViewPager vp;

    private BaseFragmentPageAdapter myAdapter;

    private ArrayList<String> mTitleList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    public static int FileSize;
    public static String Apk_Name;
    public static MainActivity mainActivity;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void attachView() {
        mPresenter.attachView(this);
    }

    @Override
    public void detachView() {
        mPresenter.detachView();
    }

    @Override
    public void initView() {



        UmengUtil.onEvent("MainActivity");
        setSwipeBackEnable(false);
        mTitleList.add(getString(R.string.DownRank));
        mTitleList.add(getString(R.string.Home));
        mTitleList.add(getString(R.string.Film));
        mTitleList.add(getString(R.string.Me));

        HomeFragment homeFragment = new HomeFragment();
        DownRankingFragment downRankingFragment = new DownRankingFragment();
        FilmFragment filmFragment = new FilmFragment();
        MeFragment meFragment = new MeFragment();
        mFragments.add(downRankingFragment);
        mFragments.add(homeFragment);
        mFragments.add(filmFragment);
        mFragments.add(meFragment);

        myAdapter = new BaseFragmentPageAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        vp.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(vp);

        PgyUpdateManager.setIsForced(true); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(this);
        mainActivity = this;

        String dd = PreferUtil.getInstance().getcrashTxtPath();
        if(dd!=null&& !dd.equals("")){
            UmengUtil.onCarshEvent(ReadTxtFiles(dd));
        }

        mPresenter.Apk_Update();
        UmengUtil.onEvent("DeviceInfo",getDeviceInfo(this));

    }

    public  void  startActivityin(Intent i,Activity a){
        startActivityIn(i,a);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void killAll() {
        super.killAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyUpdateManager.unregister();
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = getMac(context);

            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMac(Context context) {
        String mac = "";
        if (context == null) {
            return mac;
        }
        if (Build.VERSION.SDK_INT < 23) {
            mac = getMacBySystemInterface(context);
        } else {
            mac = getMacByJavaAPI();
            if (TextUtils.isEmpty(mac)){
                mac = getMacBySystemInterface(context);
            }
        }
        return mac;

    }

    @TargetApi(9)
    private static String getMacByJavaAPI() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();
                if ("wlan0".equals(netInterface.getName()) || "eth0".equals(netInterface.getName())) {
                    byte[] addr = netInterface.getHardwareAddress();
                    if (addr == null || addr.length == 0) {
                        return null;
                    }
                    StringBuilder buf = new StringBuilder();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    return buf.toString().toLowerCase(Locale.getDefault());
                }
            }
        } catch (Throwable e) {
        }
        return null;
    }

    private static String getMacBySystemInterface(Context context) {
        if (context == null) {
            return "";
        }
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (checkPermission(context, Manifest.permission.ACCESS_WIFI_STATE)) {
                WifiInfo info = wifi.getConnectionInfo();
                return info.getMacAddress();
            } else {
                return "";
            }
        } catch (Throwable e) {
            return "";
        }
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (context == null) {
            return result;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Throwable e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }


    @Override
    public void Apk_Update_Success(Apk_UpdateBean.DataBean dataBean) {

        AppInfoRequest appInfoRequest = new AppInfoRequest();
        appInfoRequest.setAppName(AppUtils.getAppName(this));
        appInfoRequest.setAppPackageName(AppUtils.getAppPackageName(this));
        appInfoRequest.setAppVersionCode(AppUtils.getAppVersionCode(this));
        appInfoRequest.setAppVersionName(AppUtils.getAppVersionName(this));
        appInfoRequest.setDate(TimeUtils.getNowTimeString());

        mPresenter.Pust_App_Info(appInfoRequest);

    }

    @Override
    public void Pust_App_Info_Success() {

    }
}

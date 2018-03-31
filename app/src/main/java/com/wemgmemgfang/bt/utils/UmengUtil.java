package com.wemgmemgfang.bt.utils;

import android.content.Context;

import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.PhoneUtils;
import com.umeng.analytics.MobclickAgent;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.wemgmemgfang.bt.utils.DeviceUtils.ReadTxtFile;
import static com.wemgmemgfang.bt.utils.DeviceUtils.ReadTxtFiles;

/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class UmengUtil {

    private static UmengUtil umengUtil;
    private static Context context;
    private static HashMap<String, String> maps;

    private UmengUtil(Context context) {
        this.context = context;
        maps = new HashMap<>();
        maps.put("判断设备是否是手机", String.valueOf(PhoneUtils.isPhone()));
        maps.put("判断设备是否root", String.valueOf(com.blankj.utilcode.utils.DeviceUtils.isDeviceRooted()));
        maps.put("当前网络类型", String.valueOf(NetworkUtils.getNetworkType()));
        maps.put("设备型号", com.blankj.utilcode.utils.DeviceUtils.getModel());
        maps.put("设备系统版本号", String.valueOf(com.blankj.utilcode.utils.DeviceUtils.getSDKVersion()));
    }

    public static synchronized void UmengUtilInit(Context context) {
        if (umengUtil == null) {
            umengUtil = new UmengUtil(context);
        }
    }

    public static void onEvent(String EventsName) {
        MobclickAgent.onEvent(context, EventsName, maps);
    }

    public static void onCarshEvent(String carsh) {

        List<String> txtLine = new ArrayList<>();
        String textPath = PreferUtil.getInstance().getcrashTxtPath();
        HashMap<String, String> carshMaps = new HashMap<>();
        if (!textPath.equals("")) {
            txtLine = ReadTxtFile(textPath);
            if (txtLine.size() != 0) {
                for (int i=0;i<txtLine.size();i++) {
                    carshMaps.put(String.valueOf(i),txtLine.get(i));
                }
            }
            maps.put("carsh", carsh);
            MobclickAgent.onEvent(context, "carsh", carshMaps);
        }
    }
}

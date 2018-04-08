package com.wemgmemgfang.bt.RequestBody;

/**
 * sayid ....
 * Created by wengmf on 2018/4/8.
 */

public class AppInfoRequest {

    String date;
    String AppPackageName;
    String AppName;
    String AppVersionName;
    int AppVersionCode;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAppPackageName() {
        return AppPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        AppPackageName = appPackageName;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getAppVersionName() {
        return AppVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        AppVersionName = appVersionName;
    }

    public int getAppVersionCode() {
        return AppVersionCode;
    }

    public void setAppVersionCode(int appVersionCode) {
        AppVersionCode = appVersionCode;
    }

    @Override
    public String toString() {
        return "AppInfoRequest{" +
                "date=" + date +
                ", AppPackageName='" + AppPackageName + '\'' +
                ", AppName='" + AppName + '\'' +
                ", AppVersionName='" + AppVersionName + '\'' +
                ", AppVersionCode='" + AppVersionCode + '\'' +
                '}';
    }


}

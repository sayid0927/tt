/**
 * Copyright(C)2012-2013 深圳市掌星立意科技有限公司版权所有
 * 创 建 人:Jacky
 * 修 改 人:
 * 创 建日期:2013-7-25
 * 描    述:xml储存数据
 * 版 本 号:
 */
package com.wemgmemgfang.bt.utils;

import android.content.Context;
import android.content.SharedPreferences;


public final class PreferUtil {

    public static PreferUtil INSTANCE;
    private static SharedPreferences mPrefer;
    private static final String APP_NAME = "com.wengmengfan.btwang";

    private static final String PLAY_PATH = "play_path";
    private static final String PLAY_TITLE = "play_title";
    private static final String PLAY_IMGURL= "play_imgurl";

    public void setPlayTitle(String flag){
        putString(PLAY_TITLE, flag);
    }

    public  String getPlayTitle(){
        return  getString(PLAY_TITLE,"");
    }


    public void setPlayPath(String flag){
        putString(PLAY_PATH, flag);
    }

    public  String getPlayPath(){
        return  getString(PLAY_PATH,"");
    }



    public void setPlayimgUrl(String flag){
        putString(PLAY_IMGURL, flag);
    }

    public  String getPlayimgUrl(){
        return  getString(PLAY_IMGURL,"");
    }


    private PreferUtil() {
    }

    public static PreferUtil getInstance() {
        if (INSTANCE == null) {
            return new PreferUtil();
        }
        return INSTANCE;
    }

    public void init(Context ctx) {
        mPrefer = ctx.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE
                | Context.MODE_PRIVATE);
        mPrefer.edit().commit();
    }


    public String getString(String key, String defValue) {
        return mPrefer.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mPrefer.getInt(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPrefer.getBoolean(key, defValue);
    }

    public void putString(String key, String value) {
        mPrefer.edit().putString(key, value).commit();
    }

    public void putInt(String key, int value) {
        mPrefer.edit().putInt(key, value).commit();
    }

    public void putBoolean(String key, boolean value) {
        mPrefer.edit().putBoolean(key, value).commit();
    }

    public void putLong(String key, long value) {
        mPrefer.edit().putLong(key, value).commit();
    }

    public long getLong(String key, long defValue) {
        return mPrefer.getLong(key, defValue);
    }

    public void removeKey(String key) {
        mPrefer.edit().remove(key).commit();
    }


}

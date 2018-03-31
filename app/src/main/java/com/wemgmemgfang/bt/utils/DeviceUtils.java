package com.wemgmemgfang.bt.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.wemgmemgfang.bt.base.Constant;
import com.wemgmemgfang.bt.contentprovider.PlayController;
import com.wemgmemgfang.bt.contentprovider.PlayProvider;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs on 2016/7/7.
 */
public class DeviceUtils {
    /*
     * 获取应用名
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            //获取包管理者
            PackageManager pm = context.getPackageManager();
            //获取packageInfo
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            //获取versionName
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionName;
    }

    /*
     * 获取应用版本
     */
    public static int getVersionCode(Context context) {

        int versionCode = 0;
        try {
            //获取包管理者
            PackageManager pm = context.getPackageManager();
            //获取packageInfo
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            //获取versionCode
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionCode;
    }


    public static String getSDPath() {
        String sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File
                    .separator + Constant.FILEPATH;
        } else {
            File file = new File("/mnt/sdcard/" + Constant.FILEPATH);//创建文件
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    sdDir = file.getAbsolutePath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return file.getAbsolutePath();
            } else {
                sdDir = file.getAbsolutePath();
            }
            return sdDir;
        }
        return sdDir;
    }

    public static String getSDPath(String fName) {
        String sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File
                    .separator + Constant.FILEPATH + "/" + fName;
        } else {
            File file = new File("/mnt/sdcard/" + Constant.FILEPATH + "/" + fName);//创建文件
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    sdDir = file.getAbsolutePath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return file.getAbsolutePath();
            } else {
                sdDir = file.getAbsolutePath();
            }
            return sdDir;
        }
        return sdDir;
    }

    public static String getSDVideoPath(String fName) {
        String sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File
                    .separator + "Video" + "/" + fName;
        } else {
            File file = new File("/mnt/sdcard/" + "Video" + "/" + fName);//创建文件
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    sdDir = file.getAbsolutePath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return file.getAbsolutePath();
            } else {
                sdDir = file.getAbsolutePath();
            }
            return sdDir;
        }
        return sdDir;
    }


    public static PlayController getPlayStart(Context context, int id) {

        PlayController playController = new PlayController();
        Uri playUri = PlayProvider.PLAY_CONTENT_URI;
        Cursor playCursor = context.getContentResolver().query(playUri, new String[]{"_id", "type", "isPlay"}, null, null, null);

        if (playCursor != null) {
            while (playCursor.moveToNext()) {
                if (playCursor.getInt(0) == id) {
                    playController.Id = playCursor.getInt(0);
                    playController.type = playCursor.getString(1);
                    playController.isPlay = playCursor.getString(2);
                }
            }
            playCursor.close();
        }
        return playController;
    }

    public static void updatePlayStart(Context context, int id, String falg) {
        Uri bookUri = PlayProvider.PLAY_CONTENT_URI;
        ContentValues values = new ContentValues();
        values.put("isPlay", falg);
        try {
            int c = context.getContentResolver().update(bookUri, values, "_id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.toString();
        }
    }

    public static long getSDFreeSize() {
        if (ExistSDCard()) {
            //取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            //获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            //空闲的数据块的数量
            long freeBlocks = sf.getAvailableBlocks();
            //返回SD卡空闲大小
            return freeBlocks * blockSize;  //单位Byte
            //return (freeBlocks * blockSize)/1024;   //单位KB
//            return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
        }
        return 0;
    }

    public static boolean ExistSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }


    public static List<String> ReadTxtFile(String strFilePath) {
        String path = strFilePath;
        List<String> newList = new ArrayList<String>();
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            Log.d("TestFile", "The File doesn't not exist.");
        } else {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
                String line;
                //分行读取
                while ((line = reader.readLine()) != null) {
                    String temp3 = EncodingUtils.getString(line.getBytes(),"UTF-8");
                    newList.add(temp3);
                }
                reader.close();
            } catch (java.io.FileNotFoundException e) {
                Log.d("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.d("TestFile", e.getMessage());
            }
        }
        return newList;
    }


    public static String ReadTxtFiles(String strFilePath) {
        String path = strFilePath;
        StringBuffer newList = new StringBuffer();
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            Log.d("TestFile", "The File doesn't not exist.");
        } else {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB2312"));
                String line;
                //分行读取
                while ((line = reader.readLine()) != null) {
                    String temp3 = EncodingUtils.getString(line.getBytes(),"UTF-8");
                    newList.append(temp3+"\n");
                }
                reader.close();
            } catch (java.io.FileNotFoundException e) {
                Log.d("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.d("TestFile", e.getMessage());
            }
        }
        return newList.toString();
    }

}



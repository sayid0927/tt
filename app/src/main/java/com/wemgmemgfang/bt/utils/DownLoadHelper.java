package com.wemgmemgfang.bt.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.utils.LogUtils;
import com.wemgmemgfang.bt.base.Constant;
import com.wemgmemgfang.bt.database.CollectionInfoDao;
import com.wemgmemgfang.bt.database.DownVideoInfoDao;
import com.wemgmemgfang.bt.entity.DownVideoInfo;
import com.wemgmemgfang.bt.service.DownTorrentVideoService;
import com.wemgmemgfang.bt.service.DownVoideService;

import java.io.File;
import java.io.Serializable;

/**
 * sayid ....
 * Created by wengmf on 2018/3/29.
 */

public class DownLoadHelper {


    private volatile static DownLoadHelper SINGLETANCE;
    private DownVideoInfoDao downVideoInfoDao;

    private DownLoadHelper() {
        downVideoInfoDao = GreenDaoUtil.getDaoSession().getDownVideoInfoDao();
    }

    public static DownLoadHelper getInstance() {
        if (SINGLETANCE == null) {
            synchronized (DownLoadHelper.class) {
                if (SINGLETANCE == null) {
                    SINGLETANCE = new DownLoadHelper();
                }
            }
        }
        return SINGLETANCE;
    }

    public synchronized void submit(Context context, DownVideoInfo downVideoInfo) {
        DownVideoInfo d= downVideoInfoDao.queryBuilder().where(DownVideoInfoDao.Properties.PlayPath.eq(downVideoInfo.getPlayPath())).unique();
        if(d==null){
            if(!DownVoideService.isDown){
                Bundle bundle = new Bundle();
                bundle.putString("PlayPath", downVideoInfo.getPlayPath());
                Intent intent = new Intent(context, DownVoideService.class);
                intent.putExtras(bundle);
                context.startService(intent);
                addTask(downVideoInfo);
            }
        }else {
            ToastUtils.showLongToast("已在下载队列中");
        }
    }
    public void addTask(DownVideoInfo downVideoInfo) {
        downVideoInfoDao.insert(downVideoInfo);
        ToastUtils.showLongToast("添加到下载队列中");
    }
}

package com.wemgmemgfang.bt.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wemgmemgfang.bt.database.DownVideoInfoDao;
import com.wemgmemgfang.bt.entity.DownVideoInfo;
import com.wemgmemgfang.bt.service.DownVoideService;

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

    public void submit(Context context, DownVideoInfo downVideoInfo) {

        DownVideoInfo d = downVideoInfoDao.queryBuilder().where(DownVideoInfoDao.Properties.PlayPath.eq(downVideoInfo.getPlayPath())).unique();

        if (d == null) {
            addTask(downVideoInfo);
            if(!DownVoideService.isDown) {
                Bundle bundle = new Bundle();
                bundle.putString("PlayPath", downVideoInfo.getPlayPath());
                bundle.putString("state", downVideoInfo.getState());
                Intent intent = new Intent(context, DownVoideService.class);
                intent.putExtras(bundle);
                context.startService(intent);
            }
        } else {
            if (d.getPlayPath().equals(downVideoInfo.getPlayPath()) && !downVideoInfo.getState().equals(d.getState())) {
                downVideoInfo.setId(d.getId());
                downVideoInfoDao.update(downVideoInfo);
                Bundle bundle = new Bundle();
                bundle.putString("PlayPath", downVideoInfo.getPlayPath());
                bundle.putString("state", downVideoInfo.getState());
                Intent intent = new Intent(context, DownVoideService.class);
                intent.putExtras(bundle);
                context.startService(intent);
            }
        }
    }

    public void addTask(DownVideoInfo downVideoInfo) {
        downVideoInfoDao.insert(downVideoInfo);
        ToastUtils.showLongToast("添加到下载队列中");
    }
}

package com.wemgmemgfang.bt.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.wemgmemgfang.bt.database.DownVideoInfoDao;
import com.wemgmemgfang.bt.entity.DownVideoInfo;
import com.wemgmemgfang.bt.utils.DeviceUtils;
import com.wemgmemgfang.bt.utils.GreenDaoUtil;
import com.wemgmemgfang.bt.view.CommonDialog;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * sayid ....
 * Created by wengmf on 2018/3/29.
 */

public class DownVoideService extends Service {


    public static boolean isDown;
    private long taskId;
    private CommonDialog commonDialog;
    private DownVideoInfoDao downVideoInfoDao;
    private String PlayPath;

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                switch (msg.what) {
                    case 0:
                        XLTaskInfo taskInfo = XLTaskHelper.instance().getTaskInfo(taskId);
                        Log.e("TAG",taskInfo.mFileName+"\n"+taskInfo.mDownloadSize);
                        if (taskInfo.mFileSize > DeviceUtils.getSDFreeSize()) {
                            XLTaskHelper.instance().stopTask(taskId);
                            commonDialog.show();
                        } else {
                            DownVideoInfo downVideoInfo = downVideoInfoDao.queryBuilder().where(DownVideoInfoDao.Properties.PlayPath.eq(PlayPath)).unique();
                            switch (taskInfo.mTaskStatus) {
                                case 0:
                                    isDown = false;
                                    downVideoInfo.setMTaskStatus(taskInfo.mTaskStatus);
                                    downVideoInfoDao.update(downVideoInfo);
                                    ToastUtils.showLongToast("服务器太忙,请稍会再试");
                                    XLTaskHelper.instance().stopTask(taskId);
                                    break;
                                case 1:
                                    isDown = true;
                                    downVideoInfo.setMTaskStatus(taskInfo.mTaskStatus);
                                    downVideoInfo.setMDownloadSize(taskInfo.mDownloadSize);
                                    downVideoInfo.setMFileSize(taskInfo.mFileSize);
                                    downVideoInfoDao.update(downVideoInfo);
                                    EventBus.getDefault().post(downVideoInfo);
                                    handler.sendMessageDelayed(handler.obtainMessage(0, taskId), 1000);

                                    break;
                                case 2:
                                    isDown = false;
                                    Logger.e("taskInfo.mDownloadSize>>>  " + taskInfo.mDownloadSize + "\n" + "taskInfo.mFileSize>>  " + taskInfo.mFileSize);
                                    downVideoInfo.setMTaskStatus(taskInfo.mTaskStatus);
                                    downVideoInfo.setMDownloadSize(taskInfo.mDownloadSize);
                                    downVideoInfo.setMFileSize(taskInfo.mFileSize);
                                    downVideoInfoDao.update(downVideoInfo);
                                    EventBus.getDefault().post(downVideoInfo);
                                    handler.sendMessageDelayed(handler.obtainMessage(1, taskId), 1000);
                                    ToastUtils.showLongToast("下载完成");

                                    break;

                                case 3:
                                    isDown = false;
                                    downVideoInfo.setMTaskStatus(taskInfo.mTaskStatus);
                                    downVideoInfoDao.update(downVideoInfo);
                                    XLTaskHelper.instance().stopTask(taskId);
                                    ToastUtils.showLongToast("下载失败,请稍会再试");
                                    break;
                            }
                        }
                        break;

                    case 1:

                        List<DownVideoInfo> downVideoInfoList = downVideoInfoDao.loadAll();
                        if (downVideoInfoList != null && downVideoInfoList.size() != 0) {
                            for (int i = 0; i < downVideoInfoList.size(); i++) {
                                DownVideoInfo d = downVideoInfoList.get(i);
                                if (d.getMTaskStatus() != 2) {
                                    try {
                                        if (d.getPlayPath().startsWith("thunder://")) {
                                            taskId = XLTaskHelper.instance().addThunderTask(d.getPlayPath(), d.getSaveVideoPath(), d.getPlayTitle());
                                        } else {
                                            taskId = XLTaskHelper.instance().addTorrentTask(d.getPlayPath(), d.getSaveVideoPath(), null);
                                        }
                                        handler.sendMessage(handler.obtainMessage(0));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                }
                            }
                        }
                        break;

                }
            }
        }
    };


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null != intent) {
            try {
                downVideoInfoDao = GreenDaoUtil.getDaoSession().getDownVideoInfoDao();
                commonDialog = new CommonDialog(DownVoideService.this, "存储空间不足");
                Bundle bundle = intent.getExtras();
                PlayPath = bundle.getString("PlayPath");
                DownVideoInfo downVideoInfo = downVideoInfoDao.queryBuilder().where(DownVideoInfoDao.Properties.PlayPath.eq(PlayPath)).unique();
                String state = downVideoInfo.getState();
                if (state != null) {
                    switch (state) {
                        case "开始":

                            if (PlayPath.startsWith("thunder://")) {
                                taskId = XLTaskHelper.instance().addThunderTask(PlayPath, downVideoInfo.getSaveVideoPath(), downVideoInfo.getPlayTitle());
                            } else {
                                taskId = XLTaskHelper.instance().addTorrentTask(PlayPath, downVideoInfo.getSaveVideoPath(), null);
                            }
                            downVideoInfo.setTaskId(taskId);
                            downVideoInfoDao.update(downVideoInfo);
                            PlayPath = downVideoInfo.getPlayPath();
                            handler.sendMessage(handler.obtainMessage(0));

                            break;
                        case "停止":
                            XLTaskHelper.instance().stopTask(downVideoInfo.getTaskId());
                            EventBus.getDefault().post(downVideoInfo);

                            break;
                        case "删除":
                            downVideoInfoDao.delete(downVideoInfo);
                            XLTaskHelper.instance().deleteTask(downVideoInfo.getTaskId(), downVideoInfo.getSaveVideoPath());
                            EventBus.getDefault().post(downVideoInfo);
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

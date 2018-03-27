package com.wemgmemgfang.bt.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.blankj.utilcode.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.wemgmemgfang.bt.base.BaseApplication;
import com.wemgmemgfang.bt.bean.DownVideoBean;
import com.wemgmemgfang.bt.utils.DeviceUtils;
import com.wemgmemgfang.bt.utils.NotificationHandler;
import com.wemgmemgfang.bt.utils.PreferUtil;
import com.wemgmemgfang.bt.view.CommonDialog;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * sayid ....
 * Created by wengmf on 2018/3/14.
 */

public class DownTorrentVideoService extends Service {


    private String playPath, playTitle, videoPath, PlayimgUrl;
    private NotificationCompat.Builder nBuilder;
    private CommonDialog commonDialog;
    private NotificationHandler nHandler;
    private long taskId = -1;
    private boolean isDown = false;

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                XLTaskInfo taskInfo = XLTaskHelper.instance().getTaskInfo(taskId);
                if (taskInfo.mFileSize > DeviceUtils.getSDFreeSize()) {
                    XLTaskHelper.instance().stopTask(taskId);
                    nHandler.cancelNotification((int) taskId);
                    commonDialog.show();
                }
                switch (taskInfo.mTaskStatus) {
                    case 0:

                        isDown = false;
                        Logger.e("连接中");
                        ToastUtils.showLongToast("服务器太忙,请稍会再试");
                        XLTaskHelper.instance().stopTask(taskId);
                        nHandler.cancelNotification((int) taskId);

                        break;
                    case 1:
                        isDown = true;
                        if (taskInfo.mDownloadSize > 0)
                            nHandler.updateProgressNotification(nBuilder, (int) (taskInfo.mDownloadSize * 100 / taskInfo.mFileSize), (int) taskId);
                        handler.sendMessageDelayed(handler.obtainMessage(0, taskId), 1000);

                        for (DownVideoBean d : BaseApplication.downVideoBeanList) {
                            if (d.getTaskId() == taskId) {
                                d.setmDownloadSize(taskInfo.mDownloadSize);
                                d.setmFileSize(taskInfo.mFileSize);
                                d.setmTaskStatus(taskInfo.mTaskStatus);
                                EventBus.getDefault().post(d);
                            }
                        }
                        break;
                    case 2:

                        isDown = false;
                        nHandler.cancelNotification((int) taskId);
                        Logger.e("下载完成");
                        if( BaseApplication.downVideoBeanList.size()!=0) {
                            for (int d =0;d<BaseApplication.downVideoBeanList.size();d++) {
                                if (BaseApplication.downVideoBeanList.get(d).getTaskId() == taskId) {
                                    BaseApplication.downVideoBeanList.remove(BaseApplication.downVideoBeanList.get(d));
                                }
                            }
                            if (BaseApplication.downVideoBeanList.size() != 0) {
                                String path = BaseApplication.downVideoBeanList.get(0).getPlayPath();
                                String title = BaseApplication.downVideoBeanList.get(0).getPlayTitle();
                                String savePath = DeviceUtils.getSDVideoPath(title);
                                try {
                                    if (path.startsWith("thunder://")) {
                                        taskId = XLTaskHelper.instance().addThunderTask(path, savePath, title);
                                    } else {
                                        taskId = XLTaskHelper.instance().addTorrentTask(path, savePath, null);
                                    }
                                    BaseApplication.downVideoBeanList.get(0).setTaskId(taskId);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                nBuilder = nHandler.createProgressNotification(DownTorrentVideoService.this, playTitle, (int) taskId);
                                handler.sendMessage(handler.obtainMessage(0));
                            }
                        }

                        break;

                    case 3:
                        isDown = false;
                        XLTaskHelper.instance().stopTask(taskId);
                        nHandler.cancelNotification((int) taskId);
                        ToastUtils.showLongToast(playTitle + "下载失败,请稍会再试");
                        Logger.e("失败");
                        break;
                }
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        playPath = PreferUtil.getInstance().getPlayPath();
        playTitle = PreferUtil.getInstance().getPlayTitle();
        PlayimgUrl = PreferUtil.getInstance().getPlayimgUrl();
        videoPath = DeviceUtils.getSDVideoPath(playTitle);
        DownVideoBean downVideoBean = new DownVideoBean();

        if (!isDown) {
            try {
                if (playPath.startsWith("thunder://")) {
                    taskId = XLTaskHelper.instance().addThunderTask(playPath, videoPath, playTitle);
                } else {
                    taskId = XLTaskHelper.instance().addTorrentTask(playPath, videoPath, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            nBuilder = nHandler.createProgressNotification(DownTorrentVideoService.this, playTitle, (int) taskId);
            handler.sendMessage(handler.obtainMessage(0));
            downVideoBean.setTaskId(taskId);
        } else {
            downVideoBean.setTaskId(-1);
        }

        downVideoBean.setmTaskStatus(0);
        downVideoBean.setPlayimgUrl(PlayimgUrl);
        downVideoBean.setPlayPath(playPath);
        downVideoBean.setPlayTitle(playTitle);

        BaseApplication.downVideoBeanList.add(downVideoBean);

        return super.onStartCommand(intent, flags, startId);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();
        XLTaskHelper.init(getApplicationContext());
        nHandler = NotificationHandler.getInstance(this);
        commonDialog = new CommonDialog(DownTorrentVideoService.this, "存储空间不足");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}

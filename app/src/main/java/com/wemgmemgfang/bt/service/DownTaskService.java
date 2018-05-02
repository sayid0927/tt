package com.wemgmemgfang.bt.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.database.DownVideoInfoDao;
import com.wemgmemgfang.bt.entity.DownVideoInfo;
import com.wemgmemgfang.bt.utils.GreenDaoUtil;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/4/29 0029.
 */

public class DownTaskService extends Service {

    private DownVideoInfoDao downVideoInfoDao;
    private String downPath;
    private long taskId;


    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                switch (msg.what) {
                    case 0:
                        long taskId = (long) msg.obj;
                        XLTaskInfo taskInfo = XLTaskHelper.instance().getTaskInfo(taskId);
                        LogUtils.e( "mTaskStatus:" + taskInfo.mTaskStatus+"\n"
                                +"fileSize:" + taskInfo.mFileSize+"\n"
                                + " downSize:" + taskInfo.mDownloadSize+"\n"
                                +"mDownloadSpeed"+taskInfo.mDownloadSpeed);
                        DownVideoInfo d = downVideoInfoDao.queryBuilder().where(DownVideoInfoDao.Properties.PlayPath.eq(downPath)).unique();

                        switch (taskInfo.mTaskStatus) {

                            case 0:
//                                d.setId(d.getId());
//                                d.setMFileSize(taskInfo.mFileSize);
//                                d.setMDownloadSize(taskInfo.mDownloadSize);
//                                d.setMTaskStatus(taskInfo.mTaskStatus);
//                                d.setState(getString(R.string.downError));
//                                d.setTaskId(taskId);
//                                downVideoInfoDao.update(d);
//                                EventBus.getDefault().post(d);
//                                ToastUtils.showLongToast("服务器太忙,请稍会再试");
                                break;

                            case 1:

                                d.setId(d.getId());
                                d.setMFileSize(taskInfo.mFileSize);
                                d.setMDownloadSize(taskInfo.mDownloadSize);
                                d.setMTaskStatus(taskInfo.mTaskStatus);
                                d.setMDownloadSpeed(taskInfo.mDownloadSpeed);
                                d.setState(getString(R.string.downIng));
                                d.setTaskId(taskId);
                                downVideoInfoDao.update(d);
                                EventBus.getDefault().post(d);
                                handler.sendMessageDelayed(handler.obtainMessage(0, taskId), 1000);

                                break;

                            case 2:
                                d.setId(d.getId());
                                d.setMFileSize(taskInfo.mFileSize);
                                d.setMDownloadSize(taskInfo.mDownloadSize);
                                d.setMTaskStatus(taskInfo.mTaskStatus);
                                d.setState(getString(R.string.downSuccess));
                                d.setTaskId(taskId);
                                downVideoInfoDao.update(d);
                                EventBus.getDefault().post(d);
                                ToastUtils.showLongToast(d.getPlayTitle()+"下载完成");
                                break;

                            case 3:
                                d.setId(d.getId());
                                d.setMFileSize(taskInfo.mFileSize);
                                d.setMDownloadSize(taskInfo.mDownloadSize);
                                d.setMTaskStatus(taskInfo.mTaskStatus);
                                d.setState(getString(R.string.downError));
                                downVideoInfoDao.update(d);
                                EventBus.getDefault().post(d);
                                ToastUtils.showLongToast("服务器太忙,请稍会再试");
                                break;

                        }

                        break;
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        XLTaskHelper.init(getApplicationContext());
        downVideoInfoDao = GreenDaoUtil.getDaoSession().getDownVideoInfoDao();
        Bundle bundle = intent.getExtras();
        downPath = bundle.getString("PlayPath");
        DownVideoInfo downVideoInfo = downVideoInfoDao.queryBuilder().where(DownVideoInfoDao.Properties.PlayPath.eq(downPath)).unique();
        try {
            taskId = XLTaskHelper.instance().addThunderTasks(downPath, downVideoInfo.getSaveVideoPath(), null);
            handler.sendMessage(handler.obtainMessage(0, taskId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}

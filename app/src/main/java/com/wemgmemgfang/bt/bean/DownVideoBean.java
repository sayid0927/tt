package com.wemgmemgfang.bt.bean;

/**
 * sayid ....
 * Created by wengmf on 2018/3/22.
 */

public class DownVideoBean {
    private String playPath;
    private String playTitle;
    private String PlayimgUrl;
    private int  mTaskStatus;
    private  long taskId;
    private long mFileSize;
    private long mDownloadSize;

    public int getmTaskStatus() {
        return mTaskStatus;
    }

    public void setmTaskStatus(int mTaskStatus) {
        this.mTaskStatus = mTaskStatus;
    }

    public long getmFileSize() {
        return mFileSize;
    }

    public void setmFileSize(long mFileSize) {
        this.mFileSize = mFileSize;
    }

    public long getmDownloadSize() {
        return mDownloadSize;
    }

    public void setmDownloadSize(long mDownloadSize) {
        this.mDownloadSize = mDownloadSize;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getPlayPath() {
        return playPath;
    }

    public void setPlayPath(String playPath) {
        this.playPath = playPath;
    }

    public String getPlayTitle() {
        return playTitle;
    }

    public void setPlayTitle(String playTitle) {
        this.playTitle = playTitle;
    }

    public String getPlayimgUrl() {
        return PlayimgUrl;
    }

    public void setPlayimgUrl(String playimgUrl) {
        PlayimgUrl = playimgUrl;
    }


    @Override
    public String toString() {
        return "DownVideoBean{" +
                "playPath='" + playPath + '\'' +
                ", playTitle='" + playTitle + '\'' +
                ", PlayimgUrl='" + PlayimgUrl + '\'' +
                ", taskId=" + taskId +
                ", mFileSize=" + mFileSize +
                ", mDownloadSize=" + mDownloadSize +
                '}';
    }
}

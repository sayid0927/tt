package com.wemgmemgfang.bt.bean;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class MessageEventBean {
    private String mFileName;
    private long mFileSize;
    private long mDownloadSize;


    public String getmFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
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

    @Override
    public String toString() {
        return "MessageEventBean{" +
                "mFileName='" + mFileName + '\'' +
                ", mFileSize=" + mFileSize +
                ", mDownloadSize=" + mDownloadSize +
                '}';
    }
}

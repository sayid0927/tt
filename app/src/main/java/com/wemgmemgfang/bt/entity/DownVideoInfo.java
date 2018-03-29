package com.wemgmemgfang.bt.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * sayid ....
 * Created by wengmf on 2018/3/22.
 */
@Entity
public class DownVideoInfo {
    @Id(autoincrement = true) //自增
    private Long id; //主键
    private String playPath;
    private String playTitle;
    private String PlayimgUrl;
    private String hrefUrl;
    private  String hrefTitle;
    private  String type;
    private  String state;
    private int  mTaskStatus;
    private  long taskId;
    private long mFileSize;
    private String saveVideoPath;
    private long mDownloadSize;
    @Generated(hash = 1136827977)
    public DownVideoInfo(Long id, String playPath, String playTitle,
            String PlayimgUrl, String hrefUrl, String hrefTitle, String type,
            String state, int mTaskStatus, long taskId, long mFileSize,
            String saveVideoPath, long mDownloadSize) {
        this.id = id;
        this.playPath = playPath;
        this.playTitle = playTitle;
        this.PlayimgUrl = PlayimgUrl;
        this.hrefUrl = hrefUrl;
        this.hrefTitle = hrefTitle;
        this.type = type;
        this.state = state;
        this.mTaskStatus = mTaskStatus;
        this.taskId = taskId;
        this.mFileSize = mFileSize;
        this.saveVideoPath = saveVideoPath;
        this.mDownloadSize = mDownloadSize;
    }
    @Generated(hash = 773913658)
    public DownVideoInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPlayPath() {
        return this.playPath;
    }
    public void setPlayPath(String playPath) {
        this.playPath = playPath;
    }
    public String getPlayTitle() {
        return this.playTitle;
    }
    public void setPlayTitle(String playTitle) {
        this.playTitle = playTitle;
    }
    public String getPlayimgUrl() {
        return this.PlayimgUrl;
    }
    public void setPlayimgUrl(String PlayimgUrl) {
        this.PlayimgUrl = PlayimgUrl;
    }
    public int getMTaskStatus() {
        return this.mTaskStatus;
    }
    public void setMTaskStatus(int mTaskStatus) {
        this.mTaskStatus = mTaskStatus;
    }
    public long getTaskId() {
        return this.taskId;
    }
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
    public long getMFileSize() {
        return this.mFileSize;
    }
    public void setMFileSize(long mFileSize) {
        this.mFileSize = mFileSize;
    }
    public long getMDownloadSize() {
        return this.mDownloadSize;
    }
    public void setMDownloadSize(long mDownloadSize) {
        this.mDownloadSize = mDownloadSize;
    }
    public String getSaveVideoPath() {
        return this.saveVideoPath;
    }
    public void setSaveVideoPath(String saveVideoPath) {
        this.saveVideoPath = saveVideoPath;
    }
    public String getHrefTitle() {
        return this.hrefTitle;
    }
    public void setHrefTitle(String hrefTitle) {
        this.hrefTitle = hrefTitle;
    }
    public String getHrefUrl() {
        return this.hrefUrl;
    }
    public void setHrefUrl(String hrefUrl) {
        this.hrefUrl = hrefUrl;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }



}

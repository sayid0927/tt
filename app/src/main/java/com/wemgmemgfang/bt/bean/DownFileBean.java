package com.wemgmemgfang.bt.bean;

/**
 * Created by Administrator on 2018/3/22 0022.
 */

public class DownFileBean {

    private  String fileName;
    private  long fileSize;
    private  String filePath;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

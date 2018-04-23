package com.wemgmemgfang.bt.bean;

/**
 * Created by Administrator on 2017/10/31 0031.
 */

public class Apk_UpdateBean {


    /**
     * res : 00000
     * message : 查询成功
     * currentTimes : 1.52343498627902E9
     * data : {"VersionCode":1,"Apk_Name":"app-release.apk","FileSize":4584698,"Update_Info":"更新内容\n  1. 异常处理\n 2. 异常处理\n","Apk_Update_Path":"Apk_Update_Path"}
     */

    private String res;
    private String message;
    private double currentTimes;
    private DataBean data;

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getCurrentTimes() {
        return currentTimes;
    }

    public void setCurrentTimes(double currentTimes) {
        this.currentTimes = currentTimes;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * VersionCode : 1
         * Apk_Name : app-release.apk
         * FileSize : 4584698
         * Update_Info : 更新内容
         1. 异常处理
         2. 异常处理

         * Apk_Update_Path : Apk_Update_Path
         */

        private int VersionCode;
        private String Apk_Name;
        private int FileSize;
        private String Update_Info;
        private String Apk_Update_Path;

        public int getVersionCode() {
            return VersionCode;
        }

        public void setVersionCode(int VersionCode) {
            this.VersionCode = VersionCode;
        }

        public String getApk_Name() {
            return Apk_Name;
        }

        public void setApk_Name(String Apk_Name) {
            this.Apk_Name = Apk_Name;
        }

        public int getFileSize() {
            return FileSize;
        }

        public void setFileSize(int FileSize) {
            this.FileSize = FileSize;
        }

        public String getUpdate_Info() {
            return Update_Info;
        }

        public void setUpdate_Info(String Update_Info) {
            this.Update_Info = Update_Info;
        }

        public String getApk_Update_Path() {
            return Apk_Update_Path;
        }

        public void setApk_Update_Path(String Apk_Update_Path) {
            this.Apk_Update_Path = Apk_Update_Path;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "VersionCode=" + VersionCode +
                    ", Apk_Name='" + Apk_Name + '\'' +
                    ", FileSize=" + FileSize +
                    ", Update_Info='" + Update_Info + '\'' +
                    ", Apk_Update_Path='" + Apk_Update_Path + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Apk_UpdateBean{" +
                "res='" + res + '\'' +
                ", message='" + message + '\'' +
                ", currentTimes=" + currentTimes +
                ", data=" + data +
                '}';
    }
}

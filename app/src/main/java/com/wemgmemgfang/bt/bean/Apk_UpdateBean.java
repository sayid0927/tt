package com.wemgmemgfang.bt.bean;

/**
 * Created by Administrator on 2017/10/31 0031.
 */

public class Apk_UpdateBean {


    /**
     * res : 00000
     * message : 鏌ヨ鎴愬姛
     * currentTimes : 1.50954753638E9
     * data : {"VersionCode":1,"fileSize":5087267,"Apk_Update_Path":"Apk_Update_Path"}
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
         * fileSize : 5087267
         * Apk_Update_Path : Apk_Update_Path
         * Update_Info
         *
         * Apk_Name
         */

        private int VersionCode;
        private int FileSize;
        private String Apk_Update_Path;
        private String Update_Info;
        private String Apk_Name;

        public String getApk_Name() {
            return Apk_Name;
        }

        public void setApk_Name(String apk_Name) {
            Apk_Name = apk_Name;
        }

        public String getUpdate_Info() {
            return Update_Info;
        }

        public void setUpdate_Info(String update_Info) {
            Update_Info = update_Info;
        }

        public int getVersionCode() {
            return VersionCode;
        }

        public void setVersionCode(int VersionCode) {
            this.VersionCode = VersionCode;
        }

        public int getFileSize() {
            return FileSize;
        }

        public void setFileSize(int FileSize) {
            this.FileSize = FileSize;
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
                    ", FileSize=" + FileSize +
                    ", Apk_Update_Path='" + Apk_Update_Path + '\'' +
                    ", Update_Info='" + Update_Info + '\'' +
                    ", Apk_Name='" + Apk_Name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Apk_Update{" +
                "res='" + res + '\'' +
                ", message='" + message + '\'' +
                ", currentTimes=" + currentTimes +
                ", data=" + data +
                '}';
    }
}

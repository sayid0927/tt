package com.wemgmemgfang.bt.bean;

import java.util.List;

/**
 * sayid ....
 * Created by wengmf on 2018/3/21.
 */

public class VideoDetailsBean {
    private List<VideoInfoBean> videoInfoBeans;
    private  List<VideoLinks> videoLinks;
   private   String movieDescription;

    public List<VideoLinks> getVideoLinks() {
        return videoLinks;
    }

    public void setVideoLinks(List<VideoLinks> videoLinks) {
        this.videoLinks = videoLinks;
    }

    public List<VideoInfoBean> getVideoInfoBeans() {
        return videoInfoBeans;
    }

    public void setVideoInfoBeans(List<VideoInfoBean> videoInfoBeans) {
        this.videoInfoBeans = videoInfoBeans;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public  static  class  VideoLinks{
        private  String thunder;
        private  String label;
        private  String Title;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getThunder() {
            return thunder;
        }

        public void setThunder(String thunder) {
            this.thunder = thunder;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return "VideoLinks{" +
                    "thunder='" + thunder + '\'' +
                    ", label='" + label + '\'' +
                    ", Title='" + Title + '\'' +
                    '}';
        }
    }

    public  static  class VideoInfoBean{
        String type;
        String putType;
        String yanyuan;
        String putYanyuan;


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPutType() {
            return putType;
        }

        public void setPutType(String putType) {
            this.putType = putType;
        }

        public String getYanyuan() {
            return yanyuan;
        }

        public void setYanyuan(String yanyuan) {
            this.yanyuan = yanyuan;
        }

        public String getPutYanyuan() {
            return putYanyuan;
        }

        public void setPutYanyuan(String putYanyuan) {
            this.putYanyuan = putYanyuan;
        }

        @Override
        public String toString() {
            return "VideoInfoBean{" +
                    "type='" + type + '\'' +
                    ", putType='" + putType + '\'' +
                    ", yanyuan='" + yanyuan + '\'' +
                    ", putYanyuan='" + putYanyuan + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "VideoDetailsBean{" +
                "videoInfoBeans=" + videoInfoBeans +
                ", videoLinks=" + videoLinks +
                ", movieDescription='" + movieDescription + '\'' +
                '}';
    }
}

package com.wemgmemgfang.bt.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/17 0017.
 */

public class HomeInfoBean {

    private List<ColTitleBean> colTitleBean;
    private  List <HotsInfoBean> hotsInfoBeans;
    private  List<SectionBean> sectionBeans;

    public List<SectionBean> getSectionBeans() {
        return sectionBeans;
    }

    public void setSectionBeans(List<SectionBean> sectionBeans) {
        this.sectionBeans = sectionBeans;
    }

    public List<ColTitleBean> getColTitleBean() {
        return colTitleBean;
    }

    public void setColTitleBean(List<ColTitleBean> colTitleBean) {
        this.colTitleBean = colTitleBean;
    }

    public List<HotsInfoBean> getHotsInfoBeans() {
        return hotsInfoBeans;
    }

    public void setHotsInfoBeans(List<HotsInfoBean> hotsInfoBeans) {
        this.hotsInfoBeans = hotsInfoBeans;
    }


    public  static class   SectionBean{
        private String type;
        private String herf;
        private String imgUrl;
        private String Score;
        private String language;
        private String em;
        private String title;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getHerf() {
            return herf;
        }

        public void setHerf(String herf) {
            this.herf = herf;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getScore() {
            return Score;
        }

        public void setScore(String score) {
            Score = score;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getEm() {
            return em;
        }

        public void setEm(String em) {
            this.em = em;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public  static  class  HotsInfoBean{
        private String type;
        private String herf;
        private String imgUrl;
        private String Score;
        private String language;
        private String em;
        private String title;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getHerf() {
            return herf;
        }

        public void setHerf(String herf) {
            this.herf = herf;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getScore() {
            return Score;
        }

        public void setScore(String score) {
            Score = score;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getEm() {
            return em;
        }

        public void setEm(String em) {
            this.em = em;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ColTitleBean {
        private String hrefUrl;
        private String title;
        private String countPop;

        public String getHrefUrl() {
            return hrefUrl;
        }

        public void setHrefUrl(String hrefUrl) {
            this.hrefUrl = hrefUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCountPop() {
            return countPop;
        }

        public void setCountPop(String countPop) {
            this.countPop = countPop;
        }
    }

}


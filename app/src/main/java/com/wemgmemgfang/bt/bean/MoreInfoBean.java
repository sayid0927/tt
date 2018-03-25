package com.wemgmemgfang.bt.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class MoreInfoBean {


    private List<MoreTypeBean> moreTypeBeans;
    private  List<MoreVideoInfoBean>moreVideoInfoBeans;

    public List<MoreVideoInfoBean> getMoreVideoInfoBeans() {
        return moreVideoInfoBeans;
    }

    public void setMoreVideoInfoBeans(List<MoreVideoInfoBean> moreVideoInfoBeans) {
        this.moreVideoInfoBeans = moreVideoInfoBeans;
    }

    public List<MoreTypeBean> getMoreTypeBeans() {
        return moreTypeBeans;
    }

    public void setMoreTypeBeans(List<MoreTypeBean> moreTypeBeans) {
        this.moreTypeBeans = moreTypeBeans;
    }

    public  static  class  MoreVideoInfoBean{
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


        @Override
        public String toString() {
            return "MoreVideoInfoBean{" +
                    "type='" + type + '\'' +
                    ", herf='" + herf + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", Score='" + Score + '\'' +
                    ", language='" + language + '\'' +
                    ", em='" + em + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }




    public  static  class MoreTypeBean{
        private String  type;
        private String typeName;
        private String href;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        @Override
        public String toString() {
            return "MoreTypeBean{" +
                    "type='" + type + '\'' +
                    ", typeName='" + typeName + '\'' +
                    ", href='" + href + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MoreInfoBean{" +
                "moreTypeBeans=" + moreTypeBeans +
                ", moreVideoInfoBeans=" + moreVideoInfoBeans +
                '}';
    }
}

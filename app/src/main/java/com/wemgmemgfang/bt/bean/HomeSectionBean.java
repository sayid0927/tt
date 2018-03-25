package com.wemgmemgfang.bt.bean;

/**
 * Created by Administrator on 2018/3/20 0020.
 */

public class HomeSectionBean  implements com.chad.library.adapter.base.entity.MultiItemEntity {


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
    public int getItemType() {
        return 1;
    }
}

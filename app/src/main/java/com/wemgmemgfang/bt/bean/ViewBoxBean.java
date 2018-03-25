package com.wemgmemgfang.bt.bean;

/**
 * sayid ....
 * Created by wengmf on 2018/3/12.
 */

public class ViewBoxBean {

    private String imgUrl;
    private  String alt;
    private String size;
    private String sizeNum;
    private String context;
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSizeNum() {
        return sizeNum;
    }

    public void setSizeNum(String sizeNum) {
        this.sizeNum = sizeNum;
    }

}

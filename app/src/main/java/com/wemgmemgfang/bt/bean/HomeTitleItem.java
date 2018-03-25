package com.wemgmemgfang.bt.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;

/**
 * Created by Administrator on 2018/3/20 0020.
 */

public class HomeTitleItem extends AbstractExpandableItem<HomeSectionBean> implements com.chad.library.adapter.base.entity.MultiItemEntity {


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

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}

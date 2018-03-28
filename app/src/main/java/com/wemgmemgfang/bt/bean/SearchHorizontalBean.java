package com.wemgmemgfang.bt.bean;

/**
 * sayid ....
 * Created by wengmf on 2018/3/28.
 */

public class SearchHorizontalBean {

    private  String name;
    private  String type;
    private boolean start;

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

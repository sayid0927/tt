package com.wemgmemgfang.bt.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * sayid ....
 * Created by wengmf on 2018/3/26.
 */
@Entity
public class CollectionInfo {
    @Id(autoincrement = true) //自增
    private Long id; //主键
    private  String  HrefUrl;
    private  String  imgUrl;
    private String  Title;
    private  String itemType;

    @Generated(hash = 1363961491)
    public CollectionInfo(Long id, String HrefUrl, String imgUrl, String Title,
            String itemType) {
        this.id = id;
        this.HrefUrl = HrefUrl;
        this.imgUrl = imgUrl;
        this.Title = Title;
        this.itemType = itemType;
    }
    @Generated(hash = 358181051)
    public CollectionInfo() {
    }
    public String getHrefUrl() {
        return this.HrefUrl;
    }
    public void setHrefUrl(String HrefUrl) {
        this.HrefUrl = HrefUrl;
    }
    public String getImgUrl() {
        return this.imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getTitle() {
        return this.Title;
    }
    public void setTitle(String Title) {
        this.Title = Title;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getItemType() {
        return this.itemType;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    

}

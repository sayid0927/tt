package com.wemgmemgfang.bt.utils;

/**
 * sayid ....
 * Created by wengmf on 2018/2/11.
 */

public class StringUtlis {


    public  static  String subString(String c, String sub ){
        return  c.substring(c.lastIndexOf(sub) + 1, c.length());
    }

    public  static  String suString(String c, String sub ){
        return  c.substring(1, c.indexOf(sub,c.indexOf(sub) +1));
    }



}

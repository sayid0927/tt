package com.wemgmemgfang.bt.utils;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * sayid ....
 * Created by wengmf on 2018/3/2.
 */

public class RandomUtils {



//    //双重校验锁获取一个Random单例
//    @SuppressLint("NewApi")
//    public static ThreadLocalRandom getRandom() {
//        return ThreadLocalRandom.current();
//    }
//    /**
//     * 获得一个[0,max)之间的随机整数。
//     * @param max
//     * @return
//     */
//    public static int getRandomInt(int max) {
//        return getRandom().nextInt(max);
//    }




    public static String   getAgentString(){
        List<String> agentString = new ArrayList<>();
        Random rand = new Random();
        agentString.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36 OPR/26.0.1656.60");
        agentString.add("Opera/8.0 (Windows NT 5.1; U; en)");
        agentString.add("Mozilla/5.0 (Windows NT 5.1; U; en; rv:1.8.1) Gecko/20061208 Firefox/2.0.0 Opera 9.50");
        agentString.add("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; en) Opera 9.50");
        agentString.add("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
        agentString.add("Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10");
        agentString.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2");
        agentString.add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
        agentString.add("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
        agentString.add("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16");
        agentString.add("UCWEB7.0.2.37/28/999");
        return  agentString.get(rand.nextInt(agentString.size()));
    }

}

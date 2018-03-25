/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wemgmemgfang.bt.presenter.impl;


import com.wemgmemgfang.bt.api.Api;
import com.wemgmemgfang.bt.base.RxPresenter;
import com.wemgmemgfang.bt.bean.DownRaningBean;
import com.wemgmemgfang.bt.presenter.contract.DownRankingContract;
import com.wemgmemgfang.bt.utils.RandomUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DownRankingPresenter extends RxPresenter<DownRankingContract.View> implements DownRankingContract.Presenter<DownRankingContract.View> {


    private Api api;
    public static boolean isLastSyncUpdateed = false;

    @Inject
    public DownRankingPresenter(Api Api) {
        this.api = Api;
    }


    @Override
    public void Fetch_DownRankingInfo(final String url) {

        Observable.create(new Observable.OnSubscribe< List<DownRaningBean>>() {

            @Override
            public void call(Subscriber<? super  List<DownRaningBean>> subscriber) {
                //在call方法中执行异步任务
                List<DownRaningBean> downRaningBeans = new ArrayList<>();

                try {

                    Connection connect = Jsoup.connect(url);
                    Map<String, String> header = new HashMap<>();
                    header.put("User-Agent", RandomUtils.getAgentString());
                    header.put("Accept", "	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                    header.put("Accept-Language", "zh-cn,zh;q=0.5");
                    header.put("Accept-Charset", "	GB2312,utf-8;q=0.7,*;q=0.7");
                    Connection data = connect.data(header);
                    Document doc = data.get();
                    Elements manhua = doc.select("ul.e2");
                    String html = manhua.html();
                    Elements document = Jsoup.parse(html).getElementsByTag("li");

                    for(Element e : document){
                        DownRaningBean downRaningBean = new DownRaningBean();
                        downRaningBean.setHref(e.select("a").attr("href"));
                        downRaningBean.setImgUrl(e.select("img").attr("src"));
                        downRaningBean.setTitle(e.select("img").attr("alt"));
                        downRaningBean.setAdd(e.select("span").text());
                        downRaningBean.setIntro(e.select("p").text());
                        downRaningBeans.add(downRaningBean);
                    }
                } catch (Exception e) {
                    //注意：如果异步任务中需要抛出异常，在执行结果中处理异常。需要将异常转化未RuntimException
                    throw new RuntimeException(e);
                }
                //调用subscriber#onNext方法将执行结果返回
                subscriber.onNext(downRaningBeans);
                //调用subscriber#onCompleted方法完成异步任务
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())//指定异步任务在IO线程运行
                .observeOn(AndroidSchedulers.mainThread())//制定执行结果在主线程运行
                .subscribe(new Observer<List<DownRaningBean>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<DownRaningBean> data) {
                        if (data != null && mView != null ) {
                            mView.Fetch_DownRankingInfo_Success(data);
                        }
                    }
                });
    }
}

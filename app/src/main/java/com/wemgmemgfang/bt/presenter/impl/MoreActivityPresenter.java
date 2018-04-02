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
import com.wemgmemgfang.bt.bean.MoreInfoBean;
import com.wemgmemgfang.bt.presenter.contract.MoreActivityContract;
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

public class MoreActivityPresenter extends RxPresenter<MoreActivityContract.View> implements MoreActivityContract.Presenter<MoreActivityContract.View> {

    private Api bookApi;
    public static boolean isLastSyncUpdateed = false;

    @Inject
    public MoreActivityPresenter(Api bookApi) {
        this.bookApi = bookApi;
    }


    @Override
    public void Fetch_MoreTypeInfo(final String url) {
        Observable.create(new Observable.OnSubscribe<MoreInfoBean>() {

            @Override
            public void call(Subscriber<? super MoreInfoBean> subscriber) {
                //在call方法中执行异步任务
                MoreInfoBean moreInfoBean = new MoreInfoBean();
                List<MoreInfoBean.MoreTypeBean> moreTypeBeanList = new ArrayList<>();
                List<MoreInfoBean.MoreVideoInfoBean> moreVideoInfoBeanList = new ArrayList<>();

                try {

                    Connection connect = Jsoup.connect(url);
                    Map<String, String> header = new HashMap<>();
                    header.put("User-Agent", RandomUtils.getAgentString());
                    header.put("Accept", "	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                    header.put("Accept-Language", "zh-cn,zh;q=0.5");
                    header.put("Accept-Charset", "	GB2312,utf-8;q=0.7,*;q=0.7");
                    Connection data = connect.data(header);
                    Document doc = data.get();
                    Element genreList = doc.getElementById("movie-list-filters");
                    String string = genreList.html();
                    Elements tagA = Jsoup.parse(string).getElementsByTag("a");

                    Elements elCol = doc.select("div.list_mov");
                    for (int n = 0; n < elCol.size(); n++) {
                        MoreInfoBean.MoreVideoInfoBean moreVideoInfoBean = new MoreInfoBean.MoreVideoInfoBean();
                        Elements docPoster = elCol.get(n).select("div.list_mov_poster");
                        Elements docTitle = elCol.get(n).select("div.list_mov_title");

                        for(int k=0;k<docPoster.size();k++) {
                            moreVideoInfoBean.setImgUrl(docPoster.get(k).select("img").attr("data-original"));
                            Elements span = docPoster.get(k).select("span.corner");

                            for (Element c : span) {
                                String sclass = c.select("span").attr("class");
                                String sa = c.select("span").text();
                                if (sclass.equals("corner score")) {
                                    moreVideoInfoBean.setScore(sa);
                                } else {
                                    moreVideoInfoBean.setLanguage(sa);
                                }
                            }
                            moreVideoInfoBean.setEm(docTitle.get(k).select("em").text());
                            moreVideoInfoBean.setHerf(docTitle.get(k).select("a").attr("href"));
                            moreVideoInfoBean.setTitle(docTitle.get(k).select("a").text());
                            moreVideoInfoBeanList.add(moreVideoInfoBean);

                        }
                    }


                    for (int i = 0; i < tagA.size(); i++) {
                        MoreInfoBean.MoreTypeBean moreTypeBean = new MoreInfoBean.MoreTypeBean();
                        String id = tagA.get(i).select("a").attr("id");
                        if (id.contains("genre")) {
                            moreTypeBean.setType("类型: ");
                        } else if (id.contains("year")) {
                            moreTypeBean.setType("年代: ");
                        } else if (id.contains("language")) {
                            moreTypeBean.setType("语言: ");
                        } else if (id.contains("location")) {
                            moreTypeBean.setType("地区: ");
                        } else if (id.contains("orderby")) {
                            moreTypeBean.setType("排序: ");
                        }
                        moreTypeBean.setHref(tagA.get(i).select("a").attr("href"));
                        moreTypeBean.setTypeName(tagA.get(i).text());
                        moreTypeBeanList.add(moreTypeBean);
                    }
                    moreInfoBean.setMoreTypeBeans(moreTypeBeanList);
                    moreInfoBean.setMoreVideoInfoBeans(moreVideoInfoBeanList);
                } catch (Exception e) {
                    //注意：如果异步任务中需要抛出异常，在执行结果中处理异常。需要将异常转化未RuntimException
                    throw new RuntimeException(e);
                }
                //调用subscriber#onNext方法将执行结果返回
                subscriber.onNext(moreInfoBean);
                //调用subscriber#onCompleted方法完成异步任务
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())//指定异步任务在IO线程运行
                .observeOn(AndroidSchedulers.mainThread())//制定执行结果在主线程运行
                .subscribe(new Observer<MoreInfoBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MoreInfoBean data) {
                        if (data != null && mView != null) {
                            mView.Fetch_MoreTypeInfo_Success(data);
                        }
                    }
                });
    }
}

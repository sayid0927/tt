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
import com.wemgmemgfang.bt.bean.VideoDetailsBean;
import com.wemgmemgfang.bt.presenter.contract.DetailsActivityContract;
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

public class DetailsActivityPresenter extends RxPresenter<DetailsActivityContract.View> implements DetailsActivityContract.Presenter<DetailsActivityContract.View> {

    private Api bookApi;
    public static boolean isLastSyncUpdateed = false;

    @Inject
    public DetailsActivityPresenter(Api bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void Fetch_VideoDetailsInfo(final String url) {
        Observable.create(new Observable.OnSubscribe<VideoDetailsBean>() {

            @Override
            public void call(Subscriber<? super VideoDetailsBean> subscriber) {
                //在call方法中执行异步任务
                VideoDetailsBean videoDetailsBean = new VideoDetailsBean();
                List<VideoDetailsBean.VideoInfoBean> videoInfoBeanList = new ArrayList<>();
                List<VideoDetailsBean.VideoLinks> videoLinksArrayList = new ArrayList<>();

                try {

                    Connection connect = Jsoup.connect(url).timeout(30000).validateTLSCertificates(false);
                    Map<String, String> header = new HashMap<>();
                    header.put("User-Agent", RandomUtils.getAgentString());
                    header.put("Accept", "	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                    header.put("Accept-Language", "zh-cn,zh;q=0.5");
                    header.put("Accept-Charset", "	GB2312,utf-8;q=0.7,*;q=0.7");
                    Connection data = connect.data(header);
                    Document doc = data.get();

                    Elements elSpan = doc.select("p.movie-info");
                    String movieDescription = doc.getElementById("movie_description").text();
                    Elements elLinks = doc.select("div.td-dl-links");

                    for(Element e : elLinks){
                        VideoDetailsBean.VideoLinks videoLinks = new VideoDetailsBean.VideoLinks();
                        videoLinks.setThunder(e.select("a").attr("href"));
                        videoLinks.setLabel(e.select("span").text());
                        videoLinks.setTitle(e.select("a").text());
                        videoLinksArrayList.add(videoLinks);
                    }

                    boolean s = false;
                    for (Element e : elSpan) {
                        VideoDetailsBean.VideoInfoBean videoInfoBean = new VideoDetailsBean.VideoInfoBean();
                        if (!s) {
                            videoInfoBean.setType(e.select("span").text());
                            videoInfoBean.setPutType(e.select("a").text());
                            s = true;
                        } else {
                            videoInfoBean.setYanyuan(e.select("span").text());
                            videoInfoBean.setPutYanyuan(e.select("a").text());
                        }
                        videoInfoBeanList.add(videoInfoBean);
                    }

                    videoDetailsBean.setVideoInfoBeans(videoInfoBeanList);
                    videoDetailsBean.setMovieDescription(movieDescription);
                    videoDetailsBean.setVideoLinks(videoLinksArrayList);

                } catch (Exception e) {
                    //注意：如果异步任务中需要抛出异常，在执行结果中处理异常。需要将异常转化未RuntimException
                    throw new RuntimeException(e);
                }
                //调用subscriber#onNext方法将执行结果返回
                subscriber.onNext(videoDetailsBean);
                //调用subscriber#onCompleted方法完成异步任务
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())//指定异步任务在IO线程运行
                .observeOn(AndroidSchedulers.mainThread())//制定执行结果在主线程运行
                .subscribe(new Observer<VideoDetailsBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VideoDetailsBean data) {
                        if (data != null && mView != null) {
                            mView.Fetch_VideoDetailsInfo_Success(data);
                        }
                    }
                });
    }
}

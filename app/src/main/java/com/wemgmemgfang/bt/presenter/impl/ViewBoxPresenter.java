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

import com.blankj.utilcode.utils.RegexUtils;
import com.wemgmemgfang.bt.api.Api;
import com.wemgmemgfang.bt.base.RxPresenter;
import com.wemgmemgfang.bt.bean.DownHrefBean;
import com.wemgmemgfang.bt.bean.ViewBoxBean;
import com.wemgmemgfang.bt.presenter.contract.ViewBoxContract;
import com.wemgmemgfang.bt.utils.DeviceUtils;
import com.wemgmemgfang.bt.utils.RandomUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ViewBoxPresenter extends RxPresenter<ViewBoxContract.View> implements ViewBoxContract.Presenter<ViewBoxContract.View> {


    private Api api;
    public static boolean isLastSyncUpdateed = false;

    @Inject
    public ViewBoxPresenter(Api Api) {
        this.api = Api;
    }


    @Override
    public void Fetch_ViewBoxInfo(final String url) {
        Observable.create(new Observable.OnSubscribe<ViewBoxBean>() {

            @Override
            public void call(Subscriber<? super ViewBoxBean> subscriber) {
                //在call方法中执行异步任务
                ViewBoxBean viewBoxBean = new ViewBoxBean();
                try {
                    Connection connect = Jsoup.connect(url);
                    Map<String, String> header = new HashMap<>();
                    header.put("User-Agent", RandomUtils.getAgentString());
                    header.put("Accept", "	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                    header.put("Accept-Language", "zh-cn,zh;q=0.5");
                    header.put("Accept-Charset", "	GB2312,utf-8;q=0.7,*;q=0.7");
                    Connection data = connect.data(header);
                    Document doc = data.get();
                    Elements manhua = doc.select("div.picview");
                    Elements manhuas = doc.select("div.infolist");
                    Elements contents = doc.select("div.content");
                    Elements downurllist = doc.select("ul.downurllist");


                    String contHtml = contents.html();
                    Elements document = Jsoup.parse(contHtml).getElementsByTag("p");
                    String d = Jsoup.parse(contHtml).getElementsByTag("p").text();
                    viewBoxBean.setContext(d);
                    String documentHtml = document.html();
                    Elements docstrong = Jsoup.parse(documentHtml).getElementsByTag("strong");

                    String downurllistHtml = downurllist.html();
                    Elements strong = Jsoup.parse(downurllistHtml).getElementsByTag("strong");


                    for (Element e : manhua) {
                        viewBoxBean.setImgUrl(e.select("img").attr("src"));
                        viewBoxBean.setAlt(e.select("img").attr("alt"));
                        for (Element es : manhuas) {
                            viewBoxBean.setSize(es.select("small").text());
                            viewBoxBean.setSizeNum(es.select("span").text());
                        }
                        for (Element c : docstrong) {
                            viewBoxBean.setContext(c.select("strong").text());
                        }
                        for (Element s : strong) {
                            viewBoxBean.setHref(s.select("a").attr("href"));
                        }
                    }
                } catch (Exception e) {
                    //注意：如果异步任务中需要抛出异常，在执行结果中处理异常。需要将异常转化未RuntimException
                    throw new RuntimeException(e);
                }
                //调用subscriber#onNext方法将执行结果返回

                subscriber.onNext(viewBoxBean);
                //调用subscriber#onCompleted方法完成异步任务
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())//指定异步任务在IO线程运行
                .observeOn(AndroidSchedulers.mainThread())//制定执行结果在主线程运行
                .subscribe(new Observer<ViewBoxBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ViewBoxBean data) {
                        if (data != null && mView != null) {
                            mView.Fetch_ViewBoxInfo_Success(data);
                        }
                    }
                });
    }

    @Override
    public void Fetch_HrefUrl(final String Url) {
        Observable.create(new Observable.OnSubscribe<List<DownHrefBean>>() {

            @Override
            public void call(Subscriber<? super List<DownHrefBean>> subscriber) {
                //在call方法中执行异步任务
                List<DownHrefBean> downHrefBeanList = new ArrayList<>();
                try {
                    Connection connect = Jsoup.connect(Url);
                    Map<String, String> header = new HashMap<>();
                    header.put("User-Agent", RandomUtils.getAgentString());
                    header.put("Accept", "	text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
                    header.put("Accept-Language", "zh-cn,zh;q=0.5");
                    header.put("Accept-Charset", "	GB2312,utf-8;q=0.7,*;q=0.7");
                    Connection data = connect.data(header);
                    Document doc = data.get();

                    Elements downEl = doc.select("div.downsky");
                    String contHtml = downEl.html();
                    Elements ulEl = Jsoup.parse(contHtml).getElementsByTag("ul");
                    String aHtml = ulEl.html();
                    Elements aEl = Jsoup.parse(aHtml).getElementsByTag("a");

                    for (Element e : aEl) {
                        DownHrefBean downHrefBean = new DownHrefBean();
                        downHrefBean.setDownUrl(e.select("a").attr("href"));
                        downHrefBean.setTitle(e.select("a").attr("title"));
                        downHrefBeanList.add(downHrefBean);
                    }
                } catch (Exception e) {
                    //注意：如果异步任务中需要抛出异常，在执行结果中处理异常。需要将异常转化未RuntimException
                    throw new RuntimeException(e);
                }
                //调用subscriber#onNext方法将执行结果返回

                subscriber.onNext(downHrefBeanList);
                //调用subscriber#onCompleted方法完成异步任务
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())//指定异步任务在IO线程运行
                .observeOn(AndroidSchedulers.mainThread())//制定执行结果在主线程运行
                .subscribe(new Observer<List<DownHrefBean>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mView!=null)
                            mView.showError("网络错误");
                    }

                    @Override
                    public void onNext(List<DownHrefBean> data) {
                        if (data != null && mView != null) {
                            boolean isJpg=false;
                            for (DownHrefBean d : data) {
                                isJpg  = RegexUtils.isMatch("^http(.*)\\.zip$", d.getDownUrl());
                                if (isJpg) {
                                    mView.Fetch_HrefUrl_Success(d);
                                    break;
                                }
                            }
                            if( !isJpg && mView!=null)
                                mView.showError("暂无电影资源");
                        }
                    }
                });
    }

    @Override
    public void download_Zip(final DownHrefBean downHrefBean) {

        Subscription rxSubscription = api.downloadPicFromNet(downHrefBean.getDownUrl()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                     if(mView!=null)
                        mView.showError(e.toString());

                    }

                    @Override
                    public void onNext(Response<ResponseBody> data) {

                        try {
                            String destFileName = downHrefBean.getTitle() + ".zip";
                            File file = saveFile(data, destFileName);
                            mView.download_Zip_Success(file.getPath());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void Down_Torrent_File() {

    }

    public File saveFile(Response<ResponseBody> response, String destFileName) throws Exception {
        String destFileDir = DeviceUtils.getSDPath();
        InputStream in = null;
        FileOutputStream out = null;
        byte[] buf = new byte[2048 * 10];
        int len;
        try {
            File dir = new File(destFileDir);
            if (!dir.exists()) {                 // 如果文件不存在新建一个
                dir.mkdirs();
            }
            in = response.body().byteStream();
            File file = new File(dir, destFileName);
            out = new FileOutputStream(file);
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            return file;
        } catch (Exception e) {
            e.toString();

        } finally {
            in.close();
            out.close();
        }
        return null;
    }

}

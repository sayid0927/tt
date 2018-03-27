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


import com.blankj.utilcode.utils.LogUtils;
import com.orhanobut.logger.Logger;
import com.wemgmemgfang.bt.api.Api;
import com.wemgmemgfang.bt.base.RxPresenter;
import com.wemgmemgfang.bt.bean.SearchBean;
import com.wemgmemgfang.bt.presenter.contract.FilmFragmentContract;
import com.wemgmemgfang.bt.presenter.contract.SearchContract;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchPresenter extends RxPresenter<SearchContract.View> implements SearchContract.Presenter<SearchContract.View> {


    private Api api;
    public static boolean isLastSyncUpdateed = false;

    @Inject
    public SearchPresenter(Api Api) {
        this.api = Api;
    }


    @Override
    public void Fetch_Search_Info(String keyword) {

        Subscription rxSubscription = api.Fetch_Search_Info(keyword).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        if (mView != null && responseBodyResponse.isSuccessful()) {
                            try {
                                String html = responseBodyResponse.body().string();
                                Elements document = Jsoup.parse(html).getElementsByClass("list-group-item");
                                List<SearchBean> searchBeanList = new ArrayList<>();
                                for (int i = 0; i < document.size(); i++) {
                                    SearchBean searchBean = new SearchBean();
                                    searchBean.setImgUrl(document.get(i).select("img").attr("src"));
                                    searchBean.setH5(document.get(i).select("h5").text());
                                    searchBean.setEm(document.get(i).select("em").text());
                                    searchBean.setHref(document.get(i).select("a").attr("href"));
                                    searchBean.setScore( document.get(i).select("a").attr("data-score"));
                                    searchBean.setYear(document.get(i).select("a").attr("data-year"));
                                    searchBean.setSpan( document.get(i).select("span").text());
                                    searchBean.setH4(document.get(i).select("h4").text());
                                    searchBeanList.add(searchBean);
                                }
                                mView.Fetch_Search_Info_Success(searchBeanList);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}

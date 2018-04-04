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
import com.wemgmemgfang.bt.api.Api;
import com.wemgmemgfang.bt.base.RxPresenter;
import com.wemgmemgfang.bt.bean.Apk_UpdateBean;
import com.wemgmemgfang.bt.presenter.contract.MainContract;
import com.wemgmemgfang.bt.utils.DeviceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivityPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter<MainContract.View> {

    private Api bookApi;
    public static boolean isLastSyncUpdateed = false;

    @Inject
    public MainActivityPresenter(Api bookApi) {
        this.bookApi = bookApi;
    }




    @Override
    public void Apk_Update() {
        Subscription rxSubscription = bookApi.Fetch_Apk_Update().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Apk_UpdateBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.toString());
                    }
                    @Override
                    public void onNext(Apk_UpdateBean data) {
                        if (data != null && mView != null && data.getRes().equals("00000")) {
                            Apk_UpdateBean.DataBean dataBean = data.getData();
                            mView.Apk_Update_Success(dataBean);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

//    @Override
//    public void Apk_Update_Path() {
//        Subscription rxSubscription = bookApi.Fetch_Apk_Update_Path().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Response<ResponseBody>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mView.showError(e.toString());
//                    }
//
//                    @Override
//                    public void onNext(Response<ResponseBody> data) {
//                        try {
////                            File file = saveFile(data);
////                            mView.Apk_Update_Path_Success(file);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//        addSubscrebe(rxSubscription);
//
//    }




    private String destFileName = System.currentTimeMillis() + ".apk";

    public File saveFile(Response<ResponseBody> response) throws Exception {

        String destFileDir = DeviceUtils.getSDPath();
        InputStream in = null;
        FileOutputStream out = null;
        byte[] buf = new byte[1024];
        int len;
        try {
            File dir = new File(destFileDir);
            if (!dir.exists()) {// 如果文件不存在新建一个
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

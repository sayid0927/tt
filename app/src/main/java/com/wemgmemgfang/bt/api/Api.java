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
package com.wemgmemgfang.bt.api;


import com.google.gson.Gson;
import com.wemgmemgfang.bt.RequestBody.AppInfoRequest;
import com.wemgmemgfang.bt.RequestBody.PhoneInfoRequest;
import com.wemgmemgfang.bt.base.Constant;
import com.wemgmemgfang.bt.bean.Apk_UpdateBean;


import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class Api {

    public static Api instance;

    private ApiService service;

    public Api(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .client(okHttpClient)
                .baseUrl(Constant.API_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .build();


        service = retrofit.create(ApiService.class);
    }

    public static Api getInstance(OkHttpClient okHttpClient) {
        if (instance == null)
            instance = new Api(okHttpClient);
        return instance;
    }

    public Observable<Response<ResponseBody>> downloadPicFromNet(String imgUrl) {
        return service.downloadPicFromNet(imgUrl);
    }



    public Observable<Response<ResponseBody>> Fetch_Apk_Update_Path() {
        return service.Fetch_Apk_Update_Path();
    }

    public Observable<Response<ResponseBody>> Fetch_Search_Info(String keyword) {
        return service.Fetch_Search_Info(keyword);
    }

    public Observable<Apk_UpdateBean> Fetch_Apk_Update() {
        return service.Fetch_Apk_Update();

    }

    public Observable<Response<ResponseBody>> Post_App_Info(AppInfoRequest appInfoRequest) {
        return service.Post_Apk_Info(appInfoRequest);
    }

    public  Observable<Response<ResponseBody>> Post_Phone_Info(PhoneInfoRequest phoneInfoRequest){
        return  service.Post_Phone_Info(phoneInfoRequest);
    }

}

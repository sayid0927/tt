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



import com.wemgmemgfang.bt.RequestBody.AppInfoRequest;
import com.wemgmemgfang.bt.RequestBody.PhoneInfoRequest;
import com.wemgmemgfang.bt.base.Constant;
import com.wemgmemgfang.bt.bean.Apk_UpdateBean;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;


public interface ApiService {

    /**
     * 下载 图片
     */
    @GET
    Observable<Response<ResponseBody>> downloadPicFromNet(@Url String imgUrl);


    /**
     * 下载apk
     */
    @GET(Constant.APK_UPDATE_PATH)
    Observable<Response<ResponseBody>> Fetch_Apk_Update_Path();

    @Headers("url_name:stt")
    @FormUrlEncoded
    @POST("search")
    Observable<Response<ResponseBody>> Fetch_Search_Info(@Field("keyword") String keyword);



    /**
     * 提交APP信息
     */
    @POST(Constant.POST_APK_INFO)
    Observable<Response<ResponseBody>>  Post_Apk_Info(@Body AppInfoRequest appInfoRequest);

    /**
     * 提交手机信息
     */
    @POST(Constant.POST_PHONE_INFO)
    Observable<Response<ResponseBody>>  Post_Phone_Info(@Body PhoneInfoRequest phoneInfoRequest);


    /**
     * 获取APP更新信息
     */
    @GET(Constant.APK_UPDATE)
    Observable<Apk_UpdateBean> Fetch_Apk_Update();

}
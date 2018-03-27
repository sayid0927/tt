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
package com.wemgmemgfang.bt.api.support;


import com.wemgmemgfang.bt.base.Constant;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Retrofit2 Header拦截器。用于保存和设置Cookies
 *
 * @author yuyh.
 * @date 16/8/6.
 */
public final class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

            //获取request
            Request request = chain.request();
            //获取request的创建者builder
            Request.Builder builder = request.newBuilder();
            //从request中获取headers，通过给定的键url_name
            List<String> headerValues = request.headers("url_name");
            if (headerValues != null && headerValues.size() > 0) {
                //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
//                builder.removeHeader(HttpConfig.HEADER_KEY);
                //匹配获得新的BaseUrl
                String headerValue = headerValues.get(0);
                HttpUrl newBaseUrl = null;
                if ("other".equals(headerValue)) {
                    newBaseUrl = HttpUrl.parse(Constant.API_BASE_URL);
                } else if ("book".equals(headerValue)) {
                    newBaseUrl = HttpUrl.parse(Constant.API_BASE_URL);
                } else if("stt".equals(headerValue)) {
                    newBaseUrl = HttpUrl.parse(Constant.STT_URL);
                }

                //从request中获取原有的HttpUrl实例oldHttpUrl
                HttpUrl oldHttpUrl = request.url();
                //重建新的HttpUrl，修改需要修改的url部分
                HttpUrl newFullUrl = oldHttpUrl
                        .newBuilder()
                        .scheme(newBaseUrl.scheme())
                        .host(newBaseUrl.host())
                        .port(newBaseUrl.port())
                        .build();

                //重建这个request，通过builder.url(newFullUrl).build()；
                //然后返回一个response至此结束修改
                return chain.proceed(builder.url(newFullUrl).build());
            } else {
                return chain.proceed(request);

            }
        }
    }


//        Request original = chain.request();
//
//        String url = original.url().toString();
//
//        if (url.contains("book/") ||
//                url.contains("book-list/") ||
//                url.contains("toc/") ||
//                url.contains("post/") ||
//                url.contains("user/")) {
//            Request request = original.newBuilder()
//                    .addHeader("User-Agent", "ZhuiShuShenQi/3.40[preload=false;locale=zh_CN;clientidbase=android-nvidia]") // 不能转UTF-8
//                    .addHeader("X-User-Agent", "ZhuiShuShenQi/3.40[preload=false;locale=zh_CN;clientidbase=android-nvidia]")
////                    .addHeader("X-Device-Id", DeviceUtils.getIMEI(AppUtils.getAppContext()))
//                    .addHeader("Host", "api.qushuwang.com")
//                    .addHeader("Connection", "Keep-Alive")
//                    .addHeader("If-None-Match", "W/\"2a04-4nguJ+XAaA1yAeFHyxVImg\"")
//                    .addHeader("If-Modified-Since", "Tue, 02 Aug 2016 03:20:06 UTC")
//                    .build();
//            return chain.proceed(request);
//        }
//
//        return chain.proceed(original);
//    }
//}


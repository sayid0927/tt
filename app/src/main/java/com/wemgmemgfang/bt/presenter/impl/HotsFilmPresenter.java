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
import com.wemgmemgfang.bt.presenter.contract.HotsFilmContract;

import javax.inject.Inject;

public class HotsFilmPresenter extends RxPresenter<HotsFilmContract.View> implements HotsFilmContract.Presenter<HotsFilmContract.View> {


    private Api api;
    public static boolean isLastSyncUpdateed = false;

    @Inject
    public HotsFilmPresenter(Api Api) {
        this.api = Api;
    }


}

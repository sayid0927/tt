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
package com.wemgmemgfang.bt.presenter.contract;


import com.wemgmemgfang.bt.base.BaseContract;
import com.wemgmemgfang.bt.bean.DownHrefBean;
import com.wemgmemgfang.bt.bean.ViewBoxBean;

import java.util.List;

public interface ViewBoxContract {

    interface View extends BaseContract.BaseView {
        void Fetch_ViewBoxInfo_Success(ViewBoxBean data);

        void Fetch_HrefUrl_Success(DownHrefBean data);

        void download_Zip_Success(String filePath);

        void  Down_Torrent_File_Success();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void Fetch_ViewBoxInfo(String url);

        void Fetch_HrefUrl(String Url);

        void download_Zip(DownHrefBean data);

        void  Down_Torrent_File();

    }
}

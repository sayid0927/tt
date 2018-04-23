package com.wemgmemgfang.bt.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeiDetailsActivity extends BaseActivity {


    @BindView(R.id.iv)
    ImageView iv;
    private String ImgUrl;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mei_details;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void initView() {

        ImgUrl = getIntent().getStringExtra("ImgUrl");
        ImgLoadUtils.loadImage(this, ImgUrl, iv);

    }

}

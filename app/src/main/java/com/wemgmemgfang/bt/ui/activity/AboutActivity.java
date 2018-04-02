package com.wemgmemgfang.bt.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.utils.UmengUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * sayid ....
 * Created by wengmf on 2018/3/8.
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.llExit)
    LinearLayout llExit;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void initView() {
        UmengUtil.onEvent("AboutActivity");
        setSwipeBackEnable(true);
        tvTitle.setText("关于香蜜");

    }

    @OnClick({R.id.llExit})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.llExit:
                this.finish();
                break;
        }
    }


}

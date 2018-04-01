package com.wemgmemgfang.bt.ui.activity;

import android.widget.LinearLayout;

import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.utils.UmengUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * sayid ....
 * Created by wengmf on 2018/3/8.
 */

public class FeedbackActivity extends BaseActivity {


    @BindView(R.id.llExit)
    LinearLayout llExit;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void initView() {
        UmengUtil.onEvent("FeedbackActivity");
    }


    @OnClick(R.id.llExit)
    public void onViewClicked() {
        this.finish();
    }
}

package com.wemgmemgfang.bt.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.ToastUtils;
import com.pgyersdk.activity.FeedbackActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseFragment;
import com.wemgmemgfang.bt.base.Constant;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.ui.activity.AboutActivity;
import com.wemgmemgfang.bt.ui.activity.CollectionActivity;
import com.wemgmemgfang.bt.ui.activity.DownListActivity;
import com.wemgmemgfang.bt.ui.activity.MainActivity;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * sayid ....
 * Created by wengmf on 2018/3/12.
 */

public class MeFragment extends BaseFragment {


    @BindView(R.id.updae)
    RelativeLayout updae;
    @BindView(R.id.baout)
    RelativeLayout baout;
    @BindView(R.id.feedback)
    RelativeLayout feedback;
    @BindView(R.id.exit)
    RelativeLayout exit;
    @BindView(R.id.down)
    RelativeLayout down;
    @BindView(R.id.rl_collection)
    RelativeLayout rlCollection;
    @BindView(R.id.ll_denglu)
    LinearLayout llDenglu;

    UMShareAPI mShareAPI;


    @Override
    public void loadData() {
        mShareAPI = UMShareAPI.get(getActivity());
        setState(Constant.STATE_SUCCESS);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_me_home;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void attachView() {

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @OnClick({R.id.updae, R.id.baout, R.id.feedback, R.id.exit, R.id.down,R.id.ll_denglu,R.id.rl_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.down:
                getActivity().startActivity(new Intent(getActivity(), DownListActivity.class));
                break;
            case R.id.rl_collection:
                getActivity().startActivity(new Intent(getActivity(), CollectionActivity.class));

                break;

            case R.id.updae:
                ToastUtils.showLongToast("已是最新版本");
                break;
            case R.id.baout:
                getActivity().startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.feedback:
                getActivity().startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.exit:
                MainActivity.mainActivity.killAll();
                break;


            case R.id.ll_denglu:

                mShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.WEIXIN, umAuthListener);
//                mShareAPI.getPlatformInfo(getActivity(), SHARE_MEDIA.SINA, umAuthListener);
                break;

        }
    }
    //登录 回调 文档 http://dev.umeng.com/social/android/login-page#2
    UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            Log.i("登录结果",map.toString());
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            Log.i("登录结果",throwable.toString());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };
}

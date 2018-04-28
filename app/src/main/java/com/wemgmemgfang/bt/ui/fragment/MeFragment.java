package com.wemgmemgfang.bt.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.ToastUtils;
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
import com.wemgmemgfang.bt.ui.activity.FeedbackActivity;
import com.wemgmemgfang.bt.ui.activity.MainActivity;
import com.wemgmemgfang.bt.utils.UmengUtil;

import java.util.Map;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.OnClick;

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

    @Override
    public void loadData() {
        setState(Constant.STATE_SUCCESS);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_me_home;
    }

    @Override
    protected void initView() {
        UmengUtil.onEvent("MeFragment");
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
                MainActivity.mainActivity.startActivityIn(new Intent(getActivity(), DownListActivity.class),getActivity());
                break;
            case R.id.rl_collection:
                MainActivity.mainActivity.startActivityIn(new Intent(getActivity(), CollectionActivity.class),getActivity());
                break;
            case R.id.updae:
                ToastUtils.showLongToast("已是最新版本");
                break;
            case R.id.baout:
                MainActivity.mainActivity.startActivityIn(new Intent(getActivity(), AboutActivity.class),getActivity());
                break;
            case R.id.feedback:
                MainActivity.mainActivity.startActivityIn(new Intent(getActivity(), FeedbackActivity.class),getActivity());

                break;
            case R.id.exit:
                MainActivity.mainActivity.killAll();
                break;
            case R.id.ll_denglu:
//                ToastUtils.showLongToast("暂不支持");
                authorization(SHARE_MEDIA.WEIXIN);
                break;
        }
    }


    private  static  String TAG = "MeFragment";
    //授权
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                LogUtils.e("onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                LogUtils.e(TAG, "onComplete " + "授权完成");

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");

                Toast.makeText(getActivity(), "name=" + name + ",gender=" + gender, Toast.LENGTH_SHORT).show();

                //拿到信息去请求登录接口。。。
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                LogUtils.e(TAG, "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                LogUtils.e(TAG, "onCancel " + "授权取消");
            }
        });
    }
}

package com.wemgmemgfang.bt.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.view.LoadingPage;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/4 0004.
 */

public abstract class BaseFragment extends Fragment implements Stateful {


    protected View parentView;
    protected FragmentActivity activity;
    protected LayoutInflater inflater;

    protected Context mContext;

    public LoadingPage mLoadingPage;

    private boolean mIsVisible = false;     // fragment是否显示了

    private boolean isPrepared = false;

    private boolean isFirst = true; //只加载一次界面


    protected View contentView;
    private Unbinder bind;
    public ProgressDialog loadPd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        if (mLoadingPage == null) {
            mLoadingPage = new LoadingPage(getContext()) {
                @Override
                protected void initView() {
                    if (isFirst) {
                        BaseFragment.this.contentView = this.contentView;
                        bind = ButterKnife.bind(BaseFragment.this, contentView);
                        BaseFragment.this.initView(getArguments());
                        isFirst = false;
                    }
                }

                @Override
                protected void loadData() {
                    BaseFragment.this.loadData();
                }

                @Override
                protected int getLayoutId() {
                    return BaseFragment.this.getLayoutResId();
                }
            };
        }
        isPrepared = true;
        loadBaseData();
        return mLoadingPage;
    }


    @Override
    public void setState(int state) {
        mLoadingPage.state = state;
        mLoadingPage.showPage();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {//fragment可见
            mIsVisible = true;
            onVisible();
        } else {//fragment不可见
            mIsVisible = false;
        }
    }


    public void showLoadPd() {
        if (loadPd == null) {
            loadPd = new ProgressDialog(getActivity());
            loadPd.setMessage("正在加载中......");
            loadPd.show();
        } else {
            loadPd.show();
        }
    }


    public void dismissLoadPd() {
        if (loadPd != null && loadPd.isShowing())
            loadPd.dismiss();
    }




    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */

    protected void onVisible() {
        if (isFirst) {
            setupActivityComponent(BaseApplication.getBaseApplication().getAppComponent());
            attachView();
        }
        loadBaseData();//根据获取的数据来调用showView()切换界面
    }

    public void loadBaseData() {
        if (!mIsVisible || !isPrepared || !isFirst) {
            return;
        }
        loadData();
    }

    public abstract void loadData();

    public abstract int getLayoutResId();

    public abstract void attachView();

    protected void initView(Bundle bundle) {
    }

    protected void initView() {
    }

    protected abstract void setupActivityComponent(AppComponent appComponent);

    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }

}

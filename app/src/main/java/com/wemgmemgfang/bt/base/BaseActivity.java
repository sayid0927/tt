package com.wemgmemgfang.bt.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.view.CommonDialog;
import com.wemgmemgfang.bt.view.SwipeBackActivity.SwipeBackActivity;
import com.wemgmemgfang.bt.view.SwipeBackActivity.SwipeBackLayout;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends SwipeBackActivity {


    // 管理运行的所有的activity
    public final static List<AppCompatActivity> mActivities = new LinkedList<>();
    private CommonDialog commonDialog;
    private AlertDialog dialog;
    public ProgressDialog loadPd;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());


        ButterKnife.bind(this);
        setupActivityComponent(BaseApplication.getBaseApplication().getAppComponent());
        attachView();
        initView();

        synchronized (mActivities) {
            mActivities.add(this);
        }
    }

    public void showLoadPd() {
        if (loadPd == null) {
            loadPd = new ProgressDialog(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        return super.dispatchTouchEvent(event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        detachView();
        if (dialog != null)
            dialog.dismiss();
    }

    @SuppressWarnings("deprecation")
    public void initSwipeBackLayout() {
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    public void setEdgeTrackingEnabled(int size, int position) {
        if (size == 0) {
        }
        // 只有一个fragment  - 左右滑关闭
        else if (size == 1 && position == 0) {
            mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
        }
        // 多个fragment  - 位于左侧尽头 - 只可左滑关闭
        else if (size != 1 && position == 0) {
            mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        }
        // 多个fragment  - 位于右侧尽头 - 只可右滑关闭
        else if (size != 1 && position == size - 1) {
            mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);
        }
        // 多个fragment  - 位于中间 - 屏蔽所有左右滑关闭事件
        else {
            mSwipeBackLayout.setEdgeTrackingEnabled(0);
        }
    }

    protected void finishActivity() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    public   void startActivityIn(Intent intent, Activity curAct) {
        if (intent != null) {
            curAct.startActivity(intent);
            curAct.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        }
    }

    @Override
    public void finish() {
        super.finish();
        finishActivity();
    }

    public void killAll() {
        // 复制了一份mActivities 集合Å
        List<AppCompatActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<>(mActivities);
        }
        for (AppCompatActivity activity : copy) {
            activity.finish();
        }
        // 杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void dismissDialog() {
        if (dialog != null)
            dialog.dismiss();
    }


    public void showDialog(String str) {

    }

    protected abstract void setupActivityComponent(AppComponent appComponent);

    public abstract int getLayoutId();

    public abstract void attachView();

    public abstract void detachView();

    public abstract void initView();

}

package com.wemgmemgfang.bt.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.umeng.analytics.MobclickAgent;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.view.CommonDialog;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {


    // 管理运行的所有的activity
    public final static List<AppCompatActivity> mActivities = new LinkedList<>();
    private CommonDialog commonDialog;
    private AlertDialog dialog;
    public ProgressDialog loadPd;

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
        dialog = new AlertDialog.Builder(this)
//                .setIcon(R.mipmap.icon)//设置标题的图片
//                .setTitle("我是对话框")//设置对话框的标题
                .setMessage(str)//设置对话框的内容
                //设置对话框的按钮
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MainActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//                })
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MainActivity.this, "点击了确定的按钮", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//                })
                .create();
        dialog.show();
    }

    protected abstract void setupActivityComponent(AppComponent appComponent);

    public abstract int getLayoutId();

    public abstract void attachView();

    public abstract void detachView();

    public abstract void initView();

}

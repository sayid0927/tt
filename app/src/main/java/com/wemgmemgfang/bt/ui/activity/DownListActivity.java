package com.wemgmemgfang.bt.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.database.DownVideoInfoDao;
import com.wemgmemgfang.bt.entity.DownVideoInfo;
import com.wemgmemgfang.bt.ui.adapter.DownListApadter;
import com.wemgmemgfang.bt.utils.DownLoadHelper;
import com.wemgmemgfang.bt.utils.GreenDaoUtil;
import com.wemgmemgfang.bt.utils.NotificationHandler;
import com.xunlei.downloadlib.XLTaskHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import player.XLVideoPlayActivity;

public class DownListActivity extends BaseActivity {

    @BindView(R.id.rv_down)
    RecyclerView rvDown;
    @BindView(R.id.llExit)
    LinearLayout llExit;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private NotificationHandler nHandler;
    private DownListApadter mAdapter;
    private DownVideoInfoDao downVideoInfoDao;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_down_list;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void detachView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(DownVideoInfo downVideoBean) {
        List<DownVideoInfo> downVideoInfoList = downVideoInfoDao.loadAll();
        mAdapter.setNewData(downVideoInfoList);
    }

    @Override
    public void initView() {
        setSwipeBackEnable(true);
        EventBus.getDefault().register(this);
        XLTaskHelper.init(getApplicationContext());
        tvTitle.setText("我的下载");
        downVideoInfoDao = GreenDaoUtil.getDaoSession().getDownVideoInfoDao();
        final List<DownVideoInfo> downVideoInfoList = downVideoInfoDao.loadAll();

        mAdapter = new DownListApadter(downVideoInfoList, DownListActivity.this);
        rvDown.setLayoutManager(new LinearLayoutManager(DownListActivity.this));
        rvDown.setAdapter(mAdapter);

        mAdapter.OnItemLongClickListenter(new DownListApadter.OnItemLongClickListenter() {
            @Override
            public void OnItemLongClickListenter(DownVideoInfo item) {
                showDeleteDialog(item);
            }
        });

        mAdapter.OnDeleteItemListenter(new DownListApadter.OnDeleteItemListenter() {
            @Override
            public void OnDeleteItemListenter(DownVideoInfo item) {

                switch (item.getState()) {
                    case "下载完成":
                        List<File> files = FileUtils.listFilesInDir(item.getSaveVideoPath());
                        if (files != null && files.size() != 0) {
                            for (int i = 0; i < files.size(); i++) {
                                String path = files.get(i).getAbsolutePath();
                                if (xllib.FileUtils.isMediaFile(path)) {
                                    XLVideoPlayActivity.intentTo(DownListActivity.this, path, null);
                                    break;
                                }
                            }
                        } else {
                            ToastUtils.showLongToastSafe("没有本地文件");
                        }
                        break;

                    case "下载中":
                        showStopDownDialog(item);
                        break;
                    case  "下载错误":
                        showRestDownDialog(item);
                        break;
                    case "下载暂停":
                        showRestDownDialog(item);
                        break;

                    case "等待下载":


                        break;


                }
            }
        });

        mAdapter.OnItemDownSuccess(new DownListApadter.OnItemDownSuccess() {
            @Override
            public void OnItemDownSuccess(DownVideoInfo item) {
                final List<DownVideoInfo> downVideoInfoList = downVideoInfoDao.loadAll();
                for (DownVideoInfo d : downVideoInfoList) {
                    if (!d.getState().equals("下载完成")) {
                        DownLoadHelper.getInstance().submit(DownListActivity.this, d);
                        break;
                    }
                }
            }
        });
    }

    @OnClick(R.id.llExit)
    public void onViewClicked() {
        this.finish();
    }

    private void showDeleteDialog(final DownVideoInfo item) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("删除下载任务,并删除本地文件")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        downVideoInfoDao.delete(item);
//                        FileUtils.deleteDir(item.getSaveVideoPath());
//                        List<DownVideoInfo> downVideoInfoList = downVideoInfoDao.loadAll();
//                        mAdapter.setNewData(downVideoInfoList);
                        downVideoInfoDao.delete(item);
                        XLTaskHelper.instance().deleteTask(item.getTaskId(),item.getSaveVideoPath());
                        List<DownVideoInfo> downVideoInfoList = downVideoInfoDao.loadAll();
                        mAdapter.setNewData(downVideoInfoList);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void showStopDownDialog(final DownVideoInfo item) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("文件正在下载是否停止下载")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        item.setId(item.getId());
                        item.setState(getString(R.string.downStop));
                        downVideoInfoDao.update(item);
                        XLTaskHelper.instance().stopTask(item.getTaskId());
                        List<DownVideoInfo> downVideoInfoList = downVideoInfoDao.loadAll();
                        mAdapter.setNewData(downVideoInfoList);
                        dialog.dismiss();

                    }
                }).create();
        dialog.show();
    }

    private void showRestDownDialog(final DownVideoInfo item) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("是否重新开始下载")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downVideoInfoDao.delete(item);
                        XLTaskHelper.instance().deleteTask(item.getTaskId(),item.getSaveVideoPath());
                        DownLoadHelper.getInstance().submit(DownListActivity.this, item);
                        List<DownVideoInfo> downVideoInfoList = downVideoInfoDao.loadAll();
                        mAdapter.setNewData(downVideoInfoList);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }


}

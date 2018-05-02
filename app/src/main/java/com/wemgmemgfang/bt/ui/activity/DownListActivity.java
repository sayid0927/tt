package com.wemgmemgfang.bt.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.bean.DownVideoBean;
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

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DownListActivity extends BaseActivity {

    @BindView(R.id.rv_down)
    RecyclerView rvDown;
    @BindView(R.id.llExit)
    LinearLayout llExit;

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
                DownVideoInfo downVideoInfo = downVideoInfoDao.queryBuilder().where(DownVideoInfoDao.Properties.PlayPath.eq(item.getPlayPath())).unique();
                if(item.getState().equals(getString(R.string.downIng))){
                    downVideoInfo.setId(downVideoInfo.getId());
                    downVideoInfo.setState(getString(R.string.downStop));
                    downVideoInfoDao.update(downVideoInfo);
                    List<DownVideoInfo> downVideoInfoList = downVideoInfoDao.loadAll();
                    mAdapter.setNewData(downVideoInfoList);
                    XLTaskHelper.instance().stopTask(item.getTaskId());
                }else {
                    DownLoadHelper.getInstance().restartSubmit(DownListActivity.this, downVideoInfo);
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
                .setMessage("删除下载任务")
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
                        FileUtils.deleteDir(item.getSaveVideoPath());
                        List<DownVideoInfo> downVideoInfoList = downVideoInfoDao.loadAll();
                        mAdapter.setNewData(downVideoInfoList);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }
}

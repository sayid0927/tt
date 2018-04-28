package com.wemgmemgfang.bt.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.bean.DownVideoBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.database.DownVideoInfoDao;
import com.wemgmemgfang.bt.entity.DownVideoInfo;
import com.wemgmemgfang.bt.ui.adapter.DownListApadter;
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
    public void Event(DownVideoBean downVideoBean) {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initView() {
        setSwipeBackEnable(true);
        EventBus.getDefault().register(this);
        downVideoInfoDao = GreenDaoUtil.getDaoSession().getDownVideoInfoDao();
        final List<DownVideoInfo> downVideoInfoList = downVideoInfoDao.loadAll();

        mAdapter = new DownListApadter(downVideoInfoList, DownListActivity.this);
        rvDown.setLayoutManager(new LinearLayoutManager(DownListActivity.this));
        rvDown.setAdapter(mAdapter);

        mAdapter.OnDeleteItemListenter(new DownListApadter.OnDeleteItemListenter() {
            @Override
            public void OnDeleteItemListenter(DownVideoInfo item) {

                downVideoInfoList.remove(item);
                XLTaskHelper.instance().deleteTask(item.getTaskId(),  item.getSaveVideoPath());
                downVideoInfoDao.delete(item);
                mAdapter.notifyDataSetChanged();

            }
        });
    }

    @OnClick(R.id.llExit)
    public void onViewClicked() {
        this.finish();
    }
}

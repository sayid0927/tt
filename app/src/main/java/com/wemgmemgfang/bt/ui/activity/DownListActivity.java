package com.wemgmemgfang.bt.ui.activity;

import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.FileUtils;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.base.BaseApplication;
import com.wemgmemgfang.bt.bean.DownFileBean;
import com.wemgmemgfang.bt.bean.DownVideoBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.ui.adapter.DownFileListApadter;
import com.wemgmemgfang.bt.ui.adapter.DownListApadter;
import com.wemgmemgfang.bt.utils.DeviceUtils;
import com.wemgmemgfang.bt.utils.NotificationHandler;
import com.xunlei.downloadlib.XLTaskHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DownListActivity extends BaseActivity {

    @BindView(R.id.tv_down)
    TextView tvDown;
    @BindView(R.id.rv_down)
    RecyclerView rvDown;
    @BindView(R.id.tv_down_ok)
    TextView tvDownOk;
    @BindView(R.id.rv_down_ok)
    RecyclerView rvDownOk;
    @BindView(R.id.llExit)
    LinearLayout llExit;

    private NotificationHandler nHandler;
    private DownListApadter mAdapter;

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

        EventBus.getDefault().register(this);

        nHandler = NotificationHandler.getInstance(this);
        mAdapter = new DownListApadter(BaseApplication.downVideoBeanList, DownListActivity.this);
        rvDown.setLayoutManager(new LinearLayoutManager(DownListActivity.this));
        rvDown.setAdapter(mAdapter);

        mAdapter.OnDeleteItemListenter(new DownListApadter.OnDeleteItemListenter() {
            @Override
            public void OnDeleteItemListenter(DownVideoBean item) {
                String videoPath = DeviceUtils.getSDVideoPath(item.getPlayTitle());
                XLTaskHelper.instance().deleteTask(item.getTaskId(), videoPath);
                BaseApplication.downVideoBeanList.remove(item);
                nHandler.cancelNotification((int) item.getTaskId());
                mAdapter.notifyDataSetChanged();
            }
        });

        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Video";
        List<File> files = FileUtils.listFilesInDir(sdDir);
        List<DownFileBean> downFileBeanList = new ArrayList<>();

        if (files != null && files.size() != 0) {
            for (File e : files) {
                if (FileUtils.isFile(e) && !e.getName().endsWith(".js")) {
                    DownFileBean downFileBean = new DownFileBean();
                    downFileBean.setFileName(e.getName());
                    downFileBean.setFilePath(e.getAbsolutePath());
                    downFileBean.setFileSize(e.getFreeSpace());
                    downFileBeanList.add(downFileBean);

                }
            }
        }

        DownFileListApadter fAdapter = new DownFileListApadter(downFileBeanList, DownListActivity.this);
        rvDownOk.setLayoutManager(new LinearLayoutManager(DownListActivity.this));
        rvDownOk.setAdapter(fAdapter);

    }

    @OnClick(R.id.llExit)
    public void onViewClicked() {
        this.finish();
    }
}

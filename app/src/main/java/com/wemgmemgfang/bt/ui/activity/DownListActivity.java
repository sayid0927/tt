package com.wemgmemgfang.bt.ui.activity;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.database.DownVideoInfoDao;
import com.wemgmemgfang.bt.entity.DownVideoInfo;
import com.wemgmemgfang.bt.ui.adapter.DownListApadter;
import com.wemgmemgfang.bt.utils.DownLoadHelper;
import com.wemgmemgfang.bt.utils.GreenDaoUtil;
import com.wemgmemgfang.bt.utils.NotificationHandler;
import com.wemgmemgfang.bt.utils.UmengUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DownListActivity extends BaseActivity {

    @BindView(R.id.rv_down)
    SwipeMenuRecyclerView rvDown;
    @BindView(R.id.llExit)
    LinearLayout llExit;

    private NotificationHandler nHandler;
    private DownListApadter mAdapter;
    private DownVideoInfoDao downVideoInfoDao;
    private List<DownVideoInfo> downVideoInfoList;
    private DownVideoInfo downVideoInfo;

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
    public void Event(DownVideoInfo downVideoInfo) {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initView() {
        UmengUtil.onEvent("DownListActivity");
        EventBus.getDefault().register(this);
        setSwipeBackEnable(true);
        downVideoInfoDao = GreenDaoUtil.getDaoSession().getDownVideoInfoDao();
        downVideoInfoList = downVideoInfoDao.loadAll();

        mAdapter = new DownListApadter(downVideoInfoList, DownListActivity.this);
        rvDown.setLayoutManager(new LinearLayoutManager(DownListActivity.this));
        rvDown.setSwipeMenuCreator(swipeMenuCreator);
        rvDown.setSwipeMenuItemClickListener(mMenuItemClickListener);
        rvDown.setAdapter(mAdapter);

    }

    @OnClick(R.id.llExit)
    public void onViewClicked() {
        this.finish();
    }

    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_80);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            SwipeMenuItem deleteItem = new SwipeMenuItem(DownListActivity.this)
                    .setBackground(R.drawable.selector_red)
                    .setImage(R.mipmap.ic_action_delete)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

            SwipeMenuItem startItem = new SwipeMenuItem(DownListActivity.this)
                    .setBackground(R.drawable.selector_green)
                    .setText("开始")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(startItem); // 添加菜单到右侧。

            SwipeMenuItem stopItem = new SwipeMenuItem(DownListActivity.this)
                    .setBackground(R.drawable.selector_bul)
                    .setText("停止")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(stopItem); // 添加菜单到右侧。

        }
    };


    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                switch (menuPosition) {
                    case 0:
                        downVideoInfo = downVideoInfoList.get(adapterPosition);
                        downVideoInfoList.remove(downVideoInfo);
                        downVideoInfo.setState("删除");
                        DownLoadHelper.getInstance().submit(DownListActivity.this, downVideoInfo);

                        break;

                    case 1:
                        downVideoInfo = downVideoInfoList.get(adapterPosition);
                        downVideoInfo.setState("开始");
                        DownLoadHelper.getInstance().submit(DownListActivity.this, downVideoInfo);

                        break;
                    case 2:
                        downVideoInfo = downVideoInfoList.get(adapterPosition);
                        downVideoInfo.setState("停止");
                        DownLoadHelper.getInstance().submit(DownListActivity.this, downVideoInfo);
                        break;
                }
            }
        }
    };
}

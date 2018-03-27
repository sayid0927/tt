package com.wemgmemgfang.bt.ui.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.FileUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.blankj.utilcode.utils.ZipUtils;
import com.orhanobut.logger.Logger;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.base.BaseActivity;
import com.wemgmemgfang.bt.bean.DownHrefBean;
import com.wemgmemgfang.bt.bean.ViewBoxBean;
import com.wemgmemgfang.bt.component.AppComponent;
import com.wemgmemgfang.bt.component.DaggerMainComponent;
import com.wemgmemgfang.bt.database.CollectionInfoDao;
import com.wemgmemgfang.bt.entity.CollectionInfo;
import com.wemgmemgfang.bt.presenter.contract.ViewBoxContract;
import com.wemgmemgfang.bt.presenter.impl.ViewBoxPresenter;
import com.wemgmemgfang.bt.service.DownTorrentVideoService;
import com.wemgmemgfang.bt.utils.DeviceUtils;
import com.wemgmemgfang.bt.utils.GreenDaoUtil;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;
import com.wemgmemgfang.bt.utils.PreferUtil;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import player.XLVideoPlayActivity;
import pub.devrel.easypermissions.EasyPermissions;

import static com.wemgmemgfang.bt.utils.DeviceUtils.ReadTxtFile;


/**
 * sayid ....
 * Created by wengmf on 2018/3/12.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class ViewBoxActivity extends BaseActivity implements ViewBoxContract.View, EasyPermissions.PermissionCallbacks {

    @Inject
    ViewBoxPresenter mPresenter;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.llExit)
    LinearLayout llExit;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.size)
    TextView size;
    @BindView(R.id.sizeNum)
    TextView sizeNum;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.down_Video)
    Button downVideo;
    @BindView(R.id.play_Video)
    TextView playVideo;

    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.llRight)
    LinearLayout llRight;

    private String hrefUrl;
    private DownHrefBean downHrefBean;
    private String torrFile = null;
    private String videoPath, strTitle;
    private int clickType;
    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private CollectionInfoDao collectionInfoDao;
    private boolean isCollertion;
    private String Url,ImgUrl;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_viewbox;
    }

    @Override
    public void attachView() {
        mPresenter.attachView(this);
    }

    @Override
    public void detachView() {
        mPresenter.detachView();
    }

    @Override
    public void initView() {
        Url = getIntent().getStringExtra("HrefUrl");
        ImgUrl = getIntent().getStringExtra("ImgUrl");
        mPresenter.Fetch_ViewBoxInfo("http://www.zei8.me" + Url);
        showLoadPd();

    }

    @Override
    public void showError(String message) {
        if (loadPd.isShowing()) {
            loadPd.dismiss();
        }
        showDialog(message);
    }

    @Override
    public void Fetch_ViewBoxInfo_Success(ViewBoxBean data) {
        dismissLoadPd();
        strTitle = data.getAlt();
        ImgLoadUtils.loadImage(ViewBoxActivity.this, ImgUrl, img);
        tvTitle.setText(strTitle);
        title.setText(strTitle);
        size.setText(data.getSize());
        sizeNum.setText(data.getSizeNum());
        content.setText(data.getContext());
        hrefUrl = "http://www.zei8.me" + data.getHref();

        collectionInfoDao = GreenDaoUtil.getDaoSession().getCollectionInfoDao();
        List<CollectionInfo> cList = collectionInfoDao.queryBuilder().where(CollectionInfoDao.Properties.Title.eq(strTitle)).list();
        if (cList != null && cList.size() != 0) {
            isCollertion = true;
            tvCollection.setText("已收藏");
            ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc_ss));
        } else {
            isCollertion = false;
            tvCollection.setText("收藏");
            ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc));
        }
    }

    @Override
    public void Fetch_HrefUrl_Success(DownHrefBean data) {
        this.downHrefBean = data;
        mPresenter.download_Zip(data);
    }

    @Override
    public void download_Zip_Success(String filePath) {

        String destFileDir = DeviceUtils.getSDPath(downHrefBean.getTitle());
        videoPath = DeviceUtils.getSDVideoPath(downHrefBean.getTitle());

        try {
            boolean jieya = ZipUtils.unzipFile(filePath, destFileDir);
            if (jieya) {
                FileUtils.deleteFile(filePath);
                List<File> files = FileUtils.listFilesInDir(destFileDir);
                for (File f : files) {
                    if (f.getAbsolutePath().endsWith(".torrent")) {
                        torrFile = f.getAbsolutePath();
                        onClickType();
                        break;
                    } else if (f.getAbsolutePath().endsWith(".txt")) {
                        for (String txt : ReadTxtFile(f.getAbsolutePath())) {
                            if (txt.contains("thunder")) {
                                String tmp= txt.substring(0,txt.indexOf("t")-1);
                                Log.e("temp4>>    ",txt);
                                Log.e("temp4>>    ",tmp);
                                torrFile = txt.substring(txt.indexOf("t"), txt.length()).replace("\n", "").trim();
                                onClickType();
                                break;
                            }
                        }
                    }
                }
                if (torrFile == null) {
                    showDialog("暂无电影资源");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onClickType() {
        switch (clickType) {
            case 0:
                PreferUtil.getInstance().setPlayPath(torrFile);
                PreferUtil.getInstance().setPlayTitle(downHrefBean.getTitle());
                PreferUtil.getInstance().setPlayimgUrl(ImgUrl);
                startService(new Intent(this, DownTorrentVideoService.class));
                break;
            case 1:
                XLVideoPlayActivity.intentTo(this, torrFile, downHrefBean.getTitle());
                break;

        }
    }

    @Override
    public void Down_Torrent_File_Success() {

    }

    @OnClick({R.id.llExit, R.id.tvTitle, R.id.down_Video, R.id.play_Video, R.id.llRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llExit:
                this.finish();
                break;

            case R.id.down_Video:
                clickType = 0;
                if (!EasyPermissions.hasPermissions(this, perms)) {
                    EasyPermissions.requestPermissions(this, "需要读写权限", 1000, perms);
                } else {
                    mPresenter.Fetch_HrefUrl(hrefUrl);
                }
                break;

            case R.id.play_Video:
                loadPd.show();
                clickType = 1;
                if (!EasyPermissions.hasPermissions(this, perms)) {
                    EasyPermissions.requestPermissions(this, "需要读写权限", 1000, perms);
                } else {
                    mPresenter.Fetch_HrefUrl(hrefUrl);
                }
                break;

            case R.id.llRight:

                if (isCollertion) {
                    isCollertion = false;
                    CollectionInfo collectionInfo = collectionInfoDao.queryBuilder().where(CollectionInfoDao.Properties.Title.eq(strTitle)).unique();
                    collectionInfoDao.delete(collectionInfo);
                    tvCollection.setText("收藏");
                    ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc));

                } else {
                    isCollertion = true;
                    CollectionInfo collectionInfo = new CollectionInfo();
                    collectionInfo.setHrefUrl(Url);
                    collectionInfo.setTitle(strTitle);
                    collectionInfo.setImgUrl(ImgUrl);
                    collectionInfoDao.insert(collectionInfo);
                    tvCollection.setText("已收藏");
                    ivRight.setImageDrawable(getResources().getDrawable(R.mipmap.cc_ss));
                }
                break;

            case R.id.tvTitle:

                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        mPresenter.Fetch_HrefUrl(hrefUrl);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtils.showLongToast("没有权限无法下载电影");
    }
}

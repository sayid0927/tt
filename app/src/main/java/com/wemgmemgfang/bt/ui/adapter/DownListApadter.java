package com.wemgmemgfang.bt.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.blankj.utilcode.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.bean.DownVideoBean;
import com.wemgmemgfang.bt.entity.DownVideoInfo;
import com.wemgmemgfang.bt.ui.activity.DetailsActivity;
import com.wemgmemgfang.bt.ui.activity.ViewBoxActivity;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;

import java.util.List;

/**
 * sayid ....
 * Created by wengmf on 2018/3/22.
 */

public class DownListApadter extends BaseQuickAdapter<DownVideoInfo, BaseViewHolder> {

    private Context mContext;
    private List<DownVideoInfo> data;

    public DownListApadter(List<DownVideoInfo> data, Context mContext) {
        super(R.layout.item_down_list, data);
        this.mContext = mContext;
        this.data = data;
    }


    @Override
    protected void convert(BaseViewHolder helper, final DownVideoInfo item) {
        ImageView iv = helper.getView(R.id.iv_down);
        helper.setText(R.id.down_title, item.getPlayTitle());
        ProgressBar progressBar = helper.getView(R.id.progressBar);
        ImgLoadUtils.loadImage(mContext, item.getPlayimgUrl(), iv);

        progressBar.setVisibility(View.VISIBLE);
        helper.getView(R.id.tv_pro).setVisibility(View.VISIBLE);
        int size = 0;
        if (item.getMFileSize() > 0 && item.getMDownloadSize() > 0) {
            size = (int) (item.getMDownloadSize() * 100 / item.getMFileSize());
            progressBar.setProgress(size);
            switch (item.getState()) {
                case "下载暂停":
                    helper.setText(R.id.tv_pro, String.valueOf(size) + "%    " +  formatSize(item.getMFileSize())  + " / " + formatSize(item.getMDownloadSize()) + "    " +
                            item.getState());
                    break;
                case "下载错误":
                    helper.setText(R.id.tv_pro, String.valueOf(size) + "%    " +formatSize(item.getMFileSize())  + " / " + formatSize(item.getMDownloadSize())+ "    " +
                            item.getState());
                    break;
                case "下载中":
                    helper.setText(R.id.tv_pro, String.valueOf(size) + "%    " +formatSize(item.getMFileSize())  + " / " + formatSize(item.getMDownloadSize()) + "    " +
                            convertFileSize(item.getMDownloadSpeed()));
                    break;
                case "下载完成":
                    helper.setText(R.id.tv_pro,  item.getState() + "   文件路径   " + item.getSaveVideoPath());
                    if (onItemDownSuccess != null)
                        onItemDownSuccess.OnItemDownSuccess(item);
                    break;

                case "等待下载":
                    helper.setText(R.id.tv_pro, item.getState());
                    break;
            }
        } else {
            helper.setText(R.id.tv_pro, item.getState());
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteItemListenter != null)
                    onDeleteItemListenter.OnDeleteItemListenter(item);
            }
        });
        helper.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListenter != null)
                    onItemLongClickListenter.OnItemLongClickListenter(item);
                return false;
            }
        });
    }

    private OnDeleteItemListenter onDeleteItemListenter;
    private OnItemLongClickListenter onItemLongClickListenter;
    private OnItemDownSuccess onItemDownSuccess;


    public void OnDeleteItemListenter(OnDeleteItemListenter onDeleteItemListenter) {
        this.onDeleteItemListenter = onDeleteItemListenter;
    }

    public void OnItemLongClickListenter(OnItemLongClickListenter OnItemLongClickListenter) {
        this.onItemLongClickListenter = OnItemLongClickListenter;
    }

    public void OnItemDownSuccess(OnItemDownSuccess onItemDownSuccess) {
        this.onItemDownSuccess = onItemDownSuccess;
    }

    public interface OnDeleteItemListenter {
        void OnDeleteItemListenter(DownVideoInfo item);
    }

    public interface OnItemLongClickListenter {
        void OnItemLongClickListenter(DownVideoInfo item);
    }

    public interface OnItemDownSuccess {
        void OnItemDownSuccess(DownVideoInfo item);
    }

    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f M" : "%.1f M", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f K" : "%.1f K", f);
        } else
            return String.format("%d B", size);
    }

    private String formatSize(long target_size) {
        return Formatter.formatFileSize(mContext, target_size);
    }
}

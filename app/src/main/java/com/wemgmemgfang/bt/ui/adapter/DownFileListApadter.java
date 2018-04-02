package com.wemgmemgfang.bt.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.bean.DownFileBean;

import java.util.List;

/**
 * sayid ....
 * Created by wengmf on 2018/3/22.
 */

public class DownFileListApadter extends BaseQuickAdapter<DownFileBean, BaseViewHolder> {

    private Context mContext;
    private List<DownFileBean> data;

    public DownFileListApadter(List<DownFileBean> data, Context mContext) {
        super(R.layout.item_file_list, data);
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final DownFileBean item) {
        helper.setText(R.id.file_title,item.getFileName());
        helper.setText(R.id.down_label,item.getFilePath());
    }
}

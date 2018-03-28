package com.wemgmemgfang.bt.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.bean.MoreInfoBean;
import com.wemgmemgfang.bt.bean.SearchDialogBean;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/4 0004.
 */

public class MoreDialogAdapter extends BaseQuickAdapter<SearchDialogBean, BaseViewHolder> {



    private Context mContext;
    private List<SearchDialogBean> data;

    public MoreDialogAdapter(List<SearchDialogBean> data, Context mContext) {
        super( R.layout.item_search_dialog, data);
        this.mContext = mContext;
        this.data = data;

    }

    @Override
    protected void convert(BaseViewHolder helper, final SearchDialogBean item) {

        helper.setText(R.id.tv_title,item.getType()+item.getTypeName());
    }
}

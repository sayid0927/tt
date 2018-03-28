package com.wemgmemgfang.bt.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.bean.MoreInfoBean;
import com.wemgmemgfang.bt.bean.SearchHorizontalBean;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/4 0004.
 */

public class More_Search_Adapter extends BaseQuickAdapter<SearchHorizontalBean, BaseViewHolder> {


    private Context mContext;
    private List<SearchHorizontalBean> data;


    public More_Search_Adapter(List<SearchHorizontalBean> data, Context mContext) {
        super(R.layout.item_more_horizontal, data);
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SearchHorizontalBean item) {
        helper.setText(R.id.tv_title, item.getType());
        helper.setText(R.id.tv_tlob,item.getName());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlayItemClickListener.OnPlayItemClickListener(item);
            }
        });
    }

    private OnPlayItemClickListener onPlayItemClickListener;

    public void setOnPlayItemClickListener(OnPlayItemClickListener onPlayItemClickListener) {
        this.onPlayItemClickListener = onPlayItemClickListener;
    }

    public interface OnPlayItemClickListener {
        void OnPlayItemClickListener(SearchHorizontalBean item);
    }

}

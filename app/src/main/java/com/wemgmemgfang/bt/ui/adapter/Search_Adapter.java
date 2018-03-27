package com.wemgmemgfang.bt.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.bean.MoreInfoBean;
import com.wemgmemgfang.bt.bean.SearchBean;
import com.wemgmemgfang.bt.utils.ImgLoadUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/11/4 0004.
 */

public class Search_Adapter extends BaseQuickAdapter<SearchBean, BaseViewHolder> {



    private Context mContext;
    private List<SearchBean> data;

    public Search_Adapter(List<SearchBean> data, Context mContext) {
        super( R.layout.item_search_list, data);
        this.mContext = mContext;
        this.data = data;

    }

    @Override
    protected void convert(BaseViewHolder helper, final SearchBean item) {
             helper.setText(R.id.file_title,item.getH4());
             helper.setText(R.id.down_label,item.getH5());
             helper.setText(R.id.down_em,item.getEm());
            ImageView iv =   helper.getView(R.id.iv_down);
            ImgLoadUtils.GifloadImage(mContext,item.getImgUrl(),iv);
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
        void OnPlayItemClickListener(SearchBean item);
    }
}

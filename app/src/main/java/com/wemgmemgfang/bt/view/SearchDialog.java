package com.wemgmemgfang.bt.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.wemgmemgfang.bt.R;
import com.wemgmemgfang.bt.bean.MoreInfoBean;
import com.wemgmemgfang.bt.bean.SearchDialogBean;
import com.wemgmemgfang.bt.bean.SearchHorizontalBean;
import com.wemgmemgfang.bt.ui.adapter.MoreDialogAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/26.
 */

public class SearchDialog extends Dialog {
    private Context context;
    private List<MoreInfoBean.MoreTypeBean> data;
    private RecyclerView recyclerView;
    private  SearchHorizontalBean item;

    public SearchDialog(Context context, SearchHorizontalBean item, List<MoreInfoBean.MoreTypeBean> data) {
        super(context, R.style.dialog);
        this.context = context;
        this.data = data;
        this.item = item;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//将弹出框设置为全局
        setContentView(R.layout.searchdialog);
        setCanceledOnTouchOutside(false);
        setCancelable(true);//弹出框不可以换返回键取消
        setCanceledOnTouchOutside(true);//失去焦点不会消失
        recyclerView = this.findViewById(R.id.rv_serc);
        List<SearchDialogBean> searchDialogBeanList = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            if(item.getType().equals(data.get(i).getType())){
                SearchDialogBean searchDialogBean = new SearchDialogBean();
                searchDialogBean.setHref(data.get(i).getHref());
                searchDialogBean.setType(data.get(i).getType());
                searchDialogBean.setTypeName(data.get(i).getTypeName());
                searchDialogBeanList.add(searchDialogBean);
            }
        }
        MoreDialogAdapter moreDialogAdapter = new MoreDialogAdapter(searchDialogBeanList,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(moreDialogAdapter);
        moreDialogAdapter.OnMoreItemClick(new MoreDialogAdapter.OnMoreItemClick() {
            @Override
            public void OnMoreItemClick(SearchDialogBean item) {
                onMoreDialogItemClick.OnMoreDialogItemClick(item);
            }
        });
    }
    OnMoreDialogItemClick onMoreDialogItemClick;

    public  void  OnMoreDialogItemClick(OnMoreDialogItemClick onMoreDialogItemClick){
        this.onMoreDialogItemClick  = onMoreDialogItemClick;
    }

    public  interface  OnMoreDialogItemClick{
        void  OnMoreDialogItemClick(SearchDialogBean item);
    }




    public void setDialogAttributes(Activity context, final Dialog dialog,
                                    float widthP, float heightP, int gravity) {
        Display d = context.getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        Point mPoint = new Point();
        d.getSize(mPoint);
        if (heightP != 0)
            p.height = (int) (mPoint.y * heightP);
        if (widthP != 0)
            p.width = (int) (mPoint.x * widthP);
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setGravity(gravity);
    }

}

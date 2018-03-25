package com.wemgmemgfang.bt.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wemgmemgfang.bt.R;


/**
 * Created by Administrator on 2016/10/26.
 */

public class CommonDialog extends Dialog  {
    private TextView textView;
    private Context context;
    private  String str;


    public CommonDialog(Context context,String str) {
        super(context, R.style.dialog);
        this.context = context;
        this.str = str;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//将弹出框设置为全局
        setContentView(R.layout.commondialog);
        setCanceledOnTouchOutside(false);
        setCancelable(true);                         //弹出框不可以换返回键取消
        setCanceledOnTouchOutside(true); //失去焦点不会消失
        textView = (TextView)findViewById(R.id.tv_context) ;
        textView.setText(str);
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

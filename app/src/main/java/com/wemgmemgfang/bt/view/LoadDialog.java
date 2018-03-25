package com.wemgmemgfang.bt.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wemgmemgfang.bt.R;


/**
 * Created by Administrator on 2016/10/26.
 */

public class LoadDialog extends Dialog  {
    private Context context;

    public LoadDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//将弹出框设置为全局
        setContentView(R.layout.loaddialog);
        setCanceledOnTouchOutside(false);
        setCancelable(true);//弹出框不可以换返回键取消
        setCanceledOnTouchOutside(true);//失去焦点不会消失
        setDialogAttributes((Activity) context, this, 0.5f, 0, Gravity.CENTER);
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

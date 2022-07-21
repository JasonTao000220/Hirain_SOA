package com.hirain.hirain.myview;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.hirain.hirain.R;

public class Dialog extends android.app.Dialog implements View.OnClickListener {

    private TextView textView1, textView2, textView3, textView4;
    private String title, message, cancel, confirm;
    private OnCancelListener cancelListener;
    private OnConfirmListener confirmListener;


    public Dialog(Context context) {
        super(context);
    }

    public Dialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel, OnCancelListener onCancelListener) {
        this.cancel = cancel;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm, OnConfirmListener onConfirmListener) {
        this.confirm = confirm;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        //设置宽度，固定代码
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int) (size.x*0.28);//设置dialog的宽度为当前手机屏幕宽度*0.8
//        p.height=357;
        getWindow().setAttributes(p);

//        textView1 = (TextView) findViewById(R.id.tv1);
        textView2 = (TextView) findViewById(R.id.tv2);
//        if (!TextUtils.isEmpty(title)) {//不为空
//            textView1.setText(title);
//        }
        if (!TextUtils.isEmpty(message)) {//不为空
            textView2.setText(message);
        }
        if (!TextUtils.isEmpty(cancel)) {//不为空
            textView3.setText(cancel);
        }
        if (!TextUtils.isEmpty(confirm)) {//不为空
            textView4.setText(confirm);
        }
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public interface OnCancelListener {
        void onCancel(Dialog dialog);
    }

    public interface OnConfirmListener {
        void onConfirm(Dialog dialog);
    }

}
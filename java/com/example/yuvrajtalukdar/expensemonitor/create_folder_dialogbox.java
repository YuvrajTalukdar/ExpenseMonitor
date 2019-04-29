package com.example.yuvrajtalukdar.expensemonitor;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class create_folder_dialogbox extends Dialog implements OnClickListener {
    public Activity activity;
    public Button cancel;
    public Button create;
    public Dialog dialog;
    public String foldername;
    OnMyDialogResult mDialogResult;
    public EditText name;

    public interface OnMyDialogResult {
        void finish(String str);
    }

    public create_folder_dialogbox(Activity a) {
        super(a);
        this.activity = a;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0302R.layout.create_folder_dialogbox);
        setTitle("Create new folder...");
        this.cancel = (Button) findViewById(C0302R.id.cancel);
        this.create = (Button) findViewById(C0302R.id.create_folder);
        this.cancel.setOnClickListener(this);
        this.create.setOnClickListener(this);
        this.name = (EditText) findViewById(C0302R.id.folder_name);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C0302R.id.cancel:
                dismiss();
                return;
            case C0302R.id.create_folder:
                if (this.mDialogResult != null) {
                    this.mDialogResult.finish(String.valueOf(this.name.getText()));
                }
                dismiss();
                return;
            default:
                return;
        }
    }

    public void setDialogResult(OnMyDialogResult dialogResult) {
        this.mDialogResult = dialogResult;
    }
}

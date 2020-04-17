package com.myapp.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.myapp.R;

import androidx.appcompat.app.AlertDialog;

public class ProgressDialogUtils {
    private static AlertDialog mAlertDialog;

    public static void showProgressDialog(Context context) {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(context, R.style.CustomProgressDialog).create();
        }

        View loadView = LayoutInflater.from(context).inflate(R.layout.dialog_progress_view, null);
        mAlertDialog.setView(loadView, 0, 0, 0, 0);
        mAlertDialog.setCanceledOnTouchOutside(false);

        TextView tvTip = loadView.findViewById(R.id.tvTip);
        tvTip.setText("執行中...");

        mAlertDialog.show();
    }

    public static void showProgressDialog(Context context, String tip) {
        if (TextUtils.isEmpty(tip)) {
            tip = "執行中...";
        }

        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(context, R.style.CustomProgressDialog).create();
        }

        View loadView = LayoutInflater.from(context).inflate(R.layout.dialog_progress_view, null);
        mAlertDialog.setView(loadView, 0, 0, 0, 0);
        mAlertDialog.setCanceledOnTouchOutside(false);

        TextView tvTip = loadView.findViewById(R.id.tvTip);
        tvTip.setText(tip);

        mAlertDialog.show();
    }

    public static void dismiss() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }
}
package com.myapp.activity;


import android.app.Activity;
import android.app.AlertDialog;

import com.myapp.R;


public abstract class PermissionCallback {

    public void onGranted(Activity activity) {
    }

    public void onDenied(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false)
               .setTitle(R.string.insufficient_permissions_title)
               .setMessage(R.string.insufficient_permissions_notify)
               .setPositiveButton(R.string.txt_ok,
                                  (dialog, which) -> {
                                      activity.finish();
                                  });
        builder.create()
               .show();
    }
}

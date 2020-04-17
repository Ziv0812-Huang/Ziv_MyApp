package com.myapp.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.myapp.R;
import com.myapp.widget.Constants;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by i_zivhuang on 2017/6/6.
 */

public class PermissionHelpAcitvity
        extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks {

    public static final String EXTRA_REQUEST_PERMISSIONS = "extra_request_permissions";
    public static final String EXTRA_PAYMENT_METHOD_UPDATE_ID = "extra_payment_method_update_id";
    public static final String EXTRA_PAYMENT_METHOD_PAYMETHOD = "extra_payment_method_paymethod";

    public static final int REQUEST_PERMISSIONS_RESULT = 0x991;
    public static final int SETTING_DENIED_RESULT = 0x992;

    /*public interface PermissionCallBackInterface{
        void onGranted(Activity activity);
        void onDenied(Activity activity);
    }*/

    private String[] m_request_permissions;
    private String m_update_id, m_paymenthod;

    private boolean hasDeniedPermission = false;
    //private AppSettingsDialog.Builder appSettingsDialog;

    //public static PermissionCallBackInterface mPermissionCallBack;

    /**
     * Request Permissions
     */
    public static void doReqPermissions(Activity activity, String[] permissions, PermissionCallback callBack) {
        if (EasyPermissions.hasPermissions(activity, permissions)) {
            if (callBack != null) {
                callBack.onGranted(activity);
            }
        } else {
            PermissionHelpAcitvity.start(activity,permissions,null);
        }
    }

    public static void start(Activity activity,String[] permissions, Intent intent)
    {
        if (intent == null) {
            intent = new Intent();
        }
        intent.setClass(activity, PermissionHelpAcitvity.class);
        intent.putExtra(EXTRA_REQUEST_PERMISSIONS, permissions);
        activity.startActivityForResult(intent, REQUEST_PERMISSIONS_RESULT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_request_permissions = getIntent().getStringArrayExtra(EXTRA_REQUEST_PERMISSIONS);
        m_update_id = getIntent().getStringExtra(EXTRA_PAYMENT_METHOD_UPDATE_ID);
        m_paymenthod = getIntent().getStringExtra(EXTRA_PAYMENT_METHOD_PAYMETHOD);
        hasDeniedPermission = false;
        setVisible(true);
        doRequestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        } else {
            super.setResult(RESULT_CANCELED, new Intent());
            finish();
        }
    }

    @AfterPermissionGranted(REQUEST_PERMISSIONS_RESULT)
    private void doRequestPermissions() {
        if (m_request_permissions[0].equals(Manifest.permission.WRITE_SETTINGS)) {
            DialogInterface.OnClickListener listener = (dialog, which) -> {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                           Uri.parse("package:" + PermissionHelpAcitvity.this.getPackageName()));
                startActivityForResult(intent,
                                       Constants.PERMISSIONS_WRITE_SETTINGS);
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.setCancelable(false)
                                        .setTitle(R.string.request_permission)
                                        .setMessage(R.string.txt_permissions_setting)
                                        .setPositiveButton(R.string.txt_ok,
                                                           listener)
                                        .create();


            dialog.show();
        } else if (EasyPermissions.hasPermissions(this, m_request_permissions)) {
            super.setResult(RESULT_OK, new Intent());
            finish();
        } else {
            if (!hasDeniedPermission) {
                hasDeniedPermission = true;
                EasyPermissions.requestPermissions(this, getString(R.string.request_permission), REQUEST_PERMISSIONS_RESULT, m_request_permissions);
            } else {
                super.setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("PermissionHelp", "onActivityResult:  requestCode =>" + requestCode + ",resultCode => " + resultCode);
        switch (requestCode) {
            case Constants.PERMISSIONS_WRITE_SETTINGS:

                super.setResult(RESULT_OK, new Intent());
                finish();
                break;
            case AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE:
                doRequestPermissions();
                break;
            default:
                super.onActivityResult(requestCode,resultCode,data);
                break;
        }
    }
}

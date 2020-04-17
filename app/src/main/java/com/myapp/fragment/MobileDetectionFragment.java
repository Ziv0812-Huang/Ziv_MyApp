package com.myapp.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.myapp.R;
import com.myapp.activity.BaseActivity;
import com.myapp.activity.PermissionCallback;
import com.myapp.activity.PermissionHelpAcitvity;
import com.myapp.databinding.MobileDetectionFragmentBinding;
import com.myapp.model.AppInfo;
import com.myapp.model.DeviceAttributes;
import com.myapp.model.MobileDetectObject;
import com.myapp.model.ProcessInfo;
import com.myapp.utils.DeviceUtilities;
import com.myapp.utils.ExportUtils;
import com.myapp.utils.ProgressDialogUtils;
import com.myapp.viewmodel.DetectReportViewModel;
import com.myapp.viewmodel.factory.ModelFactory;
import com.myapp.widget.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.AndroidSupportInjection;

import static android.content.Context.ACTIVITY_SERVICE;

public class MobileDetectionFragment
        extends BaseFragment {

    private BaseActivity mActivity;

    private MobileDetectionFragmentBinding binding;

    private String[] mPermissions = {Manifest.permission.READ_PHONE_STATE};

    private boolean mHasGrantedPermission = false;

    private PermissionCallback mCallback = null;

    private Gson gson = new GsonBuilder().create();

    private DeviceUtilities deviceUtilities;

    public static BaseFragment newInstance() {
        MobileDetectionFragment fragment = new MobileDetectionFragment();
        return fragment;
    }

    @Override
    int getItemLayoutId() {
        return 0;
    }

    @Override
    int getFragmentLayoutId() {
        return R.layout.fragment_mobile_detection;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();

        binding = DataBindingUtil.inflate(inflater,
                                          getFragmentLayoutId(),
                                          container,
                                          false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        mCallback = new PermissionCallback() {
            @Override
            public void onGranted(final Activity activity) {
                mHasGrantedPermission = true;
            }

            @Override
            public void onDenied(final Activity activity) {
                super.onDenied(activity);
                mHasGrantedPermission = false;
            }
        };

        PermissionHelpAcitvity.doReqPermissions(mActivity, mPermissions, mCallback);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mHasGrantedPermission) {
            deviceUtilities = new DeviceUtilities(mActivity);

            if (!TextUtils.isEmpty(deviceUtilities.getPhoneNumber())) {
                binding.editPhone.setText(deviceUtilities.getPhoneNumber());
            }

            binding.btnActive.setOnClickListener(v -> {
                String name = binding.editName.getText().toString();
                String phone = binding.editPhone.getText().toString();
                String content = binding.editContent.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(content)) {
                    Toast.makeText(mActivity, "請輸入完整的資料!!", Toast.LENGTH_SHORT);
                    return;
                }

                ProgressDialogUtils.showProgressDialog(mActivity);

                v.postDelayed(() -> exportExcel(name, phone, content),
                              500);
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 @Nullable Intent data) {
        switch (requestCode) {
            case PermissionHelpAcitvity.REQUEST_PERMISSIONS_RESULT:
                if (mCallback != null) {
                    if (resultCode == mActivity.RESULT_OK) {
                        mCallback.onGranted(mActivity);
                    } else {
                        mCallback.onDenied(mActivity);
                    }
                }
                break;
            case Constants.SEND_MAIL_REPORT:
                //Log.i(TAG, "resultCode = " + resultCode);
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                AlertDialog dialog = builder.setCancelable(false)
                                            .setTitle(R.string.app_name)
                                            .setMessage(R.string.txt_report_finish)
                                            .setPositiveButton(R.string.txt_send_ok,
                                                               new DialogInterface.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(DialogInterface dialog,
                                                                                       int which) {
                                                                       Toast.makeText(mActivity,
                                                                                      getString(R.string.txt_report_thks),
                                                                                      Toast.LENGTH_SHORT)
                                                                            .show();
                                                                   }
                                                               })
                                            .setNeutralButton(R.string.txt_send_no,
                                                              null)
                                            .create();
                dialog.show();

                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void exportExcel(String name, String phone, String content) {

        MobileDetectObject object = new MobileDetectObject();
        object.setName(name);
        object.setPhone(phone);
        object.setContent(content);
        object.setDeviceAttributes(deviceUtilities.getDeviceAttributes());
        object.setAppInfos(getAppList());
        object.setProcessInfos(getProcessList());

        String fileName = "CS_Report_for_"+name+"_"+phone;

        File file = ExportUtils.exportMobileDetec(mActivity, fileName, object);

        ProgressDialogUtils.dismiss();

        if (file != null) {

            String body = "聯絡人姓名 : " + name + "\n";
            body += "聯絡人電話 : " + phone + "\n";
            body += "回報資訊 : " + "\n" + content;

            Uri contentUri = FileProvider.getUriForFile(mActivity, "com.myapp.fileprovider", file);

            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setPackage("com.google.android.gm");
            emailIntent.setType("vnd.android.cursor.item/email");
            //emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            emailIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, fileName);
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);
            //startActivity(emailIntent);
            startActivityForResult(emailIntent,
                                   Constants.SEND_MAIL_REPORT);
        }
        else {
            Toast.makeText(mActivity, "無法建立報告!!請重新操作。", Toast.LENGTH_SHORT);
        }
    }

    private void getJsonObject() {

        ModelFactory modelFactory = new ModelFactory();
        //DetectReportViewModel viewModel = ViewModelProviders.of(this,
        //                                                        modelFactory)
        //                                                    .get(DetectReportViewModel.class);

        DetectReportViewModel viewModel = new ViewModelProvider(this,
                                                                modelFactory).get(DetectReportViewModel.class);

        JsonObject jsonObject = new JsonObject();

        JsonParser jsonParser = new JsonParser();

        jsonObject.addProperty("eventToken", "a4c7f38e-e414-11e9-a03f-28aade581e8d");
        jsonObject.addProperty("phone","0939430686");
        jsonObject.add("DevicesInfo", jsonParser.parse(getDevicesInfo()));
        jsonObject.add("AppListInfo", jsonParser.parse(gson.toJson(getAppList())));
        jsonObject.add("PorcessList", jsonParser.parse(gson.toJson(getProcessList())));

        //binding.tvNote.append(jsonObject.toString());


        viewModel.detectReport(mActivity,jsonObject.toString());

    }

    private String getDevicesInfo() {

        DeviceAttributes attributes = deviceUtilities.getDeviceAttributes();

        String devices =  gson.toJson(attributes);

        //binding.tvNote.append(devices);
        return devices;
    }

    private List<AppInfo> getAppList() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = mActivity.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);
        List<AppInfo> appInfos = new ArrayList<>();

        for (ResolveInfo resolve : resolveInfos) {
            appInfos.add(new AppInfo(pm, resolve));
        }


        return appInfos;
    }

    private List<ProcessInfo> getProcessList() {
        ActivityManager activityManager = (ActivityManager) mActivity.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        List<ProcessInfo> processInfos = new ArrayList<>();

        for (ActivityManager.RunningAppProcessInfo appProcessInfo : procInfos) {
            processInfos.add(new ProcessInfo(appProcessInfo));
        }

        return processInfos;
    }

}

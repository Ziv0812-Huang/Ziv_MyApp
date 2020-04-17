package com.myapp.model;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.util.Base64;

import com.myapp.utils.BitmapUtils;

public class AppInfo {

    private String appname;
    private String packageName;
    private String versionName;
    private int versionCode;
    private String icon;
    private Bitmap iconBitmap;

    public AppInfo(PackageManager pm,ResolveInfo resolve) {

        this.appname = resolve.loadLabel(pm).toString();
        this.packageName = resolve.activityInfo.applicationInfo.packageName;

        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
            this.versionName = packageInfo.versionName;
            this.versionCode = packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //this.icon = BitmapUtils.drawableToBitmap(resolve.loadIcon(pm));
        iconBitmap = BitmapUtils.drawableToBitmap(resolve.loadIcon(pm));
        byte[] bytes =  BitmapUtils.convertBitmapToByteArray(iconBitmap);
        String base64Str = Base64.encodeToString(bytes, Base64.DEFAULT);

        this.icon = base64Str;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Bitmap getIconBitmap() {
        return iconBitmap;
    }

    public void setIconBitmap(Bitmap iconBitmap) {
        this.iconBitmap = iconBitmap;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
               "appName='" + appname + '\'' +
               ", packageName='" + packageName + '\'' +
               ", versionName='" + versionName + '\'' +
               ", versionCode=" + versionCode +
               ", icon=" + icon +
               '}';
    }
}

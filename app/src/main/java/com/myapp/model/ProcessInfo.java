package com.myapp.model;

import android.app.ActivityManager;

public class ProcessInfo {

    private int pid;
    private int uid;
    private String processName;

    public ProcessInfo(ActivityManager.RunningAppProcessInfo appProcessInfo) {
        this.pid = appProcessInfo.pid;
        this.uid = appProcessInfo.uid;
        this.processName = appProcessInfo.processName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}

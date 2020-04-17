package com.myapp.model;

import java.util.List;

public class MobileDetectObject {


    private String name;
    private String phone;
    private String Content;

    private DeviceAttributes deviceAttributes;
    private List<AppInfo> appInfos;
    private List<ProcessInfo> processInfos;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public DeviceAttributes getDeviceAttributes() {
        return deviceAttributes;
    }

    public void setDeviceAttributes(DeviceAttributes deviceAttributes) {
        this.deviceAttributes = deviceAttributes;
    }

    public List<AppInfo> getAppInfos() {
        return appInfos;
    }

    public void setAppInfos(List<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }

    public List<ProcessInfo> getProcessInfos() {
        return processInfos;
    }

    public void setProcessInfos(List<ProcessInfo> processInfos) {
        this.processInfos = processInfos;
    }
}

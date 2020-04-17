package com.myapp.manager.request;


import com.google.gson.annotations.SerializedName;
import com.myapp.model.AppInfo;
import com.myapp.model.DeviceAttributes;
import com.myapp.model.ProcessInfo;

import java.util.List;


public class DetectRequest {

	@SerializedName("eventToken")
	private String eventToken;

	@SerializedName("phone")
	private String phone;

	@SerializedName("DevicesInfo")
	private DeviceAttributes devices;

	@SerializedName("AppListInfo")
	private List<AppInfo> appInfos;

	@SerializedName("PorcessList")
	private List<ProcessInfo> processInfos;

	public String getEventToken() {
		return eventToken;
	}

	public void setEventToken(String eventToken) {
		this.eventToken = eventToken;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public DeviceAttributes getDevices() {
		return devices;
	}

	public void setDevices(DeviceAttributes devices) {
		this.devices = devices;
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

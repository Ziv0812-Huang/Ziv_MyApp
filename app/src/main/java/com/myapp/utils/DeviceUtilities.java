package com.myapp.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.myapp.model.DeviceAttributes;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class DeviceUtilities {

	private static final String OS_PLATFORM = "ANDROID";
	private static final String TEST_SUBSCRIBER_PROP = "test.subscriberid";

	private TelephonyManager telephonyManager;
	private DeviceAttributes deviceAttributes;

	private Context appContext;

	public DeviceUtilities(Context appContext) {
		this.appContext = appContext;
	}

	// FIXME : remove dependency of DeviceAttributes from util
	public DeviceAttributes getDeviceAttributes() {
		if (deviceAttributes == null) {
			deviceAttributes = new DeviceAttributes();

			deviceAttributes.oem = Build.MANUFACTURER;
			deviceAttributes.modelName = Build.MODEL;
			deviceAttributes.modelNumber = Build.MODEL;
			deviceAttributes.osPlatform = OS_PLATFORM;
			deviceAttributes.osVersion = Build.VERSION.RELEASE;
			deviceAttributes.imei = getDeviceId();
			deviceAttributes.imsi = getSubscriberId();
			deviceAttributes.country = getNetworkCountryIso();
			deviceAttributes.operator = getNetworkOperator();
			deviceAttributes.operatorName = getNetworkOperatorName();

			String[] screenDimension = getScreenDimensions(appContext);
			if (screenDimension != null) {
				deviceAttributes.screenHeight = screenDimension[0];
				deviceAttributes.screenWidth = screenDimension[1];
			}
		}
		deviceAttributes.iccid = getSimId();
		deviceAttributes.ip = getIPAddress();
		return deviceAttributes;
	}

	@SuppressLint({"HardwareIds",
				   "MissingPermission"})
	public String getDeviceId() {
		String deviceId = "";
		if (EasyPermissions.hasPermissions(appContext,
                                           Manifest.permission.READ_PHONE_STATE)) {
			if (telephonyManager == null) {
				telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
			}

			deviceId = telephonyManager.getDeviceId();
		}
		return deviceId;
	}

	private String[] getScreenDimensions(Context context) {
		DisplayMetrics displaymetrics = new DisplayMetrics();

		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;

		return new String[] { "" + height, "" + width };
	}

	@SuppressLint("MissingPermission")
	public String getSimId() {
		String simId = "";
		if (EasyPermissions.hasPermissions(appContext,
                                           Manifest.permission.READ_PHONE_STATE)) {

			if (telephonyManager == null) {
				telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
			}
			simId = telephonyManager.getSimSerialNumber();
		}
		return simId;
	}

	public String getIPAddress() {
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
				for (InetAddress addr : addrs) {
					if (!addr.isLoopbackAddress()) {
						String sAddr = addr.getHostAddress();
						boolean isIPv4 = sAddr.indexOf(':') < 0;
						if (isIPv4) {
							return sAddr;
						}
						else {
							int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
							return delim < 0 ?
								   sAddr.toUpperCase() :
								   sAddr.substring(0,
												   delim)
										.toUpperCase();
						}
					}
				}
			}
		}
		catch (Exception ignored) {
		} // for now eat exceptions
		return "";
	}

	@SuppressLint({"HardwareIds",
				   "MissingPermission"})
	public String getSubscriberId() {
		String subscriberId = "";
		if (EasyPermissions.hasPermissions(appContext,
                                           Manifest.permission.READ_PHONE_STATE)) {
			TelephonyManager telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
			subscriberId = telephonyManager.getSubscriberId();

		}
		return subscriberId;
	}

	@SuppressLint("MissingPermission")
	public String getNetworkCountryIso() {
		String country = "";
		if (EasyPermissions.hasPermissions(appContext,
                                           Manifest.permission.READ_PHONE_STATE)) {

			if (telephonyManager == null) {
				telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
			}
			country = telephonyManager.getNetworkCountryIso();
		}
		return country;
	}

	@SuppressLint("MissingPermission")
	public String getNetworkOperator() {
		String operator = "";
		if (EasyPermissions.hasPermissions(appContext,
                                           Manifest.permission.READ_PHONE_STATE)) {

			if (telephonyManager == null) {
				telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
			}
			operator = telephonyManager.getNetworkOperator();
		}
		return operator;
	}

	@SuppressLint("MissingPermission")
	public String getNetworkOperatorName() {
		String name = "";
		if (EasyPermissions.hasPermissions(appContext,
                                           Manifest.permission.READ_PHONE_STATE)) {

			if (telephonyManager == null) {
				telephonyManager = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
			}
			name = telephonyManager.getNetworkOperator();
		}
		return name;
	}

	@SuppressLint("MissingPermission")
	public String getPhoneNumber(){
		TelephonyManager telephonyManager = (TelephonyManager)appContext.getSystemService(Context.TELEPHONY_SERVICE);
		String phone = telephonyManager.getLine1Number();

		return PropertyUtils.get(appContext,TEST_SUBSCRIBER_PROP,phone);

		//return phone;
	}
}

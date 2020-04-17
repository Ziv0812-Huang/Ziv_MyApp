package com.myapp.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.myapp.model.AppInfo;
import com.myapp.model.MobileDetectObject;
import com.myapp.model.ProcessInfo;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class ExportUtils {

    private static final String TAG = "ExportUtils";

    public static File exportMobileDetec(Context context, String fname, MobileDetectObject object) {
        File exprotFile = null;

        String fileName = fname+".xlsx";

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e(TAG, "Storage not available or read only");
            return null;
        }

        boolean success = false;

        Map<String,String> hashMap = new LinkedHashMap<>();
        hashMap.put("聯絡人姓名", object.getName());
        hashMap.put("聯絡人電話", object.getPhone());
        hashMap.put("回報資訊", object.getContent());
        hashMap.put("Country", object.getDeviceAttributes().country);
        hashMap.put("ICC Id", object.getDeviceAttributes().iccid);
        hashMap.put("IMEI", object.getDeviceAttributes().imei);
        hashMap.put("Ip", object.getDeviceAttributes().ip);
        hashMap.put("Model Name", object.getDeviceAttributes().modelName);
        hashMap.put("Model Number", object.getDeviceAttributes().modelNumber);
        hashMap.put("OEM", object.getDeviceAttributes().oem);
        hashMap.put("Operator", object.getDeviceAttributes().operator);
        hashMap.put("Operator Name", object.getDeviceAttributes().operatorName);
        hashMap.put("OS Platform", object.getDeviceAttributes().osPlatform);
        hashMap.put("OS Version", object.getDeviceAttributes().osVersion);
        hashMap.put("screen height", object.getDeviceAttributes().screenHeight);
        hashMap.put("screen width", object.getDeviceAttributes().screenWidth);


        ArrayList<LinkedHashMap<String,String>> appinfos = new ArrayList<>();
        for (AppInfo appInfo : object.getAppInfos()) {
            LinkedHashMap<String,String> app_map = new LinkedHashMap<>();
            app_map.put("Icon", appInfo.getIcon());
            app_map.put("App Name", appInfo.getAppname());
            app_map.put("Package Name", appInfo.getPackageName());
            app_map.put("Version Code", String.valueOf(appInfo.getVersionCode()));
            app_map.put("Version Name", appInfo.getVersionName());

            appinfos.add(app_map);

        }

        ArrayList<LinkedHashMap<String,String>> processinfos = new ArrayList<>();
        for (ProcessInfo processInfo : object.getProcessInfos()) {
            LinkedHashMap<String,String> process_map = new LinkedHashMap<>();
            process_map.put("PID", String.valueOf(processInfo.getPid()));
            process_map.put("UID", String.valueOf(processInfo.getUid()));
            process_map.put("Process Name", processInfo.getProcessName());

            processinfos.add(process_map);

        }


        //New Workbook
        XSSFWorkbook wb = new XSSFWorkbook();

        XSSFRow row = null;
        XSSFCell cell = null;

        //New Sheet
        XSSFSheet sheet1 = wb.createSheet("Device Attributes");
        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 700));

        int i = 0;
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            row = sheet1.createRow(i);

            cell = row.createCell(0);
            cell.setCellValue(entry.getKey());

            cell = row.createCell(1);
            cell.setCellValue(entry.getValue());
            i += 1;
        }

        XSSFSheet sheet2 = wb.createSheet("APP Info");

        int j = 0;
        for (LinkedHashMap<String,String> app_map : appinfos) {
            int c = 0;
            if (j == 0) {
                row = sheet2.createRow(j);
                for (Map.Entry<String, String> entry : app_map.entrySet()) {
                    cell = row.createCell(c);
                    cell.setCellValue(entry.getKey());
                    if (entry.getKey().equals("Icon")) {
                        sheet2.setColumnWidth(c, (20 * 100));
                    } else {
                        sheet2.setColumnWidth(c, (20 * 600));
                    }
                    c += 1;
                }
                c = 0;
                j += 1;
            }
            row = sheet2.createRow(j);
            for (Map.Entry<String, String> entry : app_map.entrySet()) {
                if (entry.getKey().equals("Icon")) {
                    cell = row.createCell(c);
                    //cell.setCellValue("XXXXXXX");
                    byte[] bytes = Base64.decode(entry.getValue(), Base64.DEFAULT);
                    int pictureIdx = wb.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_PNG);
                    XSSFCreationHelper helper = wb.getCreationHelper();
                    XSSFDrawing drawing = sheet2.createDrawingPatriarch();
                    XSSFClientAnchor anchor = helper.createClientAnchor();

                    anchor.setCol1(c);
                    anchor.setRow1(j);
                    anchor.setCol2(c+1);
                    anchor.setRow2(j+1);

                    XSSFPicture picture = drawing.createPicture(anchor, pictureIdx);
                    short units = 40*20;
                    cell.getRow().setHeight(units);

                } else {
                    cell = row.createCell(c);
                    cell.setCellValue(entry.getValue());
                }
                c += 1;
            }
            j += 1;
        }

        XSSFSheet sheet3 = wb.createSheet("Process Info");

        int k = 0;
        for (LinkedHashMap<String,String> process_map : processinfos) {
            int c = 0;
            if (k == 0) {
                row = sheet3.createRow(k);
                for (Map.Entry<String, String> entry : process_map.entrySet()) {
                    cell = row.createCell(c);
                    cell.setCellValue(entry.getKey());
                    sheet3.setColumnWidth(c, (15 * 500));
                    c += 1;
                }
                c = 0;
                k += 1;
            }
            row = sheet3.createRow(k);
            for (Map.Entry<String, String> entry : process_map.entrySet()) {
                cell = row.createCell(c);
                cell.setCellValue(entry.getValue().toString());
                c += 1;
            }
            k += 1;
        }

        // Create a path where we will place our List of objects on external storage
        File file = new File(context.getExternalFilesDir(null), fileName);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w(TAG, "Writing file" + file);
            exprotFile = file;
        } catch (IOException e) {
            Log.w(TAG, "Error writing " + file, e);
        } catch (Exception e) {
            Log.w(TAG, "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
            Log.i(TAG, "Finish to export XLSX file!!");
            Toast.makeText(context, "Finish Export!!", Toast.LENGTH_SHORT);
        }
        return exprotFile;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

}

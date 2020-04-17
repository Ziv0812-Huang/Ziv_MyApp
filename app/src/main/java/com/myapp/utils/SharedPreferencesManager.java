package com.myapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.StrictMode;


import com.myapp.MyApp;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPreferencesManager {


    private SharedPreferences sharedPreferences;

    @Inject
    SharedPreferencesManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    /*public SharedPreferencesManager(Context apContext) {
        StrictMode.ThreadPolicy policy = StrictMode.allowThreadDiskReads();
        try {
            sharedPreferences = apContext.getSharedPreferences("ziv_myapp",
                                                               Context.MODE_PRIVATE);
        }
        finally {
            StrictMode.setThreadPolicy(policy);
        }
    }*/

    public void saveStringValue(String key,
                                String value) {

        Editor editor = sharedPreferences.edit();
        editor.putString(key,
                         value);
        editor.commit();
    }

    public void saveStringValueInBackground(String key,
                                            String value) {
        Editor editor = sharedPreferences.edit();
        editor.putString(key,
                         value);
        editor.apply();
    }

    public void saveBoolenValue(String key,
                                boolean value) {

        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,
                          value);
        editor.commit();
    }

    public void saveSaveIntegerValue(String key,
                                     int value) {

        Editor editor = sharedPreferences.edit();
        editor.putInt(key,
                      value);
        editor.commit();
    }

    public String getStringvalue(String key) {

        return sharedPreferences.getString(key,
                                           null);
    }

    public boolean getBoolenvalue(String key) {

        return sharedPreferences.getBoolean(key,
                                            false);
    }

    public int getIntegervalue(String key) {

        return sharedPreferences.getInt(key,
                                        0);
    }

    public void removeBykey(String key) {
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();

    }

    public void saveStringSet(String key,
                              Set<String> set) {
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
        editor.putStringSet(key,
                            set);
        editor.commit();
    }

    public Set<String> getStringSet(String key) {
        return sharedPreferences.getStringSet(key,
                                              new HashSet<String>());
    }

    public void clear() {
        sharedPreferences.edit()
                         .clear()
                         .commit();
    }

    public static class StockBasic {
        private static final String KEY_STORE_STOCK_SYMBOL = "key_store_stock_symbol";
        private static final String KEY_STOCK_BASIC_INFO = "key_stock_basic_info";

        public static Set<String> getStoreStockSymbol() {
            return MyApp.getInstance()
                        .sharedPreferencesManager
                        .getStringSet(KEY_STORE_STOCK_SYMBOL);
        }

        public static void removeStoreStockSymbol() {
            MyApp.getInstance()
                    .sharedPreferencesManager
                    .removeBykey(KEY_STORE_STOCK_SYMBOL);
        }

        public static void saveStoreStockSymbol(Set<String> store) {
            MyApp.getInstance()
                    .sharedPreferencesManager
                    .saveStringSet(KEY_STORE_STOCK_SYMBOL, store);
        }

        public static String getStockBasicInfo() {
            return MyApp.getInstance()
                    .sharedPreferencesManager
                    .getStringvalue(KEY_STOCK_BASIC_INFO);
        }

        public static void removeStockBasicInfo() {
            MyApp.getInstance()
                    .sharedPreferencesManager
                    .removeBykey(KEY_STOCK_BASIC_INFO);
        }

        public static void saveStockBasicInfo(String store) {
            MyApp.getInstance()
                    .sharedPreferencesManager
                    .saveStringValue(KEY_STOCK_BASIC_INFO, store);
        }
    }
}

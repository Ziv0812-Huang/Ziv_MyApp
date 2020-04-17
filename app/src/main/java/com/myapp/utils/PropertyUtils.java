package com.myapp.utils;

import android.content.Context;

import java.lang.reflect.Method;

public class PropertyUtils {
    private static volatile Method set = null;
    private static volatile Method get = null;

    public static void set(String prop, String value) {

        try {
            if (null == set) {
                synchronized (PropertyUtils.class) {
                    if (null == set) {
                        Class<?> cls = Class.forName("android.os.SystemProperties");
                        set = cls.getDeclaredMethod("set", new Class<?>[]{String.class, String.class});
                    }
                }
            }
            set.invoke(null, new Object[]{prop, value});
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    public static String get(String prop, String defaultvalue) {
        String value = defaultvalue;
        try {
            if (null == get) {
                synchronized (PropertyUtils.class) {
                    if (null == get) {
                        Class<?> cls = Class.forName("android.os.SystemProperties");
                        get = cls.getDeclaredMethod("get", new Class<?>[]{String.class, String.class});
                    }
                }
            }
            value = (String) (get.invoke(null, new Object[]{prop, defaultvalue}));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String get(Context context, String key, String def) throws IllegalArgumentException {

        String ret= def;

        try{

            ClassLoader cl = context.getClassLoader();
            @SuppressWarnings("rawtypes")
            Class SystemProperties = cl.loadClass("android.os.SystemProperties");

            //Parameters Types
            @SuppressWarnings("rawtypes")
            Class[] paramTypes= new Class[2];
            paramTypes[0]= String.class;
            paramTypes[1]= String.class;

            Method get = SystemProperties.getMethod("get", paramTypes);

            //Parameters
            Object[] params= new Object[2];
            params[0]= new String(key);
            params[1]= new String(def);

            ret= (String) get.invoke(SystemProperties, params);

        }catch( IllegalArgumentException iAE ){
            throw iAE;
        }catch( Exception e ){
            ret= def;
            //TODO
        }

        return ret;

    }
}

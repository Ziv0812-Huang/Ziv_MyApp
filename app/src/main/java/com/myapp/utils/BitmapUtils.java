package com.myapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

public class BitmapUtils {

    private static final String TAG = "BitmapUtils";

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap;
        int width = Math.max(drawable.getIntrinsicWidth(), 2);
        int height = Math.max(drawable.getIntrinsicHeight(), 2);
        try {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Exception e) {
            Log.e(TAG, "[drawableToBitmap]" + e.getMessage());
            bitmap = null;
        }

        return bitmap;
    }

    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bitmapdata = stream.toByteArray();

        return bitmapdata;
    }

    public static byte[] loadImageFromUrl(Context context, String url) {
        try{

            Bitmap bitmap = Picasso.get().load(url).get();

            //InputStream is = new URL(url).openStream();
            //Drawable drawable = Drawable.createFromStream(is, "pic");
            //Bitmap bitmap = BitmapFactory.decodeStream(is);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byte[] bitmapdata = stream.toByteArray();

            return bitmapdata;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

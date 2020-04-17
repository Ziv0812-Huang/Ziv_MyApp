package com.myapp.utils;

import android.app.Activity;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class UiUtils {

    public static <E extends View> E getView(@NonNull Activity activity,
                                             @IdRes int id) {
        return (E) activity.findViewById(id);
    }

    public static <E extends View> E getView(@NonNull View view,
                                             @IdRes int id) {
        return (E) view.findViewById(id);
    }

    public static void removeFragmentsById(Fragment frag, int... fragIds) {
        AppCompatActivity activity = (AppCompatActivity)frag.getActivity();
        if ((null != activity) && activity.isFinishing()) return;

        FragmentManager fm = frag.getFragmentManager();

        Fragment removeFrag;
        if (null != fragIds) {
            for (int fragId:
                    fragIds) {
                removeFrag = fm.findFragmentById(fragId);

                if (removeFrag != null) {
                    fm.beginTransaction()
                      .remove(removeFrag)
                      .commitAllowingStateLoss();
                }
            }
        }
    }
}

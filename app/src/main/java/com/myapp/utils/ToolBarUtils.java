package com.myapp.utils;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapp.R;
import com.myapp.activity.BaseActivity;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.Toolbar;


public class ToolBarUtils {
    private static final int mMenuTextSize = 18;

    public static Toolbar setMenuStyle(BaseActivity baseActivity,
                                       String title) {
        Toolbar toolbar = initCenterTitle(baseActivity, title);
        baseActivity.setDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationIcon(null);

        baseActivity.getBaseMenu().getMenu().clear();

        return toolbar;
    }

    public static Toolbar initCenterLogo(BaseActivity baseActivity,
                                         @DrawableRes int imageId) {
        Toolbar toolbar = initCustom(baseActivity, R.layout.toolbar_center_logo);

        ImageView imgLogo = toolbar.findViewById(R.id.logo);
        imgLogo.setImageResource(imageId);

        return toolbar;
    }

    private static Toolbar initCenterTitle(BaseActivity baseActivity,
                                           String title) {
        Toolbar toolbar = initCustom(baseActivity, R.layout.toolbar_center_title);

        TextView titleText = toolbar.findViewById(R.id.title);
        titleText.setText(title);

        return toolbar;
    }

    private static Toolbar initCustom(BaseActivity baseActivity,
                                      int customLayoutResId) {

        Toolbar toolbar = baseActivity.getBaseToolbar();
        toolbar.setTitle("");
        FrameLayout layout = toolbar.findViewById(R.id.toolbar_content);
        layout.removeAllViews();
        View view = baseActivity.getLayoutInflater().inflate(customLayoutResId, null);
        layout.addView(view);

        return toolbar;
    }
}

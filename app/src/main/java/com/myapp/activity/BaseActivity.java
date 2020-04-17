package com.myapp.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.myapp.R;
import com.myapp.model.Country;
import com.myapp.utils.ToolBarUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import androidx.annotation.LayoutRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    protected LinearLayout baseContentMain = null;
    protected LinearLayout baseContentBottom = null;
    protected Toolbar baseToolbar;
    protected View baseView;
    protected ActionMenuView baseMenu;
    private DrawerLayout drawerLayout;
    private NavigationView navigation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setDisplayHomeAsUpEnabled(boolean enabled) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
        getSupportActionBar().setHomeButtonEnabled(enabled);
    }

    public Toolbar getBaseToolbar() {
        return baseToolbar;
    }

    public ActionMenuView getBaseMenu() {
        return baseMenu;
    }

    protected Toolbar initToolbar() {
        return ToolBarUtils.setMenuStyle (this, getString(R.string.home_title));
    }

    protected void initContentView() {
        super.setContentView(R.layout.activity_base);
        baseContentMain = findViewById(R.id.layout_content_main);
        baseContentBottom = findViewById(R.id.layout_content_bottom);
        baseToolbar = findViewById(R.id.toolbar);
        baseMenu = findViewById(R.id.menuView);
        setSupportActionBar(baseToolbar);
        baseMenu.setOnMenuItemClickListener((MenuItem menuItem) -> onOptionsItemSelected(menuItem));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setDisplayHomeAsUpEnabled(false);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigation_view = findViewById(R.id.navigation_view);

        redrawDrawerMenu(R.menu.menu_list,-1);

        navigation_view.setNavigationItemSelectedListener((@NonNull MenuItem item) -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            return onMenuDrawerItemSelected(item);
        });
        this.baseToolbar = initToolbar();
        redrawToggle();
    }

    protected void initContentUI() {

        //menu version value
        RelativeLayout versionLayout = (RelativeLayout) navigation_view.getMenu()
                                                                       .findItem(R.id.nav_menu_version)
                                                                       .getActionView();
        AppCompatTextView textView = versionLayout.findViewById(R.id.tv_version);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            textView.setText(getString(R.string.txt_version_name, packageInfo.versionName));
        }
        catch (PackageManager.NameNotFoundException e) {
            //e.printStackTrace();
            textView.setText(getString(R.string.txt_version_name, "XXXX"));
        }
    }

    protected void setBaseContentView(@LayoutRes int resId) {
        initContentView();
        initContentUI();

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        baseView = layoutInflater.inflate(resId, null);
        baseView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                               LinearLayout.LayoutParams.MATCH_PARENT));
        baseContentMain.addView(baseView);
    }

    protected void redrawDrawerMenu(@MenuRes int MenuViewId, @LayoutRes int MenuHeaderLayoutId) {
        navigation_view.getMenu().clear();
        navigation_view.inflateMenu(MenuViewId);
        if (navigation_view.getHeaderCount() > 0) {
            navigation_view.removeHeaderView(navigation_view.getHeaderView(0));
        }
        if (MenuHeaderLayoutId != -1) {
            View header = LayoutInflater.from(this).inflate(MenuHeaderLayoutId, null);
            navigation_view.addHeaderView(header);
        }

        int width = getResources().getDisplayMetrics().widthPixels;
        int newWidth = width - (width / 4) + 10;
        ViewGroup.LayoutParams lp = navigation_view.getLayoutParams();
        lp.width = newWidth;
        lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        navigation_view.setLayoutParams(lp);
    }

    protected void redrawToggle() {
        // 將drawerLayout和toolbar整合，會出現「三」按鈕
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                                                                 drawerLayout,
                                                                 baseToolbar,
                                                                 R.string.menu_list_drawer_open,
                                                                 R.string.menu_list_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public boolean onMenuDrawerItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_home:

                return true;
        }
        return false;
    }

    public ArrayList<Country> getCountryData() {
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            JsonParser jsonParser = new JsonParser();
            String countryStr = readFile(this.getAssets().open("CountryData.json"));
            JsonArray jsonArray = jsonParser.parse(countryStr).getAsJsonArray();

            ArrayList<Country> list =  gson.fromJson(jsonArray,
                                                new TypeToken<ArrayList<Country>>() {
                                                }.getType());
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String readFile(InputStream is) {
        String content;
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            content = new String(buffer);
        }
        catch (IOException ex) {
            try {
                is.close();
            }
            catch (IOException e) {
                Log.e(TAG,"Read input exception = " + e.getMessage());
            }
            Log.e(TAG,"Read input exception = " + ex.getMessage());
            return null;
        }
        return content;

    }
}

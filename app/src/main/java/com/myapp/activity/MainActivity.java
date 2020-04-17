package com.myapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.myapp.R;
import com.myapp.fragment.TvProgramFragment;
import com.myapp.fragment.CountryFragment;
import com.myapp.fragment.MobileDetectionFragment;
import com.myapp.fragment.StockBasicInfoFragment;
import com.myapp.fragment.StockViewFragment;

import androidx.fragment.app.FragmentManager;

public class MainActivity
        extends BaseActivity {

    private FragmentManager fragmentManager;
    private boolean inHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();


    }

    @Override
    public boolean onMenuDrawerItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_home:
                inHomePage(true);
                return true;
            case R.id.action_item_a:
                inHomePage(false);
                fragmentManager.beginTransaction()
                               .replace(R.id.main_content_frame,
                                        CountryFragment.newInstance())
                               .commit();
                return true;
            case R.id.action_item_b:
                inHomePage(false);
                fragmentManager.beginTransaction()
                               .replace(R.id.main_content_frame,
                                        MobileDetectionFragment.newInstance())
                               .commit();
                return true;

            case R.id.action_item_c:
                inHomePage(false);
                fragmentManager.beginTransaction()
                               .replace(R.id.main_content_frame,
                                        StockBasicInfoFragment.newInstance())
                               .commit();

                return true;

            case R.id.action_item_d:
                inHomePage(false);
                fragmentManager.beginTransaction()
                               .replace(R.id.main_content_frame,
                                        StockViewFragment.newInstance())
                               .commit();

                return true;
            case R.id.action_item_e:
                inHomePage(false);
                fragmentManager.beginTransaction()
                               .replace(R.id.main_content_frame,
                                        TvProgramFragment.newInstance())
                               .commit();

                return true;
        }
        return false;
    }


    private void inHomePage(boolean isBool) {
        inHome = isBool;
        if (isBool) {
            findViewById(R.id.main_content_frame).setVisibility(View.INVISIBLE);
            findViewById(R.id.content_layout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.main_content_frame).setVisibility(View.VISIBLE);
            findViewById(R.id.content_layout).setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (inHome) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.setCancelable(false)
                                        .setTitle(R.string.app_name)
                                        .setMessage(R.string.txt_exit_app)
                                        .setPositiveButton(R.string.txt_exit_ok,
                                                           new DialogInterface.OnClickListener() {
                                                               @Override
                                                               public void onClick(DialogInterface dialog,
                                                                                   int which) {
                                                                   exitAPP();
                                                               }
                                                           })
                                        .setNeutralButton(R.string.txt_exit_no,
                                                          null)
                                        .create();
            dialog.show();
        }
        else {
            inHomePage(true);
        }
    }

    private void exitAPP() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog exitDialog = builder.setCancelable(false)
                                        .setTitle(R.string.app_name)
                                        .setMessage(R.string.txt_exit_new)
                                        .setPositiveButton(R.string.txt_ok,
                                                           (dialog, which) -> System.exit(0))
                                        .create();
        exitDialog.show();
    }
}

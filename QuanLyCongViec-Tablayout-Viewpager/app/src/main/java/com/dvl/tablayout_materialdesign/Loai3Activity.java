package com.dvl.tablayout_materialdesign;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class Loai3Activity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<CongViec> ds_cv;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai3);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        QuanLyCongViec quanLyCongViec=new QuanLyCongViec(getApplicationContext());
        quanLyCongViec.khoitao_Database();

        setSupportActionBar(toolbar);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new OneFragment(),getResources().getString(R.string.tab1));
        viewPagerAdapter.addFragment(new TwoFragment(),getResources().getString(R.string.tab2));
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder mydialog= new AlertDialog.Builder(Loai3Activity.this);
        LayoutInflater inflater=getLayoutInflater();
        View v=inflater.inflate(R.layout.dialog_title,null);
        mydialog.setCustomTitle(v);
        mydialog.setMessage(getString(R.string.ask_2_quit));

        mydialog.setNegativeButton(getString(R.string.no_dialog_bt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        mydialog.setPositiveButton(getString(R.string.yes_dialog_bt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Loai3Activity.this.finish();
            }
        });
        AlertDialog alertDialog=mydialog.create();
        alertDialog.show();
    }
}

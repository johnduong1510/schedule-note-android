package com.dvl.tablayout_materialdesign;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class TwoFragment extends Fragment {
    Button bt;
    EditText et;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.twofragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        bt = (Button) getActivity().findViewById(R.id.button4);
        et = (EditText) getActivity().findViewById(R.id.note);
        dodulieu();
        et.setFocusable(false);
        et.setClickable(true);

        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setFocusableInTouchMode(true);
                et.setFocusable(true);
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences save = getActivity().getSharedPreferences("Ghichu", Context.MODE_APPEND);
                SharedPreferences.Editor editor = save.edit();
                editor.putString("noidung", et.getText().toString());
                editor.commit();
                dodulieu();
                et.setFocusable(false);
                et.setClickable(true);
                Toast.makeText(getActivity(), getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                updateWidget(save);
            }
        });
    }

    private void dodulieu() {
        SharedPreferences load = getActivity().getSharedPreferences("Ghichu", Context.MODE_APPEND);
        et.setText(load.getString("noidung", ""));
    }

    private void updateWidget(SharedPreferences load)
    {
        Context context = getActivity();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.quan_ly_cong_viec_widget);
        ComponentName thisWidget = new ComponentName(context, QuanLyCongViecWidget.class);
        remoteViews.setTextViewText(R.id.noidung_text, load.getString("noidung",""));
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }
}
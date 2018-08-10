package com.dvl.tablayout_materialdesign;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Admin on 24/7/2016.
 */
public class MyAdapter extends BaseAdapter {
    Context context;
    ArrayList<CongViec> ds;
    ImageView imageView;
    TextView tv_id;
    TextView tv_noidung;
    TextView tv_thoigian;
    MyAdapter(Context context, ArrayList<CongViec> ds) {
        this.context = context;
        this.ds = ds;
    }

    @Override
    public int getCount() {
        return ds.size();
    }

    @Override
    public Object getItem(int position) {
        return ds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ds.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // View_Mot_O mot_o;
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.listitem, null);
        tv_id=(TextView) convertView.findViewById(R.id.textView3);
        tv_noidung = (TextView) convertView.findViewById(R.id.textView5);
        tv_thoigian = (TextView) convertView.findViewById(R.id.textView4);
        imageView = (ImageView) convertView.findViewById(R.id.imageView);
        tv_id.setText(ds.get(position).id + "");
        tv_noidung.setText(ds.get(position).noidung);
        tv_thoigian.setText(ds.get(position).thoigian);
        Drawable drawable = context.getResources().getDrawable(android.R.drawable.star_big_on);
        if (ds.get(position).important.equalsIgnoreCase("true")) imageView.setImageDrawable(drawable);
        else imageView.setVisibility(View.GONE);
        return convertView;
    }

}

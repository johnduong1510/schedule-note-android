package com.dvl.tablayout_materialdesign;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Admin on 12/8/2016.
 */

import android.support.v4.app.Fragment;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;


public class OneFragment extends Fragment {
    ListView listView;
    Button bt_Add;
    AdView adView;
    QuanLyCongViec quanLyCongViec;
    ArrayList<CongViec> ds_cv;
    MyAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        quanLyCongViec = new QuanLyCongViec(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.onefragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        listView = (ListView) getActivity().findViewById(R.id.listView);
        bt_Add = (Button) getActivity().findViewById(R.id.button);
        adView = (AdView) getActivity().findViewById(R.id.adView);
        adView.loadAd(new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("483D7773FD41661E08C39E08B56788C9")
                .build());
        dodulieu();
        registerForContextMenu(listView);


        bt_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ThemSuaActivity.class);
                i.putExtra("mode", "add");
                startActivityForResult(i, 1);//request code =1 : them cong viec
            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        //co index tren listview lay ra doi tuong congviec trong database tu cong viec lay id trong database (tu nguon du lieu)
        final int id_db = ds_cv.get(index).id;
        //lay id trong database (tu adapter, vi adapter co ham dua vao index tren listview tra ve id trong database)
        //int id_db=adapter.getItemId(index);
        if (id == R.id.remove) {
            AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.dialog_title_2, null);
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
                    quanLyCongViec.xoaCongViec(id_db);
                    Toast.makeText(getActivity(), getResources().getString(R.string.remove_success), Toast.LENGTH_SHORT).show();
                    dodulieu();
                }
            });
            AlertDialog alertDialog = mydialog.create();
            alertDialog.show();
        } else if (id == R.id.edit) {
            CongViec c = ds_cv.get(index);
            Intent i = new Intent(getActivity(), ThemSuaActivity.class);
            i.putExtra("congviec", c);
            i.putExtra("mode", "edit");
            startActivityForResult(i, 2);//requestcode=2 la sua
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            CongViec c = (CongViec) data.getExtras().get("congviec");
            quanLyCongViec.themCongViec(c);
            Toast.makeText(getActivity(), getResources().getString(R.string.add_success), Toast.LENGTH_SHORT).show();
            dodulieu();
        }
        //requestcode=2 la sua
        else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            CongViec c = (CongViec) data.getExtras().get("congviec");
            quanLyCongViec.suaCongViec(c);
            Toast.makeText(getActivity(), getResources().getString(R.string.edit_success), Toast.LENGTH_SHORT).show();
            dodulieu();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_option, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent i = new Intent(getActivity(), ThemSuaActivity.class);
            i.putExtra("mode", "add");
            startActivityForResult(i, 1);//request code =1 : them cong viec
        } else if (item.getItemId() == R.id.about) {
            AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
            mydialog.setMessage(getString(R.string.about));
            mydialog.setCancelable(false);
            mydialog.setPositiveButton(getString(R.string.cancel_bt), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = mydialog.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void dodulieu() {
        ds_cv = quanLyCongViec.sxCongViec();
        //ds_cv = quanLyCongViec.xemdsCongViec();
        adapter = new MyAdapter(getActivity(), ds_cv);
        listView.setAdapter(adapter);
    }
}
package com.mojafarin.ui.activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mojafarin.Modal.Notify;
import com.mojafarin.R;
import com.mojafarin.db.DatabaseHandler;
import com.mojafarin.ui.adapter.PushAdapter;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi
 */
public class PushActivity
        extends Activity {
    private PushAdapter adapter;
    private ArrayList<Notify> dataList;
    private Dialog dialog_msg;
    private ListView list;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.layout_news);
        this.dataList = new ArrayList();
        this.dataList = new DatabaseHandler(this).getNotify();
        this.dialog_msg = new Dialog(this, R.style.ProgressDialog);
        this.list = ((ListView) findViewById(R.id.list));
        this.adapter = new PushAdapter(this, R.layout.news_item, this.dataList);
        this.list.setAdapter(this.adapter);
        this.list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
                PushActivity.this.msg((Notify) PushActivity.this.dataList.get(paramAnonymousInt));
            }
        });
    }

    private void msg(final Notify paramNotify) {
        this.dialog_msg.setContentView(R.layout.dialog_msg);
        this.dialog_msg.setCancelable(true);
        this.dialog_msg.getWindow().setLayout(-1, -1);
        new DatabaseHandler(this).viewNews(paramNotify.id);
        this.dataList = new DatabaseHandler(this).getNotify();
        this.adapter = new PushAdapter(this, R.layout.news_item, this.dataList);
        this.list.setAdapter(this.adapter);
        ((TextView) this.dialog_msg.findViewById(R.id.txt_subject)).setText(paramNotify.title);
        ((TextView) this.dialog_msg.findViewById(R.id.txt_msg)).setText(paramNotify.msg);
        ImageView localImageView = (ImageView) this.dialog_msg.findViewById(R.id.img);
        localImageView.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (!paramNotify.link.equals("")) {
                    PushActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramNotify.link)));
                }
            }
        });
        ((ImageView) this.dialog_msg.findViewById(R.id.img_close)).setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                PushActivity.this.dialog_msg.dismiss();
            }
        });
        this.dialog_msg.show();
    }


    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}

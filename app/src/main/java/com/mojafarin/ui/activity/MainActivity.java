package com.mojafarin.ui.activity;


import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mojafarin.Modal.Notify;
import com.mojafarin.R;
import com.mojafarin.db.DatabaseHandler;

import ir.abrin.entities.AbrinMessage;
import ir.abrin.entities.PresenceEventResult;
import ir.abrin.entities.UserEvent;
import ir.abrin.service.AbrinCloudService;
import ir.abrin.service.SubscribeCallback;

/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi
 */
public class MainActivity extends Activity {

    Button logBtn;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.main);
		AbrinCloudService.getInstance(this).init(false);
		System.err.println(AbrinCloudService.getInstance(this).getInstallId());
		AbrinCloudService.getInstance(this).addListener(listener);
		AbrinCloudService.getInstance(this).addListenerAsync(listenerAsync );

        logBtn = (Button) findViewById(R.id.showLogBtn);
        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PushActivity.class));
//                new DatabaseHandler(MainActivity.this).addNotify(new Notify("4534945d1863484396ecf0cf84459f111","mam","salam","","",0));
            }
        });
        try {
			final TextView deviceId = (TextView) findViewById(R.id.deviceidTv);
			deviceId.setText(AbrinCloudService.getInstance(this).getInstallId());
			deviceId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
					ClipboardManager cm = (ClipboardManager)getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
					cm.setText(deviceId.getText());
					Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();

                }
            });
		}
		catch (Exception ex){
			Toast.makeText(this, "can not fetch the device id", Toast.LENGTH_SHORT).show();
		}

    }

	SubscribeCallback listener = new SubscribeCallback() {

		@Override
		public void status(UserEvent status) {
			Toast.makeText(MainActivity.this, "Event: " + status.data, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void presence(PresenceEventResult presence) {
			Toast.makeText(MainActivity.this, "Presence: ", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void message(AbrinMessage message) {
//			((TextView)findViewById(R.id.text)).setText(message.data);
			Toast.makeText(getApplicationContext(), message.data, Toast.LENGTH_SHORT).show();
			Toast.makeText(MainActivity.this, "MY DATA: " + message.data, Toast.LENGTH_SHORT).show();
		}
	};

	SubscribeCallback listenerAsync = new SubscribeCallback() {

		@Override
		public void status(UserEvent status) {
			Toast.makeText(MainActivity.this, "Event: " + status.data, Toast.LENGTH_SHORT).show();

		}

		@Override
		public void presence(PresenceEventResult presence) {
			Toast.makeText(MainActivity.this, "Presence: ", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void message(AbrinMessage message) {
			Toast.makeText(MainActivity.this, "MY DATA: " + message.data, Toast.LENGTH_SHORT).show();
			System.err.println("MY DATA: " + message.data);
		}
	};




	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

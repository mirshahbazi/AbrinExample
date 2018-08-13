package com.mojafarin.ui.adapter;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mojafarin.Modal.Notify;
import com.mojafarin.R;

import java.util.ArrayList;

/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi
 */
public class PushAdapter
        extends ArrayAdapter<Notify> {
    private Context context;

    private ArrayList<Notify> data_list = new ArrayList();
    private int layoutResourceId;

    public PushAdapter(Context paramContext, int paramInt, ArrayList<Notify> paramArrayList) {
        super(paramContext, paramInt, paramArrayList);
        this.context = paramContext;
        this.layoutResourceId = paramInt;
        this.data_list = paramArrayList;
    }

    public View getView(int position, View recycledView, ViewGroup parent) {
        ViewHolder holder;
        if (recycledView == null || recycledView.getTag() == null) {
            recycledView = ((Activity) this.context).getLayoutInflater().inflate(this.layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.txtTitle = ((TextView) recycledView.findViewById(R.id.txtTitle));
            recycledView.setTag(holder);
        }

        holder = (ViewHolder) recycledView.getTag();
        Notify param = (Notify) data_list.get(position);
        holder.txtTitle.setText(param.title);
        if (param.view == 0) {
            holder.txtTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.notify, 0, 0, 0);
        } else {
            holder.txtTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        final View finalRecycledView = recycledView;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, finalRecycledView.getPivotX(), finalRecycledView.getPivotY());
                scaleAnimation.setDuration(450);
                scaleAnimation.setFillAfter(true);
                finalRecycledView.startAnimation(scaleAnimation);
            }
        }, 50);

        return recycledView;
    }

    private static class ViewHolder {
        TextView txtTitle;
    }
}

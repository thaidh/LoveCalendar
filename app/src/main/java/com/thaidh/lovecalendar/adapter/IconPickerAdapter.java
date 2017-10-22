package com.thaidh.lovecalendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thaidh.lovecalendar.R;

/**
 * Created by thaidh on 10/22/17.
 */

public class IconPickerAdapter extends RecyclerView.Adapter<IconPickerAdapter.MyViewHolder> {
    public static final int ITEMS_PER_ROW = 5;

    Context context;

    public IconPickerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.icon_picker_row, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}

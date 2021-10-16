package com.darktornado.carrotsearch;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ListAdapter extends BaseAdapter {

    private final ArrayList<Item> list = new ArrayList<>();
    private int size = -1;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int index) {
        return list.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        Context ctx = parent.getContext();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_view_item, parent, false);
        }

        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView subtitle = (TextView) view.findViewById(R.id.subtitle);

        Item item = list.get(pos);
        icon.setImageDrawable(item.icon);
        title.setText(item.title);
        subtitle.setText(item.subtitle);

        if (size > 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size, 1);
            params.gravity = Gravity.START | Gravity.CENTER;
            icon.setLayoutParams(params);
            view.setLayoutParams(new LinearLayout.LayoutParams(-1, dip2px(ctx, 45), 1));
        }

        return view;
    }

    public void setIconSize(int size){
        this.size = size;
    }

    public void setItems(ArrayList<Item> list) {
        this.list.clear();
        this.list.addAll(list);
    }

    public void addItems(ArrayList<Item> list) {
        this.list.addAll(list);
    }

    public void removeItem(int index) {
        this.list.remove(index);
    }

    public void addItem(Item item) {
        this.list.add(item);
    }

    public void setItems(Item[] list) {
        this.list.clear();
        this.list.addAll(Arrays.asList(list));
    }

    private int dip2px(Context ctx, int dips) {
        return (int) Math.ceil(dips * ctx.getResources().getDisplayMetrics().density);
    }
}

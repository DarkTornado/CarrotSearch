package com.darktornado.carrotsearch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            StrictMode.enableDefaults();
            Intent intent = getIntent();
            final String input = intent.getStringExtra("input");
            getActionBar().setTitle(input);

            CarrotParser parser = new CarrotParser(input);
            final ArrayList<Item> items = parser.parse();

            final ListAdapter adapter = new ListAdapter();
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(1);
            ListView list = new ListView(this);
            adapter.setIconSize(0);
            adapter.setItems(items);
            list.setAdapter(adapter);
            list.setOnItemClickListener((parent, view, pos, id) -> {
                Uri uri = Uri.parse("https://www.daangn.com" + items.get(pos).url);
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent1);
            });
            final boolean[] flag = {false};
            list.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (flag[0]) return;
                    if (!list.canScrollVertically(1)) {
                        flag[0] = true;
                        final PopupWindow window = showWindow();
                        getActionBar().setTitle(input + " (불러오는 중)");
                        new Thread(() -> {
                            try {
                                ArrayList<Item> data = parser.parse();
                                items.addAll(data);
                                runOnUiThread(() -> {
                                    adapter.addItems(data);
                                    adapter.notifyDataSetChanged();
                                    getActionBar().setTitle(input);
                                    window.dismiss();
                                    flag[0] = false;
                                });
                            } catch (Exception e) {
                                toast(e.toString());
                            }
                        }).start();
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                }
            });
            layout.addView(list);

            int pad = dip2px(20);
            list.setPadding(pad, pad, pad, pad);
            setContentView(layout);
        } catch (Exception e) {
            toast(e.toString());
        }
    }


    private PopupWindow showWindow(){
        PopupWindow window = new PopupWindow(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        ProgressBar bar = new ProgressBar(this);
        int pad = dip2px(5);
        bar.setPadding(pad, pad, pad, pad);
        layout.addView(bar);
        TextView txt = new TextView(this);
        txt.setText("불러오는 중...");
        txt.setTextSize(15);
        txt.setTextColor(Color.WHITE);
        pad = dip2px(15);
        txt.setPadding(pad, dip2px(5), dip2px(10), pad);
        layout.addView(txt);
        window.setContentView(layout);
        window.setTouchable(false);
        window.setWidth(-2);
        window.setHeight(-2);
        window.setElevation(dip2px(5));
        window.setAnimationStyle(android.R.style.Animation_InputMethod);
        window.setBackgroundDrawable(new ColorDrawable(Color.argb(90, 90, 90, 90)));
        window.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        return window;
    }

    public int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }

    public void toast(final String msg) {
        runOnUiThread(() -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show());
    }
}
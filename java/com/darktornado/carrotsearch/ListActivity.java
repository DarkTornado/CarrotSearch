package com.darktornado.carrotsearch;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            StrictMode.enableDefaults();
            Intent intent = getIntent();
            String input = intent.getStringExtra("input");
            getActionBar().setTitle(input);

            CarrotParser parser = new CarrotParser(input);
            final ArrayList<Item> items = parser.parse();
            items.add(new Item("더보기", "", "", null));

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(1);
            ListView list = new ListView(this);
            final ListAdapter adapter = new ListAdapter();
            adapter.setIconSize(0);
            adapter.setItems(items);
            list.setAdapter(adapter);
            list.setOnItemClickListener((parent, view, pos, id) -> {
                if (pos == items.size() - 1) {
                    try {
                        ArrayList<Item> data = parser.parse();
                        data.add(new Item("더보기", "", "", null));
                        adapter.removeItem(items.size() - 1);
                        items.remove(items.size() - 1);
                        items.addAll(data);
                        adapter.addItems(data);
                        adapter.notifyDataSetChanged();
                        toast("로드됨");
                    } catch (Exception e) {
                        toast(e.toString());
                    }
                } else {
                    Uri uri = Uri.parse("https://www.daangn.com/" + items.get(pos).url);
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent1);
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

    public int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }

    public void toast(final String msg) {
        runOnUiThread(() -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show());
    }
}
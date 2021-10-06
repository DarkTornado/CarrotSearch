package com.darktornado.carrotsearch;

import android.graphics.drawable.Drawable;

public class Item {
    public final String title, subtitle, url;
    public final Drawable icon;

    public Item(String title, String subtitle, String url, Drawable icon) {
        this.title = title;
        this.subtitle = subtitle;
        this.url = url;
        this.icon = icon;
    }
}

package com.darktornado.carrotsearch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class CarrotParser {
    private final String input;
    private int page = 1;

    public CarrotParser(String input) {
        this.input = input;
    }

    public ArrayList<Item> parse() throws IOException {
        Elements data = Jsoup.connect("https://www.daangn.com/search/" + input.replace(" ", "%20") + "/more/flea_market?page=" + page).get()
                .select("article");
        if (data.size() == 0) return null;
        ArrayList<Item> result = new ArrayList<>();
        for (int n = 0; n < data.size(); n++) {
            Element datum = data.get(n);
            String title = datum.select("span.article-title").text();
            String region = datum.select("p.article-region-name").text();
            String price = datum.select("p.article-price ").text();
            String image = datum.select("img").get(0).attr("src");
            String url = datum.select("a").get(0).attr("src");
            result.add(new Item(title, price + " / " + region, url, new BitmapDrawable(getImageFromWeb(image))));
        }
        page++;
        return result;
    }

    private Bitmap getImageFromWeb(String link) {
        try {
            URL url = new URL(link);
            URLConnection con = url.openConnection();
            if (con != null) {
                con.setConnectTimeout(5000);
                con.setUseCaches(false);
                BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
                Bitmap bitmap = BitmapFactory.decodeStream(bis);
                bis.close();
                return bitmap;
            }
        } catch (Exception e) {
            //toast(e.toString());
        }
        return null;
    }
}
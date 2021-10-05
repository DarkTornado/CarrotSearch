package com.darktornado.carrotsearch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        TextView txt1 = new TextView(this);
        txt1.setText("지역 : ");
        txt1.setTextSize(18);
        layout.addView(txt1);
        final EditText txt2 = new EditText(this);
        txt2.setHint("지역 입력...");
        layout.addView(txt2);
        TextView txt3 = new TextView(this);
        txt3.setText("\n검색어 : ");
        txt3.setTextSize(18);
        layout.addView(txt3);
        final EditText txt4 = new EditText(this);
        txt4.setHint("검색어 입력...");
        layout.addView(txt4);
        Button search = new Button(this);
        search.setText("검색");
        search.setOnClickListener((v) -> {
            String location = txt2.getText().toString();
            String word = txt4.getText().toString();
            final String input = (location + "%20" + word).replace(" ", "%20");
            new Thread(() -> searchStuff(input)).start();
        });
        layout.addView(search);
        int pad = dip2px(16);
        layout.setPadding(pad, pad, pad, pad);
        ScrollView scroll = new ScrollView(this);
        scroll.addView(layout);
        setContentView(scroll);
    }

    private void searchStuff(String input) {
        try {
            Elements data = Jsoup.connect("https://www.daangn.com/search/" + input).get()
                    .select("div.articles-wrap").get(0).select("article");
            if (data.size() == 0) {
                toast("검색 결과가 없어요 :(");
                return;
            }
            StringBuilder result = new StringBuilder();
            for (int n = 0; n < data.size(); n++) {
                Element datum = data.get(n);
                String title = datum.select("span.article-title").text();
                String location = datum.select("p.article-region-name").text();
                String price = datum.select("p.article-price ").text();
                result.append(title).append("\n").append(location).append("\n").append(price).append("\n\n");
            }
            toast(result.toString());
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
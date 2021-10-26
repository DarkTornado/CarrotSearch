package com.darktornado.carrotsearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
            String region = txt2.getText().toString();
            String word = txt4.getText().toString();
            final String input = (region + " " + word);
            searchStuff(input);
            if (Utils.loadSettings(this, "save_input", false)) {
                Utils.saveData(this, "region", region);
                Utils.saveData(this, "word", word);
            }
        });
        layout.addView(search);
        Button set = new Button(this);
        set.setText("설정");
        set.setOnClickListener((v) -> {
            openSettings();
        });
        layout.addView(set);
        int pad = dip2px(16);
        layout.setPadding(pad, pad, pad, pad);
        ScrollView scroll = new ScrollView(this);
        scroll.addView(layout);
        setContentView(scroll);
        if (Utils.loadSettings(this, "save_input", false)) {
            String region = Utils.readData(this, "region");
            String word = Utils.readData(this, "word");
            if (region != null) txt2.setText(region);
            if (word != null) txt4.setText(word);
        }
    }

    private void searchStuff(String input) {
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("input", input);
        startActivity(intent);
    }

    private void openSettings() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("검색기 설정");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);
        String[] menus = {"입력했던 내용 저장", "앱 내부에서 제품 정보 열기"};
        final String[] bools = {"save_input", "use_web_view"};
        Switch[] sws = new Switch[menus.length];
        for (int n = 0; n < menus.length; n++) {
            sws[n] = new Switch(this);
            sws[n].setText(menus[n]);
            sws[n].setId(n);
            sws[n].setChecked(Utils.loadSettings(this, bools[n], false));
            sws[n].setOnCheckedChangeListener((view, isChecked) -> {
                Utils.saveSettings(this, bools[view.getId()], isChecked);
            });
            layout.addView(sws[n]);
        }
        int pad = dip2px(16);
        layout.setPadding(pad, pad, pad, pad);
        ScrollView scroll = new ScrollView(this);
        scroll.addView(layout);
        dialog.setView(scroll);
        dialog.setNegativeButton("닫기", null);
        dialog.show();
    }

    public int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }

    public void toast(final String msg) {
        runOnUiThread(() -> Toast.makeText(this, msg, Toast.LENGTH_SHORT).show());
    }

}
package com.example.administrator.webviewcachemanagerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.webviewcachemanagerapp.utils.DataUtils;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listview);
        SimpleAdapter adapter = new SimpleAdapter(this, DataUtils.returnData(), R.layout.item_listview, new String[]{"name", "id"}, new int[]{R.id.nameTv, R.id.idTv});
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, SimpleWebviewAct.class).putExtra("url", DataUtils.returnUrl(position + 1)).putExtra("id", DataUtils.returnId(position + 1)));
            }
        });
    }

}

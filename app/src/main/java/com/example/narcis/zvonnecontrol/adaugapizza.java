package com.example.narcis.zvonnecontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class adaugapizza extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugapizza);
        listView=(ListView)findViewById(R.id.listapizza);
    }
}

package com.example.narcis.zvonnecontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.narcis.zvonnecontrol.adaptori.adaptorpizza;
import com.example.narcis.zvonnecontrol.obiecte.coman;
import com.example.narcis.zvonnecontrol.obiecte.pizza;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class pizzaActivity extends AppCompatActivity {

    private ListView listView;
    private List<pizza> pizzaList=new ArrayList<>();
    private adaptorpizza adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);
        listView=(ListView)findViewById(R.id.listapizza);
        adaptor=new adaptorpizza(this,R.layout.adaptorpizza,pizzaList);
        listView.setAdapter(adaptor);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Pizza");


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pizzaList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    pizza aux = ds.getValue(pizza.class);
                    pizzaList.add(aux);
                }
                adaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void fab(View view) {

    }
}

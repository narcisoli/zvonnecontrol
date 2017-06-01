package com.example.narcis.zvonnecontrol;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.narcis.zvonnecontrol.adaptori.adaptorpizza;
import com.example.narcis.zvonnecontrol.obiecte.coman;
import com.example.narcis.zvonnecontrol.obiecte.pizza;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class pizzaActivity extends AppCompatActivity {

    private ListView listView;
    private List<pizza> pizzaList = new ArrayList<>();
    private adaptorpizza adaptor;
    private DatabaseReference db;
    private DatabaseReference aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);
        listView = (ListView) findViewById(R.id.listapizza);
        adaptor = new adaptorpizza(this, R.layout.adaptorpizza, pizzaList);
        listView.setAdapter(adaptor);
        db = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Pizza");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String[] items = getResources().getStringArray(R.array.optiunipizza);
                aux = db.child(pizzaList.get(position).getTip());
                new LovelyChoiceDialog(pizzaActivity.this)
                        .setTopColorRes(R.color.colorPrimary)


                        .setItems(items, new LovelyChoiceDialog.OnItemSelectedListener<String>() {
                            @Override
                            public void onItemSelected(int position1, String item) {
                                if (position1 == 0) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(pizzaActivity.this);
                                    builder.setMessage("Editezi ingrediente : "+pizzaList.get(position).getTip());
                                    final EditText input = new EditText(pizzaActivity.this);
                                    builder.setView(input);
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            aux.child("ingrediente").setValue(input.getText().toString());
                                        }
                                    });
                                    builder.setNegativeButton("Cancel", null);
                                    builder.show();

                                } else if (position1 == 1) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(pizzaActivity.this);
                                    builder.setMessage("Editezi pret : "+pizzaList.get(position).getTip());
                                    final EditText input = new EditText(pizzaActivity.this);
                                    builder.setView(input);
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            int myNum = 0;
                                            try {
                                                myNum = Integer.parseInt(input.getText().toString());
                                            } catch(NumberFormatException nfe) {
                                                Toast.makeText(pizzaActivity.this, "Pretul trebuie sa fie un numar!", Toast.LENGTH_SHORT).show();
                                            }
                                            aux.child("pret").setValue(myNum);
                                        }
                                    });

                                    builder.setNegativeButton("Cancel", null);
                                    builder.show();

                                }else if (position1 == 2) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(pizzaActivity.this);
                                    builder.setMessage("Editezi gramaj : "+pizzaList.get(position).getTip());
                                    final EditText input = new EditText(pizzaActivity.this);
                                    builder.setView(input);
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            aux.child("gramaj").setValue(input.getText().toString());
                                        }
                                    });

                                    builder.setNegativeButton("Cancel", null);
                                    builder.show();

                                }else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(pizzaActivity.this);
                                    builder.setMessage("Esti sigur ca vrei sa stergi : "+pizzaList.get(position).getTip());
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                         aux.removeValue();
                                        }
                                    });

                                    builder.setNegativeButton("Cancel", null);
                                    builder.show();
                                }
                                adaptor.notifyDataSetChanged();

                            }
                        })
                        .show();
            }
        });

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
        startActivity(new Intent(this,adaugapizza.class));
    }
}

package com.example.narcis.zvonnecontrol;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.narcis.zvonnecontrol.adaptori.adaptorcomanda;
import com.example.narcis.zvonnecontrol.obiecte.coman;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<coman> comanList = new ArrayList<>();
    private adaptorcomanda a;
    private RelativeLayout relativeLayout;
    private FloatingActionButton fab;
    boolean aBoolean = true;
    private Button butonmeniu;
    private Button butonoferta;
    private Button butonevenimente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, MyService.class));
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        ListView listView = (ListView) findViewById(R.id.listacomenzi);
        a = new adaptorcomanda(this, R.layout.adaptorcomanda, comanList);
        listView.setAdapter(a);





        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String formattedDate = df.format(c.getTime());
        Query query = db.orderByChild("data").equalTo(formattedDate);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comanList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    coman aux = ds.getValue(coman.class);
                    comanList.add(aux);
                }
                Collections.reverse(comanList);
                a.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Uri number = Uri.parse("tel:"+comanList.get(position).getNrdetelefon());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (comanList.get(position).getStatus() == 1) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Confirmi comanda?")
                            .setCancelable(true)
                            .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("Motivul pentru care vrei sa anulezi comanda :");
                                    final EditText input = new EditText(MainActivity.this);
                                    builder.setView(input);
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi").child(comanList.get(position).getId() + "").child("status").setValue(0);
                                            FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi").child(comanList.get(position).getId() + "").child("text").setValue(comanList.get(position).getText() + "\n\n" + input.getText().toString());
                                        }
                                    });

                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                                    builder.show();

                                }
                            })
                            .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi").child(comanList.get(position).getId() + "").child("status").setValue(2);
                                }
                            }).show();
                } else if (comanList.get(position).getStatus() == 2) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("A intrat la cuptor?")
                            .setCancelable(true)
                            .setNegativeButton("Nu", null)
                            .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi").child(comanList.get(position).getId() + "").child("status").setValue(3);
                                }
                            }).show();
                } else if (comanList.get(position).getStatus() == 3) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("A plecat?")
                            .setCancelable(true)
                            .setNegativeButton("Nu", null)
                            .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi").child(comanList.get(position).getId() + "").child("status").setValue(4);
                                }
                            }).show();
                } else if (comanList.get(position).getStatus() == 4) {
                    {
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage("A fost finalizata?")
                                .setCancelable(true)
                                .setNegativeButton("Nu", null)
                                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi").child(comanList.get(position).getId() + "").child("status").setValue(5);
                                    }
                                }).show();
                    }
                } else {
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }



    public void meniu(View view) {
        startActivity(new Intent(this, pizzaActivity.class));
    }

    public void oferta(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Vrei sa schimbi oferta? ");
        final EditText input = new EditText(MainActivity.this);
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                FirebaseDatabase.getInstance().getReference().child("Zvonne").child("Oferte").setValue(input.getText().toString());
                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "Oferta a fost publicata cu succes", Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void evenimente(View view) {
        startActivity(new Intent(this, EvenimentActivity.class));
    }
}

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

        fab = (FloatingActionButton) findViewById(R.id.fab);
        butonmeniu = (Button) findViewById(R.id.buttonpizza);
        butonoferta = (Button) findViewById(R.id.butonoferta);
        butonevenimente = (Button) findViewById(R.id.butonevenimente);
        butonevenimente.setVisibility(View.GONE);
        butonoferta.setVisibility(View.GONE);
        butonmeniu.setVisibility(View.GONE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aBoolean) {
                    butonevenimente.setVisibility(View.VISIBLE);
                    butonoferta.setVisibility(View.VISIBLE);
                    butonmeniu.setVisibility(View.VISIBLE);
                    aBoolean = false;
                } else {
                    butonevenimente.setVisibility(View.GONE);
                    butonoferta.setVisibility(View.GONE);
                    butonmeniu.setVisibility(View.GONE);
                    aBoolean = true;
                }
            }
        });


        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
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
                a.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDestroy() {
        notif();
        super.onDestroy();

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void notif () {

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager nm = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);


        Notification.Builder builder = new Notification.Builder(this);
        Resources res = this.getResources();
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Ticker")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Zvonne Control App")
                .setContentText("Serviciul s-a oprit");
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        Notification n = builder.build();

        nm.notify(1, n);

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

package com.example.narcis.zvonnecontrol;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyService extends Service {
    private boolean bool = false;

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
        String formattedDate = df.format(c.getTime());
        Query query = db.orderByChild("nume").equalTo(formattedDate);
        query.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                notif();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void notif() {
        if (bool) {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.putExtra("fragment", "comenzi");
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
                    .setContentTitle("Zvonne App")
                    .setContentText("O actualizare noua");
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(alarmSound);
            Notification n = builder.build();

            nm.notify(1, n);
        } else bool = true;
    }
}

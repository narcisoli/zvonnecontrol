package com.example.narcis.zvonnecontrol;

import android.app.AlarmManager;
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
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.narcis.zvonnecontrol.obiecte.coman;
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
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private boolean bool = false;
    private static long UPDATE_INTERVAL = 1*5*1000;  //default

    private static Timer timer = new Timer();
    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        _startService();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Zvonne").child("comenzi");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        Query query = db.orderByChild("data").equalTo(formattedDate);
        query.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                coman c=dataSnapshot.getValue(coman.class);
                notif(c);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i("Firebase","Child changed");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i("Firebase","Child remove");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.i("Firebase","Child moved");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Firebase","Child canceled");
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void notif(coman c) {
        if (bool) {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.putExtra("fragment", "comenzi");
            PendingIntent contentIntent = PendingIntent.getActivity(this,
                    (int) System.currentTimeMillis(), notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager nm = (NotificationManager) this
                    .getSystemService(Context.NOTIFICATION_SERVICE);


            Notification.Builder builder = new Notification.Builder(this);
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
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    private void _startService()
    {
        timer.scheduleAtFixedRate(

                new TimerTask() {

                    public void run() {

                        doServiceWork();

                    }
                }, 1000,UPDATE_INTERVAL);
        Log.i(getClass().getSimpleName(), "FileScannerService Timer started....");
    }

    private void doServiceWork()
    {
        //do something wotever you want
        //like reading file or getting data from network
        try {
        }
        catch (Exception e) {
        }

    }

    private void _shutdownService()
    {
        if (timer != null) timer.cancel();
        Log.i(getClass().getSimpleName(), "Timer stopped...");
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        _shutdownService();

        // if (MAIN_ACTIVITY != null)  Log.d(getClass().getSimpleName(), "FileScannerService stopped");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        return START_STICKY;
    }

    @Nullable


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);

        //Restart the service once it has been killed android


        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +100, restartServicePI);

    }
}

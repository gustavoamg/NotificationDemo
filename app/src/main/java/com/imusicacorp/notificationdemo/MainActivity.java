package com.imusicacorp.notificationdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;

import com.imusicacorp.notificationdemo.ui.CustomLayoutNotification;

public class MainActivity extends AppCompatActivity {

    public static final String DEFAULT_NOTIFICATION_CHANNEL = "CHANNEL_DEFAULT";
    public static final String LOW_PRIORITY_NOTIFICATION_CHANNEL = "LOW_PRIORITY_NOTIFICATION_CHANNEL";
    public static final String HIGH_PRIORITY_NOTIFICATION_CHANNEL = "HIGH_PRIORITY_NOTIFICATION_CHANNEL";
    public static final String MIN_PRIORITY_NOTIFICATION_CHANNEL = "MIN_PRIORITY_NOTIFICATION_CHANNEL";

    public static final int NOTIFICATION_BASIC = 1;
    public static final int NOTIFICATION_EXPANDABLE = 2;
    public static final int NOTIFICATION_WITH_BIG_ICON = 3;
    public static final int NOTIFICATION_WITH_INTERACTIONS = 4;
    public static final int NOTIFICATION_WITH_CUSTOM_LAYOUT = 5;

    private static int messageCounter = 0;

    private RadioGroup radioGroupPriorities;
    private NotificationManager manager;


    NotificationChannel chan1;
    NotificationChannel chan2;
    NotificationChannel chan3;
    NotificationChannel chan4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            chan1 = new NotificationChannel(DEFAULT_NOTIFICATION_CHANNEL,
                    "Channel Default", NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setLightColor(Color.GREEN);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(chan1);

            chan2 = new NotificationChannel(MIN_PRIORITY_NOTIFICATION_CHANNEL,
                    "Channel Min", NotificationManager.IMPORTANCE_MIN);
            chan2.setLightColor(Color.BLUE);
            chan2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(chan2);

            chan3 = new NotificationChannel(LOW_PRIORITY_NOTIFICATION_CHANNEL,
                    "Channel Low", NotificationManager.IMPORTANCE_LOW);
            chan3.setLightColor(Color.BLUE);
            chan3.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(chan3);

            chan4 = new NotificationChannel(HIGH_PRIORITY_NOTIFICATION_CHANNEL,
                    "Channel High", NotificationManager.IMPORTANCE_HIGH);
            chan4.setLightColor(Color.BLUE);
            chan4.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(chan4);
        } else {
            RelativeLayout rl = findViewById(R.id.channel_options);
            rl.setVisibility(View.GONE);
        }

        Button btNotificationSimple = findViewById(R.id.notificacao_tipo_1);
        btNotificationSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, getCurrentChannel())
                        .setContentTitle("Notificação Simples " + MainActivity.messageCounter)
                        .setContentText("Mensagem da notificação " + MainActivity.messageCounter)
                        .setSmallIcon(getSmallIcon())
                        .setAutoCancel(true);


                Intent resultIntent = new Intent(MainActivity.this, MainActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);

                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(resultPendingIntent);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIFICATION_EXPANDABLE, builder.build());

                messageCounter++;

            }
        });

        Button btNotificationExpandable = findViewById(R.id.notificacao_tipo_2);
        btNotificationExpandable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, getCurrentChannel())
                        .setContentTitle("Notificação Expansível " + MainActivity.messageCounter)
                        .setContentText("Mensagem da notificação " + MainActivity.messageCounter + " fechada")
                        .setSmallIcon(getSmallIcon());

                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                inboxStyle.setBigContentTitle("Conteúdo oculto da notificação:");

                String[] events = {"Linha 1", "Linha 2", "Linha 3", "Linha 4", "Linha 5", "Linha 6"};

                for (int i = 0; i < events.length; i++) {
                    inboxStyle.addLine(events[i]);
                }

                builder.setStyle(inboxStyle);

                Intent resultIntent = new Intent(MainActivity.this, MainActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);

                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(resultPendingIntent);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIFICATION_EXPANDABLE, builder.build());

                messageCounter++;
            }
        });

        Button btNotificationWithImage = findViewById(R.id.notificacao_tipo_3);
        btNotificationWithImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Drawable d = getResources().getDrawable(android.R.drawable.btn_star);

                Drawable currentState = d.getCurrent();
                Bitmap b = ((BitmapDrawable) currentState).getBitmap();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, getCurrentChannel())
                        .setContentTitle("Notificação Expansivel Com Imagem " + MainActivity.messageCounter)
                        .setContentText("Mensagem da notificação " + MainActivity.messageCounter + " fechada")
                        .setSmallIcon(getSmallIcon())
                        .setLargeIcon(b);

                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                inboxStyle.setBigContentTitle("Conteúdo oculto da notificação:");

                String[] events = {"Linha 1", "Linha 2", "Linha 3", "Linha 4", "Linha 5", "Linha 6"};

                for (int i = 0; i < events.length; i++) {
                    inboxStyle.addLine(events[i]);
                }

                builder.setStyle(inboxStyle);

                Intent resultIntent = new Intent(MainActivity.this, MainActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);

                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(resultPendingIntent);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIFICATION_WITH_BIG_ICON, builder.build());

                messageCounter++;
            }
        });

        Button btNotificationWithInteractions = findViewById(R.id.notificacao_tipo_4);
        btNotificationWithInteractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable d = getResources().getDrawable(android.R.drawable.btn_star);

                Drawable currentState = d.getCurrent();
                Bitmap b = ((BitmapDrawable) currentState).getBitmap();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, getCurrentChannel())
                        .setContentTitle("Notificação Expansivel Com Imagem e Interação" + MainActivity.messageCounter)
                        .setContentText("Mensagem da notificação " + MainActivity.messageCounter + " fechada")
                        .setSmallIcon(getSmallIcon())
                        .setLargeIcon(b)
                        .addAction(new NotificationCompat.Action(-1, "AÇÃO 1", PendingIntent.getActivity(MainActivity.this, 200, new Intent(MainActivity.this, MainActivity.class), 0)))
                        .addAction(new NotificationCompat.Action(-1, "AÇÃO 2", PendingIntent.getActivity(MainActivity.this, 200, new Intent(MainActivity.this, MainActivity.class), 0)))
                        .addAction(new NotificationCompat.Action(-1, "AÇÃO 3", PendingIntent.getActivity(MainActivity.this, 200, new Intent(MainActivity.this, MainActivity.class), 0)));

                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                inboxStyle.setBigContentTitle("Conteúdo oculto da notificação:");

                String[] events = {"Linha 1", "Linha 2", "Linha 3", "Linha 4", "Linha 5", "Linha 6"};

                for (int i = 0; i < events.length; i++) {
                    inboxStyle.addLine(events[i]);
                }

                builder.setStyle(inboxStyle);

                Intent resultIntent = new Intent(MainActivity.this, MainActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);

                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(resultPendingIntent);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIFICATION_WITH_INTERACTIONS, builder.build());

                messageCounter++;
            }
        });

        Button btNotificationWithCustomLayout = findViewById(R.id.notificacao_tipo_5);
        btNotificationWithCustomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteViews customLayout = new CustomLayoutNotification(this.getClass().getPackage().getName(), R.layout.custom_notification_layout);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, getCurrentChannel())
                        .setContent(customLayout)
                        .setContentTitle("Notificação Expansivel Com Imagem e Interação" + MainActivity.messageCounter)
                        .setContentText("Mensagem da notificação " + MainActivity.messageCounter + " fechada")
                        .setSmallIcon(getSmallIcon());

                Intent resultIntent = new Intent(MainActivity.this, MainActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);

                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(resultPendingIntent);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIFICATION_WITH_CUSTOM_LAYOUT, builder.build());

                messageCounter++;
            }
        });

        radioGroupPriorities = findViewById(R.id.main_priorities_group);
    }

    private String getCurrentChannel() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            switch (radioGroupPriorities.getCheckedRadioButtonId()) {
                case R.id.radio_1:
                    return HIGH_PRIORITY_NOTIFICATION_CHANNEL;
                case R.id.radio_2:
                    return DEFAULT_NOTIFICATION_CHANNEL;
                case R.id.radio_3:
                    return LOW_PRIORITY_NOTIFICATION_CHANNEL;
                case R.id.radio_4:
                    return MIN_PRIORITY_NOTIFICATION_CHANNEL;
                default:
                    return DEFAULT_NOTIFICATION_CHANNEL;
            }
        } else
            return DEFAULT_NOTIFICATION_CHANNEL;
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    private int getSmallIcon() {
        return android.R.drawable.stat_notify_chat;
    }
}

package com.haohui.myapplication;

import static android.content.Intent.getIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.List;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String TAG = "Notify";
    private List<Habitos> listaHabitos;


    @Override
    public void onReceive(Context context, Intent intent) {

        String titulo = intent.getStringExtra("titulo");
        String hora = intent.getStringExtra("hora");
        String fecha = "No hay fecha";
        listaHabitos = DatabaseClient.getInstance(context).getHabitoDatabase().habitosDao().getAllHabitos();


        for (int i = 0; i < listaHabitos.size(); i++) {
            if (titulo.equals(listaHabitos.get(i).getTitulo())){
                fecha = listaHabitos.get(i).getFecha();
            }
        }


        Log.d(TAG, "onReceive ejecutado"); // Asegúrate de que este log se muestra en el Logcat
        // Construye la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,
                "daily_reminder_channel")
                .setSmallIcon(R.drawable.iconoapp2) // Icono de la notificación
                .setContentTitle(titulo) // Título de la notificación
                .setContentText("Fecha: " + fecha + " Hora: " + hora) // Texto de la notificación
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Prioridad de la notificación
                .setAutoCancel(true); // La notificación se cancela al tocarla

        // Muestra la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
        Log.d(TAG, "Notificación enviada");
    }
}

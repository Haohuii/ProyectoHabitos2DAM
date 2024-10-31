package com.haohui.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class AddNotificacion extends AppCompatActivity {

    private Spinner spinner;
    private Button notificar;
    private EditText editTextTiempo;

    private static final int REQUEST_CODE = 1; // Código de solicitud para permisos
    private Button createNotificationButton; // Botón para crear la notificación
    private static final String TAG = "Notify";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_notificacion);


        editTextTiempo = (EditText) findViewById(R.id.horaNotificacion);

        // Inicializa el Spinner
        spinner = findViewById(R.id.spinnerNotificacion);

        // Lista de datos para el Spinner
        List<Habitos> opciones = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitosDao().getAllHabitos();
        Log.d("Prueba spinner", opciones.size()+"");
        ArrayList<String> titulo = new ArrayList<>();
        if (opciones.size() > 0) {
            for (Habitos elemento : opciones) {
                titulo.add(elemento.getTitulo());
            }

        } else {
            titulo.add("No hay habitos");
        }

        // Crea un ArrayAdapter usando un layout por defecto de Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, titulo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asigna el adaptador al Spinner
        spinner.setAdapter(adapter);

        // Para obtener el valor seleccionado en cualquier momento

        int posicionSeleccionada = spinner.getSelectedItemPosition();

        notificar = findViewById(R.id.crearNotificacion);
        notificar.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 y superior
                if (ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{"android.permission.POST_NOTIFICATIONS"}, REQUEST_CODE);
                } else {
                    createNotificationChannel(); // Crea el canal de notificaciones
                    scheduleDailyNotification(); // Programa una notificación diaria
                }
            } else {
                createNotificationChannel(); // Crea el canal de notificaciones
                scheduleDailyNotification(); // Programa una notificación diaria
            }

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Finalizar la actividad y volver a la actividad principal
                    startActivity(new Intent(getApplicationContext(), Pantalla2.class));
                }
            }, 2000);

        });

    }

    //Notificaciones-----------------------------------------------------------------------------------


    // Método para crear el canal de notificaciones necesario para Android 8.0 y superior
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Solo para Android 8.0 y superior
            CharSequence name = "DailyReminderChannel";
            String description = "Channel for Daily Reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("daily_reminder_channel",
                    name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.d(TAG, "Canal de notificaciones creado");
        }
    }


    // Método para programar una notificación diaria a una hora específica
    private void scheduleDailyNotification() {
        String tiempo = editTextTiempo.getText().toString();

        int hora = 12;
        int minuto = 00;
        if (!tiempo.isEmpty()) {
            // Divide la cadena en hora y minutos
            String[] parteTiempo = tiempo.split(":");
            hora = Integer.parseInt(parteTiempo[0]);
            minuto = Integer.parseInt(parteTiempo[1]);
        }
        // Obtiene una instancia del calendario
        Calendar calendar = Calendar.getInstance();
        // Establece la zona horaria predeterminada del dispositivo
        calendar.setTimeZone(TimeZone.getDefault());
        // Establece la hora actual
        calendar.setTimeInMillis(System.currentTimeMillis());
        // Configura la hora de la notificación (9:00:00 en este ejemplo)
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minuto);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.add(Calendar.SECOND, 30);

        // Si la hora configurada ya pasó, programa para el siguiente día
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Crea un intent para el receptor de la notificación
        Intent intent = new Intent(this, NotificationReceiver.class);
        String valorSeleccionado = spinner.getSelectedItem().toString();


        intent.putExtra("titulo", valorSeleccionado);
        intent.putExtra("hora", tiempo);

        // Agrega el flag FLAG_IMMUTABLE al PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Programa la notificación diaria utilizando AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.d(TAG, "Notificación diaria programada a las " + calendar.getTime());
    }

    // Manejo de los resultados de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createNotificationChannel(); // Crea el canal de notificaciones
                scheduleDailyNotification(); // Programa una notificación diaria
            }
        }
    }

}
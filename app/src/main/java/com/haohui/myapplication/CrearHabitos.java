package com.haohui.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class CrearHabitos extends AppCompatActivity {

    private ImageButton perfil;
    private ImageButton atras;
    private Button aceptarHabito;
    private EditText txtFecha, txtHora, txtTitulo;
    private String f;


//    private static final int REQUEST_CODE = 1; // Código de solicitud para permisos
//    private Button createNotificationButton; // Botón para crear la notificación
//    private static final String TAG = "Notify";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_habitos);

        perfil = findViewById(R.id.btnPerfilCrearHabitos);
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {acceso_perfil();}
        });

        atras = findViewById(R.id.btnAtrasPantalla2);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {boton_atras();}
        });

        //Referencia vista
        txtFecha = findViewById(R.id.txtFecha);
        txtHora = findViewById(R.id.txtHora);
        txtTitulo = findViewById(R.id.txtHabito);
        aceptarHabito = findViewById(R.id.aceptarHabito);

        //Acción para guardar habito
        aceptarHabito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarHabito();
            }
        });

        Intent intent = getIntent();

        txtFecha.setText(intent.getStringExtra("fecha"));

    }

    private void acceso_perfil() {
        Intent intentAdd = new Intent(this, PantallaPerfil.class);
        startActivity(intentAdd);
    }

    private void boton_atras() {
        Intent intentAdd = new Intent(this, Pantalla2.class);
        startActivity(intentAdd);
    }

    private void guardarHabito () {


        String fecha = txtFecha.getText().toString();
        String hora = txtHora.getText().toString();
        String titulo = txtTitulo.getText().toString();


        // Validar que los campos no estén vacíos
        if (fecha.isEmpty() || hora.isEmpty() || titulo.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Habitos habito = new Habitos(fecha, hora, titulo);

//        Insertar Digimon en la base de datos usando Room
        DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitosDao().insert(habito);



//         Mostrar mensaje de éxito
        Toast.makeText(this, "Habito añadido", Toast.LENGTH_SHORT).show();



        // Finalizar la actividad y volver a la actividad principal
        startActivity(new Intent(this, Pantalla2.class));

    }

}
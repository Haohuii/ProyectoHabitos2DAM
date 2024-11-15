package com.haohui.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;

public class Pantalla2 extends AppCompatActivity {
    private ImageButton perfil, atras, add, alarma;
    private CalendarView calendario;
    private String fecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);
        calendario = findViewById(R.id.calendarView);
        calendario.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                fecha = dayOfMonth +"/"+ (month + 1) + "/" + year;
            }
        });

        atras = findViewById(R.id.atras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {boton_atras();}
        });
        add = findViewById(R.id.anyadirHab);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {boton_add();}
        });
        alarma = findViewById(R.id.btnAlarma);
        alarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {boton_alarma();}
        });
    }

    private void boton_atras() {
        Intent intentAdd = new Intent(this, MainActivity.class);
        startActivity(intentAdd);
    }
    private void boton_add() {
        Intent intentAdd = new Intent(this, CrearHabitos.class);
        intentAdd.putExtra("fecha", fecha);
        startActivity(intentAdd);
    }
    private void boton_alarma() {
        Intent intentAdd = new Intent(this, AddNotificacion.class);
        startActivity(intentAdd);
    }
}
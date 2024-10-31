package com.haohui.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class EditarHabito extends AppCompatActivity {

    private ImageButton perfil;
    private ImageButton atras;
    private Button aceptarHabito;
    private EditText txtFecha, txtHora, txtTitulo;
    private List<Habitos> listaHabitos;
    private Habitos h;
    private int position;

// Hola prueba GitHub con Mauricio

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_habito);

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

        Intent intent = getIntent();
        listaHabitos = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitosDao().getAllHabitos();
        position = intent.getIntExtra("posicion", -1);
        //Log.d("pooo", position+"");
        h = listaHabitos.get(position);


        //Referencia vista
        txtFecha = findViewById(R.id.txtFecha);
        txtHora = findViewById(R.id.txtHora);
        txtTitulo = findViewById(R.id.txtHabito);
        aceptarHabito = findViewById(R.id.aceptarHabito);

        txtFecha.setText(h.getFecha());
        txtHora.setText(h.getHora());
        txtTitulo.setText(h.getTitulo());

        //Acción para guardar habito
        aceptarHabito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarHabito();
            }
        });



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

        Log.d("Hola", fecha+ " " + hora + " " + titulo);

        // Validar que los campos no estén vacíos
        if (fecha.isEmpty() || hora.isEmpty() || titulo.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        h.setFecha(fecha);
        h.setHora(hora);
        h.setTitulo(titulo);

//        Editar Digimon en la base de datos usando Room
        DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitosDao().update(h);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("habitoActualizado", (Serializable) h);
        resultIntent.putExtra("posicion", position);
        setResult(RESULT_OK, resultIntent);

//         Mostrar mensaje de éxito
        Toast.makeText(this, "Habito editado", Toast.LENGTH_SHORT).show();

        // Finalizar la actividad y volver a la actividad principal
        startActivity(new Intent(this, MainActivity.class));

    }
}
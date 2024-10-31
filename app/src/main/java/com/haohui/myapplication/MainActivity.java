package com.haohui.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button crearHabito;
    private ImageButton perfil;

    private MostrarHabitosRV adapter;
    private List<Habitos> listaHabitos;
    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crearHabito = findViewById(R.id.crearHabito);
        crearHabito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crear_habito();
            }
        });

        perfil = findViewById(R.id.perfil);
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {acceso_perfil();}
        });


        recyclerView = findViewById(R.id.rViewMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadHabitos();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void crear_habito() {
        Intent intentAdd = new Intent(this, Pantalla2.class);
        startActivity(intentAdd);
    }

    private void acceso_perfil() {
        Intent intentAdd = new Intent(this, PantallaPerfil.class);
        startActivity(intentAdd);

    }

    private void loadHabitos() {
        listaHabitos = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitosDao().getAllHabitos();

        HabitosDao habitosDao = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitosDao();
        adapter = new MostrarHabitosRV(listaHabitos, habitosDao, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Recibir el hábito actualizado y la posición
            Habitos habitoActualizado = (Habitos) data.getSerializableExtra("habitoActualizado");
            int position = data.getIntExtra("posicion", -1);

            // Asegúrate de que la posición es válida
            if (position != -1 && habitoActualizado != null) {
                adapter.updateItem(position, habitoActualizado);
            }
        }
    }




}
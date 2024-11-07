package com.haohui.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button crearHabito;
    private ImageButton perfil;

    private MostrarHabitosRV adapter;
    private List<Habitos> listaHabitos;
    private RecyclerView recyclerView;

    private Switch modoOscuro;
    private boolean nigthMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



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

        getSupportActionBar().hide();
        modoOscuro = findViewById(R.id.switch1);

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nigthMode = sharedPreferences.getBoolean("night", false);
        if(nigthMode) {
            modoOscuro.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        modoOscuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nigthMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);
                }
                editor.apply();
            }
        });
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
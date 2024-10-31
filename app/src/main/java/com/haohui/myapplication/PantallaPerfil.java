package com.haohui.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class PantallaPerfil extends AppCompatActivity {

//    private List<Habitos> listaHabitos;
//
    private MostrarHabitosRV adapter;
    private List<Habitos> listaHabitos;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_perfil);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadHabitos();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    //    public PantallaPerfil(List<Habitos> listaHabitos) {
//        this.listaHabitos = listaHabitos;
//    }

        private void loadHabitos() {
            listaHabitos = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitosDao().getAllHabitos();
            HabitosDao habitosDao = DatabaseClient.getInstance(getApplicationContext()).getHabitoDatabase().habitosDao();
            adapter = new MostrarHabitosRV(listaHabitos, habitosDao, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
}
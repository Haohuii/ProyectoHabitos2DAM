package com.haohui.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private MostrarHabitosRV adapter;

    // Constructor que recibe el adaptador
    public SwipeToDeleteCallback(MostrarHabitosRV adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    // No necesitamos mover ítems, así que retornamos false
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    // Método que se llama cuando un ítem es deslizado
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        Context context = viewHolder.itemView.getContext();
        Log.d("piiiiiiiiii", position+"");
        if (direction == ItemTouchHelper.LEFT) {
            adapter.deleteItem(position); // Eliminar el ítem del adaptador
        } else {

            Intent intentAdd = new Intent(context, EditarHabito.class);
            intentAdd.putExtra("posicion", position);
            context.startActivity(intentAdd);

        }
    }
}

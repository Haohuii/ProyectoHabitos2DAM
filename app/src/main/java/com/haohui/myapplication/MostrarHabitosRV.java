package com.haohui.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MostrarHabitosRV extends RecyclerView.Adapter<MostrarHabitosRV.HabitosViewHolder> {
    private List<Habitos> listaHabitos;
    private HabitosDao habitosDao;
    Context context;

    public MostrarHabitosRV(List<Habitos> listaHabitos, HabitosDao habitosDao, Context context) {
        this.listaHabitos = listaHabitos;
        this.habitosDao = habitosDao;
        this.context = context;
    }




    @NonNull
    @Override
    public HabitosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.disenyo_lista, parent, false);
        return new HabitosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitosViewHolder holder, int position) {
        Habitos habitos = listaHabitos.get(position);
        holder.txtTitulo.setText(habitos.getTitulo());
        holder.txtFecha.setText(habitos.getFecha());
        holder.txtHora.setText(habitos.getHora());

        holder.txtTitulo.setChecked(habitos.isChecked());

        if (habitos.isChecked()) {
            holder.itemView.setBackgroundColor(Color.parseColor("#429E6A"));  // Si está marcado, se pone gris
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#A3A3A3"));  // Si no está marcado, se pone blanco
        }

        // Listener para cuando el CheckBox cambie de estado
        holder.txtTitulo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Actualizamos el estado del CheckBox en el objeto Habitos
            habitos.setChecked(isChecked);

            // Cambiar el color de fondo cuando el estado cambia
            if (isChecked) {
                holder.itemView.setBackgroundColor(Color.parseColor("#429E6A"));  // Cambia a gris claro si está marcado
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#A3A3A3"));  // Cambia a blanco si no está marcado
            }

            // Opcional: Si quieres guardar el cambio en la base de datos (si es necesario)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    habitosDao.update(habitos);  // Actualizar el estado en la base de datos
                }
            }).start();
        });
    }

    public void deleteItem(int position) {
        Habitos habito = listaHabitos.get(position);
        listaHabitos.remove(position); // Eliminar el ítem de la lista
        notifyItemRemoved(position); // Notificar al adaptador que un ítem ha sido eliminado
        Toast.makeText(context, "Habito elimiado", Toast.LENGTH_SHORT).show();

        // Eliminar el ítem de la base de datos
        new Thread(new Runnable() {
            @Override
            public void run() {
                habitosDao.delete(habito);
            }
        }).start();
    }

    public void updateItem(int position, Habitos habitosActualizados) {
        listaHabitos.set(position, habitosActualizados);
        notifyItemChanged(position);
    }


    @Override
    public int getItemCount() {
        return listaHabitos.size();
    }

    public static class HabitosViewHolder extends RecyclerView.ViewHolder {
        TextView txtFecha, txtHora;
        CheckBox txtTitulo;
;


        public HabitosViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.checkBoxTitulo);
            txtFecha = itemView.findViewById(R.id.txtFechaDL);
            txtHora = itemView.findViewById(R.id.txtHoraDL);


        }
    }

}

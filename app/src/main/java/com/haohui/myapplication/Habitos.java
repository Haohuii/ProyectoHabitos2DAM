package com.haohui.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Habitos {

    @PrimaryKey(autoGenerate = true) //para BD

    private int id;
    private String fecha;
    private String hora;
    private String titulo;
    private boolean isChecked;

    public Habitos(String fecha, String hora, String titulo) {
        this.fecha = fecha;
        this.hora = hora;
        this.titulo = titulo;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getTitulo() {
        return titulo;
    }


    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

}

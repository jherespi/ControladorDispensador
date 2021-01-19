package com.example.controladordispensador;

public class Dispensador {
    int num_usos;
    String nombre;

    public Dispensador(String nombre, int num_usos){
        this.nombre = nombre;
        this.num_usos = num_usos;
    }

    public String getNombre(){
        return this.nombre;
    }

    public int getNum_usos(){
        return this.num_usos;
    }

    public void setNum_usos(Integer num_usos){
        this.num_usos = num_usos;
    }
}

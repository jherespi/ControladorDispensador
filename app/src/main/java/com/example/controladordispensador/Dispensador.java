package com.example.controladordispensador;

public class Dispensador {
    int id, num_usos;
    String nombre;

    public Dispensador(int id, String nombre, int num_usos){
        this.id = id;
        this.nombre = nombre;
        this.num_usos = num_usos;
    }

    public int getId(){
        return id;
    }
    public String getNombre(){
        return nombre;
    }

    public int getNum_usos(){
        return num_usos;
    }

    public void setNum_usos(Integer num_usos){
        this.num_usos = num_usos;
    }
}

package com.example.controladordispensador;


public class Usuario {
    int id;
    String nombre;
    Dispensador dispensador;

    public Usuario(int id, String nombre, Dispensador dispensador){
        this.id = id;
        this.nombre = nombre;
        this.dispensador = dispensador;
    }

    public int getId(){
        return id;
    }

    public Dispensador getDispensador(){
        return this.dispensador;
    }

    public String getNombre(){
        return this.nombre;
    }
}

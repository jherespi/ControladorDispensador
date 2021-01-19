package com.example.controladordispensador;


public class Usuario {
    String nombre;
    Dispensador dispensador;

    public Usuario(String nombre, Dispensador dispensador){
        this.nombre = nombre;
        this.dispensador = dispensador;
    }

    public Dispensador getDispensador(){
        return this.dispensador;
    }

    public String getNombre(){
        return this.nombre;
    }
}

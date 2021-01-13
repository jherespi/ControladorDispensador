package com.example.controladordispensador;


public class Usuario {
    int id;
    Dispensador dispensador;

    public Usuario(int id, Dispensador dispensador){
        this.id = id;
        this.dispensador = dispensador;
    }

    public int getId(){
        return id;
    }

    public Dispensador getDispensador(){
        return dispensador;
    }
}

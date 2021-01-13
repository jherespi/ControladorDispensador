package com.example.controladordispensador;

public class Dispensador {
    int id;
    int num_max_usos, num_usos;

    public Dispensador(int id, int num_max_usos, int num_usos){
        this.id = id;
        this.num_max_usos = num_max_usos;
        this.num_usos = num_usos;
    }

    public int getId(){
        return id;
    }
    public int getNum_max_usos(){
        return num_max_usos;
    }

    public int getNum_usos(){
        return num_usos;
    }
}

package com.example.proyectodeclase;

public class Contacto {
    private int id;
    private String nombre;
    private String numero;

    public Contacto(int id, String nombre, String numero) {
        this.id = id;
        this.nombre = nombre;
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumero() {
        return numero;
    }
}

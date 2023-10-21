package com.example.pm2e19899.Models;

public class Personas
{
    private Integer id;
    private String ima;
    private String spin;
    private String nombre;
    private Integer telefono;
    private String nota;

    public Personas(Integer id, String ima, String spin, String nombre, Integer telefono, String nota) {
        this.id = id;
        this.ima = ima;
        this.spin = spin;
        this.nombre = nombre;
        this.telefono = telefono;
        this.nota = nota;
    }

    public Personas() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIma() {
        return ima;
    }

    public void setIma(String ima) {
        this.ima = ima;
    }

    public String getSpin() {
        return spin;
    }

    public void setSpin(String spin) {
        this.spin = spin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}

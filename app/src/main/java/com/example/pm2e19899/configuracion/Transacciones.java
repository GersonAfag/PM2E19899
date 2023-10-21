package com.example.pm2e19899.configuracion;

public class Transacciones
{
    // Nombre de la base de datos
    public static final String namedb = "AGATHSS8";

    //Tablas de la base de datos
    public static final String Tabla  = "personas";

    // Campos de la tabla
    public static final String id = "id";
    public static final String ima = "imagen";
    public static final String spin = "paises" ;
    public static final String nombre = "nombre";
    public static final String telefono = "telefono";
    public static final String nota = "nota";


    // Consultas de Base de datos
    //ddl
    public static final String CreateTablePersonas = "CREATE TABLE personas "+
            "( id INTEGER PRIMARY KEY AUTOINCREMENT, imagen TEXT, paises TEXT, nombre TEXT, " +
            "telefono INTEGER, nota TEXT )";

    public static final String DropTablePersonas  = "DROP TABLE IF EXISTS personas";

    //dml
    public static final String SelectTablePersonas = "SELECT * FROM " + Transacciones.Tabla;



}

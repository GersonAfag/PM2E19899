package com.example.pm2e19899;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pm2e19899.Models.Personas;
import com.example.pm2e19899.configuracion.SQLiteConexion;
import com.example.pm2e19899.configuracion.Transacciones;

import java.util.ArrayList;

public class ActividadLista extends AppCompatActivity {

    private Button atras, compartir, ver, eliminar, actualizar;

    SQLiteConexion conexion;
    ListView listView;
    ArrayList<Personas> listperson;
    ArrayList<String> ArregloPersonas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_lista);

        atras = (Button) findViewById(R.id.btnAtras);
        compartir = (Button) findViewById(R.id.btncompartir);
        ver = (Button) findViewById(R.id.btnver);
        eliminar = (Button) findViewById(R.id.btneliminar);
        actualizar = (Button) findViewById(R.id.btnactualizar);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActividadLista.this);
                builder.setMessage("¿Desea eliminar este contacto?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                            }
                        }).show();
            }
        });

        View.OnClickListener butonclick = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Class<?> actividad = null;

                if (view.getId() == R.id.btnAtras) {
                    actividad = MainActivity.class;
                }


                if(actividad != null)
                {
                    NoveActivity(actividad);
                }
            }
        };

        atras.setOnClickListener(butonclick);



        try
        {
            // Establecemos una conxion a base de datos
            conexion = new SQLiteConexion(this, Transacciones.namedb, null, 1);
            listView = (ListView) findViewById(R.id.listpersonas);
            GetPersons();

            ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1,ArregloPersonas);
            listView.setAdapter(adp);



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Obtener el elemento seleccionado en la lista
                    String selectedItem = (String) parent.getItemAtPosition(position);

                    String[] datos = selectedItem.split(" ");
                    String datoEspecifico = datos[2];

                    // Realizar alguna acción con el elemento seleccionado
                    Toast.makeText(getApplicationContext(), "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActividadLista.this);
                    builder.setTitle("Acción")
                            .setMessage("¿Deseas llamar a '" + datoEspecifico + "'?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getApplicationContext(), ActivityCall.class);
                                    startActivity(intent);

                                }

                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                }
            });






        }
        catch (Exception ex)
        {
            ex.toString();
        }
    }

    private void NoveActivity(Class<?> actividad)
    {
        Intent intent = new Intent(getApplicationContext(),actividad);
        startActivity(intent);
    }

    private void GetPersons()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas person = null;
        listperson = new ArrayList<Personas>();

        Cursor cursor = db.rawQuery(Transacciones.SelectTablePersonas,null);
        while(cursor.moveToNext())
        {
            person = new Personas();
            person.setId(cursor.getInt(0));
            person.setIma(cursor.getString(1));
            person.setSpin(cursor.getString(2));
            person.setNombre(cursor.getString(3));
            person.setTelefono(cursor.getInt(4));
            person.setNota(cursor.getString(5));


            listperson.add(person);
        }

        cursor.close();
        FillList();
    }

    private void FillList()
    {
        ArregloPersonas = new ArrayList<String>();

        for(int i = 0; i < listperson.size(); i++)
        {
            ArregloPersonas.add(listperson.get(i).getId() + " - " +
                    listperson.get(i).getSpin() + " - " +
                    listperson.get(i).getNombre() + " - " +
                    listperson.get(i).getTelefono() + " - " +
                    listperson.get(i).getNota());
        }
    }
}
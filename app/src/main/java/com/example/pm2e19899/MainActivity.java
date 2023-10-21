package com.example.pm2e19899;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm2e19899.configuracion.SQLiteConexion;
import com.example.pm2e19899.configuracion.Transacciones;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ImageView ima;
    private Spinner spin;
    private EditText nombre, telefono, nota;
    private Button salvar, lista;

    static final int peticion_captura_imagen = 101;
    static final int peticion_acceso_camara = 102;
    String currentPhotoPath;
    ImageView Objetoimagen;
    Button btntomarfoto;
    String pathfoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ima = (ImageView) findViewById(R.id.imagen);
        spin = (Spinner) findViewById(R.id.spinner1);
        nombre = (EditText) findViewById(R.id.txtnombre);
        telefono = (EditText) findViewById(R.id.txttelefono);
        nota = (EditText) findViewById(R.id.txtnota);
        salvar = (Button) findViewById(R.id.btnsalvar);
        lista = (Button) findViewById(R.id.btnlist);


        View.OnClickListener butonclick = new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Class<?> actividad = null;

                if (view.getId() == R.id.btnlist) {
                    actividad = ActividadLista.class;
                }


                if(actividad != null)
                {
                    NoveActivity(actividad);
                }
            }
        };

        lista.setOnClickListener(butonclick);



        String [] paises = {"Honduras(504)", "Costa Rica", "Guatemala(502)", "El Salvador"};
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paises);
        spin.setAdapter(adapter);

        Objetoimagen = (ImageView) findViewById(R.id.imagen);
        btntomarfoto = (Button) findViewById(R.id.btnAgimg);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txtnombre = nombre.getText().toString();
                String txttelefono = telefono.getText().toString();
                String txtnota = nota.getText().toString();

                if (txtnombre.isEmpty()||txttelefono.isEmpty()||txtnota.isEmpty()) {

                    if(txtnombre.isEmpty()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Campo del Nombre vacío")
                                .setMessage("Por favor, ingrese algún dato en el campo.")
                                .setPositiveButton("Aceptar", null)
                                .show();

                    }else if(txttelefono.isEmpty()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Campo telefono vacío")
                                .setMessage("Por favor, ingrese algún dato en el campo.")
                                .setPositiveButton("Aceptar", null)
                                .show();
                    } else if (txtnota.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Campo notas vacío")
                                .setMessage("Por favor, ingrese algún dato en el campo.")
                                .setPositiveButton("Aceptar", null)
                                .show();

                    }

                } else {
                    AddPerson();
                }


            }
        });




        btntomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                permisos();
            }
        });

    }

    private void permisos()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},peticion_acceso_camara);
        }
        else
        {
            dispatchTakePictureIntent();

        }
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );


        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {


            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.pm2e19899.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, peticion_captura_imagen);
            }
        }
    }



    private void NoveActivity(Class<?> actividad)
    {
        Intent intent = new Intent(getApplicationContext(),actividad);
        startActivity(intent);
    }

    private void AddPerson()
    {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.namedb,null,1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombre, nombre.getText().toString());
        valores.put(Transacciones.spin, spin.getSelectedItem().toString());
        valores.put(Transacciones.telefono, telefono.getText().toString());
        valores.put(Transacciones.nota, nota.getText().toString());


        Long result = db.insert(Transacciones.Tabla, Transacciones.id, valores);
        Toast.makeText(getApplicationContext(), "Registro ingresado : " + result.toString(),Toast.LENGTH_LONG ).show();

        db.close();

        CleanScreen();

    }

    private void CleanScreen()
    {
        nombre.setText("");
        telefono.setText("");
        nota.setText("");
        spin.setSelection(0);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == peticion_acceso_camara )
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED )
            {
                dispatchTakePictureIntent();

            }
            else
            {
                Toast.makeText(getApplicationContext(), "Solicitamos permiso para ingresara a la camara", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void TomarFoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(intent.resolveActivity(getPackageManager())!= null)
        {
            startActivityForResult(intent, peticion_captura_imagen );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode  == peticion_captura_imagen)
        {


            try {
                File foto = new File(currentPhotoPath);
                Objetoimagen.setImageURI(Uri.fromFile(foto));
            }
            catch (Exception ex)
            {
                ex.toString();
            }

        }
    }

}
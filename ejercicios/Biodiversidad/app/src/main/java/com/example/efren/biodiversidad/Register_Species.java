package com.example.efren.biodiversidad;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Register_Species extends AppCompatActivity {


    private EditText Nombre, Latitud, Longitud, Cantidad;
    private ImageButton Foto;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_SELECT_PHOTO = 0;
    private String path;
    private StringRequest stringRequest;
    private RequestQueue request;
    private Bitmap myBitmap;
    private LocationManager locationManager;
    protected Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__species);

        Nombre = (EditText) findViewById(R.id.Nombre_especie);
        Latitud = (EditText) findViewById(R.id.Latitud_foto);
        Longitud = (EditText) findViewById(R.id.Longitud_foto);
        Cantidad = (EditText) findViewById(R.id.Cantidad_especies);
        Foto = (ImageButton) findViewById(R.id.Foto);
        Foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Opciones();
            }
        });

        request = Volley.newRequestQueue(Register_Species.this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Agregar:
                if (Validar_registro()){
                    if (myBitmap != null) {
                        CargarServicio();
                    }else{
                        Toast.makeText(this, "Favor de cargar o tomar una fotografía", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean Validar_registro(){
        if (
                !Nombre.getText().toString().equals("") &&
                !Latitud.getText().toString().equals("") &&
                !Longitud.getText().toString().equals("") &&
                !Cantidad.getText().toString().equals("")) {
            return true;
        }
        return false;
    }

    public void Opciones(){
        final CharSequence [] opciones = {"Tomar foto", "Elegir de galería", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Elige una opción");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (opciones[which].equals("Tomar foto")) {
                    abrirCamara();
                } else if (opciones[which].equals("Elegir de galería")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/");
                    startActivityForResult(intent.createChooser(intent, "Seleccione la aplicación"), REQUEST_SELECT_PHOTO);
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_SELECT_PHOTO) {
                Uri pat = data.getData();
                //Foto.setImageURI(pat);
                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(),pat);
                    Foto.setImageBitmap(myBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == REQUEST_CAMERA){
                MediaScannerConnection.scanFile(this, new String[]{path}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                    @Override
                    public void onMediaScannerConnected() {

                    }

                    @Override
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });

                myBitmap = BitmapFactory.decodeFile(path);
                Foto.setImageBitmap(myBitmap);


            }
        }
    }

    private void abrirCamara(){
        File fileImagen = new File(Environment.getExternalStorageDirectory(), "Biodiversidad_Fotos");
        boolean existe = fileImagen.exists();
        String nombreImagen = "";

        if (existe == false){
            existe = fileImagen.mkdirs();
        }
        if(existe){
            nombreImagen = "IMG_" + (System.currentTimeMillis()/1000) + ".jpg";
        }
        path = Environment.getExternalStorageDirectory() + File.separator + "Biodiversidad_Fotos" + File.separator + nombreImagen;
        Uri pathUri = FileProvider.getUriForFile(this,"com.example.efren.biodiversidad", new File(path));
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pathUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, "Tomar foto"), REQUEST_CAMERA);
    }

    private void CargarServicio(){

        String url = "http://biodiversidad.alcohomeapp.com.mx/webService_registro_especie_android.php";

        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            //Recibe la respuesta del servicio
            @Override
            public void onResponse(String response) {
                if  (response.trim().equalsIgnoreCase("ok")){
                    Toast.makeText(Register_Species.this, "Información cargada con exito", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }else{
                    Toast.makeText(Register_Species.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            //Recibe los errores generados
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register_Species.this, error.toString(), Toast.LENGTH_SHORT).show();
                //error.printStackTrace();
            }
        }
        ){
            //Envia los parámetros por post
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String id_user = getIntent().getStringExtra("Id");
                String nombre = Nombre.getText().toString();
                String latitud = Latitud.getText().toString();
                String longitud = Longitud.getText().toString();
                String cantidad = Cantidad.getText().toString();
                String foto = ConvertirImgString(myBitmap);

                Map<String, String> parametros = new HashMap<>();
                parametros.put("id_user", id_user);
                parametros.put("nombre", nombre);
                parametros.put("latitud", latitud);
                parametros.put("longitud", longitud);
                parametros.put("cantidad", cantidad);
                parametros.put("imagen", foto);
                return parametros;
            }
        };
        request.add(stringRequest);
    }

    private String ConvertirImgString(Bitmap myBitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, array);
        byte [] imagebyte = array.toByteArray();
        String ImageString = Base64.encodeToString(imagebyte,Base64.DEFAULT);
    return ImageString;
    }
}
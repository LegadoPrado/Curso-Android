package com.example.efren.biodiversidad;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class Show_Information extends AppCompatActivity {

    private ImageButton Foto_SI;
    private TextView Nombre, Latitud, Longitud, Cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__information);

        Foto_SI = (ImageButton) findViewById(R.id.MImagen);
        Nombre = (TextView) findViewById(R.id.Nombre_SI);
        Latitud = (TextView) findViewById(R.id.lat_SI);
        Longitud = (TextView) findViewById(R.id.long_SI);
        Cantidad = (TextView) findViewById(R.id.cantidad_SI);
        String id = getIntent().getStringExtra("Id");

        Picasso.with(this)
                .load("http://biodiversidad.alcohomeapp.com.mx/img/" + id + ".png")
                .error(R.drawable.ic_launcher_background)
                .fit()
                .into(Foto_SI);

        Nombre.setText(getIntent().getStringExtra("nombre"));
        Latitud.setText("Latitud: " + getIntent().getStringExtra("lat"));
        Longitud.setText("Longitud: " + getIntent().getStringExtra("long"));
        Cantidad.setText("Cantidad de avistamientos: " + getIntent().getStringExtra("cantidad"));
    }
}
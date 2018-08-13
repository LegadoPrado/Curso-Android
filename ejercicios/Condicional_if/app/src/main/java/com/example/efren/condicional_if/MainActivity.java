package com.example.efren.condicional_if;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaración de variables globales
    ImageButton Orange, Carrot, Apple, Banana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Unión de los id del xml con variables de la clase java
        Orange = (ImageButton) findViewById(R.id.Orange);
        Orange.setOnClickListener(this);
        Apple = (ImageButton) findViewById(R.id.Apple);
        Apple.setOnClickListener(this);
        Carrot = (ImageButton) findViewById(R.id.Carrot);
        Carrot.setOnClickListener(this);
        Banana = (ImageButton) findViewById(R.id.Banana);
        Banana.setOnClickListener(this);
    }

    //Evento click del botón el cual ejecuta el condicional if
    @Override
    public void onClick(View v) {
        if (v == Orange)
            //Mensaje mostrado en la pantalla para el usuario
            Toast.makeText(this, "Naranja", Toast.LENGTH_SHORT).show();
        if (v == Apple)
            //Mensaje mostrado en la pantalla para el usuario
            Toast.makeText(this, "Manzana", Toast.LENGTH_SHORT).show();
        if (v == Carrot)
            //Mensaje mostrado en la pantalla para el usuario
            Toast.makeText(this, "Zanahoria", Toast.LENGTH_SHORT).show();
        if (v == Banana)
            //Mensaje mostrado en la pantalla para el usuario
            Toast.makeText(this, "Platano", Toast.LENGTH_SHORT).show();
    }
}

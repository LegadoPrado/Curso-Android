package com.example.efren.condicional_if_else;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaración de variables globales
    RadioButton A, B, C, D;
    Button Responder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Unión de los id del xml con variables de la clase java
        A = (RadioButton) findViewById(R.id.A);
        B = (RadioButton) findViewById(R.id.B);
        C = (RadioButton) findViewById(R.id.C);
        D = (RadioButton) findViewById(R.id.D);
        Responder = (Button) findViewById(R.id.boton);

        //Evento click del botón el cual ejecuta el condicional if-else
        Responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (B.isChecked() == true)
                    //Mensaje mostrado en la pantalla para el usuario
                    Toast.makeText(MainActivity.this, "Correcto", Toast.LENGTH_SHORT).show();
                else
                    //Mensaje mostrado en la pantalla para el usuario
                    Toast.makeText(MainActivity.this, "Incorrecto", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

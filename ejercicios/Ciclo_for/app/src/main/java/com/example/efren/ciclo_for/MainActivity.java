package com.example.efren.ciclo_for;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Declaración de variables globales
    EditText numero;
    TextView imprime;
    Button inicia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Unión de los id del xml con variables de la clase java
        numero = (EditText) findViewById(R.id.Numero);
        imprime = (TextView) findViewById(R.id.imprime);
        inicia = (Button) findViewById(R.id.iniciar);

        //Evento click del botón el cual ejecuta el ciclo for
        inicia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Variable que acumulara los numeros dentro del ciclo for
                String acumulado = "";

                for (int i = Integer.parseInt(numero.getText().toString()); i <= ( Integer.parseInt(numero.getText().toString()) + 100); i++){
                    acumulado += String.valueOf(i) + ",";
                }

                //Impresión de la variable acumuladora en el TextView
                imprime.setText(acumulado);
            }
        });
    }
}

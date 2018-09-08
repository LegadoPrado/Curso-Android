package com.example.efren.ciclo_do_while;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaración de variables globales
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Unión de los id del xml con variables de la clase java
        button = (Button) findViewById(R.id.button);


        //Impresión del problema en el EditText
        textView.setText(
                "Los padres de una niña le prometieron comenzar un ahorro para ella en el banco con 10 pesos,\n" +
                        "cuando cumpliera 12 años de edad y duplicar la cantidad en cada cumpleaños subsiguiente,\n" +
                        "hasta que la niña cumpliera 18 años. \n" +
                        "Escriba un programa para determinar la cantidad total depositada a la cuenta de la niña una vez cumpla los 18 años.\n");

        //Evento click del botón el cual ejecuta el ciclo do-while
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int edad = 12, ahorro = 10;

                do{
                    ahorro += (ahorro*2);
                    edad++;
                }while (edad != 18);

                //Mensaje mostrado en la pantalla para el usuario
                Toast.makeText(MainActivity.this, "Al cumplir 18 años la niña tiene un ahorro de $" + ahorro + " en su cuenta de banco.", Toast.LENGTH_LONG).show();
            }
        });
    }
}

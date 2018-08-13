package com.example.efren.ciclo_while;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Declaración de variables globales
    EditText editText;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Unión de los id del xml con variables de la clase java
        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        //Evento click del botón el cual ejecuta el ciclo while
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Variable que toma el valor introducido por el usuario en el EditText
                int i = Integer.parseInt(editText.getText().toString());
                //Variable que acumulara los numeros dentro del ciclo while
                String acumulado = "";

                while (i <= (Integer.parseInt(editText.getText().toString()) + 100)){
                    acumulado += i + ",";
                    i++;
                }

                //Impresión de la variable acumuladora en el TextView
                textView.setText(acumulado);
            }
        });
    }
}

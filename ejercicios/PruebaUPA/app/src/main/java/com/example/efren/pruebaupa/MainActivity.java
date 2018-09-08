package com.example.efren.pruebaupa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button boton;
    TextView textView;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton=(Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView3);
        editText2=(EditText) findViewById(R.id.editText2);
        final String lista[]={"1. bebito", "2. güerito", "3. angelito"};
        for(int i=0; i<=2; i++){
            textView.append(lista[i]+"\n");
        }
        boton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                int numero = Integer.parseInt(editText2.getText().toString());
                 if(numero==1) {
                    lista[numero - 1] = "";
                }


            }
        });
    }
}

package com.example.efren.condicional_if_elseif_else;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.Numero);
        textView = (TextView) findViewById(R.id.Lista);
        button = (Button) findViewById(R.id.Comprar);

        final String [] lista = {"1.- Huevo", "2.- Papel", "3.- Arroz", "4.- Pan"};

        textView.setText(lista[0] + "\n" + lista[1] + "\n" + lista[2] + "\n" + lista[3]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("1")){
                    lista[0] = "";
                    textView.setText(lista[0] + "\n" + lista[1] + "\n" + lista[2] + "\n" + lista[3]);
                    editText.setText("");
                }else if (editText.getText().toString().equals("2")){
                    lista[1] = "";
                    textView.setText(lista[0] + "\n" + lista[1] + "\n" + lista[2] + "\n" + lista[3]);
                    editText.setText("");
                }else if(editText.getText().toString().equals("3")){
                    lista[2] = "";
                    textView.setText(lista[0] + "\n" + lista[1] + "\n" + lista[2] + "\n" + lista[3]);
                    editText.setText("");
                }else if (editText.getText().toString().equals("4")){
                    lista[3] = "";
                    textView.setText(lista[0] + "\n" + lista[1] + "\n" + lista[2] + "\n" + lista[3]);
                    editText.setText("");
                }else{
                    Toast.makeText(MainActivity.this, "Esa opción no esta disponible", Toast.LENGTH_SHORT).show();
                }if (!lista[0].equals("") && !lista[1].equals("") && !lista[2].equals("") && !lista[3].equals("")){
                    Toast.makeText(MainActivity.this, "Aún no has finalizado las compras", Toast.LENGTH_SHORT).show();
                }else if(lista[0].equals("") && lista[1].equals("") && lista[2].equals("") && lista[3].equals("")){
                    Toast.makeText(MainActivity.this, "Has finalizado las compras", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


    }
}

package com.example.efren.consumo_de_servicios;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Declaración de variables
    private Button Descarga, CSV;
    private TextView Texto;
    private EditText URLT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enlace de variables con la interfaz gráfica
        Descarga = (Button) findViewById(R.id.Descarga);
        Descarga.setOnClickListener(this);
        CSV = (Button) findViewById(R.id.CSV);
        CSV.setOnClickListener(this);
        URLT = (EditText) findViewById(R.id.URL);
        Texto = (TextView) findViewById(R.id.Texto);
    }

    //Método OnClick el cual detecta el control que fue presionado
    @Override
    public void onClick(View v) {
        //Comparación del ID recibido con variables existentes
        if (v == Descarga){
            //Ejecución de hilo para la descarga del servicio web
            new DescargarServicio().execute("http://" + URLT.getText().toString());
        }if (v == CSV){

        }
    }

    //Hilo encargado de descargar el servicio web
    private class DescargarServicio extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

            try {
                //Variable que contiene el String de la URL
                URL url = new URL(strings[0]);
                //Variable encargada de realizar la conección a la URL otorgada
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                //Variable encargada de leer el contenido en bytes de la página
                InputStream inputStream = httpURLConnection.getInputStream();
                //Variable utilizada para leer linea por linea del contenido del servicio
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();

                String Lineas = "";

                //Lectura de lineas y agregado a variable StringBuffer
                while ((Lineas = bufferedReader.readLine()) != null){
                    stringBuffer.append(Lineas);
                }

                //Vaciado de contenido a Variable String para su impreción o manipulación
                String Contenido = stringBuffer.toString();

                return Contenido;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //Método que recibe el resultado del método doInBackground
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Impresión de contenido en TextView
            Texto.setText(s);
        }
    }
}
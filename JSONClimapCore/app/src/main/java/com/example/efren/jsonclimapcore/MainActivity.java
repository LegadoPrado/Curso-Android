package com.example.efren.jsonclimapcore;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    //Creación de Objetos para el almacenamiento de los registros,
    //uno por archivo a descargar
    private ArrayList<Objeto_Variables_WRF> Dia1 = new ArrayList<Objeto_Variables_WRF>();
    private ArrayList<Objeto_Variables_WRF> Dia2 = new ArrayList<Objeto_Variables_WRF>();
    private ArrayList<Objeto_Variables_WRF> Dia3 = new ArrayList<Objeto_Variables_WRF>();
    private ArrayList<Objeto_Variables_WRF> Dia4 = new ArrayList<Objeto_Variables_WRF>();
    private ArrayList<Objeto_Variables_WRF> Dia5 = new ArrayList<Objeto_Variables_WRF>();
    //***********************************************************

    //Creación de variables para el enlace con el layout
    private Button Descargar;
    //**************************************************

    //Creación de variables a utilizar en el proyecto
    ProgressDialog progressDialog;
    private int cont = 0;
    private boolean estadoActivity = true;
    private String FechaServidor = "";
    private String CadenaDias;
    private String [] ArrayDiasPosteriores;
    //***********************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enlace de variables con el control del layout
        Descargar = (Button) findViewById(R.id.Descargar);
        Descargar.setOnClickListener(this);
    }

    //Método que detecta cuando la aplicación se minimiza
    @Override
    protected void onStop() {
        super.onStop();
        estadoActivity = false;
    }

    //Método que detecta cuando la aplicación se inicia
    //después de estar minimizada
    @Override
    protected void onStart() {
        super.onStart();
        estadoActivity = true;
    }

    @Override
    public void onClick(View v) {
        if (v == Descargar){
            new FechaServer().execute();
        }
    }

    /*Descarga de los archivos por hilos en segundo plano uno por uno, comenzando
      la nueva descarga al terminar el pasado.*/
    private class Hilo1 extends AsyncTask<String,Integer,List<Objeto_Variables_WRF>>{
        public Hilo1(ProgressDialog progressDialog) {
        }

        @Override
        protected List<Objeto_Variables_WRF> doInBackground(String... param) {
            try {
                URL url = new URL(param[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();

                int tamanoTotal = inputStream.toString().length();
                System.out.println("Tamaño" + tamanoTotal);
                String lineas;
                cont = 0;

                while ((lineas = bufferedReader.readLine()) != null) {
                        stringBuffer.append(lineas + ",");
                        cont += 1;
                    publishProgress((cont * 100) / 46300);
                }

                System.out.println("Contador de filas: " + cont);
                String TextoPlano = stringBuffer.toString().replace(" ", "");
                System.out.println("Texto plano: " + TextoPlano);

                double [][] Matriz = new double[cont][18];
                String [] Vector = TextoPlano.split(",");
                int incremento = 18;

                for (int i = 1; i < cont; i++){
                    for (int j = 0; j < 18; j++){
                        Matriz[i][j] = Double.parseDouble(Vector[incremento]);
                        incremento++;
                    }
                }
                System.out.println(Matriz[1][0]);

                for (int i = 0; i < cont; i++){
                        Dia1.add(new Objeto_Variables_WRF(
                                Matriz[i][0],
                                Matriz[i][1],
                                Matriz[i][2],
                                Matriz[i][3],
                                Matriz[i][4],
                                Matriz[i][5],
                                Matriz[i][6],
                                Matriz[i][7],
                                Matriz[i][8],
                                Matriz[i][9],
                                Matriz[i][10],
                                Matriz[i][11],
                                Matriz[i][12],
                                Matriz[i][13],
                                Matriz[i][14],
                                Matriz[i][15],
                                Matriz[i][16],
                                Matriz[i][17]));
                }

                return Dia1;
            } catch (MalformedURLException e) {
                //Error en la URL
                e.printStackTrace();
            } catch (IOException e) {
                //Errores en la conexión
                e.printStackTrace();

            } finally {

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0] == progressDialog.getMax()){
                progressDialog.setTitle("Procesando descarga");
                progressDialog.setMessage("Procesando...");
            }
            progressDialog.setIndeterminate(false);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(List<Objeto_Variables_WRF> result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            new Hilo2(Progreso(2)).execute(URL(2));
        }
    }

    private class Hilo2 extends AsyncTask<String,Integer,List<Objeto_Variables_WRF>>{

        public Hilo2(ProgressDialog progressDialog) {
        }

        @Override
        protected List<Objeto_Variables_WRF> doInBackground(String... param) {
            try {
                URL url = new URL(param[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();

                String lineas;
                cont = 0;

                while ((lineas = bufferedReader.readLine()) != null) {
                    stringBuffer.append(lineas + ",");
                    cont += 1;
                    publishProgress((cont * 100) / 46300);
                }

                System.out.println("Contador de filas: " + cont);
                String TextoPlano = stringBuffer.toString().replace(" ", "");
                System.out.println("Texto plano: " + TextoPlano);

                double [][] Matriz = new double[cont][18];
                String [] Vector = TextoPlano.split(",");
                int incremento = 18;

                for (int i = 1; i < cont; i++){
                    for (int j = 0; j < 18; j++){
                        Matriz[i][j] = Double.parseDouble(Vector[incremento]);
                        incremento++;
                    }
                }
                System.out.println(Matriz[1][0]);

                for (int i = 0; i < cont; i++){
                    Dia2.add(new Objeto_Variables_WRF(
                            Matriz[i][0],
                            Matriz[i][1],
                            Matriz[i][2],
                            Matriz[i][3],
                            Matriz[i][4],
                            Matriz[i][5],
                            Matriz[i][6],
                            Matriz[i][7],
                            Matriz[i][8],
                            Matriz[i][9],
                            Matriz[i][10],
                            Matriz[i][11],
                            Matriz[i][12],
                            Matriz[i][13],
                            Matriz[i][14],
                            Matriz[i][15],
                            Matriz[i][16],
                            Matriz[i][17]));
                }

                return Dia2;
            } catch (MalformedURLException e) {
                //Error en la URL
                e.printStackTrace();
            } catch (IOException e) {
                //Errores en la conexión
                e.printStackTrace();

            } finally {

            }
            return null;
        }
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0] == progressDialog.getMax()){
                progressDialog.setTitle("Procesando descarga");
                progressDialog.setMessage("Procesando...");
            }
            progressDialog.setIndeterminate(false);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(List<Objeto_Variables_WRF> result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            cont = 0;
            new Hilo3(Progreso(3)).execute(URL(3));
        }
    }

    private class Hilo3 extends AsyncTask<String,Integer,List<Objeto_Variables_WRF>>{

        public Hilo3(ProgressDialog progressDialog) {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Objeto_Variables_WRF> doInBackground(String... param) {
            try {
                URL url = new URL(param[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();

                String lineas;
                cont = 0;

                while ((lineas = bufferedReader.readLine()) != null) {
                    stringBuffer.append(lineas + ",");
                    cont += 1;
                    publishProgress((cont * 100) / 46300);
                }

                System.out.println("Contador de filas: " + cont);
                String TextoPlano = stringBuffer.toString().replace(" ", "");

                double [][] Matriz = new double[cont][18];
                String [] Vector = TextoPlano.split(",");
                int incremento = 18;

                for (int i = 1; i < cont; i++){
                    for (int j = 0; j < 18; j++){
                        Matriz[i][j] = Double.parseDouble(Vector[incremento]);
                        incremento++;
                    }
                }
                for (int i = 0; i < cont; i++){
                    Dia3.add(new Objeto_Variables_WRF(
                            Matriz[i][0],
                            Matriz[i][1],
                            Matriz[i][2],
                            Matriz[i][3],
                            Matriz[i][4],
                            Matriz[i][5],
                            Matriz[i][6],
                            Matriz[i][7],
                            Matriz[i][8],
                            Matriz[i][9],
                            Matriz[i][10],
                            Matriz[i][11],
                            Matriz[i][12],
                            Matriz[i][13],
                            Matriz[i][14],
                            Matriz[i][15],
                            Matriz[i][16],
                            Matriz[i][17]));
                }

                return Dia3;
            } catch (MalformedURLException e) {
                //Error en la URL
                e.printStackTrace();
            } catch (IOException e) {
                //Errores en la conexión
                e.printStackTrace();

            } finally {

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0] == progressDialog.getMax()){
                progressDialog.setTitle("Procesando descarga");
                progressDialog.setMessage("Procesando...");
            }
            progressDialog.setIndeterminate(false);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(List<Objeto_Variables_WRF> result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            new Hilo4(Progreso(4)).execute(URL(4));
        }
    }

    private class Hilo4 extends AsyncTask<String,Integer,List<Objeto_Variables_WRF>>{

        public Hilo4(ProgressDialog progressDialog) {
        }
        @Override
        protected List<Objeto_Variables_WRF> doInBackground(String... param) {
            try {
                URL url = new URL(param[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();

                String lineas;
                cont = 0;

                while ((lineas = bufferedReader.readLine()) != null) {
                    stringBuffer.append(lineas + ",");
                    cont += 1;
                    publishProgress((cont * 100) / 46300);
                }

                System.out.println("Contador de filas: " + cont);
                String TextoPlano = stringBuffer.toString().replace(" ", "");
                System.out.println("Texto plano: " + TextoPlano);

                double [][] Matriz = new double[cont][18];
                String [] Vector = TextoPlano.split(",");
                int incremento = 18;

                for (int i = 1; i < cont; i++){
                    for (int j = 0; j < 18; j++){
                        Matriz[i][j] = Double.parseDouble(Vector[incremento]);
                        incremento++;
                    }
                }
                System.out.println(Matriz[1][0]);

                for (int i = 0; i < cont; i++){
                    Dia4.add(new Objeto_Variables_WRF(
                            Matriz[i][0],
                            Matriz[i][1],
                            Matriz[i][2],
                            Matriz[i][3],
                            Matriz[i][4],
                            Matriz[i][5],
                            Matriz[i][6],
                            Matriz[i][7],
                            Matriz[i][8],
                            Matriz[i][9],
                            Matriz[i][10],
                            Matriz[i][11],
                            Matriz[i][12],
                            Matriz[i][13],
                            Matriz[i][14],
                            Matriz[i][15],
                            Matriz[i][16],
                            Matriz[i][17]));
                }

                return Dia4;
            } catch (MalformedURLException e) {
                //Error en la URL
                e.printStackTrace();
            } catch (IOException e) {
                //Errores en la conexión
                e.printStackTrace();

            } finally {

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0] == progressDialog.getMax()){
                progressDialog.setTitle("Procesando descarga");
                progressDialog.setMessage("Procesando...");
            }
            progressDialog.setIndeterminate(false);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(List<Objeto_Variables_WRF> result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            cont = 0;
            new Hilo5(Progreso(5)).execute(URL(5));
        }
    }

    private class Hilo5 extends AsyncTask<String,Integer,List<Objeto_Variables_WRF>>{

        public Hilo5(ProgressDialog progressDialog) {
        }

        @Override
        protected List<Objeto_Variables_WRF> doInBackground(String... param) {
            try {
                URL url = new URL(param[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();

                String lineas;
                cont = 0;

                while ((lineas = bufferedReader.readLine()) != null) {
                    stringBuffer.append(lineas + ",");
                    cont += 1;
                    publishProgress((cont * 100) / 46300);
                }

                System.out.println("Contador de filas: " + cont);
                String TextoPlano = stringBuffer.toString().replace(" ", "");
                System.out.println("Texto plano: " + TextoPlano);

                double [][] Matriz = new double[cont][18];
                String [] Vector = TextoPlano.split(",");
                int incremento = 18;

                for (int i = 1; i < cont; i++){
                    for (int j = 0; j < 18; j++){
                        Matriz[i][j] = Double.parseDouble(Vector[incremento]);
                        incremento++;
                    }
                }
                System.out.println(Matriz[1][0]);

                for (int i = 0; i < cont; i++){
                    Dia5.add(new Objeto_Variables_WRF(
                            Matriz[i][0],
                            Matriz[i][1],
                            Matriz[i][2],
                            Matriz[i][3],
                            Matriz[i][4],
                            Matriz[i][5],
                            Matriz[i][6],
                            Matriz[i][7],
                            Matriz[i][8],
                            Matriz[i][9],
                            Matriz[i][10],
                            Matriz[i][11],
                            Matriz[i][12],
                            Matriz[i][13],
                            Matriz[i][14],
                            Matriz[i][15],
                            Matriz[i][16],
                            Matriz[i][17]));
                }

                return Dia5;
            } catch (MalformedURLException e) {
                //Error en la URL
                e.printStackTrace();
            } catch (IOException e) {
                //Errores en la conexión
                e.printStackTrace();

            } finally {

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0] == progressDialog.getMax()){
                progressDialog.setTitle("Procesando descarga");
                progressDialog.setMessage("Procesando...");
            }
            progressDialog.setIndeterminate(false);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(List<Objeto_Variables_WRF> result) {
            super.onPostExecute(result);
            System.out.println("Todos los archivos han sido descargados con éxito");
            progressDialog.dismiss();
            cont = 0;
            Toast.makeText(MainActivity.this, "Todos los archivos han sido descargados con éxito", Toast.LENGTH_SHORT).show();
            if (estadoActivity == false) {

            }
            Intent intent = new Intent(MainActivity.this, Mapa.class);
            startActivity(intent);
        }
    }
    //***************************************************************************

    //Método para descargar la fecha del servidor
    private class FechaServer extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String Fecha = "";
            try {
                URL url = new URL("http://pdiarios.alcohomeapp.com.mx/dia.php");
                HttpURLConnection httpURLConnection = null;
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = null;
                inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                Fecha = bufferedReader.readLine();
                System.out.println("Fecha del servidor: " + Fecha);
                return Fecha;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            FechaServidor = s;
            CadenaDias = (Entregar4Dias(DescomponerFecha(s)));
            ArrayDiasPosteriores = CadenaDias.split("/");
            new Hilo1(Progreso(1)).execute(URL(1));
        }
    }

    //Función para la carga de la URL, recibe como parámetro
    //un entero que representa el nombre del txt a descargar
    private String URL (int Dia){
        String URL = "";
             URL =
                    "http://pdiarios.alcohomeapp.com.mx/Docs/"+ FechaServidor + "/d" + Dia + ".txt";
        return URL;
    }
    //******************************************************

    //Funcion para crear una animación de progreso, recibe como
    //parámetro un número entero el cual representa el nombre
    //del archivo que se encuentra en progreso de descarga.
    private ProgressDialog Progreso(int Numero){

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Descarga");
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Descargando " + ArrayDiasPosteriores[Numero - 1] + " ...");
        progressDialog.show();
        return progressDialog;
    }

    //Funcion que descompone la fecha recibida del servidor con formato AAAA-MM-DD
    private int [] DescomponerFecha(String Fechaservidor){
        String [] FechaVector = Fechaservidor.split("-");
        int [] Fecha = new int[3];
        Fecha[0] = Integer.parseInt(FechaVector[0]);
        Fecha[1] = Integer.parseInt(FechaVector[1]);
        Fecha[2] = Integer.parseInt(FechaVector[2]);
        return Fecha;
    }

    //Función que genera 4 fechas posteriores a la otorgada por el
    //servidor, al igual que valida año bisiesto meses con 31 y 30
    //días.
    private String Entregar4Dias(int [] Fecha){
        String FechaUnida = "";
        boolean bisiesto = false;

        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        if (Fecha[1] < 10) {
            FechaUnida = Fecha[0] + "-0" + Fecha[1] + "-" + Fecha[2] + "/";
        }else{
            FechaUnida = Fecha[0] + "-" + Fecha[1] + "-" + Fecha[2] + "/";
        }

        for (int i = 0; i < 4; i++) {
            if (gregorianCalendar.isLeapYear(Fecha[0])) {
                bisiesto = true;
            }
                if (Fecha[1] == 12 && Fecha[2] == 31) {
                    Fecha[0] = Fecha[0] + 1;
                    Fecha[1] = 1;
                    Fecha[2] = 1;
                    if (Fecha[1] < 10) {
                        FechaUnida += Fecha[0] + "-0" + Fecha[1] + "-" + Fecha[2] + "/";
                    }else{
                        FechaUnida += Fecha[0] + "-" + Fecha[1] + "-" + Fecha[2] + "/";
                    }
                } else if (Fecha[1] == 1 || Fecha[1] == 3 || Fecha[1] == 5 || Fecha[1] == 7 || Fecha[1] == 8
                        || Fecha[1] == 10 && Fecha[2] == 31) {
                    Fecha[1] = Fecha[1] + 1;
                    Fecha[2] = 1;
                    if (Fecha[1] < 10) {
                        FechaUnida += Fecha[0] + "-0" + Fecha[1] + "-" + Fecha[2] + "/";
                    }else{
                        FechaUnida += Fecha[0] + "-" + Fecha[1] + "-" + Fecha[2] + "/";
                    }
                }else if (Fecha[1] == 4 || Fecha[1] == 6 || Fecha[1] == 9 || Fecha[1] == 11  && Fecha[2] == 30) {
                    Fecha[1] = Fecha[1] + 1;
                    Fecha[2] = 1;
                    if (Fecha[1] < 10) {
                        FechaUnida += Fecha[0] + "-0" + Fecha[1] + "-" + Fecha[2] + "/";
                    }else{
                        FechaUnida += Fecha[0] + "-" + Fecha[1] + "-" + Fecha[2] + "/";
                    }
                }else if (Fecha[1] == 2 && Fecha[2] == 28 && bisiesto == true){
                    Fecha[2] = Fecha[2] + 1;
                    if (Fecha[1] < 10) {
                        FechaUnida += Fecha[0] + "-0" + Fecha[1] + "-" + Fecha[2] + "/";
                    }else{
                        FechaUnida += Fecha[0] + "-" + Fecha[1] + "-" + Fecha[2] + "/";
                    }
                }else if (Fecha[1] == 2 && Fecha[2] == 28 && bisiesto == false){
                    Fecha[1] = Fecha[1] + 1;
                    Fecha[2] = 1;
                    if (Fecha[1] < 10) {
                        FechaUnida += Fecha[0] + "-0" + Fecha[1] + "-" + Fecha[2] + "/";
                    }else{
                        FechaUnida += Fecha[0] + "-" + Fecha[1] + "-" + Fecha[2] + "/";
                    }
                }else{
                    Fecha[2] = Fecha[2] + 1;
                    if (Fecha[1] < 10) {
                        FechaUnida += Fecha[0] + "-0" + Fecha[1] + "-" + Fecha[2] + "/";
                    }else{
                        FechaUnida += Fecha[0] + "-" + Fecha[1] + "-" + Fecha[2] + "/";
                    }
                }
            }
        return FechaUnida;
    }
}

package com.example.efren.biodiversidad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Read_Page {

    public Read_Page() {
    }

    // Función dedicada a la lectura del contenido de una URL, en este caso utilizada
    // en el servicio web que contiene la información en formato de texto JSON
    public String Read_Page(String Url) {

        HttpURLConnection urlConnection = null;

        try {

            URL url = new URL(Url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream in = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer buffer = new StringBuffer();

            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    }

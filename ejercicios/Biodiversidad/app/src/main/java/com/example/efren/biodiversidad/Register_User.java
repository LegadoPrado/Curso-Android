package com.example.efren.biodiversidad;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Register_User extends AppCompatActivity {

    private EditText Nombre, Apellido, Fecha, Usuario, Contraseña, C_Contraseña, Correo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__user);
        
        Nombre = (EditText) findViewById(R.id.Nombre);
        Apellido = (EditText) findViewById(R.id.Apellido);
        Fecha = (EditText) findViewById(R.id.Fecha);
        Usuario = (EditText) findViewById(R.id.Usuario);
        Contraseña = (EditText) findViewById(R.id.Contraseña);
        C_Contraseña = (EditText) findViewById(R.id.C_Contraseña);
        Correo = (EditText) findViewById(R.id.Correo);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case R.id.Agregar:
                if (!Nombre.getText().toString().equals("") &&
                        !Apellido.getText().toString().equals("") &&
                        !Fecha.getText().toString().equals("") &&
                        !Usuario.getText().toString().equals("") &&
                        !Contraseña.getText().toString().equals("") &&
                        !C_Contraseña.getText().toString().equals("") &&
                        !Correo.getText().toString().equals("")) {
                    if (Contraseña.getText().toString().equals(C_Contraseña.getText().toString())){
                        new Registro().execute();
                    }else{
                        Toast.makeText(this, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class Registro extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String Contenido = new Read_Page().Read_Page("http://biodiversidad.alcohomeapp.com.mx/webService_registro_usuario.php?nombre=" + Nombre.getText().toString() + "&apellido=" + Apellido.getText().toString() + "&fecha=" + Fecha.getText().toString() + "&usuario=" + Usuario.getText().toString() + "&password=" + Contraseña.getText().toString() + "&email=" + Correo.getText().toString());
            try {
                JSONArray jsonArray = new JSONArray(Contenido);
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                }
                return jsonObject.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equalsIgnoreCase("ok")) {
                Toast.makeText(Register_User.this, s, Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
        }
    }
}
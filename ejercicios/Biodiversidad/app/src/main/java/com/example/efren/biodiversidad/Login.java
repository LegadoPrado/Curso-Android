package com.example.efren.biodiversidad;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText Usuario, Contraseña;
    private ArrayList<Obj_Login> obj_logins = new ArrayList<>();
    private Obj_Login obj_login = new Obj_Login();
    private boolean Status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Usuario = (EditText) findViewById(R.id.Usuario);
        Contraseña = (EditText) findViewById(R.id.Contraseña);
        ((Button) findViewById(R.id.Ingresar)).setOnClickListener(this);
    }

    private class Login_User extends AsyncTask<String, String, ArrayList<Obj_Login>> {

        @Override
        protected ArrayList<Obj_Login> doInBackground(String... strings) {
            try {
                String Contenido = new Read_Page().Read_Page("http://biodiversidad.alcohomeapp.com.mx/webService_login.php?usuario=" + Usuario.getText().toString() + "&password=" + Contraseña.getText().toString());
                JSONArray jsonArray = new JSONArray(Contenido);
                JSONObject jsonObject;

                for (int i = 0; i < jsonArray.length(); i ++){
                    jsonObject = jsonArray.getJSONObject(i);
                    if (!jsonObject.getString("status").equals("Error")) {
                        obj_login.setNombre(jsonObject.getString("nombre"));
                        obj_login.setStatus(jsonObject.getString("status"));
                        obj_login.setApellido(jsonObject.getString("apellido"));
                        obj_login.setFecha(jsonObject.getString("fecha"));
                        obj_login.setId((jsonObject.getInt("id")));
                        obj_login.setCorreo(jsonObject.getString("email"));
                        obj_login.setUsuario(jsonObject.getString("usuario"));
                        obj_logins.add(obj_login);
                    }else{
                        Status = false;
                    }
                }
                return obj_logins;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Obj_Login> obj_logins) {
            super.onPostExecute(obj_logins);

            if (obj_logins != null){
                if (Status == false){
                    Toast.makeText(Login.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }else{
                    Intent intent = new Intent(Login.this, User_select_option.class);
                    intent.putExtra("Nombre", obj_logins.get(0).getNombre());
                    intent.putExtra("Apellido", obj_logins.get(0).getApellido());
                    intent.putExtra("Correo", obj_logins.get(0).getCorreo());
                    intent.putExtra("Fecha", obj_logins.get(0).getFecha());
                    intent.putExtra("Id", String.valueOf(obj_logins.get(0).getId()));
                    intent.putExtra("Usuario", obj_logins.get(0).getUsuario());
                    Limpiar();
                    startActivity(intent);
                }
            }else{
                Toast.makeText(Login.this, "Error en el servicio web", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Ingresar:
                new Login_User().execute();
                break;
        }
    }

    private void Limpiar(){
        Usuario.setText("");
        Contraseña.setText("");
    }
}
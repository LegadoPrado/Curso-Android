package com.example.efren.biodiversidad;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ((Button) findViewById(R.id.Inicio)).setOnClickListener(this);
        ((Button) findViewById(R.id.Login)).setOnClickListener(this);
        ((Button) findViewById(R.id.Registro)).setOnClickListener(this);
        new Try_Connection().connection(this);
        CheckIfGPSIsEnabled();

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.Inicio:
                intent = new Intent(Menu.this, Public_select_option.class);
                startActivity(intent);
                break;
            case R.id.Login:
                intent = new Intent(Menu.this, Login.class);
                startActivity(intent);
                break;
            case R.id.Registro:
                intent = new Intent(Menu.this, Register_User.class);
                startActivity(intent);
                break;
        }
    }

    public void CheckIfGPSIsEnabled() {
        int GPS = 0;
        try {
            GPS = Settings.Secure.getInt(getBaseContext().getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (GPS == 0) {
                ActivateGPS();
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }


    }
    private void ActivateGPS(){
        new AlertDialog.Builder(Menu.this)
                    .setTitle("GPS")
                    .setMessage("El GPS está desactivado")
                    .setPositiveButton("Activar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("",null)
                    .show();
}
}
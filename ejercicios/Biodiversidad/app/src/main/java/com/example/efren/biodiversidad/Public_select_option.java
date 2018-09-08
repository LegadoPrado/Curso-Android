package com.example.efren.biodiversidad;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Public_select_option extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_select_option);

        ((Button) findViewById(R.id.Especies)).setOnClickListener(this);
        ((Button) findViewById(R.id.Zonas)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.Especies:
                intent = new Intent(Public_select_option.this, Show_Species_Public.class);
                startActivity(intent);
                break;
            case R.id.Zonas:
                intent = new Intent(Public_select_option.this, Show_Zones.class);
                startActivity(intent);
                break;
        }
    }
}
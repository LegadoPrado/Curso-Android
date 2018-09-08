package com.example.efren.biodiversidad;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class User_select_option extends AppCompatActivity implements View.OnClickListener{

    TextView NombreTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select_option);

        NombreTV = (TextView) findViewById(R.id.NombreTV);
        NombreTV.setText(getIntent().getStringExtra("Nombre"));
        ((Button) findViewById(R.id.Mis_especies)).setOnClickListener(this);
        ((Button) findViewById(R.id.Capturar)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.Mis_especies:
                intent = new Intent(User_select_option.this, Show_My_Species.class);
                intent.putExtra("Id", getIntent().getStringExtra("Id"));
                startActivity(intent);
                break;
            case R.id.Capturar:
                intent = new Intent(User_select_option.this, Register_Species.class);
                intent.putExtra("Id", getIntent().getStringExtra("Id"));
                startActivity(intent);
                break;
        }
    }
}

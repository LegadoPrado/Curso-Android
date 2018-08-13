package com.example.efren.condicional_switch_case;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

   EditText editText;
   TextView textView;
   Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        textView.setText("1.- Rojo \n2.- Azul\n3.- Verde\n4.- Negro");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (editText.getText().toString()){
                    case "1":
                        button.setBackgroundColor(Color.parseColor("#FF4600"));
                        break;
                    case "2":
                        button.setBackgroundColor(Color.parseColor("#001EFF"));
                        break;
                    case "3":
                        button.setBackgroundColor(Color.parseColor("#0DFF00"));
                        break;
                    case "4":
                        button.setBackgroundColor(Color.parseColor("#000000"));
                        break;
                }
            }
        });
    }
}

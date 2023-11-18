package com.example.myapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.R;

public class TelaPrinicipal extends AppCompatActivity {


    ImageView perfil;
    TextView usertext, emailtext;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_prinicipal);

        perfil = findViewById(R.id.perfil);
        usertext = findViewById(R.id.usertext);
        emailtext = findViewById(R.id.emailtext);

        SharedPreferences sp = getSharedPreferences("bd_temp", Context.MODE_PRIVATE);
        String email = sp.getString("email", "");
        emailtext.setText(email);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( TelaPrinicipal.this, Perfil.class);
                startActivity(intent);
            }
        });
    }

}
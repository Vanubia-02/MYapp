package com.example.myapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.myapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imglogo;
        imglogo = findViewById(R.id.imglogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // iniciar animação da imagem
                imglogo.animate().scaleX(1.5f).scaleY(1.5f).setDuration(1000);

                // aguarda animação e inicia o tempo dd eepera
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sp = getSharedPreferences("bd_temp", Context.MODE_PRIVATE);
                        String auxMail = sp.getString("email", "");

                        Intent intent = new Intent(MainActivity.this, Cadastro.class);
                        startActivity(intent);

                        // Fechar a Activity atual
                        finish();
                    }
                }, 1000); // Aguardar 1s e passa para a próxima Activity
            }
        }, 2500);//tempo de animação da imagem

    }
}


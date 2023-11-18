package com.example.myapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.DAO.UserDAO;
import com.example.myapp.Model.User;
import com.example.myapp.R;

public class Perfil extends AppCompatActivity {

    TextView email, username;
    Button btnAlter, btnDelete, btnLogout;

    ImageView imgretorno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        SharedPreferences sp = getSharedPreferences("bd_temp", Context.MODE_PRIVATE);
        String auxMail = sp.getString("email", "");

            email = findViewById(R.id.txtEmailperfil);
            username = findViewById(R.id.txtUserPerfil);
            btnAlter = findViewById(R.id.btnAlterar);
            btnDelete = findViewById(R.id.btnDelete);
            btnLogout = findViewById(R.id.btnLogout);
            imgretorno = findViewById(R.id.imgretorno);


        /*

        // Criar uma instância do UserDAO
        UserDAO userDAO = new UserDAO(this, new User()); // Pode precisar ajustar conforme necessário

        // Obter o usuário do banco de dados com base no email
        User user = userDAO.getUserByMail(auxMail);

        // Verificar se o usuário foi encontrado
        if (user != null) {
            // Exibir dados na interface do usuário
            email.setText(user.getEmail());
            username.setText(user.getUsername());
        }
    */
        imgretorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this,  TelaPrinicipal.class);
                startActivity(intent);
            }
        });

        btnAlter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(Perfil.this, Atualizar.class);
                    startActivity(it);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(Perfil.this, Deletar.class);
                    startActivity(it);
                }
            });
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.commit();

                    Intent it = new Intent(Perfil.this, Login.class);
                    startActivity(it);
                }
            });
        }

    }

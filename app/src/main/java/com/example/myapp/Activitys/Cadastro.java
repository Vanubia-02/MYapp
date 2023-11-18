package com.example.myapp.Activitys;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.DAO.UserDAO;
import com.example.myapp.Helper.DBhelper;
import com.example.myapp.Model.User;
import com.example.myapp.R;

public class Cadastro extends AppCompatActivity {
    Button btnLogin, btnCadastro;
    EditText txtEmail, txtUser, txtPass, txtPassConfirm;
    User user;
    DBhelper db;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        SharedPreferences sp = getSharedPreferences("bd_temp", Context.MODE_PRIVATE);
        String auxMail = sp.getString("email", "");


        if(!auxMail.equals("")){
            Intent intent = new Intent(Cadastro.this, TelaPrinicipal.class);
            startActivity(intent);
        }

        btnLogin = findViewById(R.id.btnloginTC);
        btnCadastro = findViewById(R.id.btnCadastroTC);
        txtEmail = findViewById(R.id.inputEmailTC);
        txtUser = findViewById(R.id.inputUserTC);
        txtPass = findViewById(R.id.inputPasswordTC);
        txtPassConfirm = findViewById(R.id.inputPasswordConfirmTC);



        //redirecionamento botão de login  - tela cadastro
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Cadastro.this, Login.class );
                startActivity(intent);

            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, mail, pass, passCon;
                user = txtUser.getText().toString();
                mail = txtEmail.getText().toString();
                pass = txtPass.getText().toString();
                passCon = txtPassConfirm.getText().toString();

                if(user.equals("")){txtUser.setError("Campo vazio!");}
                if(mail.equals("")){txtEmail.setError("Campo vazio!");}
                if(pass.equals("")){txtPass.setError("Campo vazio!");}
                if(passCon.equals("")){txtPassConfirm.setError("Campo vazio!");}

                if (user.equals("") || mail.equals("") || pass.equals("") || passCon.equals("")){
                    //Testo campos vazios
                    Toast.makeText(Cadastro.this, "Todos os campos vazios!", Toast.LENGTH_SHORT).show();
                } else if(!pass.equals(passCon)){
                    //Testo senhas diferentes
                    txtPass.setError("Senhas diferentest!");
                    txtPassConfirm.setError("Senhas diferentes!");
                    txtPass.setText("");
                    txtPassConfirm.setText("");
                    Toast.makeText(Cadastro.this, "Senhas diferentes!", Toast.LENGTH_SHORT).show();
                } else {
                    //Salvo os dados
                    UserDAO uDAO = new UserDAO(getApplicationContext(), new User(user, mail, pass));
                    if(uDAO.signUpVality()==true){
                        //Verifico se já possui cadastro com o email
                        Toast.makeText(Cadastro.this, "Email em uso!", Toast.LENGTH_SHORT).show();
                        txtEmail.setError("Email em uso!");
                        txtEmail.setText("");
                    } else{
                        //Se não houver eu salvo
                        uDAO.signUp();
                        //Limpo os campos
                        txtUser.setText("");
                        txtEmail.setText("");
                        txtPass.setText("");
                        txtPassConfirm.setText("");

                        SharedPreferences sp = getSharedPreferences("bd_temp", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("email", mail);
                        editor.commit();

                        Toast.makeText(Cadastro.this, "Registro Realizado com Sucesso!", Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(Cadastro.this, TelaPrinicipal.class);
                        startActivity(it);
                    }
                }
            }
        });
    }


    }






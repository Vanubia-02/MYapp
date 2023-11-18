package com.example.myapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapp.DAO.UserDAO;
import com.example.myapp.Model.User;
import com.example.myapp.R;

public class Atualizar extends AppCompatActivity {

    ImageView imgretorno;

    EditText txtUser, txtPass, txtPassCon, txtEmail;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);

        imgretorno = findViewById(R.id.imgretornoP);

        imgretorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Atualizar.this,  Perfil.class);
                startActivity(intent);
            }
        });


        SharedPreferences sp = getSharedPreferences("bd_temp", Context.MODE_PRIVATE);
        String auxMail = sp.getString("email", "");

        if(auxMail.equals("")){
            Intent intent = new Intent(Atualizar.this, Login.class);
            startActivity(intent);
        } else {

            User user = new User();
            user.setEmail(auxMail);
            UserDAO uDAO = new UserDAO(getApplicationContext(), user);
            user = uDAO.getUserByMail();

            txtEmail = findViewById(R.id.edtEmail);
            txtUser = findViewById(R.id.edtnome);
            txtPass = findViewById(R.id.edtPass);
            txtPassCon = findViewById(R.id.edtPassconfirm);
            btnUpdate = findViewById(R.id.btnAlterar);

            txtEmail.setText(user.getEmail());
            txtUser.setText(user.getUsername());
            txtPass.setText(user.getPassword());
            txtPassCon.setText(user.getPassword());

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String usuario, email, pass, passCon;
                    usuario = txtUser.getText().toString();
                    email = txtEmail.getText().toString();
                    pass = txtPass.getText().toString();
                    passCon = txtPassCon.getText().toString();

                    if (usuario.equals("")){txtUser.setError("Preencha o campo!");}
                    if (email.equals("")){txtEmail.setError("Preencha o campo!");}
                    if (pass.equals("")){txtPass.setError("Preencha o campo!");}
                    if (passCon.equals("")){txtPassCon.setError("Preencha o campo!");}

                    if (usuario.equals("") || email.equals("") || pass.equals("") || passCon.equals("")) {
                        //Testo campos vazios
                        Toast.makeText(Atualizar.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    } else if (!pass.equals(passCon)) {
                        txtPass.setError("Senhas diferentes!");
                        txtPassCon.setError("Senhas diferentes!");
                        txtPass.setText("");
                        txtPassCon.setText("");
                        Toast.makeText(Atualizar.this, "Senhas diferentes!", Toast.LENGTH_SHORT).show();
                    } else {
                        //Salvo os dados
                        UserDAO uDAOUpdate = new UserDAO(getApplicationContext(), new User(usuario, email, pass));
                        if (uDAOUpdate.signUpVality() == true && !email.equals(auxMail)) {
                            //Verifico se já possui cadastro com o email
                            Toast.makeText(Atualizar.this, "Este e-mail já está em uso!", Toast.LENGTH_SHORT).show();
                            txtEmail.setError("Este e-mail já está em uso!");
                            txtEmail.setText("");
                        } else {
                            //Se não houver eu altero
                            if (uDAOUpdate.update(auxMail) == true) {
                                Toast.makeText(Atualizar.this, "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();

                                SharedPreferences sp = getSharedPreferences("bd_temp", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("email", email);
                                editor.commit();

                                Intent intent = new Intent(Atualizar.this, TelaPrinicipal.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Atualizar.this, "Erro ao alterar dados.", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }
                }
            });
        }
    }

}

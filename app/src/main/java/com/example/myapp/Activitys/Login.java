package com.example.myapp.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.myapp.DAO.UserDAO;
import com.example.myapp.Model.User;
import com.example.myapp.R;

public class Login extends AppCompatActivity {

    Button btncadastro, btnlogin;
    EditText txtEmial, txtPassl;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences("bd_temp", Context.MODE_PRIVATE);
        String auxMail = sp.getString("email", "");

        if(!auxMail.equals("")){
            Intent it = new Intent(Login.this, TelaPrinicipal.class);
            startActivity(it);
        }

        btnlogin = findViewById(R.id.btnloginTL);
        btncadastro = findViewById(R.id.btnCadastroTL);
        txtEmial = findViewById(R.id.inputEmailTL);
        txtPassl = findViewById(R.id.inputPasswordTL);


        btncadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Cadastro.class);
                startActivity(intent);
                // Fechar a Activity atual
                finish();

            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail, pass;
                mail = txtEmial.getText().toString();
                pass = txtPassl.getText().toString();

                if(mail.equals("")){txtEmial.setError("Campo vazio!");}
                if(pass.equals("")){txtPassl.setError("Campo vazio!");}

                if (mail.equals("") || pass.equals("")){
                    //Testo campos vazios
                    Toast.makeText(Login.this, "Campo vazio!", Toast.LENGTH_SHORT).show();
                } else{
                    User user = new User();
                    user.setEmail(mail);
                    user.setPassword(pass);

                    UserDAO uDAO = new UserDAO(getApplicationContext(), user);
                    if(uDAO.signUpVality()==false){
                        txtEmial.setText("");
                        txtEmial.setError("Dados invalidos!");
                        txtPassl.setText("");
                        txtPassl.setError("Dados invalidos!");
                    } else{
                        User auxUser = uDAO.getUserByMail();

                        if(!auxUser.getPassword().equals(user.getPassword())){
                            Toast.makeText(Login.this, "Senha inválida!", Toast.LENGTH_SHORT).show();
                            txtPassl.setText("");
                            txtPassl.setError("Senha inválida!");
                        } else{
                            // Login permitido
                            SharedPreferences sp = getSharedPreferences("bd_temp", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("email", user.getEmail());
                            editor.commit();
                            Intent it = new Intent(Login.this, TelaPrinicipal.class);
                            startActivity(it);
                        }
                    }
                }
            }
        });

    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.item1){
            Intent it = new Intent(this, List.class);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }*/
}
package com.example.ghifa.mrad2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private EditText mEmail, mPass;
    TextView toReg;
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = (EditText)findViewById(R.id.log_email);
        mPass = (EditText)findViewById(R.id.log_pass);
        progressBar = (ProgressBar) findViewById(R.id.login_loading);
        toReg = (TextView)findViewById(R.id.to_register);
        mAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.GONE);

        toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(login.this, register.class);
                startActivity(register);
                finish();
            }
        });

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userLogin();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(login.this, main.class));
        }
    }

    private void userLogin()
    {
        String email = mEmail.getText().toString().trim();
        String pass = mPass.getText().toString().trim();

        if(email.isEmpty())
        {
            mEmail.setError("Email wajib diisi!");
            mEmail.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            mPass.setError("Password wajib diisi!");
            mPass.requestFocus();
            return;
        }
        if(pass.length() < 6)
        {
            mPass.setError("Jumlah password minimal 6 karakter atau lebih");
            mPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    Intent sukses = new Intent(login.this, main.class);
                    sukses.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(sukses);
                    finish();
                    Toast.makeText(login.this, "Selamat datang :)", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}

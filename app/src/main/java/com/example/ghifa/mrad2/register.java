package com.example.ghifa.mrad2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    private EditText mNama, mEmail, mPhone, mPass;
    private ProgressBar progressBar;
    private TextView toLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNama = (EditText)findViewById(R.id.reg_nama);
        mEmail = (EditText)findViewById(R.id.reg_email);
        mPhone = (EditText)findViewById(R.id.reg_phone);
        mPass = (EditText)findViewById(R.id.reg_pass);
        progressBar = (ProgressBar) findViewById(R.id.register_loading);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.to_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, login.class));
                finish();
            }
        });

        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        progressBar.setVisibility(View.GONE);
        if(mAuth.getCurrentUser() != null)
        {

        }
    }

    private void registerUser()
    {
        final String nama = mNama.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String phone = mPhone.getText().toString().trim();
        final String saldo = "0";
        final String image = "";
        String pass = mPass.getText().toString().trim();

        if(nama.isEmpty())
        {
            mNama.setError("Nama lengkap wajib diisi!");
            mNama.requestFocus();
            return;
        }

        if(email.isEmpty())
        {
            mEmail.setError("Email wajib diisi!");
            mEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mEmail.setError("Isi email dengan benar!");
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
            mPass.setError("Password harus berisi 6 karakter atau lebih!");
            mPass.requestFocus();
            return;
        }

        if(phone.isEmpty())
        {
            mPhone.setError("Nomor telepon wajib diisi!");
            mPhone.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful())
                        {
                            User user = new User(nama, email, phone, saldo, image);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.GONE);
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(register.this, "Berhasil Daftar!", Toast.LENGTH_SHORT).show();
                                                Intent selesai = new Intent(register.this, login.class);
                                                startActivity(selesai);
                                                finish();
                                            }
                                            else
                                            {
                                                if(task.getException() instanceof FirebaseAuthUserCollisionException)
                                                {
                                                    Toast.makeText(register.this, "Email sudah terdaftar, silahkan menggunakan email lain", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    });

                        }
                        else
                        {
                            Toast.makeText(register.this, "Gagal daftar!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}

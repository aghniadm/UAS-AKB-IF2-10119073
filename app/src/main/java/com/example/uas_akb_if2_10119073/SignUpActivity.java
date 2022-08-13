package com.example.uas_akb_if2_10119073;
/*
    NIM  : 10119073
    Nama : Aghnia Dewi Mahiranie
    Kelas: IF-2
 */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.uas_akb_if2_10119073.ui.profile.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn,btnSignUp;
    private ProgressBar progressBar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

//                if (TextUtils.isEmpty(email)){
//                    Toast.makeText(SignUpActivity.this, "email tidak boleh kosong", Toast.LENGTH_SHORT).show();
//                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this, "Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
                if(password.length() < 6){
                    Toast.makeText(SignUpActivity.this, "Password Minimal 6 Karakter", Toast.LENGTH_SHORT).show();
                }

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "User Berhasil Dibuat", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();

                        } else {
                            Toast.makeText(SignUpActivity.this, "Autentikasi Gagal" + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });
    }
}
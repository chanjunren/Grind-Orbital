package com.example.grind_jr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView loginButton, registerButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
//            Toast.makeText(MainActivity.this, "If is being run", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Home.class));
        }

        loginButton = (ImageView) findViewById(R.id.loginButton);
        registerButton = (ImageView) findViewById(R.id.registerButton);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == loginButton) {
            finish();
            startActivity(new Intent(this, Login.class));
        } else if (v == registerButton){
            finish();
            startActivity(new Intent(this, Registration.class));
        }
    }
}

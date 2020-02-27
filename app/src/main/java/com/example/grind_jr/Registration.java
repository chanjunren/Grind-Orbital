package com.example.grind_jr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends Activity {

    private ImageView createNewUser;
    private ProgressDialog progressDialog;
    private EditText newEmail, newPw, confirmPw;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference newUser;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        createNewUser = (ImageView) findViewById(R.id.createUser);
        newEmail = (EditText) findViewById(R.id.registerEmail);
        newPw = (EditText) findViewById(R.id.registerPassword);
        confirmPw = (EditText) findViewById(R.id.confirmPassword);
        createNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    private void registerUser() {
        final String email = newEmail.getText().toString().trim();
        String password = newPw.getText().toString().trim();
        String confirmation = confirmPw.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(confirmation)) {
            Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmation)) {
            Toast.makeText(this, "Your passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            firebaseUser = task.getResult().getUser();
                            newUser = databaseReference.child(firebaseUser.getUid());
                            newUser.child("email").setValue(email);
                            newUser.child("Quests").setValue("null");
                            newUser.child("Incomes").setValue("null");
                            newUser.child("Expenditures").setValue("null");
                            newUser.child("Sprites").setValue("null");
                            newUser.child("Exp").setValue(0);
                            Toast.makeText(Registration.this,"Registered successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Registration.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
package com.example.sampleschooladmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sampleschooladmin.Model.AdminModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edt_email,edt_password;
    Button btn_login;

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!= null)
        {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
        btn_login = findViewById(R.id.btn_login);
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);


        btn_login.setOnClickListener(login);
    }

    View.OnClickListener login = view -> {
        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(authResult -> {
                    user = firebaseAuth.getCurrentUser();
                    reference = FirebaseDatabase.getInstance().getReference(user.getUid());
                    reference.get()
                            .addOnSuccessListener(dataSnapshot -> {
                                if (dataSnapshot.exists())
                                {
                                    AdminModel model = dataSnapshot.getValue(AdminModel.class);
                                    assert model != null;
                                    if (model.getType().equals("admin"))
                                    {

                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                                else {
                                    Snackbar.make(view,"Un-Authorised Access",Snackbar.LENGTH_SHORT).show();
                                    firebaseAuth.signOut();
                                }
                            })
                            .addOnFailureListener(e -> {
                                Snackbar.make(view,""+e,Snackbar.LENGTH_LONG)
                                        .setAction("OK",null)
                                        .show();
                                firebaseAuth.signOut();
                            });

                })
                .addOnFailureListener(e -> {
                    Snackbar.make(view, "" + e, Snackbar.LENGTH_LONG)
                            .setAction("OK", null)
                            .show();
                    firebaseAuth.signOut();
                });

    };
}
package com.example.PickBeforeGo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.android.material.textfield.TextInputLayout;
import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.helper.UserHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    EditText editTextUsername, editTextEmail, editTextPassword;
    Button btnSignup, btnToLogin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    private static final String TAG = "signup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialising all xmlelements in activity_sign_up.xml
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnSignup = findViewById(R.id.btnSignup);
        btnToLogin = findViewById(R.id.btnToLogin);

        final boolean[] passwordVisible = {false};

        //Save data in Firebase on button click
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(view);
            }
        });

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void registerUser (View view){
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        Log.i(TAG, "registering user");

        String username = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // check validation of email, password and username
        if (validateEmail() && validatePassword() && validateUsername()){
            Log.i(TAG, "user validated");
            fAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(SignupActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "create user success");

                    //to store other values into the Firestore
                    //TODO Show some error when password not long enough
                    FirebaseUser user = fAuth.getCurrentUser();
                    DocumentReference df = fStore.collection("Users").document(user.getUid());

                    UserHelper helperClass = new UserHelper(username,email,password, false);

                    df.set(helperClass);

                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignupActivity.this, "Failed to create an Account", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.e(TAG, "user not validated");
        }

    }

    private Boolean validateUsername(){
        String val = editTextUsername.getText().toString();
        String noWhiteSpace = "(?=\\S+$)";

        if (val.isEmpty()){
            editTextUsername.setError("Field cannot be empty");
            return false;
        } /*else if (val.length()>=15) {
            regUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)){
            regUsername.setError("White spaces are not allowed");
            return false;
        }*/ else {
            editTextUsername.setError(null);
            return true;
        }

    }
    private Boolean validateEmail(){
        String val = editTextEmail.getText().toString();

        if (val.isEmpty()){
            editTextEmail.setError("Field cannot be empty");
            return false;
        }
        else {
            editTextEmail.setError(null);
            return true;
        }
    }
    private Boolean validatePassword(){
        String val = editTextPassword.getText().toString();
        if (val.isEmpty()) {
            editTextPassword.setError("Field cannot be empty");
            return false;
        } else if (val.length() <= 7) {
            editTextPassword.setError("Password too short");
            return false;
        } else {
            editTextPassword.setError(null);
            return true;
        }

    }
}
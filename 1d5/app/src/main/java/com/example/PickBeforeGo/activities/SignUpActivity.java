package com.example.PickBeforeGo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.PickBeforeGo.R;
import com.example.PickBeforeGo.components.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;

//import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

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
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
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
            fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "create user success");

                    //to store other values into the Firestore
                    FirebaseUser user = fAuth.getCurrentUser();
                    DocumentReference df = fStore.collection("Users").document(user.getUid());

                    User helperClass = new User(username, email, sha256(password), false);

                    df.set(helperClass);

                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpActivity.this, "Failed to create an Account", Toast.LENGTH_SHORT).show();
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
        }
        else {
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

    public static String sha256(String base) {
        try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(base.getBytes("UTF-8"));
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    } catch(Exception ex){
        throw new RuntimeException(ex);
    }
}
}
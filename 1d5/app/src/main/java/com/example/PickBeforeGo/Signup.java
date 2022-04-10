package com.example.PickBeforeGo;

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
import com.example.PickBeforeGo.helper.UserHelperClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup extends AppCompatActivity {

    private static final String TAG = "TAG";
    EditText regFullname, regUsername, regEmail, regPassword;
    Button regBtn, regtoLoginBtn;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //Hooks to all xmlelements in activity_sign_up.xml
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.regbtn);
        regtoLoginBtn = findViewById(R.id.tologinbtn);
        //Save data in Firebase on button click
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(view);
            }
        });

        regtoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void registerUser (View view){

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        Log.i(TAG, "registering user");

        String username = regUsername.getText().toString();
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();
        // check validation of email, password and username
        if (validateEmail() | validatepassword() | validateusername()){
            Log.i(TAG, "user validated");
            fAuth.createUserWithEmailAndPassword(regEmail.getText().toString(),regPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(Signup.this, "Account Created", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "create user success");

                    //to store other values into the Firestore
                    //TODO Show some error when password not long enough
                    FirebaseUser user = fAuth.getCurrentUser();
                    DocumentReference df = fStore.collection("Users").document(user.getUid());

                    UserHelperClass helperClass = new UserHelperClass(username,email,password, false);

                    df.set(helperClass);

                    startActivity(new Intent(Signup.this, Login.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Signup.this, "Failed to create an Account", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.i(TAG, "user not validated");
        }

    }

    private Boolean validateusername(){
        String val = regUsername.getText().toString();
        String noWhiteSpace = "(?=\\S+$)";

        if (val.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        } /*else if (val.length()>=15) {
            regUsername.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)){
            regUsername.setError("White spaces are not allow");
            return false;
        }*/ else {
            regUsername.setError(null);
            return true;
        }

    }
    private Boolean validateEmail(){
        String val = regEmail.getText().toString();
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";

        if (val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        } /*else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid Email format");
            return false;
        } */else {
            regEmail.setError(null);
            return true;
        }
    }
    private Boolean validatepassword(){
        String val = regPassword.getText().toString();
        if (val.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (val.length()<=7) {
            regUsername.setError("Username too short");
            return false;
        } else {
            regPassword.setError(null);
            return true;
        }
        //can include password validation type

    }
}
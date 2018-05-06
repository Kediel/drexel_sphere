package com.example.kdl.drexelsphere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EmailPasswordSignup extends AppCompatActivity {

    private EditText mEmailField, mPasswordField, mNameField, mGradYearField, mMajorField;

    private Button signInB, registerB, signOutB, verifyB;
    private ProgressBar progressBar;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private DatabaseReference userDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password_signup);

        mEmailField = findViewById(R.id.email);
        mPasswordField = findViewById(R.id.password);

        mNameField = findViewById(R.id.user_name);
        mGradYearField = findViewById(R.id.user_grad_year);
        mMajorField = findViewById(R.id.user_major);
        progressBar = findViewById(R.id.progressBar);

        // Buttons
        signInB = findViewById(R.id.email_sign_in_button);
        registerB = findViewById(R.id.email_register_button);
//      signOutB = findViewById(R.id.sign_out_button);
//        verifyB = findViewById(R.id.button_verify_email); // Create a verify_email_button


        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        userDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Click Listener for the sign in button. Fix variable.
        signInB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailPasswordSignup.this, EmailPasswordLogin.class));
                finish();
            }
        });
        // ~~~~~~~~~~~~~~~~~~~~~~ REGISTRATION FUNCTION ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        // Click Listener for the register button.
        registerB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = mEmailField.getText().toString().trim();
                String password = mPasswordField.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    Toast.makeText(getApplicationContext(), "Enter a valid email address!", Toast.LENGTH_SHORT).show();
                    //return;
                }

                if (TextUtils.isEmpty(password)) {

                    Toast.makeText(getApplicationContext(), "Enter a valid password!", Toast.LENGTH_SHORT).show();
                    //return;
                }

                if (password.length() < 7) {

                    Toast.makeText(getApplicationContext(), "Password is too short. A minimum of 7 characters is required!", Toast.LENGTH_SHORT).show();
                    //return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // Create user
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(EmailPasswordSignup.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)

                    {
                        Toast.makeText(EmailPasswordSignup.this, "createUserWithEmail:onComplete" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);


                        // If sign in fails, display a message to the user. If sign in succeeds the auth listener will be notified and logic to handle the
                        // signed in user can be handled with the listener.

                        if (!task.isSuccessful()) {

                            Toast.makeText(EmailPasswordSignup.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                            Log.e("Signed up failed", task.getException().toString());

                        } else {

                            addUser();

//                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
//
//
//                            String name = mNameField.getText().toString();
//                            String major = mMajorField.getText().toString();
//                            String gradyear = mGradYearField.getText().toString();
//
//                            Map newPost = new HashMap();
//
//                            newPost.put("name", name);
//                            newPost.put("major", major);
//                            newPost.put("gradyear", gradyear);
//
//                            current_user_db.setValue(newPost);
//
//                            Intent i = new Intent(EmailPasswordSignup.this, MainActivity.class);
//                            startActivity(i);
                        }

                    }

                });
            }
        });
    }

    private void addUser() {

        String user_id = mAuth.getCurrentUser().getUid();

        String name = mNameField.getText().toString();
        String major = mMajorField.getText().toString();
        String gradyear = mGradYearField.getText().toString();

        if (!TextUtils.isEmpty(name)) {

            //creating an Artist Object
            UserContent user = new UserContent(name, major, gradyear);

            //Saving the Artist
            userDatabase.child(user_id).setValue(user);

//                    //setting edittext to blank again
//                    editTextName.setText("");

            //displaying a success toast
            Toast.makeText(EmailPasswordSignup.this, "Account added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(EmailPasswordSignup.this, "Please enter a name", Toast.LENGTH_LONG).show();
        }

//                Map newEvent = new HashMap();
//
//                newEvent.put("event name", name);
//                newEvent.put("event time", time);
//                newEvent.put("event details", details);
//
//                mDatabase.child(user_id).child("Events").setValue(newEvent);
//
//                Toast.makeText(CreateAnEvent.this, "Create event successful", Toast.LENGTH_SHORT).show();


        startActivity(new Intent(EmailPasswordSignup.this, MainActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}




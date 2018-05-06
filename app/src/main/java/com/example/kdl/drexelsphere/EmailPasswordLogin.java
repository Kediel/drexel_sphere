package com.example.kdl.drexelsphere;
// https://drive.google.com/drive/folders/0B3OcjiZgz7U4MHNueGthczJGd2M
// https://www.youtube.com/watch?v=VFS-wfz9Nb4

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordLogin extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText mEmailField, mPasswordField;

    FirebaseAuth.AuthStateListener mAuthListener;

    // Buttons

    private Button registerB;
    private Button signInB;
    private Button verifyB;
    private Button twitterB;
    private ProgressBar progressBar;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        mAuth.addAuthStateListener(mAuthListener);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(EmailPasswordLogin.this, MainActivity.class));
            finish();
        }

        mEmailField = findViewById(R.id.email);
        mPasswordField = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);

        registerB = findViewById(R.id.email_register_button);

        signInB = findViewById(R.id.email_sign_in_button);

        twitterB = findViewById(R.id.twitter_register_button);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                if (firebaseAuth.getCurrentUser() != null){
//                    startActivity(new Intent(LoginScreen.this, MainActivity.class));
//                }
            }
        };

        twitterB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailPasswordLogin.this, TwitterLoginActivity.class));
            }
        });

        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailPasswordLogin.this, EmailPasswordSignup.class));
                finish();
            }
        });

        signInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailField.getText().toString();
                final String password = mPasswordField.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(EmailPasswordLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        Toast.makeText(getApplicationContext(), "Password is too short. A minimum of 7 characters is required!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(EmailPasswordLogin.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(EmailPasswordLogin.this, MainActivity.class);
                                    intent.putExtra("Email", mAuth.getCurrentUser().getEmail());
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}

//    // [START on_start_check_user]
//    //@Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
//    // [END on_start_check_user]
//
//    private void createAccount(String email, String password) {
//        Log.d(TAG, "createAccount:" + email);
//        if (!validateForm()) {
//            return;
//        }
//
//        // Creates a progress message.
//        ProgressDialog pd = new ProgressDialog(EmailPasswordLogin.this);
//        pd.setMessage("Loading. Please wait...");
//        pd.setCancelable(false); // Used so that ProgressDialog cannot be cancelled until the work is done.
//        // Shows the progress message.
//        pd.show();
//
//        // [START create_user_with_email]
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordLogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // Creates a progress message.
//                        ProgressDialog pd = new ProgressDialog(EmailPasswordLogin.this);
//                        pd.setMessage("Loading. Please wait...");
//                        pd.setCancelable(false); // Used so that ProgressDialog cannot be cancelled until the work is done.
//                        // Dismisses the progress message.
//                        pd.dismiss();
//                    }
//                });
//        // [END create_user_with_email]
//    }
//
//    private void signIn(String email, String password) {
//        Log.d(TAG, "signIn:" + email);
//        if (!validateForm()) {
//            return;
//        }
//
//        // Creates a progress message.
//        ProgressDialog pd = new ProgressDialog(EmailPasswordLogin.this);
//        pd.setMessage("Loading. Please wait...");
//        pd.setCancelable(false); // Used so that ProgressDialog cannot be cancelled until the work is done.
//        // Shows the progress message.
//        pd.show();
//
//        // [START sign_in_with_email]
//        mAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(EmailPasswordLogin.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // [START_EXCLUDE]
//                        if (!task.isSuccessful()) {
//                            mStatusTextView.setText(R.string.auth_failed);
//                        }
//                        // Creates a progress message.
//                        ProgressDialog pd = new ProgressDialog(EmailPasswordLogin.this);
//                        pd.setMessage("Loading. Please wait...");
//                        pd.setCancelable(false); // Used so that ProgressDialog cannot be cancelled until the work is done.
//                        // Dismisses the progress message.
//                        pd.dismiss();
//                    }
//                });
//        // [END sign_in_with_email]
//    }
//
//    private void signOut() {
//        mAuth.signOut();
//        updateUI(null);
//    }
//
//
//    private void sendEmailVerification() {
//        // Disable button
//        findViewById(R.id.button_verify_email).setEnabled(false);
//
//        // Send verification email
//        // [START send_email_verification]
//        final FirebaseUser user = mAuth.getCurrentUser();
//        user.sendEmailVerification()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // [START_EXCLUDE]
//                        // Re-enable button
//                        findViewById(R.id.button_verify_email).setEnabled(true);
//
//                        if (task.isSuccessful()) {
//                            Toast.makeText(EmailPasswordLogin.this,
//                                    "Verification email sent to " + user.getEmail(),
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Log.e(TAG, "sendEmailVerification", task.getException());
//                            Toast.makeText(EmailPasswordLogin.this,
//                                    "Failed to send verification email.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                        // [END_EXCLUDE]
//                    }
//                });
//        // [END send_email_verification]
//    }
//
//    private boolean validateForm() {
//        boolean valid = true;
//
//        String email = mEmailField.getText().toString();
//        if (TextUtils.isEmpty(email)) {
//            mEmailField.setError("Required.");
//            valid = false;
//        } else {
//            mEmailField.setError(null);
//        }
//
//        String password = mPasswordField.getText().toString();
//        if (TextUtils.isEmpty(password)) {
//            mPasswordField.setError("Required.");
//            valid = false;
//        } else {
//            mPasswordField.setError(null);
//        }
//
//        return valid;
//    }
//
//    private void updateUI(FirebaseUser user) {
//
//        // Creates a progress message.
//        ProgressDialog pd = new ProgressDialog(EmailPasswordLogin.this);
//        pd.setMessage("Loading. Please wait...");
//        pd.setCancelable(false); // Used so that ProgressDialog cannot be cancelled until the work is done.
//        // Dismisses the progress message.
//        pd.dismiss();
//
//
//        if (user != null) {
//            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
//                    user.getEmail(), user.isEmailVerified()));
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
//
//            findViewById(R.id.email_sign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.email).setVisibility(View.GONE);
//            //findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);
//
//            //findViewById(R.id.verify_email_button).setEnabled(!user.isEmailVerified());
//        } else {
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);
//
//            findViewById(R.id.email_sign_in_button).setVisibility(View.VISIBLE);
//            findViewById(R.id.email).setVisibility(View.VISIBLE);
//            findViewById(R.id.email_sign_in_button).setVisibility(View.GONE);
//        }
//
//    }
//
//    //@Override
//    public void onClick(View v) {
//        int i = v.getId();
//        if (i == R.id.email_register_button) {
//            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
//        } else if (i == R.id.email_sign_in_button) {
//            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
//        } else if (i == R.id.sign_out_button) {
//            signOut();
//        } else if (i == R.id.button_verify_email) {
//          //  sendEmailVerification();
//        }
//    }
//}
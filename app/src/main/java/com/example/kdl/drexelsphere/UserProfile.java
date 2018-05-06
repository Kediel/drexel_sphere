package com.example.kdl.drexelsphere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity{

    DatabaseReference userDatabse;

    TextView major_userprofile, gradyear_userprofile, name_userprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Get Action Bar for Sign-out Button
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Drexel Sphere");
        myToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(myToolbar);

        String user_id = FirebaseAuth.getInstance().getUid();

        userDatabse = FirebaseDatabase.getInstance().getReference("Users");

        name_userprofile = findViewById(R.id.name_userprofile);
        major_userprofile = findViewById(R.id.major_userprofile);
        gradyear_userprofile = findViewById(R.id.gradyear_userprofile);
    }

    @Override
    protected void onStart() {
        super.onStart();

        userDatabse.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String user_id = FirebaseAuth.getInstance().getUid();



                UserContent userContent = dataSnapshot.child(user_id).getValue(UserContent.class);

                name_userprofile.setText(userContent.getuserGradYear());
                major_userprofile.setText(userContent.getuserMajor());
                gradyear_userprofile.setText(userContent.getuserName()); // TODO: fix error get name in user's graduate year in Firebase after sign up

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error getting value", Toast.LENGTH_LONG).show();

            }
        });
    }


    private void viewProfile() {
        Toast.makeText(getApplicationContext(),"Already in User Profile", Toast.LENGTH_LONG).show();
    }
    private void logOutUser() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(UserProfile.this, EmailPasswordLogin.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:
                logOutUser();
                return true;

            case R.id.profile:
                viewProfile();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}


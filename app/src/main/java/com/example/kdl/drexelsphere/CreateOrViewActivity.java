package com.example.kdl.drexelsphere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateOrViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_view);
    }

    /** Called when the user taps the Create Event button */
    public void createEvent(View view) {
        Intent intent = new Intent(this, CreateAnEvent.class);
        startActivity(intent);

    }
    public void viewFeed(View view) {
        Intent intent = new Intent(this, LobbyEventListActivity.class);
        startActivity(intent);

    }

    public void viewHeatMap(View view) {
        Intent intent = new Intent(this, HeatMap.class);
        startActivity(intent);
    }
}

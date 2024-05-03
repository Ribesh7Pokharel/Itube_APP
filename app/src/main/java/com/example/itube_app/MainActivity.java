package com.example.itube_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        // Assuming a simple delay to mimic loading resources or checking credentials
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLoginAndRedirect();
            }
        }, 2000); // Delay for 2 seconds
    }

    private void checkLoginAndRedirect() {
        // Assuming 'isUserLoggedIn' is a method that checks the logged-in status from your DatabaseHelper
        if (dbHelper.isUserLoggedIn()) {
            startActivity(new Intent(MainActivity.this, VideoManagementActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        finish(); // Ensures this activity is removed from the back stack
    }
}


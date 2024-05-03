package com.example.itube_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button btnLogIn = findViewById(R.id.btnLogIn);

        btnSignUp.setOnClickListener(v -> {
            // Navigate to SignUpActivity when the user clicks the Sign Up button
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        btnLogIn.setOnClickListener(v -> {
            // Navigate to LoginActivity when the user clicks the Log In button
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}



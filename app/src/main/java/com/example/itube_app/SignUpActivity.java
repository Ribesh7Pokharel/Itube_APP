package com.example.itube_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SignUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button createAccountButton = findViewById(R.id.create_account_button);
        createAccountButton.setOnClickListener(v -> {
            // Navigate to CreateAccountActivity when the user clicks the button
            Intent intent = new Intent(SignUpActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        });
    }
}

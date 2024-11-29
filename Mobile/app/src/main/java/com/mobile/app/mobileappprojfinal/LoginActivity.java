package com.mobile.app.mobileappprojfinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextLogin, editTextPassword;
    private Button buttonLogin;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLogin = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        db = new DBHelper(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the entered username and password
                String username = editTextLogin.getText().toString();
                String password = editTextPassword.getText().toString();

                // Check if the entered username and password match the stored credentials
                boolean checkUsernamePassword = db.usernamePassword(username, password);

                if (checkUsernamePassword) {
                    // Display a success message
                    Toast.makeText(getApplicationContext(), R.string.message_connection_success,
                            Toast.LENGTH_SHORT).show();

                    // Open HomeActivity on successful login
                    openHomeActivity();
                } else {
                    // Display a failure message
                    Toast.makeText(getApplicationContext(), R.string.message_connection_failed,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to open HomeActivity
    private void openHomeActivity() {
        // Create an Intent to navigate to HomeActivity
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);

        // Optional: Close the LoginActivity if you don't want the user to navigate back to it using the back button
        finish();

        Log.d("LoginActivity", "Opening HomeActivity");
    }
}

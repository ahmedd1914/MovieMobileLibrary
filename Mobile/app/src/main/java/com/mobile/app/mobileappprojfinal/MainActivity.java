package com.mobile.app.mobileappprojfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    DBHelper db;
    EditText e1, e2, e3;
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHelper(this);
        e1 = findViewById(R.id.username);
        e2 = findViewById(R.id.pass);
        e3 = findViewById(R.id.repass);
        b1 = findViewById(R.id.register);
        b2 = findViewById(R.id.login);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                String s3 = e3.getText().toString();

                if (validateInput(s1, s2, s3)) {
                    boolean checkUsername = db.checkUsername(s1);
                    if (checkUsername) {
                        boolean insert = db.insertUser(s1, s2);
                        if (insert) {
                            Toast.makeText(getApplicationContext(), "Registration successful",
                                    Toast.LENGTH_SHORT).show();


                            // Start LoginActivity after successful registration
                            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(loginIntent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration failed. Please try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Username already exists. Please choose a different username.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
    // Validation method with regular expressions
    private boolean validateInput(String username, String password, String confirmPassword) {
        if (!isValidUsername(username)) {
            e1.setError("Invalid username format");
            return false;
        }

        if (!isValidPassword(password)) {
            e2.setError("Password must have at least one number, one uppercase letter, one lowercase letter, and a minimum length of 8 characters");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            e3.setError("Passwords do not match");
            return false;
        }

        return true;
    }
    private boolean isValidUsername(String username) {
        // Adjust the regular expression pattern as needed
        String usernameRegex = "^[a-zA-Z0-9_-]{3,20}$";
        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        // Password must have at least one number, one uppercase letter, one lowercase letter, and a minimum length of 8 characters
        //String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        String passwordRegex = "^[a-zA-Z0-9_-]{3,20}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}


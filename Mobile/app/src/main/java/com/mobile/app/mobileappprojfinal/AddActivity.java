package com.mobile.app.mobileappprojfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    EditText titleInput, authorInput, descriptionInput;
    Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        titleInput = findViewById(R.id.title_input);
        authorInput = findViewById(R.id.author_input);
        descriptionInput = findViewById(R.id.description_input);


        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper myDB = new DBHelper(AddActivity.this);
                long result = myDB.addMovie(
                        titleInput.getText().toString().trim(),
                        authorInput.getText().toString().trim(),
                        descriptionInput.getText().toString().trim()
                );

                if (result != -1) {
                    // If the insertion was successful, load and display the image

                    Toast.makeText(AddActivity.this, "Movie added successfully", Toast.LENGTH_SHORT).show();

                    // Open HomeActivity
                    Intent intent = new Intent(AddActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // Optional: Finish the current activity to prevent going back to it using the back button
                } else {
                    Toast.makeText(AddActivity.this, "Failed to add movie", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

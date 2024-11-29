package com.mobile.app.mobileappprojfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton add_Button;

    private DBHelper myDB;
    private ArrayList<String> movie_id, movie_title, movie_author, movie_description;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViews();
        setListeners();
        setupRecyclerView();
    }

    private void initializeViews() {
        add_Button = findViewById(R.id.add_button);
        recyclerView = findViewById(R.id.recyclerView); // Add this line
    }

    private void setListeners() {
        add_Button.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, AddActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setupRecyclerView() {
        myDB = new DBHelper(HomeActivity.this);

        movie_id = new ArrayList<>();
        movie_title = new ArrayList<>();
        movie_author = new ArrayList<>();
        movie_description = new ArrayList<>();

        storeDataInArrays();

        movieAdapter = new MovieAdapter(HomeActivity.this, movie_id, movie_title, movie_author, movie_description);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
    }

    private void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                movie_id.add(cursor.getString(0));
                movie_title.add(cursor.getString(1));
                movie_author.add(cursor.getString(2));
                movie_description.add(cursor.getString(3));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialogDeleteAll();
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmDialogDeleteAll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Data?");
        builder.setMessage("Are you really sure you want to erase all data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDB.deleteAllData();
                // Close the current instance of HomeActivity
                finish();
                // Start a new instance of HomeActivity
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing if "No" is clicked
            }
        });
        builder.create().show();
    }
}

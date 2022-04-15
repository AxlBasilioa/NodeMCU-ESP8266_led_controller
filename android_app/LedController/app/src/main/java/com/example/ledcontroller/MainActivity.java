package com.example.ledcontroller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Button btnStart, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnExit = findViewById(R.id.btnExit);
        this.setListeners();
    }

    public void setListeners() {
        btnStart.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SetupAddress.class)));
        btnExit.setOnClickListener(v -> makeAlertDialog());
    }

    public void makeAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("Accept", (dialog, which) -> finish());
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        makeAlertDialog();
    }
}
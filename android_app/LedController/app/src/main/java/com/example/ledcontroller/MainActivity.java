package com.example.ledcontroller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


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

    public void setListeners(){
        btnStart.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SetupAddress.class)));
        btnExit.setOnClickListener(v -> makeAlertDialog());
    }

    public void makeAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Seguro desea salir?");
        builder.setPositiveButton("Aceptar", (dialog, which) -> finish());
        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        makeAlertDialog();
    }
}
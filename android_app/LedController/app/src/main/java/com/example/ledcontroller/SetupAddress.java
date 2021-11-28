package com.example.ledcontroller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;

public class SetupAddress extends AppCompatActivity {
    private EditText host;
    private Button btnLogin;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_address);

        initToolbar();

        this.variablesIdMapped();

        try{ getPreferencesData();}
        catch (Exception e){ e.printStackTrace();}

        this.setBtnLoginListener();
    }


    public void variablesIdMapped(){
        host = findViewById(R.id.edtHostName);
        checkBox = findViewById(R.id.checkbox_save_data);
        btnLogin = findViewById(R.id.btnLoginFtp);
    }

    public void setBtnLoginListener(){
        String pattern = "[0-9.]+";
        btnLogin.setOnClickListener(v -> {

            String host = this.host.getText().toString();
            if(!host.equals("") && host.matches(pattern)) {
                if (checkBox.isChecked()) {
                    this.saveData(host, checkBox.isChecked());
                } else {
                    this.saveData("", false);
                }
                Intent i = new Intent(this, Configuration.class);
                i.putExtra("host", host);
                startActivity(i);
            }else{
                Toast.makeText(this, "Debe ingresar una IP valida", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveData(String host, boolean ischecked){
        SharedPreferences preferences = getSharedPreferences("device-data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("host",host);
        editor.putBoolean("checkbox", ischecked);
        editor.apply();
    }

    public void getPreferencesData(){
        SharedPreferences preferences = getSharedPreferences("device-data", MODE_PRIVATE);
        host.setText(preferences.getString("host", ""));
        checkBox.setChecked(preferences.getBoolean("checkbox", true));
    }

    //back button toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void initToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private boolean ping(String host){
        System.out.println("executeCommand");
        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process  mIpAddrProcess = runtime.exec("/system/bin/ping -c" + host);
            int mExitValue = mIpAddrProcess.waitFor();
            System.out.println(" mExitValue "+mExitValue);
            if(mExitValue==0){
                return true;
            }else{
                return false;
            }
        }
        catch (InterruptedException ignore)
        {
            ignore.printStackTrace();
            System.out.println(" Exception:"+ignore);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(" Exception:"+e);
        }
        return false;
    }

}
package com.example.ledcontroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

import java.util.Locale;

public class Configuration extends AppCompatActivity implements ColorPicker.OnColorChangedListener {

    private String host;
    private boolean state = true;
    private String aux, url;
    private int  r, g, b;
    private EditText hexa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        host = getIntent().getExtras().getString("host");

        hexa = findViewById(R.id.hexa_edit);

        url = "http://" + host;

        setSwitchListener();
        setColorPickerListener();
    }

    protected void sendRequest(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                System.out::println, System.out::println);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    protected void setEndpoint() {
        String red = String.format(Locale.getDefault(),"%03d", r);
        String green = String.format(Locale.getDefault(), "%03d", g);
        String blue = String.format(Locale.getDefault(), "%03d", b);
        if (state) {
            aux = url + "/LED=OFF&R="+red+"&V="+green+"&B="+blue;
        } else {
            aux = url + "/LED=ON&R="+red+"&V="+green+"&B="+blue;
        }
    }

    protected void setSwitchListener() {
        SwitchCompat onOffSwitch = findViewById(R.id.onOff_switch);
        onOffSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            state = !state;
            setEndpoint();
            System.out.println(aux + " " + state);
            sendRequest(aux);
        });
    }

    protected void setColorPickerListener() {
        ColorPicker picker = findViewById(R.id.picker);

        //To get the color
        hexa.setText(String.format("#%06X", (0xFFFFFF & picker.getColor())));

        //To set the old selected color u can do it like this
        picker.setOldCenterColor(picker.getColor());

        // adds listener to the colorpicker which is implemented
        //in the activity
        picker.setOnColorChangedListener(this);

        //to turn of showing the old color
        picker.setShowOldCenterColor(false);

    }

    protected String intToRGB(int colorint) {
        String hexa = String.format("#%06X", (0xFFFFFF & colorint));
        r = Color.red(colorint);
        g = Color.green(colorint);
        b = Color.blue(colorint);
        return "R:" + r + "| G:" + g + "| B:" + b;
    }

    @Override
    public void onColorChanged(int color) {
        intToRGB(color);
        setEndpoint();
        System.out.println(aux + " " + state);
        hexa.setText(String.format("#%06X", (0xFFFFFF & color)));
        sendRequest(aux);
    }
}
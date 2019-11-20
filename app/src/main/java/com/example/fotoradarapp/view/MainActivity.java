package com.example.fotoradarapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.fotoradarapp.R;
import com.example.fotoradarapp.control.MainControl;

public class MainActivity extends AppCompatActivity {

    MainControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        control = new MainControl(this);
    }

    public void enviarLink(View view) {
        control.enviarLink();
    }
}

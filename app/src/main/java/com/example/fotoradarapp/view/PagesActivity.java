package com.example.fotoradarapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fotoradarapp.R;
import com.example.fotoradarapp.control.PageControl;

public class PagesActivity extends AppCompatActivity {

    PageControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);
        control = new PageControl(this);
    }
}

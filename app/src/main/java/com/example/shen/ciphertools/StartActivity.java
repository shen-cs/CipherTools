package com.example.shen.ciphertools;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

/**
 * Created by Shen Chang-Shao on 2016/2/26.
 */
public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //setTitle(R.string.app_name);]
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setIcon(R.drawable.actionbar_logo);
        //actionBar.setTitle(R.string.app_name);
        actionBar.setCustomView(R.layout.action_bar_custom_view_home);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        // actionBar.setDisplayShowHomeEnabled(true);
        // actionBar.setIcon(R.drawable.actionbar_logo);
    }

    public void switchScreenCaesar(View view) {

        Intent myIntent = new Intent(view.getContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
    }

    public void switchScreenVigenere(View view) {
        Intent myIntent = new Intent(view.getContext(), VigenereAcitvity.class);
        startActivityForResult(myIntent, 0);
    }
}

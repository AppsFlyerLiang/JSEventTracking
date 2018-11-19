package com.appsflyer.support.jseventtracking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnUrlLoading = findViewById(R.id.btnUrlLoading);
        btnUrlLoading.setOnClickListener(this);
        Button btnJavaScript = findViewById(R.id.btnJavaScript);
        btnJavaScript.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUrlLoading:
                startActivity(new Intent(this, UrlLoadingActivity.class));
                break;
            case R.id.btnJavaScript:
                startActivity(new Intent(this, JSEventActivity.class));
                break;
        }
    }
}

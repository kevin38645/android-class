package com.example.ch5_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        TextView output = (TextView) findViewById(R.id.lblOutput);
        float size = output.getTextSize();
        output.setTextSize(size + 5);
    }
}
package com.example.ch5_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
    implements View.OnClickListener, View.OnLongClickListener {
    private float original_size = 20;
    private TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        output = (TextView) findViewById(R.id.lblOutput);
        output.setTextSize(original_size);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
        btn.setOnLongClickListener(this);
    }
    @Override
    public void onClick(View view) {
        float size = output.getTextSize();
        output.setTextSize(size + 5);
    }
    @Override
    public boolean onLongClick(View view) {
        output.setTextSize(original_size);
        return true;
    }
}
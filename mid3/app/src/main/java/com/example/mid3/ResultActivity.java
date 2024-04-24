package com.example.mid3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    TextView getNameText;

    TextView getAgeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getNameText = findViewById(R.id.getNameText);
        getAgeText = findViewById(R.id.getAgeText);

        String name = getIntent().getStringExtra("userName");
        String age = getIntent().getStringExtra("userAge");

        getNameText.setText(name);
        getAgeText.setText(age);
    }

    public void backToScreenAction(View view){
        finish();
    }
}
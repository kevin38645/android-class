package com.example.toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void alertButtonClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("hello!")
            .setMessage("Welcome to App!")
            .setNegativeButton("Cancel",this)
            .setPositiveButton("OK",this)
            .show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                finish();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
package com.example.app_collection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class GuessNumber extends AppCompatActivity implements DialogInterface.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_number);
    }

    public void alertButtonClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("返回主畫面")
                .setMessage("您確定要離開嗎？")
                .setNegativeButton("取消",this)
                .setPositiveButton("好的",this)
                .setCancelable(false)
                .show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                Toast.makeText(this, "回到主畫面", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }

    public void click_button1(View view) {

    }

}
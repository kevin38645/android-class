package com.example.mid3;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity{
    private ActivityResultLauncher<Intent> activityResultLauncher;
    EditText inputNameText;
    EditText inputAgeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputNameText = findViewById(R.id.inputName);
        inputAgeText = findViewById(R.id.inputAge);

        activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String friends = data.getStringExtra("selectedFriends");
                        // 更新UI或處理數據
                    }
                }
            }
        );
    }

    public void clickSubmitButton(View view){
        Intent sendIntent = new Intent(MainActivity.this, ResultActivity.class);
        sendIntent.putExtra("userName",inputNameText.getText().toString());
        sendIntent.putExtra("userAge",inputAgeText.getText().toString());
        activityResultLauncher.launch(sendIntent);
    }
}
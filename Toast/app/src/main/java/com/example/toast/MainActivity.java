package com.example.toast;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity{

    private String answer;
    private EditText guessInput;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guessInput = findViewById(R.id.guessInput);
        resultText = findViewById(R.id.resultText);
        generateAnswer();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toF:
                // 啟動到首頁的Intent或執行其他操作
                return true;
            // 處理其他菜單項...
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void onGuess(View view) {
        String guess = guessInput.getText().toString();
        if (guess.length() != 4) {
            resultText.setText("請輸入四位數字");
            return;
        }
        String result = checkGuess(guess);
        resultText.setText(result);
        if (result.equals("4A0B")) {
            resultText.setText("恭喜你猜對了！答案是 " + answer);
            generateAnswer();
        }
    }

    private void generateAnswer() {
        Random random = new Random();
        Set<Character> set = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        while (set.size() < 4) {
            char digit = (char) ('0' + random.nextInt(10));
            if (set.add(digit)) {
                sb.append(digit);
            }
        }
        answer = sb.toString();
    }

    private String checkGuess(String guess) {
        int a = 0, b = 0;
        for (int i = 0; i < 4; i++) {
            char g = guess.charAt(i);
            if (g == answer.charAt(i)) {
                a++;
            } else if (answer.contains(String.valueOf(g))) {
                b++;
            }
        }
        return a + "A" + b + "B";
    }

    public void switch_button(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}

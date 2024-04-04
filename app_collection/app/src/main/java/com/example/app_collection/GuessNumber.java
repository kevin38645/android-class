package com.example.app_collection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GuessNumber extends AppCompatActivity implements DialogInterface.OnClickListener {
    String GameMode;
    String expectNumber = "";
    Spinner selectNumSpinner1;
    Spinner selectNumSpinner2;
    Spinner selectNumSpinner3;
    Spinner selectNumSpinner4;
    TextView warning_textView;
    int totalPlayTime = 0;
    String totalPlayLog = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_number);

        Initial();
    }

    void Initial(){
        GameMode = "NoRepeat";
       selectNumSpinner1 = findViewById(R.id.numberSelection1);
       selectNumSpinner2 = findViewById(R.id.numberSelection2);
       selectNumSpinner3 = findViewById(R.id.numberSelection3);
       selectNumSpinner4 = findViewById(R.id.numberSelection4);
       RandomNumber();

       warning_textView = findViewById(R.id.warning_text);
    }

    void RandomNumber(){
        totalPlayTime = 0;

        Random random = new Random();
        switch (GameMode){
            case "NoRepeat":
                List<Integer> numbers = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    numbers.add(i);
                }
                Collections.shuffle(numbers);
                List<Integer> selectedNumbers = numbers.subList(0, 4);
                for(int i=0;i< selectedNumbers.size();i++){
                    expectNumber += selectedNumbers.get(i);
                }
                System.out.println(selectedNumbers);
                break;
            case "HasRepeat":
                for (int i =0 ;i <4;i++){
                    int randomNumber = random.nextInt(10);
                    expectNumber += randomNumber;
                }
                break;
        }
        TextView textView = (TextView) findViewById(R.id.answer_debug);
        textView.setText(expectNumber);
    }

    public void ClickButton_GuessNumber(View view){
        String outputText = "";

        String selectedValue1 = selectNumSpinner1.getSelectedItem().toString();
        String selectedValue2 = selectNumSpinner2.getSelectedItem().toString();
        String selectedValue3 = selectNumSpinner3.getSelectedItem().toString();
        String selectedValue4 = selectNumSpinner4.getSelectedItem().toString();
        String playerGuessNumber = selectedValue1 + selectedValue2 + selectedValue3 + selectedValue4;

        switch (GameMode){
            case "NoRepeat":
                outputText = NoRepeatGuess(playerGuessNumber);
                break;
            case "HasRepeat":
                break;
        }

        TextView textView = (TextView) findViewById(R.id.player_guess);
        if(!outputText.equals("")){
            totalPlayLog = outputText + "\n" + totalPlayLog;
            textView.setText(totalPlayLog);
        }
    }

    private String NoRepeatGuess(String playerGuessNumber) {
        for(int i=0; i< playerGuessNumber.length();i++){
            char num = playerGuessNumber.charAt(i);
            if(playerGuessNumber.indexOf(num) != playerGuessNumber.lastIndexOf(num)){
                warning_textView.setText("警告！這個模式下無法輸入重複數字！");
                return "";
            }
        }
        warning_textView.setText("");
        totalPlayTime++;
        int A = 0;
        int B = 0;

        for(int i=0;i<4;i++){
            char oneNumChar = playerGuessNumber.charAt(i);
            String oneNumStr = Character.toString(oneNumChar);
            if(expectNumber.contains(oneNumStr)){
                if(expectNumber.charAt(i) == oneNumChar){
                    A++;
                }else{
                    B++;
                }
            }
        }

        return "猜 " + totalPlayTime + "：            " + playerGuessNumber + "            結果: "+ A + "A" + B + "B";
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


}
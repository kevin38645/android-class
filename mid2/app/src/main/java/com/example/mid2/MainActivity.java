package com.example.mid2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    String expectNumber = "";
    Spinner selectNumSpinner1;
    Spinner selectNumSpinner2;
    Spinner selectNumSpinner3;
    Spinner selectNumSpinner4;
    TextView player_guess;
    TextView answer_debug;
    TextView radio_result;
    int totalPlayTime = 0;
    String totalPlayLog = "";
    Button buttonGuess;
    RadioGroup genderSelection;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Initial();
    }

    void Initial(){
        genderSelection = findViewById(R.id.genderSelection);
        genderSelection.setOnCheckedChangeListener(this);
        radio_result = findViewById(R.id.radio_result);
        selectNumSpinner1 = findViewById(R.id.numberSelection1);
        selectNumSpinner2 = findViewById(R.id.numberSelection2);
        selectNumSpinner3 = findViewById(R.id.numberSelection3);
        selectNumSpinner4 = findViewById(R.id.numberSelection4);
        buttonGuess = findViewById(R.id.guessNumber);
        answer_debug = findViewById(R.id.answer_debug);
        player_guess = findViewById(R.id.player_guess);
        RandomNumber();
    }


    void RestartGame(){
        totalPlayTime = 0;
        totalPlayLog = "";
        expectNumber = "";
        answer_debug.setText("");
        player_guess.setText("");
        RandomNumber();
    }
    void RandomNumber(){
        Random random = new Random();
        for (int i =0 ;i <4;i++){
            int randomNumber = random.nextInt(10);
            expectNumber += randomNumber;
        }
        answer_debug.setText(expectNumber);
    }

    public void ClickButton_GuessNumber(View view){
        String outputText = "";

        String selectedValue1 = selectNumSpinner1.getSelectedItem().toString();
        String selectedValue2 = selectNumSpinner2.getSelectedItem().toString();
        String selectedValue3 = selectNumSpinner3.getSelectedItem().toString();
        String selectedValue4 = selectNumSpinner4.getSelectedItem().toString();
        String playerGuessNumber = selectedValue1 + selectedValue2 + selectedValue3 + selectedValue4;

        outputText = GuessRepeatNumber(playerGuessNumber);

        if(!outputText.equals("")){
            totalPlayLog = outputText + "\n" + totalPlayLog;
            player_guess.setText(totalPlayLog);
        }
    }

    public void RestartGame(View view){
        GenerateAlertTextBox("重新開始遊戲", "您要重新生成一個新的數字嗎？", "取消", "好的", 1);
    }

    private String GuessRepeatNumber(String playerGuessNumber) {
        totalPlayTime++;
        int A = 0;
        int B = 0;
        int C = 0;
        boolean[] repeatList = new boolean[10];
        int[] answerRepeatTimesList = new int[10];
        for(int i=0;i<10;i++) {
            repeatList[i] = false;
            answerRepeatTimesList[i] = 0;
        }

        for(int i=0;i<4;i++){
            int number = expectNumber.charAt(i) - '0';
            answerRepeatTimesList[number]++;
        }

        for(int x=0;x<4;x++){
            char oneNumChar = playerGuessNumber.charAt(x);
            for(int i=0;i<4;i++){
                if(expectNumber.charAt(i) == oneNumChar){
                    int playerGuessNumberInt = playerGuessNumber.charAt(i) - '0';

                    //Repeat number detect.
                    if(answerRepeatTimesList[playerGuessNumberInt] > 1){
                        //first time
                        if(!repeatList[playerGuessNumberInt]){
                            repeatList[playerGuessNumberInt] = true;
                            C++;
                        }
                    }

                    if(i == x){
                        A++;
                    }else{
                        B++;
                    }
                }
            }
            System.out.println("A: " + A + " - B: " + B + " - C: " + C);
        }


        if(A == 4){
            WinGame("恭喜答對", "恭喜您！您猜對了！\n您總共猜了 " + totalPlayTime +" 次", "","確認", 2);
        }

        return "猜 " + totalPlayTime + "：          " + playerGuessNumber + "            結果: "+ A + "A" + B + "B" + C + "C";
    }

    public void WinGame(String Title, String Message, String NegativeMsg, String PositiveMsg, int type){
        GenerateAlertTextBox("恭喜答對", "恭喜您！您猜對了！\n您總共猜了 " + totalPlayTime +" 次", "","確認", 2);
    }

    public void GenerateAlertTextBox(String Title, String Message, String NegativeMsg, String PositiveMsg, int type){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(Title)
                .setMessage(Message)
                .setNegativeButton(NegativeMsg, null)
                .setPositiveButton(PositiveMsg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (type == 0) {
                            // 離開的回應
                            Toast.makeText(MainActivity.this, "回到主畫面", Toast.LENGTH_SHORT).show();
                            finish();
                        }else if(type == 1){
                            RestartGame();
                            Toast.makeText(MainActivity.this, "重新生成數字完成！", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        String result = "";
        RadioButton buttonText = null;
        if(checkedId == R.id.radioMale){
            buttonText = findViewById(R.id.radioMale);
        }else if(checkedId == R.id.radioFemale){
            buttonText = findViewById(R.id.radioFemale);
        }
        result = "您選擇的性別：" + buttonText.getText();
        radio_result.setText(result);
    }
}
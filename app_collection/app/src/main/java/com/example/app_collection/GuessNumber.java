package com.example.app_collection;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GuessNumber extends AppCompatActivity{
    String currentGameMode;
    String expectNumber = "";
    Spinner selectNumSpinner1;
    Spinner selectNumSpinner2;
    Spinner selectNumSpinner3;
    Spinner selectNumSpinner4;
    TextView warning_textView;
    TextView player_guess;
    TextView answer_debug;
    TextView mode_text;
    int totalPlayTime = 0;
    String totalPlayLog = "";
    Button buttonGuess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_number);

        Initial();
    }

    void Initial(){
        currentGameMode = "HasRepeat";
        selectNumSpinner1 = findViewById(R.id.numberSelection1);
        selectNumSpinner2 = findViewById(R.id.numberSelection2);
        selectNumSpinner3 = findViewById(R.id.numberSelection3);
        selectNumSpinner4 = findViewById(R.id.numberSelection4);
        mode_text = findViewById(R.id.mode_text);
        buttonGuess = findViewById(R.id.GuessNumber);
        answer_debug = findViewById(R.id.answer_debug);
        player_guess = findViewById(R.id.player_guess);
        ModeChange(currentGameMode);
       RandomNumber();
       warning_textView = findViewById(R.id.warning_text);
    }

    void ModeChange(String gameMode){
        currentGameMode = gameMode;
        switch (gameMode){
            case "NoRepeat":
                mode_text.setText("沒有重複數字");
                break;
            case "HasRepeat":
                mode_text.setText("可能出現重複數字");
                break;
        }
    }
    void RestartGame(){

        totalPlayTime = 0;
        totalPlayLog = "";
        expectNumber = "";
        answer_debug.setText("");
        warning_textView.setText("");
        player_guess.setText("");
        UpdateButton("default");
        RandomNumber();
    }
    void RandomNumber(){
        Random random = new Random();
        switch (currentGameMode){
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
                break;
            case "HasRepeat":
                for (int i =0 ;i <4;i++){
                    int randomNumber = random.nextInt(10);
                    expectNumber += randomNumber;
                }
                break;
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

        switch (currentGameMode) {
            case "NoRepeat":
                outputText = NoRepeatGuess(playerGuessNumber);
                break;
            case "HasRepeat":
//                outputText = type_1A2B3C(playerGuessNumber);

                outputText = HasRepeatGuess(playerGuessNumber);
                break;
        }

        if(!outputText.equals("")){
            totalPlayLog = outputText + "\n" + totalPlayLog;
            player_guess.setText(totalPlayLog);
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

        if(totalPlayLog.contains(playerGuessNumber)){
            warning_textView.setText("您已經猜過這個數字！");
            return "";
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

        if(A == 4){
            WinGame();
        }

        return "猜 " + totalPlayTime + "：            " + playerGuessNumber + "            結果: "+ A + "A" + B + "B";
    }
    private String HasRepeatGuess(String playerGuessNumber) {
        if(totalPlayLog.contains(playerGuessNumber)){
            warning_textView.setText("您已經猜過這個數字！");
            return "";
        }

        warning_textView.setText("");
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
            WinGame();
        }

        return "猜 " + totalPlayTime + "：          " + playerGuessNumber + "            結果: "+ A + "A" + B + "B" + C + "C";
    }


    public void WinGame(){
        warning_textView.setText("恭喜您！您猜對了！");
        GenerateAlertTextBox("恭喜答對", "恭喜您！您猜對了！\n您總共猜了 " + totalPlayTime +" 次", "","確認", 2);
        UpdateButton("win");
    }

    public void UpdateButton(String type){
        int color;
        switch (type){
            case "win":
                color = ContextCompat.getColor(GuessNumber.this, R.color.gray_500); // 獲取顏色資源
                buttonGuess.setBackgroundTintList(ColorStateList.valueOf(color)); // 改變按鈕顏色
                buttonGuess.setEnabled(false);
                buttonGuess.setTextColor(Color.BLACK); // 改變按鈕顏色
                break;
            case "default":
                color = ContextCompat.getColor(GuessNumber.this, R.color.blue_500); // 獲取顏色資源
                buttonGuess.setBackgroundTintList(ColorStateList.valueOf(color)); // 改變按鈕顏色
                buttonGuess.setEnabled(true);
                buttonGuess.setTextColor(Color.WHITE); // 改變按鈕顏色
                break;
        }
    }
    public void BackToTitleScreen(View view){
        GenerateAlertTextBox("返回主畫面", "您確定要離開嗎？", "取消", "好的", 0);
    }
    public void RestartGame(View view){
        GenerateAlertTextBox("重新開始遊戲", "您要重新生成一個新的數字嗎？", "取消", "好的", 1);
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
                            Toast.makeText(GuessNumber.this, "回到主畫面", Toast.LENGTH_SHORT).show();
                            finish();
                        }else if(type == 1){
                            RestartGame();
                            Toast.makeText(GuessNumber.this, "重新生成數字完成！", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setCancelable(false)
                .show();
    }

    public void ModeSelectionTextBox(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] options = {"沒有重複數字", "可能出現重複數字"};
        builder.setTitle("選擇猜數字遊戲模式：")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println(which);
                        switch (which){
                            case 0:
                                ModeChange("NoRepeat");
                                break;
                            case 1:
                                ModeChange("HasRepeat");
                                break;
                        }
                        RestartGame();
                        Toast.makeText(GuessNumber.this, "模式已設定為：" + options[which], Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", null)
                .setCancelable(false)
                .show();
    }
}
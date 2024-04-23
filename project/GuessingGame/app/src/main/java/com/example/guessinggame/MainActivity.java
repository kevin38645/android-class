package com.example.guessinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String expectNumber = "";

    TextView answer_debug;
    TextView result_text;
    EditText inputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initial();
    }

    void Initial(){
        answer_debug = findViewById(R.id.answer_debug);
        inputField = findViewById(R.id.inputNumber);
        result_text = findViewById(R.id.result_text);
        RandomNumber();
    }

    public void GuessNumber(View view){
        String playerText = String.valueOf(inputField.getText());
        System.out.println(playerText);
        int A = 0;
        int B = 0;

        for(int i=0;i<4;i++){
            char oneNumChar = playerText.charAt(i);
            String oneNumStr = Character.toString(oneNumChar);
            if(expectNumber.contains(oneNumStr)){
                if(expectNumber.charAt(i) == oneNumChar){
                    A++;
                }else{
                    B++;
                }
            }
        }
        String build = A + "A" + B +"B";
        result_text.setText(build);
    }

    void RandomNumber(){
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        List<Integer> selectedNumbers = numbers.subList(0, 4);
        for(int i=0;i< selectedNumbers.size();i++){
            expectNumber += selectedNumbers.get(i);
        }

        answer_debug.setText(expectNumber);
    }
}
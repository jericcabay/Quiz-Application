package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Basic_Math extends AppCompatActivity {

    private TextView num1Text, num2Text, resultText, scoreText;
    private EditText answerInput;
    private Button checkButton;
    private int num1, num2, correctAnswer, score = 0;
    private int maxNumber = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_math);

        num1Text = findViewById(R.id.Number1);
        num2Text = findViewById(R.id.Number2);
        answerInput = findViewById(R.id.AnswerInput);
        checkButton = findViewById(R.id.Enter);

        scoreText = findViewById(R.id.Score);


        generateRandomNumber();

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });


    }
    private void generateRandomNumber() {
        Random random = new Random();
        num1 = random.nextInt(maxNumber) + 1;
        num2 = random.nextInt(maxNumber) + 1;
        correctAnswer = num1 * num2;

        if(score >= 10) {
            maxNumber = 20;
        }else if(score >= 20) {
            maxNumber = 40;
        }else if(score >= 40) {
            maxNumber = 60;
        }else if(score >= 60) {
            maxNumber = 80;
        }else {
            maxNumber = 100;
        }

        num1Text.setText(String.valueOf(num1));
        num2Text.setText(String.valueOf(num2));
    }
    private void checkAnswer() {
        String userInput = answerInput.getText().toString().trim();

        if(userInput.isEmpty()) {
            answerInput.setError("Please Enter an Answer");
            return;
        }

        int userAnswer = Integer.parseInt(userInput);
        if(userAnswer == correctAnswer) {
            score++;
            scoreText.setText("Score: " + score);

            generateRandomNumber();
        }else {
            answerInput.setError("Wrong answer! Try Again");
        }

        answerInput.setText("");
    }
}
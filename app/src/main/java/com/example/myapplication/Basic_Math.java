package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class Basic_Math extends AppCompatActivity {

    private TextView num1Text, num2Text, operatorText, scoreText;
    private EditText answerInput;
    private Button checkButton;
    private int num1, num2, correctAnswer, score = 0;
    private int maxNumber = 10;
    private char operator;

    private EditText draggableEditText;
    private View dropArea;
    private float dX, dY;
    private boolean isDropped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_math);

        // Initialize UI components
        num1Text = findViewById(R.id.Number1);
        num2Text = findViewById(R.id.Number2);
        answerInput = findViewById(R.id.AnswerInput);
        checkButton = findViewById(R.id.Enter);
        operatorText = findViewById(R.id.Operator);
        scoreText = findViewById(R.id.Score);

        draggableEditText = findViewById(R.id.draggableEditText);
        dropArea = findViewById(R.id.dropArea);

        // Generate first question
        generateRandomNumber();

        // Drag and drop functionality
        draggableEditText.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;
                    case MotionEvent.ACTION_UP:
                        checkIfDropped(v);
                        break;
                }
                return true;
            }
        });

        // Check answer button click event
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void generateRandomNumber() {
        Random random = new Random();

        // Adjust maxNumber based on score
        if (score >= 40) {
            maxNumber = 999;
        } else if (score >= 20) {
            maxNumber = 99;
        } else {
            maxNumber = 9;
        }

        num1 = random.nextInt(maxNumber) + 1;
        num2 = random.nextInt(maxNumber) + 1;

        // Randomly select an operator
        int operatorIndex = random.nextInt(4);
        switch (operatorIndex) {
            case 0:
                operator = '+';
                correctAnswer = num1 + num2;
                break;
            case 1:
                operator = '-';
                correctAnswer = num1 - num2;
                break;
            case 2:
                operator = '*';
                correctAnswer = num1 * num2;
                break;
            case 3:
                operator = '/';
                // Ensure num2 is not 0 to avoid division by zero
                while (num2 == 0) {
                    num2 = random.nextInt(maxNumber) + 1;
                }
                correctAnswer = num1 / num2; // Integer division
                break;
        }

        // Update UI
        num1Text.setText(String.valueOf(num1));
        num2Text.setText(String.valueOf(num2));
        operatorText.setText(String.valueOf(operator));
    }

    private void checkAnswer() {
        String userInput = answerInput.getText().toString().trim();

        if (userInput.isEmpty()) {
            answerInput.setError("Please Enter an Answer");
            return;
        }

        int userAnswer = Integer.parseInt(userInput);
        if (userAnswer == correctAnswer) {
            score++;
            scoreText.setText("Score: " + score);
            generateRandomNumber();
        } else {
            answerInput.setError("Wrong answer! Try Again");
        }

        answerInput.setText("");
    }

    private void checkIfDropped(View v) {
        float editX = v.getX();
        float editY = v.getY();

        float dropX = dropArea.getX();
        float dropY = dropArea.getY();
        float dropWidth = dropArea.getWidth();
        float dropHeight = dropArea.getHeight();

        // Check if EditText is inside drop area
        if (editX >= dropX && editX + v.getWidth() <= dropX + dropWidth &&
                editY >= dropY && editY + v.getHeight() <= dropY + dropHeight) {
            isDropped = true;
            Toast.makeText(this, "Dropped in the Area!", Toast.LENGTH_SHORT).show();
        } else {
            isDropped = false;
            Toast.makeText(this, "Not in the Drop Area!", Toast.LENGTH_SHORT).show();
        }
    }
}

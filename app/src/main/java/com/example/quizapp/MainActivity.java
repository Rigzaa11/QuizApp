package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView totalQuestions;
    TextView questions;
    Button ansA, ansB, ansC, ansD;
    Button submit;

    int score = 0;
    int questionTotal = QnA.questions.length;
    int currentQuest = 0;
    String selectedAns = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestions = findViewById(R.id.total_quest);
        questions = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submit = findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submit.setOnClickListener(this);

        totalQuestions.setText("Total Questions: "+questionTotal);

        loadNewQuestion();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {

        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);


        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.submit_btn){
            if(selectedAns.equals(QnA.CorrectAns[currentQuest])){
                score++;
            }
            currentQuest++;
            loadNewQuestion();

        }else{
            selectedAns = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GRAY);
        }
    }
    void loadNewQuestion(){
        if(currentQuest == questionTotal){
            finishQuiz();
            return;
        }

        questions.setText(QnA.questions[currentQuest]);
        ansA.setText(QnA.choices[currentQuest][0]);
        ansB.setText(QnA.choices[currentQuest][1]);
        ansC.setText(QnA.choices[currentQuest][2]);
        ansD.setText(QnA.choices[currentQuest][3]);
    }
    void finishQuiz(){
        String result = "";
        if( score > questionTotal*0.50){
            result = "Passed";
        }else{
            result = "Failed";
        }
        new AlertDialog.Builder(this)
                .setTitle(result)
                .setMessage("Score is "+score+" out of "+questionTotal)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }
    void restartQuiz(){
        score = 0;
        currentQuest = 0;
        loadNewQuestion();
    }
}
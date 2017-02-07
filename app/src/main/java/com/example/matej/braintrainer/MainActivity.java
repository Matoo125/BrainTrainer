package com.example.matej.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    RelativeLayout playground;
    TextView equationTextView, resultTextView, pointsTextView, timerTextView;
    ArrayList<Integer> answers = new ArrayList<>();
    int locationOfCorrectAnswer;
    Button button0, button1, button2, button3;
    int score = 0;
    int numberOfQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button)findViewById(R.id.startButton);
        playground = (RelativeLayout)findViewById(R.id.playground);
        equationTextView = (TextView)findViewById(R.id.equation);
        resultTextView = (TextView)findViewById(R.id.result);
        pointsTextView = (TextView)findViewById(R.id.points);
        timerTextView = (TextView)findViewById(R.id.time);
        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);

       // start();
    }

    public void start() {
        score = 0;
        numberOfQuestions = 0;
        startButton.setVisibility(View.INVISIBLE);
        playground.setVisibility(View.VISIBLE);
        pointsTextView.setText(R.string.default_points);

        generateQuestion();

        new CountDownTimer(31000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                resultTextView.setText("Your score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
                timerTextView.setText(R.string.time_zero);
                startButton.setText(R.string.try_again);
                startButton.setVisibility(View.VISIBLE);
            }
        }.start();

//        timerTextView.setText(R.string.default_time);
//        pointsTextView.setText((R.string.default_points));
//        resultTextView.setText("");
//        startButton.setVisibility(View.INVISIBLE);
    }


    public void generateQuestion() {

        // create random number generator
        Random rand = new Random();

        // generate two random numbers 0-20
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        // put generated numbers to equation text view
        equationTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        // generate random location where correct answer will be stored
        locationOfCorrectAnswer = rand.nextInt(4);

        // clear answers array
        answers.clear();

        // incorrect answer cannot match the correct answer
        int incorrectAnswer;

        // generate 4 numbers if for loop
        for (int i =0; i<4; i++) {
            // put correct answer to it's location
            if (i == locationOfCorrectAnswer) {
                answers.add(a + b);
            } else {
                // generate random number 0-40
                incorrectAnswer = rand.nextInt(41);
                // while incorrect answer is the same as correct generate again
                while (incorrectAnswer == a + b) {
                    incorrectAnswer = rand.nextInt(41);
                }
                // add incorrect answer to array
                answers.add(incorrectAnswer);
            }
        }

        // set all button texts to it's number from array
        button0.setText(String.format("%s", answers.get(0)));
        button1.setText(String.format("%s", answers.get(1)));
        button2.setText(String.format("%s", answers.get(2)));
        button3.setText(String.format("%s", answers.get(3)));
    }

    public void chooseAnswer(View view) {
        // Log.i("Tag",(String) view.getTag());

        // check if tag of button is equal to location of correct answer
        if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
            // increase score variable
            score ++;
            // set result text view to correct
            resultTextView.setText(R.string.correct);
        } else {
            // set result text view to wrong
            resultTextView.setText(R.string.wrong);
        }

        // increase number of answered questions
        numberOfQuestions++;
        // update score points text view
        pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        generateQuestion();
    }

    public void startButton(View view) {

        start();

    }

}

package com.ADITIAILAWADHI.aditieducationalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ADITIAILAWADHI.aditieducationalapp.helper.DBHelper;
import com.ADITIAILAWADHI.aditieducationalapp.helper.QuestionImageManager;
import com.ADITIAILAWADHI.aditieducationalapp.helper.SoundManager;

import java.util.Timer;
import java.util.TimerTask;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private SoundManager soundManager;
    private int marimbaSound;
    private int you_winSound;
    private QuestionImageManager questionImageManager;
    private String levelStr = "1";
    private int duration;
    private String username;
    private int score = 0;
    private int question_index = 0;

    // number of questions per game
    private final int no_of_questions = 3;

    // Getting values
    private TextView questionNumberView;
    private TextView durationView;
    private TextView scoreView;
    private ImageView questionImageView;
    private EditText answerText;
    private Button answerButton;
    private Timer timer;

    private Button play_againButton;

    // random array
    private int position = -1;
    Integer[] intArray = {0,1,2,3,4,5};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.score){
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //get level sent from SettingActivity
        Intent intent = getIntent();
        levelStr = intent.getStringExtra("levelStr");
        questionImageManager = new QuestionImageManager(levelStr, getAssets());
        dbHelper = new DBHelper(this);
        soundManager = new SoundManager(this);
        marimbaSound = soundManager.addSound(R.raw.marimba);
        you_winSound = soundManager.addSound(R.raw.you_win);
        // get view elements
        questionNumberView = findViewById(R.id.questionNumberView);
        durationView = findViewById(R.id.durationView);
        scoreView = findViewById(R.id.scoreView);
        questionImageView = findViewById(R.id.questionImageView);
        answerText = findViewById(R.id.answerText);
        answerButton = findViewById(R.id.answerButton);

        play_againButton = findViewById(R.id.play_againButton);

        List<Integer> intList = Arrays.asList(intArray);
        Collections.shuffle(intList);
        intList.toArray(intArray);

        playGame();

        //process user answer
        processUserAnswer();

        if (savedInstanceState != null){
            duration = savedInstanceState.getInt("duration", 1000);
        }

    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void processUserAnswer() {
        answerButton.setOnClickListener(view -> {
            //get user answer
            String userAnswer = answerText.getText().toString().trim();
            if (userAnswer.length() == 0){
                Toast.makeText(GameActivity.this, "please enter an answer", Toast.LENGTH_SHORT).show();
                return;
            }
            // check user answer with correct answer
            //if (userAnswer.equals(questionImageManager.getAnswer(question_index))){
            if (userAnswer.equals(questionImageManager.getAnswer(intArray[position]))){
                score += 10;
                int totalScore = 30;
                scoreView.setText(String.format("Score: %d/%d", score, totalScore));
                soundManager.play(you_winSound, 1);
                //increase score by 10
                //play a winning sound
                //update scoreview
            }else{
                soundManager.play(marimbaSound,1);
            }
            //check if it is not the last question
            if (question_index < no_of_questions){
                question_index++;
                showQuestion();
                //increase question_index by 1
                //showQuestion();
            }
            //check if it is already played the last question
            if(question_index == no_of_questions){
                stopTime();
                //stop the timer
                //some more action you may need to do
                answerButton.setText("Game Over");
                answerButton.setClickable(false);

                play_againButton.setVisibility(View.VISIBLE);
                play_againButton.setOnClickListener(v -> {
                    Intent intent = new Intent(GameActivity.this, SettingActivity.class);
                    startActivity(intent);
                });

                //save a record in the database
                dbHelper.insertPlayer(username,duration,levelStr,score);

                //testing
                //test_db();
            }
        });
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void playGame() {
        //get username
        SharedPreferences preferences = getSharedPreferences("aditi_item", MODE_PRIVATE);
        username = preferences.getString("username", "Aditi");
        //initialise score
        scoreView.setText(String.format("Score: 0/%d", 10 * no_of_questions));
        //clear input
        answerText.setText("");
        answerText.requestFocus();
        //reset question_index
        question_index = 0;
        //initialise question number
        questionNumberView.setText("Question: " + (question_index + 1));
        //start playing
        showQuestion();
        startTimer();
    }

    @SuppressLint("SetTextI18n")
    private void showQuestion() {
        //check if it is not the last question
        //Put Random number of question for good marks
        if (question_index < no_of_questions){

            //Non-Repeating Random number of question
            position++;
            questionImageView.setImageBitmap(questionImageManager.getImage(intArray[position]));

            //update question number
            questionNumberView.setText("Question: " + (question_index + 1));
            //clear input
            answerText.setText("");
            answerText.requestFocus();
        }
    }

    private void startTimer() {
        //counting time by seconds
        duration = -1;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                duration++;
                //update durationView
                durationView.setText(String.format("DURATION: %d", duration));
            }
        }, 1000, 1000);
    }

    private void stopTime(){
        if (timer != null){
            timer.cancel();
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("duration", duration);
    }
}
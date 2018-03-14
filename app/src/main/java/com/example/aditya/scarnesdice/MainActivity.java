package com.example.aditya.scarnesdice;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnRoll, btnHold, btnReset;
    TextView userScore, cpuScore, winner;
    ImageView die_image;
    int die_images[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};
    int userOverallScore = 0, userTurnScore = 0, cpuOverallScore = 0, cpuTurnScore = 0, temp, temp1, temp2,roll=0;
    Random random = new Random();
    Handler handler = new Handler();
    // Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.turn);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRoll = (Button) findViewById(R.id.roll);
        btnHold = (Button) findViewById(R.id.hold);
        btnReset = (Button) findViewById(R.id.reset);
        userScore = (TextView) findViewById(R.id.user_score);
        cpuScore = (TextView) findViewById(R.id.cpu_score);
        winner = (TextView) findViewById(R.id.win);
        die_image = (ImageView) findViewById(R.id.die);

        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDie();
            }
        });

        btnHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holdDie();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

    }

    public void rollDie() {
        Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.turn);
        roll = random.nextInt(6) + 1;
        temp1 = userOverallScore;
        die_image.startAnimation(rotation);
        die_image.setImageResource(die_images[roll - 1]);
        if (roll == 1) {
            userTurnScore = 0;
            temp1 = userOverallScore;
            cpuPlays();
        } else {
            userTurnScore += roll;
            temp1 += roll;
        }
        if (temp1 >= 100) {
            userOverallScore = temp1;
            updateLabels();
            winner.setText("You Win!");
            winner.setVisibility(View.VISIBLE);
            btnHold.setEnabled(false);
            btnRoll.setEnabled(false);
        }
    }

    public void holdDie() {
        userOverallScore += userTurnScore;
        userTurnScore = 0;
        updateLabels();
        cpuPlays();
    }

    public void cpuPlays() {
        Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.turn);
        btnHold.setEnabled(false);
        btnRoll.setEnabled(false);
        temp = 0;
        temp2 = cpuOverallScore;
        do {
            roll = random.nextInt(6) + 1;
            die_image.startAnimation(rotation);
            die_image.setImageResource(die_images[roll - 1]);
            if (roll == 1) {
                cpuTurnScore = 0;
                temp2 = cpuOverallScore;
                updateLabels();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnHold.setEnabled(true);
                        btnRoll.setEnabled(true);
                    }
                }, 2500);
                break;
            } else {
                cpuTurnScore += roll;
                temp += roll;
                temp2 += roll;
            }
            if (temp >= 20) {
                cpuOverallScore += cpuTurnScore;
                cpuTurnScore = 0;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnHold.setEnabled(true);
                        btnRoll.setEnabled(true);
                    }
                }, 2500);
                updateLabels();
                break;
            }
            if (temp2 >= 100) {
                cpuOverallScore = temp2;
                updateLabels();
                winner.setText("Computer Wins!");
                winner.setVisibility(View.VISIBLE);
                btnRoll.setEnabled(false);
                btnHold.setEnabled(false);
                break;
            }
        } while (roll != 1);
    }

    public void resetGame() {
        userOverallScore = userTurnScore = cpuTurnScore = cpuOverallScore = 0;
        btnHold.setEnabled(true);
        btnRoll.setEnabled(true);
        winner.setVisibility(View.INVISIBLE);
        updateLabels();
    }

    public void updateLabels() {
        userScore.setText("Your Score: " + userOverallScore);
        cpuScore.setText("Computer Score: " + cpuOverallScore);
    }
}

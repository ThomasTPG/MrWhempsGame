package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigInteger;

/**
 * Created by Thomas on 24/02/2016.
 */
public class ScoreScreen extends Activity {
    int mode;
    float timeRemaining;
    int win;
    int coinsCollected;
    int score;
    int previousScore;
    TextView textview1;
    TextView textview2;
    TextView textview3;
    TextView textview4;
    TextView textview5;
    LinearLayout ll;
    int completedLevel;
    boolean bonusLvl;
    LevelTimer data2;
    String coindatafilename = "Coin_data.txt";
    String coindatafilePath;
    int multiplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout);
        mode = getIntent().getIntExtra("Mode", 0);
        textview1 = (TextView) findViewById(R.id.scoreScreen1);
        textview2 = (TextView) findViewById(R.id.scoreScreen2);
        textview3 = (TextView) findViewById(R.id.scoreScreen3);
        textview4 = (TextView) findViewById(R.id.scoreScreen4);
        textview5 = (TextView) findViewById(R.id.scoreScreen5);
        ll = (LinearLayout) findViewById(R.id.mainlayoutscorescreen);
        completedLevel = getIntent().getIntExtra("CompletedLevel",1);
        LevelSpecifics data = new LevelSpecifics(completedLevel);
        data2 = new LevelTimer(completedLevel);
        bonusLvl = data.isBonusLevel();
        coindatafilePath = getFilesDir() + "/" + coindatafilename;
        multiplier = FileTools.readMultiplierFromFile(coindatafilePath);
        //If mode = 0, we're on the main game
        if (mode == 0) {
            win = getIntent().getIntExtra("Win", 0);
            if (win == 0) {
                timeRemaining = getIntent().getFloatExtra("TimeRemaining", 0);
            } else {
                timeRemaining = 0;
            }
            coinsCollected = getIntent().getIntExtra("Score", 0);
            writeMainScoreScreen();
        }
        //If mode = 1, we're on infinite mode
        if (mode == 1) {
            score = getIntent().getIntExtra("Score", 0);
            previousScore = getIntent().getIntExtra("PreviousScore", 0);
            writeInfiniteScoreScreen();
        }
    }

    private void writeMainScoreScreen() {
        switch (win) {
            case (1):
                textview1.setText("You have won!");
                break;
            case (0):
                textview1.setText("You lose!");
                if(!bonusLvl){
                    textview2.setText("Time remaining: " + timeRemaining);
                }
                else{
                    textview2.setText("You needed to collect " + data2.getScoreLimit() + " coins.");
                }
                break;
        }
        textview3.setText("Score: " + coinsCollected);
        textview4.setText("Multiplier: x" + multiplier);
        BigInteger Bmultiplier = BigInteger.valueOf(multiplier);
        BigInteger Bscore = BigInteger.valueOf(coinsCollected);
        Bscore = Bmultiplier.multiply(Bscore);
        textview5.setText("Coins collected: " + Bscore);

        addRetryButton();

        Button backToMenu = new Button(this);
        backToMenu.setText("Back to level select");
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent levelIntent = new Intent("thomas.MAINLEVELSELECT");
                startActivity(levelIntent);
                finish();
            }
        });
        ll.addView(backToMenu);
    }

    private void writeInfiniteScoreScreen() {
        if (score > previousScore) {
            textview1.setText("You beat your old highscore!");
        } else {
            textview1.setText("You didn't beat your old highscore.");
        }
        textview2.setText("Your score: " + score);
        textview3.setText("Your old highscore: " + previousScore);
        textview4.setText("Multiplier: x" + multiplier);
        BigInteger Bmultiplier = BigInteger.valueOf(multiplier);
        BigInteger Bscore = BigInteger.valueOf(score);
        Bscore = Bmultiplier.multiply(Bscore);
        textview5.setText("Coins collected: " + Bscore);

        addRetryButton();

        Button backToMenu = new Button(this);
        backToMenu.setText("Back to level select");
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent levelIntent = new Intent("thomas.INFINITELEVELSELECT");
                startActivity(levelIntent);
                finish();
            }
        });
        ll.addView(backToMenu);
    }

    public void addRetryButton(){
        Button retryLevel = new Button(this);
        retryLevel.setText("Retry");
        retryLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent levelIntent = new Intent("thomas.GAMEMAIN");
                levelIntent.putExtra("Level", completedLevel);
                levelIntent.putExtra("Mode", mode);
                startActivity(levelIntent);
                finish();
            }
        });
        ll.addView(retryLevel);
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();

    }

    @Override
    protected void onStop() {
        finish();
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        if (mode == 0){
            final Intent levelIntent = new Intent("thomas.MAINLEVELSELECT");
            startActivity(levelIntent);
            finish();
        }
        else if (mode == 1){
            final Intent levelIntent = new Intent("thomas.INFINITELEVELSELECT");
            startActivity(levelIntent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

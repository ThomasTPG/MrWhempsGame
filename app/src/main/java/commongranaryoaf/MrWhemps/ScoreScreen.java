package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.math.BigInteger;
import java.util.logging.Level;

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
    LinearLayout ll;
    int completedLevel;
    boolean bonusLvl;
    LevelTimer data2;
    String coindatafilename = "Coin_data.txt";
    String coindatafilePath;
    int multiplier;
    LevelSpecifics data;
    int achievements;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_layout);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mode = getIntent().getIntExtra("Mode", 0);
        ll = (LinearLayout) findViewById(R.id.mainlayoutscorescreen);
        completedLevel = getIntent().getIntExtra("CompletedLevel",1);
        achievements = getIntent().getIntExtra("Achievement",-1);
        data = new LevelSpecifics(completedLevel);
        data2 = new LevelTimer(completedLevel);
        bonusLvl = data.isBonusLevel();
        coindatafilePath = getFilesDir() + "/" + coindatafilename;
        multiplier = FileTools.readMultiplierFromFile(coindatafilePath);
        coinsCollected = getIntent().getIntExtra("Score", 0);
        //If mode = 0, we're on the main game
        if (mode == 0) {
            win = getIntent().getIntExtra("Win", 0);
            if (win == 0) {
                timeRemaining = getIntent().getFloatExtra("TimeRemaining", 0);
            } else {
                timeRemaining = 0;
            }

            writeMainScoreScreen();
        }
        //If mode = 1, we're on infinite mode
        if (mode == 1) {
            score = getIntent().getIntExtra("Score", 0);
            previousScore = getIntent().getIntExtra("PreviousScore", 0);
            writeInfiniteScoreScreen();
        }
        if (achievements >= 0){
            Toast.makeText(this, getResources().getString(R.string.achievement_announcement), Toast.LENGTH_LONG).show();
        }
    }

    private void writeMainScoreScreen() {
        LinearLayout mainScoreScreen = (LinearLayout) findViewById(R.id.mainlayoutscorescreen);

        //Add level header

        TextView level = new TextView(this);
        level.setGravity(Gravity.CENTER);
        level.setTextColor(ContextCompat.getColor(this, R.color.black));
        level.setTextSize(getResources().getDimension(R.dimen.score_screen_header));
        level.setText("Level " + completedLevel);
        mainScoreScreen.addView(level);

        //Add status header

        TextView status = new TextView(this);
        status.setGravity(Gravity.CENTER);
        status.setTextSize(getResources().getDimension(R.dimen.score_screen_sub_header));
        status.setTextColor(ContextCompat.getColor(this, R.color.black));
        switch (win) {
            case (1):
                status.setText("You have won!");
                break;
            case (0):
                if(!bonusLvl){
                    status.setText("You lose!\nTime remaining: " + timeRemaining);
                }
                else{
                    status.setText("You lose!\nYou needed to collect " + data2.getScoreLimit() + " coins.");
                }
                break;
        }
        mainScoreScreen.addView(status);

        //Add coin header

        TextView coinHeader = new TextView(this);
        coinHeader.setGravity(Gravity.CENTER);
        coinHeader.setTextColor(ContextCompat.getColor(this, R.color.black));
        coinHeader.setTextSize(getResources().getDimension(R.dimen.score_screen_sub_header));
        coinHeader.setText("\nCoins");
        mainScoreScreen.addView(coinHeader);

        TextView coinData = new TextView(this);
        coinData.setGravity(Gravity.CENTER);
        coinData.setTextColor(ContextCompat.getColor(this, R.color.black));


        BigInteger Bmultiplier = BigInteger.valueOf(multiplier);
        BigInteger Bscore = BigInteger.valueOf(coinsCollected);
        Bscore = Bmultiplier.multiply(Bscore);
        coinData.setText("\nCoins collected: " + coinsCollected + "\nMultiplier: x" + multiplier + "\nMultiplied Coins: " + Bscore);
        mainScoreScreen.addView(coinData);

        if (win == 1){
            if (completedLevel < 30){
                addNextLevelButton();
            }
        }
        else{
            addRetryButton();
        }


        Button backToMenu = new Button(this);
        backToMenu.setText("Back to level select");
        backToMenu.setTextSize(getResources().getDimension(R.dimen.score_screen_header));
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
        LinearLayout mainScoreScreen = (LinearLayout) findViewById(R.id.mainlayoutscorescreen);

        //Add level header

        TextView level = new TextView(this);
        level.setGravity(Gravity.CENTER);
        level.setTextColor(ContextCompat.getColor(this, R.color.black));
        level.setTextSize(getResources().getDimension(R.dimen.score_screen_header));
        level.setText("Level " + completedLevel);
        mainScoreScreen.addView(level);

        //Add hiscore header
        TextView hiscoreHeader = new TextView(this);
        hiscoreHeader.setGravity(Gravity.CENTER);
        hiscoreHeader.setTextColor(ContextCompat.getColor(this, R.color.black));
        hiscoreHeader.setTextSize(getResources().getDimension(R.dimen.score_screen_sub_header));

        if (score > previousScore) {
            hiscoreHeader.setText("You beat your old highscore!");
        } else {
            hiscoreHeader.setText("You didn't beat your old highscore.");
        }
        mainScoreScreen.addView(hiscoreHeader);

        //Display the scores
        TextView scores = new TextView(this);
        scores.setGravity(Gravity.CENTER);
        scores.setTextColor(ContextCompat.getColor(this, R.color.black));
        scores.setText("Your score: " + score + "\nYour old highscore: " + previousScore);
        mainScoreScreen.addView(scores);

        //Add coin header

        TextView coinHeader = new TextView(this);
        coinHeader.setGravity(Gravity.CENTER);
        coinHeader.setTextColor(ContextCompat.getColor(this, R.color.black));
        coinHeader.setTextSize(getResources().getDimension(R.dimen.score_screen_sub_header));
        coinHeader.setText("\nCoins");
        mainScoreScreen.addView(coinHeader);

        TextView coinData = new TextView(this);
        coinData.setGravity(Gravity.CENTER);
        coinData.setTextColor(ContextCompat.getColor(this, R.color.black));

        BigInteger Bmultiplier = BigInteger.valueOf(multiplier);
        BigInteger Bscore = BigInteger.valueOf(coinsCollected);
        Bscore = Bmultiplier.multiply(Bscore);
        coinData.setText("\nCoins collected: " + coinsCollected + "\nMultiplier: x" + multiplier + "\nMultiplied Coins: " + Bscore);
        mainScoreScreen.addView(coinData);

        //Add medal data
        TextView medalHeader = new TextView(this);
        medalHeader.setGravity(Gravity.CENTER);
        medalHeader.setTextColor(ContextCompat.getColor(this, R.color.black));
        medalHeader.setTextSize(getResources().getDimension(R.dimen.score_screen_sub_header));
        int maxScore = Math.max(score, previousScore);

        if (maxScore >= data.challengeScoreHard()){
            medalHeader.setText("\nMedal: Gold");
        }
        else if (maxScore >= data.challengeScoreMedium()){
            medalHeader.setText("\nMedal: Silver\nNext medal at score: " + data.challengeScoreHard());
        }
        else if (maxScore > data.challengeScoreEasy()){
            medalHeader.setText("\nMedal: Bronze\nNext medal at score: " + data.challengeScoreMedium());
        } else{
            medalHeader.setText("\nMedal: None\nNext medal at score: " + data.challengeScoreEasy());
        }
        mainScoreScreen.addView(medalHeader);


        addRetryButton();

        Button backToMenu = new Button(this);
        backToMenu.setText("Back to level select");
        backToMenu.setTextSize(getResources().getDimension(R.dimen.score_screen_header));
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
        retryLevel.setTextSize(getResources().getDimension(R.dimen.score_screen_header));
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

    public void addNextLevelButton(){
        Button nextLevel = new Button(this);
        nextLevel.setText("Next Level");
        nextLevel.setTextSize(getResources().getDimension(R.dimen.score_screen_header));
        nextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent levelIntent = new Intent("thomas.GAMEMAIN");
                levelIntent.putExtra("Level", completedLevel + 1);
                levelIntent.putExtra("Mode", mode);
                startActivity(levelIntent);
                finish();
            }
        });
        ll.addView(nextLevel);
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

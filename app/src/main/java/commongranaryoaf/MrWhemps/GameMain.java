package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Thomas on 05/02/2016.
 */
public class GameMain extends Activity implements View.OnTouchListener{

    GameView gameView;
    int level;
    TextView scoreboard;
    TextView timer;
    double timeRemaining;
    Thread scoreKeeper;
    boolean running;
    boolean ended;
    int score;
    int mode;
    boolean timerOn = false;
    LevelTimer lvlTimer;
    int win = 0;
    //A boolean which checks if we are on a bonus level
    boolean bonusLevel;
    int achievement = -1;
    InterstitialAd mInterstitialAd;
    boolean adShown = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameview_content);

        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                quitAfterAd();
            }
        });

        requestNewInterstitial();

        level = getIntent().getIntExtra("Level", 1);
        // mode =0 is normal, mode = 1 is infinite
        mode = getIntent().getIntExtra("Mode", 0);
        LevelSpecifics data = new LevelSpecifics(level);
        bonusLevel = false;
        if (mode == 0){
            timerOn = true;
            lvlTimer = new LevelTimer(level);
            bonusLevel = data.isBonusLevel();
        }
        gameView = (GameView) findViewById(R.id.gameviewsurface);
        scoreboard = (TextView) findViewById(R.id.score);
        timer = (TextView) findViewById(R.id.timer);
        gameView.setOnTouchListener(this);
        gameView.setLevel(level);
        running = true;
        ended = false;
        System.out.println("Created");
        if ((mode == 0) && !(bonusLevel)){
            scoreKeeper = new Thread(){
                @Override
                public void run() {
                    while (running){

                        try{
                            sleep(100);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                updateTimer();
                                updateScore();
                                if (lvlTimer.checkTimeUp()){
                                    win = 1;
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            gameExit();
                                        }
                                    });
                                }
                            }
                        });
                        if(gameView.getLose()){
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    gameExit();
                                }
                            });
                        }

                    }
                }
            };
        }
        else{
            scoreKeeper = new Thread(){
                @Override
                public void run() {
                    while (running){

                        try{
                            sleep(100);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                updateScore();
                            }
                        });
                        if (bonusLevel){
                            if (gameView.getLose()){
                                if ((score >= lvlTimer.getScoreLimit())){
                                    win = 1;
                                }
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        gameExit();
                                    }
                                });
                            }
                        }
                        else{
                            if (gameView.getLose()){
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        gameExit();
                                    }
                                });
                            }
                        }
                    }
                }
            };
        }

        scoreKeeper.start();
    }

    public void updateScore(){
        score = gameView.getScore();
        scoreboard.setText("Score: " + score + "   Level: " + level);
    }

    public void getAchievement(){
        achievement = gameView.getAchievement();
    }

    public void updateTimer(){
        timeRemaining = lvlTimer.getTimeRemaining();
        if (timeRemaining < 0){
            timeRemaining = 0;
        }
        timer.setText("Time remaining = " + timeRemaining);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("E7058BEECC773607F44055F079B22F7C")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onPause() {

        super.onPause();
        gameExit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        running = false;
        gameExit();
    }



    private void gameExit(){

        final Intent menuIntent;
        System.out.println(mode);
        if (mode == 1){
            menuIntent = new Intent("thomas.INFINITELEVELSELECT");

        }
        else{
            menuIntent = new Intent("thomas.MAINLEVELSELECT");
            menuIntent.putExtra("TimeRemaining", (float) timeRemaining);

        }
        menuIntent.putExtra("Score", score);
        getAchievement();
        menuIntent.putExtra("Achievement", achievement);

        gameView.onPause();
        running = false;
        System.out.println("RUNNING" + running);

        if (!ended){
            ended = true;
            menuIntent.putExtra("Level", level);
            menuIntent.putExtra("Win", win);
            if (mInterstitialAd.isLoaded() && !adShown) {
                adShown = true;
                mInterstitialAd.show();
            }
            else
            {
                startActivity(menuIntent);
                finish();
            }
        }

    }

    private void quitAfterAd()
    {
        final Intent menuIntent;
        System.out.println(mode);
        if (mode == 1){
            menuIntent = new Intent("thomas.INFINITELEVELSELECT");

        }
        else{
            menuIntent = new Intent("thomas.MAINLEVELSELECT");
            menuIntent.putExtra("TimeRemaining",(float) timeRemaining);

        }
        menuIntent.putExtra("Score", score);
        getAchievement();
        menuIntent.putExtra("Achievement", achievement);
        menuIntent.putExtra("Level", level);
        menuIntent.putExtra("Win", win);

        gameView.onPause();
        running = false;
        startActivity(menuIntent);
        finish();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
            gameView.setSpriteX(event.getX());
            switch(v.getId()){
                case (R.id.gameviewsurface):
                    if (event.getAction() == MotionEvent.ACTION_UP){
                        gameView.spriteJump();
                    }
                    break;
            }
            return true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mInterstitialAd.setAdListener(null);
        mInterstitialAd = null;
    }
}



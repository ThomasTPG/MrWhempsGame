package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.logging.Level;

/**
 * Created by Thomas on 07/02/2016.
 */
public class InfiniteLevelSelect extends Activity {
    String leveldatafilename = "Level_data.txt";
    Context context;
    int lengthStoredScore = 4;
    File leveldatafile;
    File coindatafile;
    String coindatafilename = "Coin_data.txt";
    String coindatafilePath;
    String leveldatafilePath;
    int levelsUnlocked;
    int goldCount = 0;
    int silverCount = 0;
    int bronzeCount = 0;
    boolean achievementMaxCoinsUnlocked;
    String achievementfilename = "Achievement_data.txt";
    File achievementdatafile;
    String achievementdatafilePath;
    boolean achievementAnnounce = false;
    LevelView levelSelectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setUpFiles();
        achievementdatafilePath = context.getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
        achievementMaxCoinsUnlocked = FileTools.readSpecificAchievementFromFile(20,achievementdatafilePath);
        levelsUnlocked = readLevelFromFile();
        int previousLevel = getIntent().getIntExtra("Level",0);
        int score;
        int multiplier;
        int achievement;
        if (previousLevel > 0){
            score = getIntent().getIntExtra("Score",0);
            achievement = getIntent().getIntExtra("Achievement",-1);
            multiplier = FileTools.readMultiplierFromFile(coindatafilePath);
            BigInteger Bmultiplier = BigInteger.valueOf(multiplier);
            BigInteger Bscore = BigInteger.valueOf(score);
            Bscore = Bmultiplier.multiply(Bscore);
            FileTools.writeCoinsToFile(Bscore, coindatafilePath, coindatafile);
            if (!achievementMaxCoinsUnlocked){
                if(FileTools.getYourCoins(coindatafilePath).equals(FileTools.getMaxCoins())){
                    FileTools.writeAchievementToFile(20,achievementdatafilePath,achievementdatafile);
                    achievement = 20;
                }
            }
            int previousScore = readScoreFromFile(previousLevel);
            if (score>previousScore){
                writeScoresFileData(previousLevel, score);
            }
            final Intent scoreIntent = new Intent("thomas.SCORESCREEN");
            scoreIntent.putExtra("Score",score);
            scoreIntent.putExtra("Mode",1);
            scoreIntent.putExtra("PreviousScore",previousScore);
            scoreIntent.putExtra("CompletedLevel",previousLevel);
            scoreIntent.putExtra("Achievement", achievement);
            startActivity(scoreIntent);

        }

        setUpView();

        //Create the background
        LinearLayout main_layout = (LinearLayout) findViewById(R.id.level_select_background);
        main_layout.setBackgroundResource(R.drawable.animated_shop_background);

        AnimationDrawable frameAnimation = (AnimationDrawable) main_layout.getBackground();

        frameAnimation.start();

    }

    private void setUpFiles(){
        leveldatafilePath = getFilesDir() + "/" + leveldatafilename;
        leveldatafile = new File(leveldatafilePath);
        coindatafilePath = getFilesDir() + "/" + coindatafilename;
        coindatafile = new File(coindatafilePath);
        checkFileExists();
    }

    private void setUpView(){
        //Creates the scrollview for the level select menu
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.level_select_content, null);

        // Create a LinearLayout element
        LinearLayout ll = (LinearLayout) v.findViewById(R.id.level_list);

        //Add the back button

        Button backToMenu = new Button(this);
        backToMenu.setText("Back");
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ll.addView(backToMenu);

        //Add the medal table to the top of the screen

        LinearLayout medalTable = new LinearLayout(this);
        medalTable.setOrientation(LinearLayout.HORIZONTAL);
        medalTable.setWeightSum(3);
        TextView bronzeMedals = new TextView(this);
        TextView silverMedals = new TextView(this);
        TextView goldMedals   = new TextView(this);
        bronzeMedals.setGravity(Gravity.CENTER_HORIZONTAL);
        silverMedals.setGravity(Gravity.CENTER_HORIZONTAL);
        goldMedals.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        bronzeMedals.setBackgroundColor(ContextCompat.getColor(this, R.color.bronze));
        bronzeMedals.setLayoutParams(params);
        silverMedals.setLayoutParams(params);
        goldMedals.setLayoutParams(params);
        silverMedals.setBackgroundColor(ContextCompat.getColor(this, R.color.silver));
        goldMedals.setBackgroundColor(ContextCompat.getColor(this, R.color.gold));
        medalTable.addView(bronzeMedals);
        medalTable.addView(silverMedals);
        medalTable.addView(goldMedals);
        ll.addView(medalTable);

        //Add the levels, level headers etc

        for (int ii = 1; ii< levelsUnlocked; ii++){

            Button newButton = new Button(this);
            switch(ii){
                case(8):
                    newButton.setText("Level 8: Bonus Level 1");
                    break;
                case(11):
                    newButton.setText("Level 11: Bonus Level 2");
                    break;
                case(17):
                    newButton.setText("Level 17: Bonus Level 3");
                    break;
                case(23):
                    newButton.setText("Level 23: Bonus Level 4");
                    break;
                case(28):
                    newButton.setText("Level 28: Bonus Level 5");
                    break;
                case(30):
                    newButton.setText("Level 30: Bonus Level 6");
                    break;
                default:
                    newButton.setText("Level" + ii);
                    break;
            }
            final int levelSelected = ii;
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent levelIntent = new Intent("thomas.GAMEMAIN");
                    levelIntent.putExtra("Level", levelSelected);
                    levelIntent.putExtra("Mode", 1);
                    startActivity(levelIntent);
                    finish();
                }
            });

            TextView score = new TextView(this);
            score.setText("Highscore = " + readScoreFromFile(ii));

            RelativeLayout relativeLayout = new RelativeLayout(this);


            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            newButton.setLayoutParams(buttonParams);
            relativeLayout.addView(newButton);

            RelativeLayout.LayoutParams scoreParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            scoreParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            scoreParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            score.setLayoutParams(scoreParams);
            relativeLayout.addView(score);

            LevelSpecifics challengeScores = new LevelSpecifics(ii);
            if (readScoreFromFile(ii) >= challengeScores.challengeScoreHard()) {
                score.setBackgroundColor(getResources().getColor(R.color.gold));
                score.setTextColor(getResources().getColor(R.color.black));
                goldCount++;
            } else if (readScoreFromFile(ii) >= challengeScores.challengeScoreMedium()) {
                score.setBackgroundColor(ContextCompat.getColor(this, R.color.silver));
                score.setTextColor(getResources().getColor(R.color.black));
                silverCount++;
            } else if (readScoreFromFile(ii) > challengeScores.challengeScoreEasy()){
                score.setBackgroundColor(ContextCompat.getColor(this, R.color.bronze));
                score.setTextColor(getResources().getColor(R.color.black));
                bronzeCount++;
            } else {
                score.setTextColor(getResources().getColor(R.color.white));
            }


            ll.addView(relativeLayout);


        }

        bronzeMedals.setText("Bronze: " + bronzeCount);
        bronzeMedals.setTextColor(getResources().getColor(R.color.black));
        silverMedals.setText("Silver: " + silverCount);
        silverMedals.setTextColor(getResources().getColor(R.color.black));
        goldMedals.setText("Gold: " + goldCount);
        goldMedals.setTextColor(getResources().getColor(R.color.black));

        // Display the view
        setContentView(v);

        //Check achievements unlocked
        checkAchievements();
    }

    public void checkAchievements(){

        //Check bronze achievements
        if(!FileTools.readSpecificAchievementFromFile(0,achievementdatafilePath)){
            if (bronzeCount + silverCount + goldCount >= 10){
                FileTools.writeAchievementToFile(0,achievementdatafilePath,achievementdatafile);
                achievementAnnounce = true;
            }
        }
        if(!FileTools.readSpecificAchievementFromFile(1,achievementdatafilePath)){
            if (bronzeCount + silverCount + goldCount>= 20){
                FileTools.writeAchievementToFile(1,achievementdatafilePath,achievementdatafile);
                achievementAnnounce = true;
            }
        }
        if(!FileTools.readSpecificAchievementFromFile(2,achievementdatafilePath)){
            if (bronzeCount +silverCount+goldCount>= 30){
                FileTools.writeAchievementToFile(2,achievementdatafilePath,achievementdatafile);
                achievementAnnounce = true;
            }
        }
        //Check silver achievements
        if(!FileTools.readSpecificAchievementFromFile(3,achievementdatafilePath)){
            if (silverCount +goldCount>= 10){
                FileTools.writeAchievementToFile(3,achievementdatafilePath,achievementdatafile);
                achievementAnnounce = true;
            }
        }
        if(!FileTools.readSpecificAchievementFromFile(4,achievementdatafilePath)){
            if (silverCount+goldCount >= 20){
                FileTools.writeAchievementToFile(4,achievementdatafilePath,achievementdatafile);
                achievementAnnounce = true;
            }
        }
        if(!FileTools.readSpecificAchievementFromFile(5,achievementdatafilePath)){
            if (silverCount +goldCount>= 30){
                FileTools.writeAchievementToFile(5,achievementdatafilePath,achievementdatafile);
                achievementAnnounce = true;
            }
        }
        //Check gold achievements
        if(!FileTools.readSpecificAchievementFromFile(6,achievementdatafilePath)){
            if (goldCount >= 10){
                FileTools.writeAchievementToFile(6,achievementdatafilePath,achievementdatafile);
                achievementAnnounce = true;
            }
        }
        if(!FileTools.readSpecificAchievementFromFile(7,achievementdatafilePath)){
            if (goldCount >= 20){
                FileTools.writeAchievementToFile(7,achievementdatafilePath,achievementdatafile);
                achievementAnnounce = true;
            }
        }
        if(!FileTools.readSpecificAchievementFromFile(8,achievementdatafilePath)){
            if (goldCount >= 30){
                FileTools.writeAchievementToFile(8,achievementdatafilePath,achievementdatafile);
                achievementAnnounce = true;
            }
        }
        if (achievementAnnounce){
            Toast.makeText(this, getResources().getString(R.string.achievement_announcement), Toast.LENGTH_LONG).show();
        }
    }

    public int readLevelFromFile(){
        String fileStored = readDataFromFile();
        String[] data;

        if (fileStored != "Error"){
            data = fileStored.split("=");
            return Integer.parseInt(data[0]);
        }
        return 0;

    }

    public void onClick(View v) {


        switch (v.getId()) {
            case (R.id.level1):
                final Intent levelIntent = new Intent("thomas.GAMEMAIN");
                levelIntent.putExtra("Level", 1);
                startActivity(levelIntent);
                finish();
                break;
            case (R.id.backfromlevels):
                final Intent menuIntent = new Intent("thomas.MAINMENU");
                startActivity(menuIntent);
                finish();
        }
    }

    public void writeScoresFileData(int level, int score){
        String fileStored = readDataFromFile();
        String[] scores;
        String[] data;
        String newScore = Integer.toString(score);
        String newBuffer = "";
        String newFileString = "";
        if (fileStored != "Error"){
            data = fileStored.split("=");
            scores = data[1].split("-");
            for (int jj = 0; jj<(4-newScore.length());jj++){
                newBuffer = newBuffer + "0";
            }
            scores[level-1] = newBuffer + newScore;
            newFileString = data[0] + "=";
            for (int kk = 0; kk<levelsUnlocked; kk++){
                newFileString = newFileString + scores[kk] + "-";
            }
            FileTools.writeToFile(newFileString, leveldatafile);
        }
    }



    public int readScoreFromFile(int level){
        //Level scores are stored in the following format: 001=0050-0020-0010-0094 etc. The first 3 digits
        // show the furthest level achieved. The following digits are the highscores for each level.
        String fileStored = readDataFromFile();
        String[] scores;
        String[] data;
        if (fileStored != "Error"){
            data = fileStored.split("=");
            scores = data[1].split("-");
            return Integer.parseInt(scores[level -1]);
        }
        return 0;
    }

    public String readDataFromFile(){
        String fileStored;
        try {
            FileInputStream ourFile = new FileInputStream(new File(leveldatafilePath));
            InputStreamReader reader = new InputStreamReader(ourFile);
            char[] buffer = new char[4+levelsUnlocked*(lengthStoredScore+1)];
            reader.read(buffer);
            fileStored = new String(buffer);
        } catch (Exception e){
            e.printStackTrace();
            fileStored = "Error";
        }
        return fileStored;
    }


    public void checkFileExists(){
        if (!leveldatafile.exists()){
            System.out.println("newfile");
            createDefaultFile();
        }else{
            System.out.println("File Exists");
        }

    }

    public void createDefaultFile(){
        System.out.println("Create new file");
        String defaultFileContent = "001=0000-";
        //for (int i = 0; i<numberOfLevels; i++){
          //  defaultFileContent = defaultFileContent + "0000-";
        //}
        FileTools.writeToFile(defaultFileContent, leveldatafile);

    }

    @Override
    public void onBackPressed() {
        final Intent levelIntent = new Intent("thomas.MAINMENU");
        startActivity(levelIntent);
        finish();
    }


}










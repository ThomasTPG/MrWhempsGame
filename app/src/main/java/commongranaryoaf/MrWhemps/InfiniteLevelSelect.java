package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Created by Thomas on 07/02/2016.
 */
public class InfiniteLevelSelect extends Activity {
    int purchasables = 50;
    String leveldatafilename = "Level_data.txt";
    Context context;
    int lengthStoredScore = 4;
    File leveldatafile;
    File coindatafile;
    String coindatafilename = "Coin_data.txt";
    String coindatafilePath;
    String leveldatafilePath;
    int levelsUnlocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();
        setUpFiles();
        levelsUnlocked = readLevelFromFile();
        int previousLevel = getIntent().getIntExtra("Level",0);
        int score = 0;
        int multiplier = 1;
        if (previousLevel > 0){
            score = getIntent().getIntExtra("Score",0);
            multiplier = FileTools.readMultiplierFromFile(coindatafilePath);
            BigInteger Bmultiplier = BigInteger.valueOf(multiplier);
            BigInteger Bscore = BigInteger.valueOf(score);
            Bscore = Bmultiplier.multiply(Bscore);
            FileTools.writeCoinsToFile(Bscore,coindatafilePath,coindatafile);
            int previousScore = readScoreFromFile(previousLevel);
            if (score>previousScore){
                writeScoresFileData(previousLevel, score);
            };
            final Intent scoreIntent = new Intent("thomas.SCORESCREEN");
            scoreIntent.putExtra("Score",score);
            scoreIntent.putExtra("Mode",1);
            scoreIntent.putExtra("PreviousScore",previousScore);
            scoreIntent.putExtra("CompletedLevel",previousLevel);
            startActivity(scoreIntent);

        }
        setUpView();

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

        // Find the ScrollView
        ScrollView sv = (ScrollView) v.findViewById(R.id.scrollView1);

        // Create a LinearLayout element
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        Button backToMenu = new Button(this);
        backToMenu.setText("Back");
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ll.addView(backToMenu);

        for (int ii = 1; ii< levelsUnlocked; ii++){
            TextView levelHeader = new TextView(this);
            switch (ii){
                case(1):
                    levelHeader.setText("Starting Out");
                    ll.addView(levelHeader);
                    break;
                case(4):
                    levelHeader.setText("Moving Platforms");
                    ll.addView(levelHeader);
                    break;
                case(8):
                    levelHeader.setText("Hard Platforms");
                    ll.addView(levelHeader);
                    break;
                case(18):
                    levelHeader.setText("Drones");
                    ll.addView(levelHeader);
                    break;
            }
            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

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


            horizontalLayout.addView(newButton);
            horizontalLayout.addView(score);
            LevelSpecifics challengeScores = new LevelSpecifics(ii);
            if (readScoreFromFile(ii) >= challengeScores.challengeScoreHard()){
                horizontalLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.gold));
            } else if (readScoreFromFile(ii) >= challengeScores.challengeScoreMedium()){
                horizontalLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.silver));
            } else if (readScoreFromFile(ii) > 0){
                horizontalLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.bronze));
            } else{
                horizontalLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            }


            ll.addView(horizontalLayout);


        }

        // Add the LinearLayout element to the ScrollView
        sv.addView(ll);

        // Display the view
        setContentView(v);
    }

    public int readLevelFromFile(){
        String fileStored = readDataFromFile();
        String[] data;
        System.out.println(fileStored);

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
            System.out.println(fileStored);
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
        System.out.println(defaultFileContent);
        FileTools.writeToFile(defaultFileContent, leveldatafile);

    }

    @Override
    public void onBackPressed() {
        final Intent levelIntent = new Intent("thomas.MAINMENU");
        startActivity(levelIntent);
        finish();
    }

}







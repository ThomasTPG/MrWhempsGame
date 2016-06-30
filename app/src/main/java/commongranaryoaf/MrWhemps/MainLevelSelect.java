package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
public class MainLevelSelect extends Activity {
    int purchasables = 50;
    String leveldatafilename = "Level_data.txt";
    Context context;
    int numberOfLevels = 30;
    int levelsUnlocked;
    int lengthStoredScore = 4;
    File leveldatafile;
    File coindatafile;
    String leveldatafilePath;
    String coindatafilename = "Coin_data.txt";
    String coindatafilePath;
    int score;
    int multiplier;
    int achievement;
    boolean achievementMaxCoinsUnlocked;
    String achievementfilename = "Achievement_data.txt";
    File achievementdatafile;
    String achievementdatafilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setUpFiles();
        achievementdatafilePath = context.getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
        achievementMaxCoinsUnlocked = FileTools.readSpecificAchievementFromFile(14,achievementdatafilePath);
        int achievement = getIntent().getIntExtra("Achievement",-1);
        int previousLevel = getIntent().getIntExtra("Level",0);
        int win = getIntent().getIntExtra("Win",0);
        levelsUnlocked = readLevelFromFile();
        if ((previousLevel == levelsUnlocked)  && win == 1){
            writeLevelFileData(previousLevel+1);
            levelsUnlocked = levelsUnlocked + 1;
        }
        if (previousLevel > 0){
            score = getIntent().getIntExtra("Score",0);
            multiplier = FileTools.readMultiplierFromFile(coindatafilePath);
            BigInteger Bmultiplier = BigInteger.valueOf(multiplier);
            BigInteger Bscore = BigInteger.valueOf(score);
            Bscore = Bmultiplier.multiply(Bscore);
            FileTools.writeCoinsToFile(Bscore,coindatafilePath,coindatafile);
            if (!achievementMaxCoinsUnlocked){
                if(FileTools.getYourCoins(coindatafilePath).equals(FileTools.getMaxCoins())){
                    FileTools.writeAchievementToFile(14,achievementdatafilePath,achievementdatafile);
                    achievement = 14;
                }
            }
            float timeRemaining = getIntent().getFloatExtra("TimeRemaining",0);
            final Intent scoreIntent = new Intent("thomas.SCORESCREEN");
            scoreIntent.putExtra("Score", score);
            scoreIntent.putExtra("Win",win);
            scoreIntent.putExtra("Mode", 0);
            scoreIntent.putExtra("TimeRemaining",timeRemaining);
            scoreIntent.putExtra("CompletedLevel",previousLevel);
            scoreIntent.putExtra("Achievement",achievement);
            scoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        int numberOfLevelsShown = Math.min(levelsUnlocked,numberOfLevels);

        for (int ii = 1; ii<= numberOfLevelsShown; ii++){
            //Add the level header
            TextView levelHeader = new TextView(this);
            TextView description = new TextView(this);
            int progress;
            switch (ii){
                case(1):
                    progress = Math.min(numberOfLevelsShown-1, 3);
                    levelHeader.setText("Starting Out " + progress + "/3");
                    description.setText("Easy levels to begin with.");
                    ll.addView(levelHeader);
                    ll.addView(description);
                    break;
                case(4):
                    progress = Math.min(numberOfLevelsShown-4,5);
                    levelHeader.setText("Moving Platforms " + progress +"/5");
                    ll.addView(levelHeader);
                    break;
                case(9):
                    progress = Math.min(numberOfLevelsShown-9,9);
                    levelHeader.setText("Hard Platforms" + progress +"/9");
                    ll.addView(levelHeader);
                    break;
                case(18):
                    progress = Math.min(numberOfLevelsShown-18,5);
                    levelHeader.setText("Drones " + progress +"/5");
                    ll.addView(levelHeader);
                    break;
            }
            //Add the level select button
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
                    levelIntent.putExtra("Mode", 0);
                    startActivity(levelIntent);
                    finish();
                }
            });
            horizontalLayout.addView(newButton);
            ll.addView(horizontalLayout);
        }

        // Add the LinearLayout element to the ScrollView
        sv.addView(ll);

        // Display the view
        setContentView(v);
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

    public void writeLevelFileData(int level){
        String fileStored = readDataFromFile();
        String levelData;
        String[] data;
        String newLevel = Integer.toString(level);
        String newBuffer = "";
        String newFileString = "";
        if (fileStored != "Error"){
            data = fileStored.split("=");
            for (int jj = 0; jj<(3-newLevel.length());jj++){
                newBuffer = newBuffer + "0";
            }
            levelData = newBuffer + newLevel;
            newFileString = levelData + "=" + data[1] + "0000-";
            FileTools.writeToFile(newFileString, leveldatafile);
        }
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

    public int readLevelFromFile(){
        String fileStored = readDataFromFile();
        String[] data;
        if (fileStored != "Error"){
            data = fileStored.split("=");
            return Integer.parseInt(data[0]);
        }
        return 0;
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







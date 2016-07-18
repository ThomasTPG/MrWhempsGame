package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

        // Create a LinearLayout element
        LinearLayout ll = (LinearLayout) v.findViewById(R.id.level_list);

        addBackButton(ll);

        int numberOfLevelsShown = Math.min(levelsUnlocked,numberOfLevels);

        for (int ii = 1; ii<= numberOfLevelsShown; ii++){

            addLevelHeader(ll, numberOfLevelsShown,ii);
            addStoryButton(ll, ii);
            addLevelButtons(ll,ii);
        }

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

    public void addLevelButtons(LinearLayout ll, int ii){

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
                levelIntent.putExtra("Mode", 0);
                startActivity(levelIntent);
                finish();
            }
        });

        RelativeLayout relativeLayout = new RelativeLayout(this);


        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        newButton.setLayoutParams(buttonParams);
        relativeLayout.addView(newButton);

        ll.addView(relativeLayout);
    }

    public void addLevelHeader(LinearLayout ll, int numberOfLevelsShown, int ii){
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
                levelHeader.setText("Moving Platforms " + progress + "/5");
                ll.addView(levelHeader);
                break;
            case(9):
                progress = Math.min(numberOfLevelsShown-9,9);
                levelHeader.setText("Hard Platforms" + progress +"/9");
                ll.addView(levelHeader);
                break;
            case(18):
                progress = Math.min(numberOfLevelsShown-18,6);
                levelHeader.setText("Drones " + progress +"/6");
                ll.addView(levelHeader);
                break;
            case(24):
                progress = Math.min(numberOfLevelsShown-24,7);
                if (levelsUnlocked > 30){
                    progress = 7;
                }
                levelHeader.setText("Laser walls " + progress +"/7");
                ll.addView(levelHeader);
                break;
        }
    }

    public void addBackButton(LinearLayout ll){
        Button backToMenu = new Button(this);
        backToMenu.setText("Back");
        backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ll.addView(backToMenu);
    }

    public void addStoryButton(LinearLayout ll, int ii){
        switch (ii){
            case (2):
                Button startStory = new Button(this);
                startStory.setText("Back");
                startStory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent storyIntent = new Intent("thomas.STORYMAIN");
                        storyIntent.putExtra("StoryNumber", 1);
                        startActivity(storyIntent);
                        finish();
                    }
                });
                ll.addView(startStory);
                break;

        }
    }

}







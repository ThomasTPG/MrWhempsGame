package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Thomas on 05/02/2016.
 */
public class storyMain extends Activity implements View.OnTouchListener{

    StoryView storyView;
    int storyNumber;
    boolean running;
    boolean ended;
    Thread storyMaker;
    String achievementfilename = "Achievement_data.txt";
    String achievementdatafilePath;
    boolean achievement10Unlocked;
    boolean achievement20Unlocked;
    boolean achievement30Unlocked;
    File achievementdatafile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storyview_content);
        storyNumber = getIntent().getIntExtra("StoryNumber", 1);

        storyView = (StoryView) findViewById(R.id.storyviewsurface);
        achievementdatafilePath = this.getFilesDir() + "/" + achievementfilename;
        achievement10Unlocked = FileTools.readSpecificAchievementFromFile(27,achievementdatafilePath);
        achievement20Unlocked = FileTools.readSpecificAchievementFromFile(28,achievementdatafilePath);
        achievement30Unlocked = FileTools.readSpecificAchievementFromFile(29,achievementdatafilePath);
        achievementdatafile = new File(achievementdatafilePath);

        storyView.setOnTouchListener(this);
        storyView.setStory(storyNumber);
        running = true;
        ended = false;

        storyMaker = new Thread(){
            @Override
            public void run() {
                while (running){
                    try{
                        sleep(100);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    if(storyView.getStop()){
                        storyExit();
                    }
                }
            }
        };


    storyMaker.start();
    }

    @Override
    protected void onPause() {

        super.onPause();
        storyExit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        running = false;
        storyExit();
    }



    private void storyExit(){
        final Intent menuIntent;
        menuIntent = new Intent("thomas.MAINLEVELSELECT");

        storyView.onPause();
        running = false;

        if (!ended){


            if (!achievement10Unlocked && storyNumber == 2)
            {
                FileTools.writeAchievementToFile(27,achievementdatafilePath,achievementdatafile);
                menuIntent.putExtra("StoryAchievement",10);
            }
            if (!achievement20Unlocked && storyNumber == 5)
            {
                FileTools.writeAchievementToFile(28,achievementdatafilePath,achievementdatafile);
                menuIntent.putExtra("StoryAchievement",10);
            }
            if (!achievement30Unlocked && storyNumber == 6)
            {
                FileTools.writeAchievementToFile(29,achievementdatafilePath,achievementdatafile);
                menuIntent.putExtra("StoryAchievement",10);
            }
            startActivity(menuIntent);
            finish();
            ended = true;
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(v.getId()){
            case (R.id.storyviewsurface):
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    storyView.nextImage();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}



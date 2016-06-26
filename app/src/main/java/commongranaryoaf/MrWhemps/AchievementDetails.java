package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Created by Thomas on 17/05/2016.
 */
public class AchievementDetails extends Activity {

    int page;  //The page the item is on
    int item;  //The position of the item on the page
    int index; //The position of the item in the file
    String achievementfilename = "Achievement_data.txt";
    File achievementdatafile;
    String achievementdatafilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        achievementdatafilePath = getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
        setContentView(R.layout.achievement_details_layout);
        page = getIntent().getIntExtra("Page",0);
        item = getIntent().getIntExtra("Image",1);
        index = 12 * (page) + (item-1);
        setImage();
    }

    public void setImage(){
        ImageView achievementImage = (ImageView) findViewById(R.id.achievementIcon);
        TextView description = (TextView) findViewById(R.id.description);

        switch (page){
            case (0):
                switch (item){
                    case(1):
                        Bitmap bronze1;
                        if(FileTools.readSpecificAchievementFromFile(0,achievementdatafilePath)){
                            bronze1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.achievementbronze1);
                        } else{
                            bronze1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.lockedachievement);
                        }
                        achievementImage.setImageBitmap(bronze1);
                        description.setText(this.getString(R.string.bronze1_description));
                        break;
                    case(2):
                        Bitmap bronze2;
                        if(FileTools.readSpecificAchievementFromFile(1,achievementdatafilePath)){
                            bronze2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.achievementbronze2);
                        } else{
                            bronze2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.lockedachievement);
                        }
                        achievementImage.setImageBitmap(bronze2);
                        description.setText(this.getString(R.string.bronze2_description));
                        break;
                    case(3):
                        Bitmap bronze3;
                        if(FileTools.readSpecificAchievementFromFile(2,achievementdatafilePath)){
                            bronze3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.achievementbronze3);
                        } else{
                            bronze3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.lockedachievement);
                        }
                        achievementImage.setImageBitmap(bronze3);
                        description.setText(this.getString(R.string.bronze3_description));
                        break;
                    case(4):
                        Bitmap silver1;
                        if(FileTools.readSpecificAchievementFromFile(3,achievementdatafilePath)){
                            silver1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.achievementsilver1);
                        } else{
                            silver1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.lockedachievement);
                        }
                        achievementImage.setImageBitmap(silver1);
                        description.setText(this.getString(R.string.silver1_description));
                        break;
                    case(5):
                        Bitmap silver2;
                        if(FileTools.readSpecificAchievementFromFile(4,achievementdatafilePath)){
                            silver2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.achievementsilver2);
                        } else{
                            silver2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.lockedachievement);
                        }
                        achievementImage.setImageBitmap(silver2);
                        description.setText(this.getString(R.string.silver2_description));
                        break;
                    case(6):
                        Bitmap silver3;
                        if(FileTools.readSpecificAchievementFromFile(5,achievementdatafilePath)){
                            silver3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.achievementsilver3);
                        } else{
                            silver3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.lockedachievement);
                        }
                        achievementImage.setImageBitmap(silver3);
                        description.setText(this.getString(R.string.silver3_description));
                        break;
                    case(7):
                        Bitmap gold1;
                        if(FileTools.readSpecificAchievementFromFile(6,achievementdatafilePath)){
                            gold1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.achievementgold1);
                        } else{
                            gold1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.lockedachievement);
                        }
                        achievementImage.setImageBitmap(gold1);
                        description.setText(this.getString(R.string.gold1_description));
                        break;
                    case(8):
                        Bitmap gold2;
                        if(FileTools.readSpecificAchievementFromFile(7,achievementdatafilePath)){
                            gold2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.achievementgold2);
                        } else{
                            gold2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.lockedachievement);
                        }
                        achievementImage.setImageBitmap(gold2);
                        description.setText(this.getString(R.string.gold2_description));
                        break;
                    case(9):
                        Bitmap gold3;
                        if(FileTools.readSpecificAchievementFromFile(8,achievementdatafilePath)){
                            gold3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.achievementgold3);
                        } else{
                            gold3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.lockedachievement);
                        }
                        achievementImage.setImageBitmap(gold3);
                        description.setText(this.getString(R.string.gold3_description));
                        break;
                    case(10):
                        Bitmap art1;
                        if(FileTools.readSpecificAchievementFromFile(9,achievementdatafilePath)){
                            art1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.achievementart1);
                        } else{
                            art1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.lockedachievement);
                        }
                        achievementImage.setImageBitmap(art1);
                        description.setText(this.getString(R.string.art1_description));
                        break;
                    case(11):
                        Bitmap art2;
                        if(FileTools.readSpecificAchievementFromFile(10,achievementdatafilePath)){
                            art2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.achievementart2);
                        } else{
                            art2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.lockedachievement);
                        }
                        achievementImage.setImageBitmap(art2);
                        description.setText(this.getString(R.string.art2_description));
                        break;
                    case(12):
                        Bitmap art3;
                        if(FileTools.readSpecificAchievementFromFile(11,achievementdatafilePath)){
                            art3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.achievementart3);
                        } else{
                            art3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.lockedachievement);
                        }
                        achievementImage.setImageBitmap(art3);
                        description.setText(this.getString(R.string.art3_description));
                        break;


                }
                break;
            case(1):
                switch (item){
                    case(1):
                        Bitmap underCoverPlatforms;
                        if(FileTools.readSpecificAchievementFromFile(12,achievementdatafilePath)){
                            underCoverPlatforms = BitmapFactory.decodeResource(this.getResources(), R.drawable.achievementplatforms16);
                        } else{
                            underCoverPlatforms = BitmapFactory.decodeResource(this.getResources(), R.drawable.lockedachievement);
                        }
                        achievementImage.setImageBitmap(underCoverPlatforms);
                        description.setText(this.getString(R.string.under_hard_platform_description));
                        break;

                }
                break;


        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClick(View v){
        switch (v.getId()){
            case (R.id.backtoachievements):
                onBackPressed();
                break;
        }
    }



}

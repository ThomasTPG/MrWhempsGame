package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Thomas on 18/06/2016.
 */
public class Achievements extends Activity{

    int buttonHeight = 0;
    int page;
    int numberOfPages = 3;
    String achievementfilename = "Achievement_data.txt";
    File achievementdatafile;
    String achievementdatafilePath;
    int numberOfAch = 24;
    int numberUnlocked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievements_layout);
        achievementdatafilePath = getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
        countAchievements();
        page = getIntent().getIntExtra("Page",0);
        setImageButtons();

        //Create the background
        LinearLayout main_layout = (LinearLayout) findViewById(R.id.achievements_background);
        main_layout.setBackgroundResource(R.drawable.animated_shop_background);

        AnimationDrawable frameAnimation = (AnimationDrawable) main_layout.getBackground();

        frameAnimation.start();
    }

    public Bitmap produceScaledImage(int id){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds= true;
        BitmapFactory.decodeResource(this.getResources(), id, options);

        int sampleSize = 1;
        int height = options.outHeight;
        while (height > 2 * buttonHeight){
            sampleSize *=2;
            height /=2;
        }

        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(this.getResources(),id,options);
    }

    public void setImageButtons(){
        TextView ach_banner = (TextView) findViewById(R.id.achievement_banner);
        ach_banner.setText("Achievements unlocked: " + numberUnlocked + "/" + numberOfAch);
        TextView pageNumber = (TextView) findViewById(R.id.page_number);
        pageNumber.setText("Page " + (page + 1) + " of 3");
        switch(page){
            case 0:
                loadPage0();
                break;
            case 1:
                loadPage1();
                break;
            case 2:
                loadPage2();
                break;
        }

    }

    public void loadPage0(){
        Bitmap locked = produceScaledImage(R.drawable.lockedachievement);
        RelativeLayout rel1 = (RelativeLayout) findViewById(R.id.rel1);
        RelativeLayout rel2 = (RelativeLayout) findViewById(R.id.rel2);
        RelativeLayout rel3 = (RelativeLayout) findViewById(R.id.rel3);
        rel1.setBackgroundColor(getResources().getColor(R.color.achievement_background));
        rel2.setBackgroundColor(getResources().getColor(R.color.achievement_background));
        rel3.setBackgroundColor(getResources().getColor(R.color.achievement_background));

        //Bronze1 Achievement
        ImageView image1 = (ImageView) findViewById(R.id.achievementView1);
        if (FileTools.readSpecificAchievementFromFile(0,achievementdatafilePath)){
            Bitmap bronze1 = produceScaledImage(R.drawable.achievementbronze1);
            image1.setImageBitmap(bronze1);
        } else{
            image1.setImageBitmap(locked);
        }

        //Bronze2 Achievement
        ImageView image2 = (ImageView) findViewById(R.id.achievementView2);
        if (FileTools.readSpecificAchievementFromFile(1,achievementdatafilePath)){
            Bitmap bronze2 = produceScaledImage(R.drawable.achievementbronze2);
            image2.setImageBitmap(bronze2);
        } else{
            image2.setImageBitmap(locked);
        }

        //Bronze3 Achievement
        ImageView image3 = (ImageView) findViewById(R.id.achievementView3);
        if (FileTools.readSpecificAchievementFromFile(2,achievementdatafilePath)){
            Bitmap bronze3 = produceScaledImage(R.drawable.achievementbronze3);
            image3.setImageBitmap(bronze3);
        } else{
            image3.setImageBitmap(locked);
        }

        //Silver1 Achievement
        ImageView image4 = (ImageView) findViewById(R.id.achievementView4);
        if (FileTools.readSpecificAchievementFromFile(3,achievementdatafilePath)){
            Bitmap silver1 = produceScaledImage(R.drawable.achievementsilver1);
            image4.setImageBitmap(silver1);
        } else{
            image4.setImageBitmap(locked);
        }

        //Silver2 Achievement
        ImageView image5 = (ImageView) findViewById(R.id.achievementView5);
        if (FileTools.readSpecificAchievementFromFile(4,achievementdatafilePath)){
            Bitmap silver2 = produceScaledImage(R.drawable.achievementsilver2);
            image5.setImageBitmap(silver2);
        } else{
            image5.setImageBitmap(locked);
        }

        //Silver3 Achievement
        ImageView image6 = (ImageView) findViewById(R.id.achievementView6);
        if (FileTools.readSpecificAchievementFromFile(5,achievementdatafilePath)){
            Bitmap silver3 = produceScaledImage(R.drawable.achievementsilver3);
            image6.setImageBitmap(silver3);
        } else{
            image6.setImageBitmap(locked);
        }

        //Gold1 Achievement
        ImageView image7 = (ImageView) findViewById(R.id.achievementView7);
        if (FileTools.readSpecificAchievementFromFile(6,achievementdatafilePath)){
            Bitmap gold1 = produceScaledImage(R.drawable.achievementgold1);
            image7.setImageBitmap(gold1);
        } else{
            image7.setImageBitmap(locked);
        }

        //Gold2 Achievement
        ImageView image8 = (ImageView) findViewById(R.id.achievementView8);
        if (FileTools.readSpecificAchievementFromFile(7,achievementdatafilePath)){
            Bitmap gold2 = produceScaledImage(R.drawable.achievementgold2);
            image8.setImageBitmap(gold2);
        } else{
            image8.setImageBitmap(locked);
        }

        //Gold3 Achievement
        ImageView image9 = (ImageView) findViewById(R.id.achievementView9);
        if (FileTools.readSpecificAchievementFromFile(8,achievementdatafilePath)){
            Bitmap gold3 = produceScaledImage(R.drawable.achievementgold3);
            image9.setImageBitmap(gold3);
        } else{
            image9.setImageBitmap(locked);
        }
    }

    public void loadPage1(){
        Bitmap locked = produceScaledImage(R.drawable.lockedachievement);
        RelativeLayout rel1 = (RelativeLayout) findViewById(R.id.rel1);
        RelativeLayout rel2 = (RelativeLayout) findViewById(R.id.rel2);
        RelativeLayout rel3 = (RelativeLayout) findViewById(R.id.rel3);
        rel1.setBackgroundColor(getResources().getColor(R.color.achievement_background));
        rel2.setBackgroundColor(getResources().getColor(R.color.achievement_background));
        rel3.setBackgroundColor(getResources().getColor(R.color.achievement_background));


        //Art1 Achievement (lvl 8)
        ImageView image1 = (ImageView) findViewById(R.id.achievementView1);
        if (FileTools.readSpecificAchievementFromFile(12,achievementdatafilePath)){
            Bitmap art1 = produceScaledImage(R.drawable.achievementart1);
            image1.setImageBitmap(art1);
        } else{
            image1.setImageBitmap(locked);
        }

        //Art2 Achievement (lvl 11)
        ImageView image2 = (ImageView) findViewById(R.id.achievementView2);
        if (FileTools.readSpecificAchievementFromFile(13,achievementdatafilePath)){
            Bitmap art2 = produceScaledImage(R.drawable.achievementart2);
            image2.setImageBitmap(art2);
        } else{
            image2.setImageBitmap(locked);
        }

        //Art3 Achievement (lvl 17)
        ImageView image3 = (ImageView) findViewById(R.id.achievementView3);
        if (FileTools.readSpecificAchievementFromFile(14,achievementdatafilePath)){
            Bitmap art3 = produceScaledImage(R.drawable.achievementart3);
            image3.setImageBitmap(art3);
        } else{
            image3.setImageBitmap(locked);
        }

        //Art4 Achievement (lvl 23)
        ImageView image4 = (ImageView) findViewById(R.id.achievementView4);
        if (FileTools.readSpecificAchievementFromFile(15,achievementdatafilePath)){
            Bitmap art4 = produceScaledImage(R.drawable.achievementart4);
            image4.setImageBitmap(art4);
        } else{
            image4.setImageBitmap(locked);
        }

        //Art5 Achievement (lvl 28)
        ImageView image5 = (ImageView) findViewById(R.id.achievementView5);
        if (FileTools.readSpecificAchievementFromFile(16,achievementdatafilePath)){
            Bitmap art5 = produceScaledImage(R.drawable.achievementart5);
            image5.setImageBitmap(art5);
        } else{
            image5.setImageBitmap(locked);
        }

        //Art6 Achievement (lvl 30)
        ImageView image6 = (ImageView) findViewById(R.id.achievementView6);
        if (FileTools.readSpecificAchievementFromFile(17,achievementdatafilePath)){
            Bitmap art6 = produceScaledImage(R.drawable.achievementart6);
            image6.setImageBitmap(art6);
        } else{
            image6.setImageBitmap(locked);
        }

        //Lvl16 platforms Achievement
        ImageView image7 = (ImageView) findViewById(R.id.achievementView7);
        if (FileTools.readSpecificAchievementFromFile(18,achievementdatafilePath)){
            Bitmap undercoverPlatforms = produceScaledImage(R.drawable.achievementplatforms16);
            image7.setImageBitmap(undercoverPlatforms);
        } else{
            image7.setImageBitmap(locked);
        }

        //Lvl22 drones Achievement
        ImageView image8 = (ImageView) findViewById(R.id.achievementView8);
        if (FileTools.readSpecificAchievementFromFile(19,achievementdatafilePath)){
            Bitmap droneCollision = produceScaledImage(R.drawable.achievementdrones22);
            image8.setImageBitmap(droneCollision);
        } else{
            image8.setImageBitmap(locked);
        }

        //Max coins Achievement
        ImageView image9 = (ImageView) findViewById(R.id.achievementView9);
        if (FileTools.readSpecificAchievementFromFile(20,achievementdatafilePath)){
            Bitmap maxCoins = produceScaledImage(R.drawable.achievementmaxcoins);
            image9.setImageBitmap(maxCoins);
        } else{
            image9.setImageBitmap(locked);
        }
    }

    public void loadPage2(){
        Bitmap locked = produceScaledImage(R.drawable.lockedachievement);
        RelativeLayout rel1 = (RelativeLayout) findViewById(R.id.rel1);
        RelativeLayout rel2 = (RelativeLayout) findViewById(R.id.rel2);
        RelativeLayout rel3 = (RelativeLayout) findViewById(R.id.rel3);
        rel1.setBackgroundColor(getResources().getColor(R.color.achievement_background));
        rel2.setBackgroundColor(getResources().getColor(R.color.transparent));
        rel3.setBackgroundColor(getResources().getColor(R.color.transparent));

        //four_drones Achievement (lvl 26)
        ImageView image1 = (ImageView) findViewById(R.id.achievementView1);
        if (FileTools.readSpecificAchievementFromFile(24,achievementdatafilePath)){
            Bitmap fourDrones = produceScaledImage(R.drawable.achievementfourdrones);
            image1.setImageBitmap(fourDrones);
        } else{
            image1.setImageBitmap(locked);
        }

        //15 consecutive walls Achievement (lvl 25)
        ImageView image2 = (ImageView) findViewById(R.id.achievementView2);
        if (FileTools.readSpecificAchievementFromFile(25,achievementdatafilePath)){
            Bitmap consecutiveDrones = produceScaledImage(R.drawable.achievementterriblehurdler);
            image2.setImageBitmap(consecutiveDrones);
        } else{
            image2.setImageBitmap(locked);
        }

        //5 consecutive walls Achievement (lvl 24)
        ImageView image3 = (ImageView) findViewById(R.id.achievementView3);
        if (FileTools.readSpecificAchievementFromFile(26,achievementdatafilePath)){
            Bitmap consecutiveWalls = produceScaledImage(R.drawable.achievementconsecutivedrones);
            image3.setImageBitmap(consecutiveWalls);
        } else{
            image3.setImageBitmap(locked);
        }

        ImageView image4 = (ImageView) findViewById(R.id.achievementView4);
        if (FileTools.readSpecificAchievementFromFile(27,achievementdatafilePath)){
            Bitmap level10 = produceScaledImage(R.drawable.achievement_10);
            image4.setImageBitmap(level10);
        } else{
            image4.setImageBitmap(locked);
        }

        ImageView image5 = (ImageView) findViewById(R.id.achievementView5);
        if (FileTools.readSpecificAchievementFromFile(28,achievementdatafilePath)){
            Bitmap level20 = produceScaledImage(R.drawable.achievement_20);
            image5.setImageBitmap(level20);
        } else{
            image5.setImageBitmap(locked);
        }

        ImageView image6 = (ImageView) findViewById(R.id.achievementView6);
        if (FileTools.readSpecificAchievementFromFile(29,achievementdatafilePath)){
            Bitmap level30 = produceScaledImage(R.drawable.achievement_30);
            image6.setImageBitmap(level30);
        } else{
            image6.setImageBitmap(locked);
        }

        ImageView image7 = (ImageView) findViewById(R.id.achievementView7);
        image7.setImageResource(R.color.transparent);

        ImageView image8 = (ImageView) findViewById(R.id.achievementView8);
        image8.setImageResource(R.color.transparent);

        ImageView image9 = (ImageView) findViewById(R.id.achievementView9);
        image9.setImageResource(R.color.transparent);
    }

    public void onClick(View v){
        final Intent details = new Intent("thomas.ACHIEVEMENTDETAILS");
        switch (v.getId()){

            case (R.id.backfromshop):
                final Intent menuIntent = new Intent("thomas.MAINMENU");
                startActivity(menuIntent);
                finish();
                break;

            case (R.id.nextpagebutton):
                page = (page +1) % numberOfPages;
                setImageButtons();
                break;

            case (R.id.preivouspagebutton):
                page = (page + numberOfPages-1) % numberOfPages;
                setImageButtons();
                break;

            case(R.id.achievementView1):
                details.putExtra("Image",1);
                openDetails(details);
                break;

            case(R.id.achievementView2):
                details.putExtra("Image", 2);
                openDetails(details);
                break;

            case(R.id.achievementView3):
                details.putExtra("Image",3);
                openDetails(details);
                break;

            case(R.id.achievementView4):
                details.putExtra("Image",4);
                openDetails(details);
                break;

            case(R.id.achievementView5):
                details.putExtra("Image",5);
                openDetails(details);
                break;

            case(R.id.achievementView6):
                details.putExtra("Image",6);
                openDetails(details);
                break;

            case(R.id.achievementView7):
                details.putExtra("Image",7);
                openDetails(details);
                break;

            case(R.id.achievementView8):
                details.putExtra("Image",8);
                openDetails(details);
                break;

            case(R.id.achievementView9):
                details.putExtra("Image",9);
                openDetails(details);
                break;
            case(R.id.achievementView10):
                details.putExtra("Image", 10);
                openDetails(details);
                break;
            case(R.id.achievementView11):
                details.putExtra("Image", 11);
                openDetails(details);
                break;
            case(R.id.achievementView12):
                details.putExtra("Image", 12);
                openDetails(details);
                break;
        }
    }

    public void openDetails(Intent details){
        details.putExtra("Page",page);
        startActivity(details);
    }

    @Override
    public void onBackPressed() {
        final Intent levelIntent = new Intent("thomas.MAINMENU");
        startActivity(levelIntent);
        finish();
    }

    public void countAchievements(){
        //Count page 1 achievements
        for (int ii = 0; ii<9;ii++){
            if (FileTools.readSpecificAchievementFromFile(ii,achievementdatafilePath)){
                numberUnlocked ++;
            }
        }

        //Count page 2 achievements
        for (int ii = 12; ii<21;ii++){
            if (FileTools.readSpecificAchievementFromFile(ii,achievementdatafilePath)){
                numberUnlocked ++;
            }
        }

        //Count page 3 achievements
        for (int ii = 24; ii<30;ii++){
            if (FileTools.readSpecificAchievementFromFile(ii,achievementdatafilePath)){
                numberUnlocked ++;
            }
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        RelativeLayout relLay = (RelativeLayout) findViewById(R.id.rel1);
        buttonHeight = relLay.getHeight();
        setImageButtons();
    }
}

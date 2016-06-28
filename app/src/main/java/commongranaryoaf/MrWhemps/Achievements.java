package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;

/**
 * Created by Thomas on 18/06/2016.
 */
public class Achievements extends Activity{

    int buttonHeight = 0;
    int page;
    int numberOfPages = 2;
    String achievementfilename = "Achievement_data.txt";
    File achievementdatafile;
    String achievementdatafilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievements_layout);
        achievementdatafilePath = getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
        page = getIntent().getIntExtra("Page",0);
        setImageButtons();
    }

    public Bitmap produceScaledImage(int id){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds= true;
        BitmapFactory.decodeResource(this.getResources(),id,options);

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
        switch(page){
            case 0:
                loadPage0();
                break;
            case 1:
                loadPage1();
                break;
        }

    }

    public void loadPage0(){
        Bitmap locked = produceScaledImage(R.drawable.lockedachievement);

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

        //Art1 Achievement
        ImageView image10 = (ImageView) findViewById(R.id.achievementView10);
        if (FileTools.readSpecificAchievementFromFile(9,achievementdatafilePath)){
            Bitmap art1 = produceScaledImage(R.drawable.achievementart1);
            image10.setImageBitmap(art1);
        } else{
            image10.setImageBitmap(locked);
        }

        //Art2 Achievement
        ImageView image11 = (ImageView) findViewById(R.id.achievementView11);
        if (FileTools.readSpecificAchievementFromFile(10,achievementdatafilePath)){
            Bitmap art2 = produceScaledImage(R.drawable.achievementart2);
            image11.setImageBitmap(art2);
        } else{
            image11.setImageBitmap(locked);
        }

        //Art3 Achievement
        ImageView image12 = (ImageView) findViewById(R.id.achievementView12);
        if (FileTools.readSpecificAchievementFromFile(11,achievementdatafilePath)){
            Bitmap art3 = produceScaledImage(R.drawable.achievementart3);
            image12.setImageBitmap(art3);
        } else{
            image12.setImageBitmap(locked);
        }
    }

    public void loadPage1(){
        Bitmap locked = produceScaledImage(R.drawable.lockedachievement);

        //Lvl16 platforms Achievement
        ImageView image1 = (ImageView) findViewById(R.id.achievementView1);
        if (FileTools.readSpecificAchievementFromFile(12,achievementdatafilePath)){
            Bitmap undercoverPlatforms = produceScaledImage(R.drawable.achievementplatforms16);
            image1.setImageBitmap(undercoverPlatforms);
        } else{
            image1.setImageBitmap(locked);
        }

        //Lvl22 platforms Achievement
        ImageView image2 = (ImageView) findViewById(R.id.achievementView2);
        if (FileTools.readSpecificAchievementFromFile(13,achievementdatafilePath)){
            Bitmap droneCollision = produceScaledImage(R.drawable.achievementdrones22);
            image2.setImageBitmap(droneCollision);
        } else{
            image2.setImageBitmap(locked);
        }

        //Max coins Achievement
        ImageView image3 = (ImageView) findViewById(R.id.achievementView3);
        if (FileTools.readSpecificAchievementFromFile(14,achievementdatafilePath)){
            Bitmap maxCoins = produceScaledImage(R.drawable.achievementmaxcoins);
            image3.setImageBitmap(maxCoins);
        } else{
            image3.setImageBitmap(locked);
        }
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        RelativeLayout relLay = (RelativeLayout) findViewById(R.id.imageViewBound);
        buttonHeight = relLay.getHeight();
        setImageButtons();
    }
}

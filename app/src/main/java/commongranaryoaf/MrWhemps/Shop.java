package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Thomas on 12/05/2016.
 */
public class Shop extends Activity {

    int page = 0;
    int numberOfPages = 2;
    int buttonHeight = 0;
    String achievementfilename = "Achievement_data.txt";
    File achievementdatafile;
    String achievementdatafilePath;
    File coindatafile;
    String coindatafilename = "Coin_data.txt";
    String coindatafilePath;
    int notSoldAlpha = 128;
    int soldAlpha = 255;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_layout);
        coindatafilePath = getFilesDir() + "/" + coindatafilename;
        coindatafile = new File(coindatafilePath);
        achievementdatafilePath = getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
        page = getIntent().getIntExtra("Page",0);
        setImageButtons();

        //Create the background
        LinearLayout main_layout = (LinearLayout) findViewById(R.id.shop_background);
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
        TextView shop_banner = (TextView) findViewById(R.id.shop_banner);
        TextView pageNumber = (TextView) findViewById(R.id.page_number);
        pageNumber.setText("Page " + (page + 1) + " of 2");
        switch(page){
            case 0:
                shop_banner.setText("Coin Multipliers");
                loadPage0();
                break;
            case 1:
                shop_banner.setText("Costumes");
                loadPage1();
                break;
        }

    }

    public void loadPage0(){
        RelativeLayout rel1 = (RelativeLayout) findViewById(R.id.rel1);
        RelativeLayout rel2 = (RelativeLayout) findViewById(R.id.rel2);
        RelativeLayout rel3 = (RelativeLayout) findViewById(R.id.rel3);
        rel1.setBackgroundColor(getResources().getColor(R.color.achievement_background));
        rel2.setBackgroundColor(getResources().getColor(R.color.achievement_background));
        rel3.setBackgroundColor(getResources().getColor(R.color.achievement_background));
        Bitmap coinx2 = produceScaledImage(R.drawable.coin2xbutton);
        Bitmap coinx3 = produceScaledImage(R.drawable.coin3xbutton);
        Bitmap coinx4 = produceScaledImage(R.drawable.coin4xbutton);
        Bitmap coinx5 = produceScaledImage(R.drawable.coin5xbutton);
        Bitmap coinx6 = produceScaledImage(R.drawable.coin6xbutton);
        Bitmap coinx8 = produceScaledImage(R.drawable.coin8xbutton);
        Bitmap coinx10 = produceScaledImage(R.drawable.coin10xbutton);
        Bitmap coinx20 = produceScaledImage(R.drawable.coin20xbutton);
        Bitmap coinx50 = produceScaledImage(R.drawable.coin50xbutton);
        ImageView image1 = (ImageView) findViewById(R.id.imageView1);
        image1.clearColorFilter();
        image1.setImageBitmap(coinx2);
        if(checkItemBought(0) == 0){
            image1.setAlpha(notSoldAlpha);
        } else {
            image1.setAlpha(soldAlpha);
            if(checkItemBought(0) == 2){
                image1.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }
        ImageView image2 = (ImageView) findViewById(R.id.imageView2);
        image2.clearColorFilter();
        image2.setImageBitmap(coinx3);
        if(checkItemBought(1) == 0){
            image2.setAlpha(notSoldAlpha);
        } else {
            image2.setAlpha(soldAlpha);
            if(checkItemBought(1) == 2){
                image2.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }
        ImageView image3 = (ImageView) findViewById(R.id.imageView3);
        image3.clearColorFilter();
        image3.setImageBitmap(coinx4);
        if(checkItemBought(2) == 0){
            image3.setAlpha(notSoldAlpha);
        } else {
            image3.setAlpha(soldAlpha);
            if(checkItemBought(2) == 2){
                image3.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }
        ImageView image4 = (ImageView) findViewById(R.id.imageView4);
        image4.clearColorFilter();
        image4.setImageBitmap(coinx5);
        if(checkItemBought(3) == 0){
            image4.setAlpha(notSoldAlpha);
        }else {
            image4.setAlpha(soldAlpha);
            if(checkItemBought(3) == 2){
                image4.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }
        ImageView image5 = (ImageView) findViewById(R.id.imageView5);
        image5.clearColorFilter();
        image5.setImageBitmap(coinx6);
        if(checkItemBought(4) == 0){
            image5.setAlpha(notSoldAlpha);
        }else {
            image5.setAlpha(soldAlpha);
            if(checkItemBought(4) == 2){
                image5.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }
        ImageView image6 = (ImageView) findViewById(R.id.imageView6);
        image6.clearColorFilter();
        image6.setImageBitmap(coinx8);
        if(checkItemBought(5) == 0){
            image6.setAlpha(notSoldAlpha);
        }else {
            image6.setAlpha(soldAlpha);
            if(checkItemBought(5) == 2){
                image6.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }
        ImageView image7 = (ImageView) findViewById(R.id.imageView7);
        image7.clearColorFilter();
        image7.setImageBitmap(coinx10);
        if(checkItemBought(6) == 0){
            image7.setAlpha(notSoldAlpha);
        }else {
            image7.setAlpha(soldAlpha);
            if(checkItemBought(6) == 2){
                image7.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }
        ImageView image8 = (ImageView) findViewById(R.id.imageView8);
        image8.clearColorFilter();
        image8.setImageBitmap(coinx20);
        if(checkItemBought(7) == 0){
            image8.setAlpha(notSoldAlpha);
        }else {
            image8.setAlpha(soldAlpha);
            if(checkItemBought(7) == 2){
                image8.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }
        ImageView image9 = (ImageView) findViewById(R.id.imageView9);
        image9.clearColorFilter();
        image9.setImageBitmap(coinx50);
        if(checkItemBought(8) == 0){
            image9.setAlpha(notSoldAlpha);
        }else {
            image9.setAlpha(soldAlpha);
            if(checkItemBought(8) == 2){
                image9.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }
    }

    public void loadPage1(){
        Bitmap locked = produceScaledImage(R.drawable.locked_costume);
        RelativeLayout rel1 = (RelativeLayout) findViewById(R.id.rel1);
        RelativeLayout rel2 = (RelativeLayout) findViewById(R.id.rel2);
        RelativeLayout rel3 = (RelativeLayout) findViewById(R.id.rel3);
        rel1.setBackgroundColor(getResources().getColor(R.color.achievement_background));
        rel2.setBackgroundColor(getResources().getColor(R.color.achievement_background));
        rel3.setBackgroundColor(getResources().getColor(R.color.achievement_background));

        //Bronze costume
        ImageView image1 = (ImageView) findViewById(R.id.imageView1);
        image1.clearColorFilter();
        if (FileTools.readSpecificAchievementFromFile(0,achievementdatafilePath)){
            Bitmap bronze = produceScaledImage(R.drawable.bronze_costume_icon);
            image1.setImageBitmap(bronze);
        } else{
            image1.setImageBitmap(locked);
        }
        if(checkItemBought(12) == 0){
            image1.setAlpha(notSoldAlpha);
        } else {
            image1.setAlpha(soldAlpha);
            if(checkItemBought(12) == 2){
                image1.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }

        //Silver costume
        ImageView image2 = (ImageView) findViewById(R.id.imageView2);
        image2.clearColorFilter();
        if (FileTools.readSpecificAchievementFromFile(4,achievementdatafilePath)){
            Bitmap silver = produceScaledImage(R.drawable.silver_costume_icon);
            image2.setImageBitmap(silver);
        } else{
            image2.setImageBitmap(locked);
        }
        if(checkItemBought(13) == 0){
            image2.setAlpha(notSoldAlpha);
        } else {
            image2.setAlpha(soldAlpha);
            if(checkItemBought(13) == 2){
                image2.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }

        //Gold costume
        ImageView image3 = (ImageView) findViewById(R.id.imageView3);
        image3.clearColorFilter();
        if (FileTools.readSpecificAchievementFromFile(8,achievementdatafilePath)){
            Bitmap gold = produceScaledImage(R.drawable.gold_costume_icon);
            image3.setImageBitmap(gold);
        } else{
            image3.setImageBitmap(locked);
        }
        if(checkItemBought(14) == 0){
            image3.setAlpha(notSoldAlpha);
        } else {
            image3.setAlpha(soldAlpha);
            if(checkItemBought(14) == 2){
                image3.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }

        //Newspaper costume
        ImageView image4 = (ImageView) findViewById(R.id.imageView4);
        image4.clearColorFilter();
        if (FileTools.readSpecificAchievementFromFile(18,achievementdatafilePath)){
            Bitmap newspaper = produceScaledImage(R.drawable.costume_newspaper_button);
            image4.setImageBitmap(newspaper);
        } else{
            image4.setImageBitmap(locked);
        }if(checkItemBought(15) == 0){
            image4.setAlpha(notSoldAlpha);
        } else {
            image4.setAlpha(soldAlpha);
            if(checkItemBought(15) == 2){
                image4.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }

        //Cubist costume
        ImageView image5 = (ImageView) findViewById(R.id.imageView5);
        image5.clearColorFilter();
        if (FileTools.readSpecificAchievementFromFile(15,achievementdatafilePath)){
            Bitmap cubist = produceScaledImage(R.drawable.cubist_costume_icon);
            image5.setImageBitmap(cubist);
        } else{
            image5.setImageBitmap(locked);
        }
        if(checkItemBought(16) == 0){
            image5.setAlpha(notSoldAlpha);
        } else {
            image5.setAlpha(soldAlpha);
            if(checkItemBought(16) == 2){
                image5.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }

        //Cyborg costume
        ImageView image6 = (ImageView) findViewById(R.id.imageView6);
        image6.clearColorFilter();
        if (FileTools.readSpecificAchievementFromFile(19,achievementdatafilePath)){
            Bitmap cyborg = produceScaledImage(R.drawable.cyborg_costume_icon);
            image6.setImageBitmap(cyborg);
        } else{
            image6.setImageBitmap(locked);
        }
        if(checkItemBought(17) == 0){
            image6.setAlpha(notSoldAlpha);
        } else {
            image6.setAlpha(soldAlpha);
            if(checkItemBought(17) == 2){
                image6.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }
        }

        //Neon costume
        ImageView image7 = (ImageView) findViewById(R.id.imageView7);
        image7.clearColorFilter();
        if (FileTools.readSpecificAchievementFromFile(14,achievementdatafilePath)){
            Bitmap neon = produceScaledImage(R.drawable.neon_costume_icon);
            image7.setImageBitmap(neon);
        } else{
            image7.setImageBitmap(locked);
        }
        if(checkItemBought(18) == 0){
            image7.setAlpha(notSoldAlpha);
        } else {
            image7.setAlpha(soldAlpha);
            if(checkItemBought(18) == 2){
                image7.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }

        }

        //6 arm costume
        ImageView image8 = (ImageView) findViewById(R.id.imageView8);
        image8.clearColorFilter();
        if (FileTools.readSpecificAchievementFromFile(29,achievementdatafilePath)){
            Bitmap sixarms = produceScaledImage(R.drawable.six_arm_icon);
            image8.setImageBitmap(sixarms);
        } else{
            image8.setImageBitmap(locked);
        }
        if(checkItemBought(19) == 0){
            image8.setAlpha(notSoldAlpha);
        } else {
            image8.setAlpha(soldAlpha);
            if(checkItemBought(19) == 2){
                image8.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }

        }

        //Wealthy costume
        ImageView image9 = (ImageView) findViewById(R.id.imageView9);
        image9.clearColorFilter();
        if (FileTools.readSpecificAchievementFromFile(20,achievementdatafilePath)){
            Bitmap wealthy = produceScaledImage(R.drawable.wealthy_costume_icon);
            image9.setImageBitmap(wealthy);
        } else{
            image9.setImageBitmap(locked);
        }
        if(checkItemBought(20) == 0){
            image9.setAlpha(notSoldAlpha);
        } else {
            image9.setAlpha(soldAlpha);
            if(checkItemBought(20) == 2){
                image9.setColorFilter(Color.GRAY, PorterDuff.Mode.LIGHTEN);
            }

        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void onClick(View v){
        final Intent details = new Intent("thomas.SHOPDETAILS");
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

            case(R.id.imageView1):
                details.putExtra("Image",1);
                openDetails(details);
                break;

            case(R.id.imageView2):
                details.putExtra("Image", 2);
                openDetails(details);
                break;

            case(R.id.imageView3):
                details.putExtra("Image",3);
                openDetails(details);
                break;

            case(R.id.imageView4):
                details.putExtra("Image",4);
                openDetails(details);
                break;

            case(R.id.imageView5):
                details.putExtra("Image",5);
                openDetails(details);
                break;

            case(R.id.imageView6):
                details.putExtra("Image",6);
                openDetails(details);
                break;

            case(R.id.imageView7):
                details.putExtra("Image",7);
                openDetails(details);
                break;

            case(R.id.imageView8):
                details.putExtra("Image",8);
                openDetails(details);
                break;

            case(R.id.imageView9):
                details.putExtra("Image",9);
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

    public int checkItemBought(int item){
        //Returns 0 is not purchased
        //Returns 1 if purchased but not activated
        //Returns 2 if purchased and activated
        String coinData = FileTools.readCoinsFromFile(coindatafilePath);
        String[] allData = coinData.split("=");
        String[] itemData = allData[1].split("-");
        if (Integer.parseInt(itemData[item]) == 0){
            return 0;
        }
        if (Integer.parseInt(itemData[item]) == 1){
            return 1;
        }
        return 2;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        RelativeLayout relLay = (RelativeLayout) findViewById(R.id.rel1);
        buttonHeight = relLay.getHeight();
        setImageButtons();
    }
}

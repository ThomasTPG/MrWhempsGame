package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Thomas on 18/06/2016.
 */
public class Achievements extends Activity{

    int buttonHeight = 0;
    int page;
    int numberOfPages = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievements_layout);
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
        Bitmap coinx3 = produceScaledImage(R.drawable.coin3xbutton);
        Bitmap coinx4 = produceScaledImage(R.drawable.coin4xbutton);
        Bitmap coinx5 = produceScaledImage(R.drawable.coin5xbutton);
        Bitmap coinx6 = produceScaledImage(R.drawable.coin6xbutton);
        Bitmap coinx8 = produceScaledImage(R.drawable.coin8xbutton);
        Bitmap coinx10 = produceScaledImage(R.drawable.coin10xbutton);
        Bitmap coinx20 = produceScaledImage(R.drawable.coin20xbutton);
        Bitmap coinx50 = produceScaledImage(R.drawable.coin50xbutton);
        ImageView image1 = (ImageView) findViewById(R.id.achievementView1);
        image1.setImageBitmap(locked);
        ImageView image2 = (ImageView) findViewById(R.id.achievementView2);
        image2.setImageBitmap(locked);
        ImageView image3 = (ImageView) findViewById(R.id.achievementView3);
        image3.setImageBitmap(locked);
        ImageView image4 = (ImageView) findViewById(R.id.achievementView4);
        image4.setImageBitmap(locked);
        ImageView image5 = (ImageView) findViewById(R.id.achievementView5);
        image5.setImageBitmap(locked);
        ImageView image6 = (ImageView) findViewById(R.id.achievementView6);
        image6.setImageBitmap(locked);
        ImageView image7 = (ImageView) findViewById(R.id.achievementView7);
        image7.setImageBitmap(locked);
        ImageView image8 = (ImageView) findViewById(R.id.achievementView8);
        image8.setImageBitmap(locked);
        ImageView image9 = (ImageView) findViewById(R.id.achievementView9);
        image9.setImageBitmap(locked);
        ImageView image10 = (ImageView) findViewById(R.id.achievementView10);
        image10.setImageBitmap(locked);
        ImageView image11 = (ImageView) findViewById(R.id.achievementView11);
        image11.setImageBitmap(locked);
        ImageView image12 = (ImageView) findViewById(R.id.achievementView12);
        image12.setImageBitmap(locked);
    }

    public void loadPage1(){
        Bitmap moustache = produceScaledImage(R.drawable.costume_moustache_button);
        Bitmap newspaper = produceScaledImage(R.drawable.costume_newspaper_button);
        Bitmap blacktie = produceScaledImage(R.drawable.costume_blacktie_button);
        Bitmap coinx5 = produceScaledImage(R.drawable.coin5xbutton);
        Bitmap coinx6 = produceScaledImage(R.drawable.coin6xbutton);
        Bitmap coinx8 = produceScaledImage(R.drawable.coin8xbutton);
        Bitmap coinx10 = produceScaledImage(R.drawable.coin10xbutton);
        Bitmap coinx20 = produceScaledImage(R.drawable.coin20xbutton);
        Bitmap coinx50 = produceScaledImage(R.drawable.coin50xbutton);
        ImageView image1 = (ImageView) findViewById(R.id.imageView1);
        image1.setImageBitmap(moustache);
        ImageView image2 = (ImageView) findViewById(R.id.imageView2);
        image2.setImageBitmap(newspaper);
        ImageView image3 = (ImageView) findViewById(R.id.imageView3);
        image3.setImageBitmap(blacktie);
        ImageView image4 = (ImageView) findViewById(R.id.imageView4);
        image4.setImageBitmap(coinx5);
        ImageView image5 = (ImageView) findViewById(R.id.imageView5);
        image5.setImageBitmap(coinx6);
        ImageView image6 = (ImageView) findViewById(R.id.imageView6);
        image6.setImageBitmap(coinx8);
        ImageView image7 = (ImageView) findViewById(R.id.imageView7);
        image7.setImageBitmap(coinx10);
        ImageView image8 = (ImageView) findViewById(R.id.imageView8);
        image8.setImageBitmap(coinx20);
        ImageView image9 = (ImageView) findViewById(R.id.imageView9);
        image9.setImageBitmap(coinx50);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        RelativeLayout relLay = (RelativeLayout) findViewById(R.id.imageViewBound);
        buttonHeight = relLay.getHeight();
        setImageButtons();
    }
}

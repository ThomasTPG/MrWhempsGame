package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

/**
 * Created by Thomas on 12/05/2016.
 */
public class Shop extends Activity {

    int page = 0;
    int numberOfPages = 2;
    int buttonHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_layout);
        page = getIntent().getIntExtra("Page",0);
        setImageButtons();

        //Create the background
        LinearLayout main_layout = (LinearLayout) findViewById(R.id.shop_background);
        main_layout.setBackgroundResource(R.drawable.animated_menu_background);

        AnimationDrawable frameAnimation = (AnimationDrawable) main_layout.getBackground();

        frameAnimation.start();
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
        image1.setImageBitmap(coinx2);
        ImageView image2 = (ImageView) findViewById(R.id.imageView2);
        image2.setImageBitmap(coinx3);
        ImageView image3 = (ImageView) findViewById(R.id.imageView3);
        image3.setImageBitmap(coinx4);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        RelativeLayout relLay = (RelativeLayout) findViewById(R.id.imageViewBound);
        buttonHeight = relLay.getHeight();
        setImageButtons();
    }
}

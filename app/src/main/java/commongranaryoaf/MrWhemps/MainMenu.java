package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * Created by Thomas on 12/02/2016.
 */
public class MainMenu extends Activity {

    String coindatafilename = "Coin_data.txt";
    String achievementfilename = "Achievement_data.txt";
    File coindatafile;
    File achievementdatafile;
    String coindatafilePath;
    String achievementdatafilePath;
    float startTime = 0;
    float timeElapsed = 0;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu_layout);
        TextView coins = (TextView) findViewById(R.id.cointextmainmenu);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        setUpFiles();
        coins.setText("Coins: " + FileTools.getYourCoins(coindatafilePath));

        //Create the background
        RelativeLayout main_layout = (RelativeLayout) findViewById(R.id.main_menu_background);
        main_layout.setBackgroundResource(R.drawable.animated_menu_background);

        AnimationDrawable frameAnimation = (AnimationDrawable) main_layout.getBackground();

        frameAnimation.start();
    }

    private void setUpFiles(){
        coindatafilePath = getFilesDir() + "/" + coindatafilename;
        achievementdatafilePath = getFilesDir() + "/" + achievementfilename;
        coindatafile = new File(coindatafilePath);
        achievementdatafile = new File(achievementdatafilePath);
        checkFileExists();
    }

    public void checkFileExists(){
        if (!coindatafile.exists()){
            System.out.println("newfile");
            FileTools.createDefaultCoinFile(coindatafile);

        }else{
            System.out.println("File Exists");
        }
        if (!achievementdatafile.exists()){
            System.out.println("newfile");
            FileTools.createDefaultAchFile(achievementdatafile);

        }else{
            System.out.println("File Exists");
        }
    }

    public void onClick(View v){

        switch (v.getId()){
            case (R.id.startbutton):
                final Intent levelIntent = new Intent("thomas.MAINLEVELSELECT");
                startActivity(levelIntent);
                finish();
                break;
            case (R.id.startinfinitebutton):
                final Intent infiniteIntent = new Intent("thomas.INFINITELEVELSELECT");
                startActivity(infiniteIntent);
                finish();
                break;
            case (R.id.settingsmenu):
                final Intent settingsIntent = new Intent("thomas.SETTINGS");
                startActivity(settingsIntent);
                finish();
                break;
            case (R.id.shopmenu):
                final Intent shopIntent = new Intent("thomas.SHOP");
                startActivity(shopIntent);
                finish();
                break;
            case (R.id.achievementmenu):
                final Intent achievementIntent = new Intent("thomas.ACHIEVEMENTS");
                startActivity(achievementIntent);
                finish();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (startTime > 0){
            timeElapsed = (System.nanoTime() - startTime)/1000000000;
            if (timeElapsed < Toast.LENGTH_LONG){
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else{
                startTime = 0;
                timeElapsed = 0;
            }

        }
        else{
            Toast.makeText(this, getResources().getString(R.string.quitting_warning), Toast.LENGTH_LONG).show();

            startTime = System.nanoTime();
        }
    }
}

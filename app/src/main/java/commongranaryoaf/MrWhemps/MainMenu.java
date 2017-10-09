package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;


import java.io.File;

/**
 * Created by Thomas on 12/02/2016.
 */
public class MainMenu extends BaseGameActivity {

    String coindatafilename = "Coin_data.txt";
    String achievementfilename = "Achievement_data.txt";
    String filenameSetting = "settings.txt";
    File fileSettings;
    String fileSettingsPath;
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
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("E7058BEECC773607F44055F079B22F7C")
                .build();
        mAdView.loadAd(adRequest);

        setUpFiles();
        coins.setText("Coins: " + FileTools.getYourCoins(coindatafilePath));

        if(getApiClient().isConnected())
        {
            onSignInSucceeded();
        }
        else
        {
            onSignInFailed();
        }

        //Create the background
        RelativeLayout main_layout = (RelativeLayout) findViewById(R.id.main_menu_background);
        main_layout.setBackgroundResource(R.drawable.animated_menu_background);

        AnimationDrawable frameAnimation = (AnimationDrawable) main_layout.getBackground();

        frameAnimation.start();
        updateOnlineAchievements();
    }

    private void setUpFiles(){
        coindatafilePath = getFilesDir() + "/" + coindatafilename;
        achievementdatafilePath = getFilesDir() + "/" + achievementfilename;
        coindatafile = new File(coindatafilePath);
        achievementdatafile = new File(achievementdatafilePath);
        fileSettingsPath = getFilesDir() + "/" + filenameSetting;
        fileSettings = new File(fileSettingsPath);
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
        if (!fileSettings.exists())
        {
            FileTools.writeToFile(FileTools.jumpOnRelease, fileSettings);
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
                if (getApiClient().isConnected())
                {
                    startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()),
                            0);
                }
                else
                {
                    final Intent achievementIntent = new Intent("thomas.ACHIEVEMENTS");
                    startActivity(achievementIntent);
                    finish();
                }
                break;
            case (R.id.sign_in_button):
                beginUserInitiatedSignIn();
                break;
            case (R.id.sign_out_button):
                signOut();
                onSignInFailed();
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

    @Override
    public void onSignInFailed() {
        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
        findViewById(R.id.sign_out_button).setVisibility(View.GONE);
    }

    @Override
    public void onSignInSucceeded() {
        findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getApiClient().isConnected())
        {
            onSignInSucceeded();
        }
        else
        {
            onSignInFailed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getApiClient().isConnected())
        {
            onSignInSucceeded();
        }
        else
        {
            onSignInFailed();
        }
    }

    private void updateOnlineAchievements()
    {
        achievementdatafilePath = getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
        if (getApiClient().isConnected())
        {
            if(FileTools.readSpecificAchievementFromFile(0,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_bronze1_id));
            }
            if(FileTools.readSpecificAchievementFromFile(1,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_bronze2_id));
            }
            if(FileTools.readSpecificAchievementFromFile(2,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_bronze3_id));
            }
            if(FileTools.readSpecificAchievementFromFile(3,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_silver1_id));
            }
            if(FileTools.readSpecificAchievementFromFile(4,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_silver2_id));
            }
            if(FileTools.readSpecificAchievementFromFile(5,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_silver3_id));
            }
            if(FileTools.readSpecificAchievementFromFile(6,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_gold1_id));
            }
            if(FileTools.readSpecificAchievementFromFile(7,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_gold2_id));
            }
            if(FileTools.readSpecificAchievementFromFile(8,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_gold3_id));
            }
            if(FileTools.readSpecificAchievementFromFile(12,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_oldmaster));
            }
            if(FileTools.readSpecificAchievementFromFile(13,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_scream));
            }
            if(FileTools.readSpecificAchievementFromFile(14,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_pop));
            }
            if(FileTools.readSpecificAchievementFromFile(15,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_cubist));
            }
            if(FileTools.readSpecificAchievementFromFile(16,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_starry));
            }
            if(FileTools.readSpecificAchievementFromFile(17,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_vitr));
            }
            if(FileTools.readSpecificAchievementFromFile(18,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_under));
            }
            if(FileTools.readSpecificAchievementFromFile(19,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_coll));
            }
            if(FileTools.readSpecificAchievementFromFile(20,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_wealth));
            }
            if(FileTools.readSpecificAchievementFromFile(24,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_four));
            }
            if(FileTools.readSpecificAchievementFromFile(25,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_terr));
            }
            if(FileTools.readSpecificAchievementFromFile(26,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_great));
            }
            if(FileTools.readSpecificAchievementFromFile(27,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_one));
            }
            if(FileTools.readSpecificAchievementFromFile(28,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_two));
            }
            if(FileTools.readSpecificAchievementFromFile(29,achievementdatafilePath)){
                Games.Achievements.unlock(getApiClient(), getString(R.string.ach_three));
            }
        }
    }
}

package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.File;

/**
 * Created by Thomas on 12/02/2016.
 */
public class Settings extends Activity {
    String filenameLevel = "Coin_data.txt";
    File fileLevel;
    String fileLevelPath;
    String achievementfilename = "Achievement_data.txt";
    File achievementdatafile;
    String achievementdatafilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        //File file = getBaseContext().getFileStreamPath(filename);
        fileLevelPath = getFilesDir() + "/" + filenameLevel;
        fileLevel = new File(fileLevelPath);
        achievementdatafilePath = getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v){
        switch (v.getId()){
            case (R.id.deletelevels):
                if (checkFileExists()){
                    fileLevel.delete();
                    achievementdatafile.delete();
                    System.out.println("File Deleted");
                }
                break;
            case (R.id.backfromsettings):
                final Intent menuIntent = new Intent("thomas.MAINMENU");
                startActivity(menuIntent);
                finish();
                break;
        }
    }
    /*

    public boolean checkFileExists(){
        try{
            if (file.exists()){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
    */
    public boolean checkFileExists(){
        //File file = new File(filename);
        File file = new File(fileLevelPath);
        if (!file.exists()){
            System.out.println("newfile");
            return false;
        }else{
            System.out.println("File Exists");
            return true;
        }
    }
}

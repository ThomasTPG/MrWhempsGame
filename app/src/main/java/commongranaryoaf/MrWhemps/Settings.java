package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

/**
 * Created by Thomas on 12/02/2016.
 */
public class Settings extends Activity {
    String filenameSetting = "settings.txt";
    File fileSettings;
    String fileLevelPath;
    FileTools fileTools;
    Button changeControls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        //File file = getBaseContext().getFileStreamPath(filename);
        fileLevelPath = getFilesDir() + "/" + filenameSetting;
        fileSettings = new File(fileLevelPath);
        fileTools = new FileTools();

        changeControls =  (Button) findViewById(R.id.changeControls);
        if (fileTools.readSettingsFromFile(fileLevelPath).equals(FileTools.jumpOnRelease))
        {
            changeControls.setText("Current controls: Jump On Release");
        }
        else if (fileTools.readSettingsFromFile(fileLevelPath).equals(FileTools.jumpOnUp))
        {
            changeControls.setText("Current controls: Jump On Tap");
        }
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
            case (R.id.changeControls):
                if (checkFileExists()){
                    if (fileTools.readSettingsFromFile(fileLevelPath).equals(FileTools.jumpOnRelease))
                    {
                        fileTools.writeToFile(FileTools.jumpOnUp, fileSettings);
                        changeControls.setText("Current controls: Jump On Tap");
                    }
                    else if (fileTools.readSettingsFromFile(fileLevelPath).equals(FileTools.jumpOnUp))
                    {
                        fileTools.writeToFile(FileTools.jumpOnRelease, fileSettings);
                        changeControls.setText("Current controls: Jump On Release");
                    }
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

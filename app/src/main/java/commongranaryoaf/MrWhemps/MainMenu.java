package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    File coindatafile;
    String coindatafilePath;
    int purchasables = 50;
    float startTime = 0;
    float timeElapsed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_menu_layout);
        TextView coins = (TextView) findViewById(R.id.cointextmainmenu);


        setUpFiles();
        coins.setText("Coins: " + FileTools.getYourCoins(coindatafilePath));
    }

    private void setUpFiles(){
        coindatafilePath = getFilesDir() + "/" + coindatafilename;
        coindatafile = new File(coindatafilePath);
        checkFileExists();
    }


    public void checkFileExists(){
        if (!coindatafile.exists()){
            System.out.println("newfile");
            createDefaultFile();

        }else{
            System.out.println("File Exists");
        }
    }

    public void createDefaultFile(){
        System.out.println("Create new file");
        String defaultFileContent = "000000000000=";
        for (int ii = 0; ii< purchasables; ii ++){
            defaultFileContent = defaultFileContent + "0-";
        }
        System.out.println(defaultFileContent);
        writeToFile(defaultFileContent);
    }

    public void writeToFile(String string){
        try {
            FileWriter fileWriter = new FileWriter(coindatafile);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write(string);
            out.close();
            fileWriter.close();
        } catch (Exception e){
            e.printStackTrace();
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

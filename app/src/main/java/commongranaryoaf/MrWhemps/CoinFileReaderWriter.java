package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.os.Bundle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * Created by Thomas on 12/05/2016.
 */
public class CoinFileReaderWriter extends Activity{

    File coindatafile;
    String coindatafilename = "Coin_data.txt";
    String coindatafilePath;
    int purchasables = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coindatafilePath = getFilesDir() + "/" + coindatafilename;
        coindatafile = new File(coindatafilePath);
    }

    public String readCoinsFromFile(){
        String fileStored;
        try {
            FileInputStream ourFile = new FileInputStream(new File(coindatafilePath));
            InputStreamReader reader = new InputStreamReader(ourFile);
            char[] buffer = new char[13 + 2*purchasables];
            reader.read(buffer);
            fileStored = new String(buffer);
        } catch (Exception e){
            e.printStackTrace();
            fileStored = "Error";
        }
        return fileStored;
    }

    public void writeToFile(String string, File file ){
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write(string);
            out.close();
            fileWriter.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}

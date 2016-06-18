package commongranaryoaf.MrWhemps;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Created by Thomas on 26/02/2016.
 */
public class FileTools {

    static int purchasables = 50;
    static String maxCoins = "999999999999";

    static public void writeToFile(String string, File file ){
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

    static public void writeCoinsToFile(BigInteger gainedCoins, String filePath, File file){
        String fileStored = readCoinsFromFile(filePath);
        String coinData;
        String[] data;
        String newFileString = "";
        if (fileStored != "Error"){
            data = fileStored.split("=");
            BigInteger newScore = new BigInteger(data[0]);
            newScore = newScore.add(gainedCoins);
            System.out.println(data[0]);
            System.out.println(newScore);
            coinData = newScore.toString();
            while (coinData.length() < 12){
                coinData = "0" + coinData;
            }
            if (coinData.length() > 12){
                coinData = maxCoins;
            }
            newFileString = coinData + "=" + data[1];
            writeToFile(newFileString,file);
        }
    }

    static public String readCoinsFromFile(String filePath){
        String fileStored;
        try {
            FileInputStream ourFile = new FileInputStream(new File(filePath));
            InputStreamReader reader = new InputStreamReader(ourFile);
            char[] buffer = new char[maxCoins.length() + 1 + 2*purchasables];
            reader.read(buffer);
            fileStored = new String(buffer);
        } catch (Exception e){
            e.printStackTrace();
            fileStored = "Error";
        }
        return fileStored;
    }

    static public int readMultiplierFromFile(String filePath){
        String fileStored;
        try {
            FileInputStream ourFile = new FileInputStream(new File(filePath));
            InputStreamReader reader = new InputStreamReader(ourFile);
            char[] buffer = new char[maxCoins.length() + 1 + 2*12];
            reader.read(buffer);
            fileStored = new String(buffer);
        } catch (Exception e){
            e.printStackTrace();
            fileStored = "Error";
        }
        String[] splitFile1 = fileStored.split("=");
        String[] splitMultipliers = splitFile1[1].split("-");
        int multiplier = 1;
        if (splitMultipliers[0].contains("2")){
            multiplier = multiplier * 2;
        }
        if (splitMultipliers[1].contains("2")){
            multiplier = multiplier * 3;
        }
        if (splitMultipliers[2].contains("2")){
            multiplier = multiplier * 4;
        }
        if (splitMultipliers[3].contains("2")){
            multiplier = multiplier * 5;
        }
        if (splitMultipliers[4].contains("2")){
            multiplier = multiplier * 6;
        }
        if (splitMultipliers[5].contains("2")){
            multiplier = multiplier * 8;
        }
        if (splitMultipliers[6].contains("2")){
            multiplier = multiplier * 10;
        }
        if (splitMultipliers[7].contains("2")){
            multiplier = multiplier * 20;
        }
        if (splitMultipliers[8].contains("2")){
            multiplier = multiplier * 50;
        }
        System.out.println(multiplier);
        return multiplier;


    }

    static public String getYourCoins(String filePath){
        return FileTools.coinFormatter(FileTools.readCoinsFromFile(filePath).substring(0,12));
    }


    static public String coinFormatter(String coins){
        int length = coins.length();
        while (length > 1){
            if (coins.charAt(0) == '0'){
                coins = coins.substring(1,length);
                length --;
            }
            else{
                break;
            }
        }
        return coins;
    }

}

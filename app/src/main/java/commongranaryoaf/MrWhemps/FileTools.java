package commongranaryoaf.MrWhemps;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Created by Thomas on 26/02/2016.
 */
public class FileTools {

    static int achievements = 50;
    static int purchasables = 50;
    static String maxCoins = "999999999999";
    static String jumpOnUp = "JumpOnUp";
    static String jumpOnRelease = "JumpOnRelease";
    static int adRate = 3;

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


    static public int readCostumeFromFile(String filePath){
        String fileStored;
        try {
            FileInputStream ourFile = new FileInputStream(new File(filePath));
            InputStreamReader reader = new InputStreamReader(ourFile);
            char[] buffer = new char[maxCoins.length() + 1 + 4*12];
            reader.read(buffer);
            fileStored = new String(buffer);
        } catch (Exception e){
            e.printStackTrace();
            fileStored = "Error";
        }
        String[] splitFile1 = fileStored.split("=");
        String[] splitItems = splitFile1[1].split("-");
        for (int ii = 12; ii < 24; ii++){
            if (splitItems[ii].contains("2")){
                return (ii - 12);
            }
        }
        return -1;
    }

    static public String readSettingsFromFile(String filePath){
        String line = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
            line = reader.readLine();
        } catch (Exception e){
            e.printStackTrace();
        }
       return line;
    }

    static public boolean getIsAdToBeShownFromFile(File file)
    {
        boolean toShow = true;
        if (!file.exists())
        {
            FileTools.writeToFile("1", file);
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            if (line != null)
            {
                int ad = Integer.parseInt(line);

                if (ad % adRate != 0)
                {
                    toShow = false;
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(Integer.toString(ad+1));
                writer.close();
                reader.close();
            }
            else
            {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(1);
                writer.close();
            }
        } catch (Exception e){
            e.printStackTrace();
            file.delete();
            FileTools.writeToFile("1", file);
        }
        return toShow;
    }

    static void forceAdNext(File file)
    {
        try {
            file.delete();
            FileTools.writeToFile("0", file);
        } catch (Exception e){
            e.printStackTrace();
        }
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

    static public void createDefaultCoinFile(File coindatafile){
        System.out.println("Create new file");
        String defaultFileContent = "000000000000=";
        for (int ii = 0; ii< purchasables; ii ++){
            defaultFileContent = defaultFileContent + "0-";
        }
        FileTools.writeToFile(defaultFileContent, coindatafile);
    }

    static public void createDefaultAchFile(File achievementdatafile){
        System.out.println("Create new file");
        String defaultFileContent = "";
        for (int ii = 0; ii< achievements; ii ++){
            defaultFileContent = defaultFileContent + "0-";
        }
        FileTools.writeToFile(defaultFileContent, achievementdatafile);
    }

    static public String readAchievementsFromFile(String filePath){
        String fileStored;
        try {
            FileInputStream ourFile = new FileInputStream(new File(filePath));
            InputStreamReader reader = new InputStreamReader(ourFile);
            char[] buffer = new char[2*achievements];
            reader.read(buffer);
            fileStored = new String(buffer);
        } catch (Exception e){
            e.printStackTrace();
            fileStored = "Error";
        }
        return fileStored;
    }

    static public boolean readSpecificAchievementFromFile(int specifiedAchievement, String filePath){
        String fileStored = readAchievementsFromFile(filePath);
        String[] achievementArray = fileStored.split("-");
        if (achievementArray[specifiedAchievement].equals("0")){
            return false;
        }
        return true;
    }

    static public void writeAchievementToFile(int specifiedAchievement, String filePath, File file){
        String fileStored = readAchievementsFromFile(filePath);
        String achData = "";
        String[] data;
        if (!fileStored.equals("Error")){
            data = fileStored.split("-");
            for (int ii = 0; ii < specifiedAchievement; ii++){
                achData = achData + data[ii] + "-";
            }
            achData = achData + "1-";
            for (int ii = specifiedAchievement + 1; ii< achievements; ii++){
                achData = achData + data[ii] + "-";
            }
            writeToFile(achData,file);
        }
    }

    static public String getMaxCoins(){
        return maxCoins;
    }

}

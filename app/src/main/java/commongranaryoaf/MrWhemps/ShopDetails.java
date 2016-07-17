package commongranaryoaf.MrWhemps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Created by Thomas on 17/05/2016.
 */
public class ShopDetails extends Activity {

    int page;  //The page the item is on
    int item;  //The position of the item on the page
    int index; //The position of the item in the file
    int purchasables = 50; //Total number of items to buy (upper limit)
    File coindatafile;
    String coindatafilename = "Coin_data.txt";
    String coindatafilePath;
    String achievementfilename = "Achievement_data.txt";
    File achievementdatafile;
    String achievementdatafilePath;

    String StrCost;
    BigInteger IntCost;
    BigInteger IntyourCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coindatafilePath = getFilesDir() + "/" + coindatafilename;
        coindatafile = new File(coindatafilePath);
        achievementdatafilePath = getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
        setContentView(R.layout.shop_details_layout);
        page = getIntent().getIntExtra("Page",0);
        item = getIntent().getIntExtra("Image",1);
        index = 12 * (page) + (item-1);
        setImage();
        setDescription();
    }

    public void setImage(){
        ImageView imageToBuy = (ImageView) findViewById(R.id.itemforsale);
        TextView description = (TextView) findViewById(R.id.description);
        TextView cost = (TextView) findViewById(R.id.cost);
        TextView yourcoins = (TextView) findViewById(R.id.yourcoins);
        TextView requirements = (TextView) findViewById(R.id.requirement);
        yourcoins.setText("Your coins: " + FileTools.getYourCoins(coindatafilePath));

        switch (page){
            case (0):
                requirements.setText(this.getString(R.string.no_requirements));
                switch (item){
                    case(1):
                        Bitmap coinx2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin2xbutton);
                        imageToBuy.setImageBitmap(coinx2);
                        description.setText(this.getString(R.string.x2_coin_desription));
                        StrCost = this.getString(R.string.x2_coin_cost);
                        break;
                    case(2):
                        Bitmap coinx3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin3xbutton);
                        imageToBuy.setImageBitmap(coinx3);
                        description.setText(this.getString(R.string.x3_coin_desription));
                        StrCost = this.getString(R.string.x3_coin_cost);
                        break;
                    case(3):
                        Bitmap coinx4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin4xbutton);
                        imageToBuy.setImageBitmap(coinx4);
                        description.setText(this.getString(R.string.x4_coin_desription));
                        StrCost = this.getString(R.string.x4_coin_cost);
                        break;
                    case(4):
                        Bitmap coinx5 = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin5xbutton);
                        imageToBuy.setImageBitmap(coinx5);
                        description.setText(this.getString(R.string.x5_coin_desription));
                        StrCost = this.getString(R.string.x5_coin_cost);
                        break;
                    case(5):
                        Bitmap coinx6 = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin6xbutton);
                        imageToBuy.setImageBitmap(coinx6);
                        description.setText(this.getString(R.string.x6_coin_desription));
                        StrCost = this.getString(R.string.x6_coin_cost);
                        break;
                    case(6):
                        Bitmap coinx8 = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin8xbutton);
                        imageToBuy.setImageBitmap(coinx8);
                        description.setText(this.getString(R.string.x8_coin_desription));
                        StrCost = this.getString(R.string.x8_coin_cost);
                        break;
                    case(7):
                        Bitmap coinx10 = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin10xbutton);
                        imageToBuy.setImageBitmap(coinx10);
                        description.setText(this.getString(R.string.x10_coin_desription));
                        StrCost = this.getString(R.string.x10_coin_cost);
                        break;
                    case(8):
                        Bitmap coinx20 = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin20xbutton);
                        imageToBuy.setImageBitmap(coinx20);
                        description.setText(this.getString(R.string.x20_coin_desription));
                        StrCost = this.getString(R.string.x20_coin_cost);
                        break;
                    case(9):
                        Bitmap coinx50 = BitmapFactory.decodeResource(this.getResources(), R.drawable.coin50xbutton);
                        imageToBuy.setImageBitmap(coinx50);
                        description.setText(this.getString(R.string.x50_coin_desription));
                        StrCost = this.getString(R.string.x50_coin_cost);
                        break;


                }
                break;
            case(1):
                switch (item){
                    case(1):
                        Bitmap bronze;
                        if(FileTools.readSpecificAchievementFromFile(0,achievementdatafilePath)){
                            bronze = BitmapFactory.decodeResource(this.getResources(), R.drawable.bronze_costume_icon);
                        } else{
                            bronze = BitmapFactory.decodeResource(this.getResources(), R.drawable.locked_costume);
                        }
                        imageToBuy.setImageBitmap(bronze);
                        description.setText(this.getString(R.string.bronze_costume_description));
                        requirements.setText("Achievements required: " + this.getString(R.string.bronze1_title));
                        StrCost = this.getString(R.string.bronze_cost);
                        break;
                    case(2):
                        Bitmap silver;
                        if(FileTools.readSpecificAchievementFromFile(4,achievementdatafilePath)){
                            silver = BitmapFactory.decodeResource(this.getResources(), R.drawable.silver_costume_icon);
                        } else{
                            silver = BitmapFactory.decodeResource(this.getResources(), R.drawable.locked_costume);
                        }
                        imageToBuy.setImageBitmap(silver);
                        description.setText(this.getString(R.string.silver_costume_description));
                        requirements.setText("Achievements required: " + this.getString(R.string.silver2_title));
                        StrCost = this.getString(R.string.silver_cost);
                        break;
                    case(3):
                        Bitmap gold;
                        if(FileTools.readSpecificAchievementFromFile(8,achievementdatafilePath)){
                            gold = BitmapFactory.decodeResource(this.getResources(), R.drawable.gold_costume_icon);
                        } else{
                            gold = BitmapFactory.decodeResource(this.getResources(), R.drawable.locked_costume);
                        }
                        imageToBuy.setImageBitmap(gold);
                        description.setText(this.getString(R.string.gold_costume_description));
                        requirements.setText("Achievements required: " + this.getString(R.string.gold3_title));
                        StrCost = this.getString(R.string.gold_cost);
                        break;
                    case(4):
                        Bitmap newspaper;
                        if(FileTools.readSpecificAchievementFromFile(26,achievementdatafilePath)){
                            newspaper = BitmapFactory.decodeResource(this.getResources(), R.drawable.costume_newspaper_button);
                        } else{
                            newspaper = BitmapFactory.decodeResource(this.getResources(), R.drawable.locked_costume);
                        }
                        imageToBuy.setImageBitmap(newspaper);
                        description.setText(this.getString(R.string.newspaper_costume_description));
                        requirements.setText("Achievements required: " + this.getString(R.string.great_hurdler_title));
                        StrCost = this.getString(R.string.newspaper_cost);
                        break;
                    case(5):
                        Bitmap cubist;
                        if(FileTools.readSpecificAchievementFromFile(15,achievementdatafilePath)){
                            cubist = BitmapFactory.decodeResource(this.getResources(), R.drawable.cubist_costume_icon);
                        } else{
                            cubist = BitmapFactory.decodeResource(this.getResources(), R.drawable.locked_costume);
                        }
                        imageToBuy.setImageBitmap(cubist);
                        description.setText(this.getString(R.string.cubist_costume_description));
                        requirements.setText("Achievements required: " + this.getString(R.string.art4_title));
                        StrCost = this.getString(R.string.cubist_cost);
                        break;
                    case(6):
                        Bitmap cyborg;
                        if(FileTools.readSpecificAchievementFromFile(19,achievementdatafilePath)){
                            cyborg = BitmapFactory.decodeResource(this.getResources(), R.drawable.cyborg_costume_icon);
                        } else{
                            cyborg = BitmapFactory.decodeResource(this.getResources(), R.drawable.locked_costume);
                        }
                        imageToBuy.setImageBitmap(cyborg);
                        description.setText(this.getString(R.string.cyborg_costume_description));
                        requirements.setText("Achievements required: " + this.getString(R.string.drone_collision_title));
                        StrCost = this.getString(R.string.cyborg_cost);
                        break;
                    case(7):
                        Bitmap neon;
                        if(FileTools.readSpecificAchievementFromFile(14,achievementdatafilePath)){
                            neon = BitmapFactory.decodeResource(this.getResources(), R.drawable.neon_costume_icon);
                        } else{
                            neon = BitmapFactory.decodeResource(this.getResources(), R.drawable.locked_costume);
                        }
                        imageToBuy.setImageBitmap(neon);
                        description.setText(this.getString(R.string.neon_costume_description));
                        requirements.setText("Achievements required: " + this.getString(R.string.art3_title));
                        StrCost = this.getString(R.string.neon_cost);
                        break;
                    case(8):
                        Bitmap starman;
                        if(FileTools.readSpecificAchievementFromFile(16,achievementdatafilePath)){
                            starman = BitmapFactory.decodeResource(this.getResources(), R.drawable.starman_costume_icon);
                        } else{
                            starman = BitmapFactory.decodeResource(this.getResources(), R.drawable.locked_costume);
                        }
                        imageToBuy.setImageBitmap(starman);
                        description.setText(this.getString(R.string.star_man_costume_description));
                        requirements.setText("Achievements required: " + this.getString(R.string.art5_title));
                        StrCost = this.getString(R.string.star_man_cost);
                        break;
                    case(9):
                        Bitmap wealthy;
                        if(FileTools.readSpecificAchievementFromFile(20,achievementdatafilePath)){
                            wealthy = BitmapFactory.decodeResource(this.getResources(), R.drawable.wealthy_costume_icon);
                        } else{
                            wealthy = BitmapFactory.decodeResource(this.getResources(), R.drawable.locked_costume);
                        }
                        imageToBuy.setImageBitmap(wealthy);
                        description.setText(this.getString(R.string.wealthy_costume_description));
                        requirements.setText("Achievements required: " + this.getString(R.string.max_currency_title));
                        StrCost = this.getString(R.string.wealthy_cost);
                        break;
                }
                break;
        }
        cost.setText("Cost: " + StrCost);
        IntCost = new BigInteger(StrCost);
        IntyourCoins = new BigInteger(FileTools.getYourCoins(coindatafilePath));
        Button buyFromShop = (Button) findViewById(R.id.buyfromshop);
        switch (checkItemBought()){
            case 0:
                if (achievementVerified(index)){
                    buyFromShop.setText("Buy");
                } else{
                    buyFromShop.setText("Locked");
                }
                break;
            case 1:
                buyFromShop.setText("Activate");
                break;
            case 2:
                buyFromShop.setText("Deactivate");
                break;
            default:
                buyFromShop.setText("Buy");
                break;

        }
    }

    public void setDescription(){

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClick(View v){
        switch (v.getId()){
            case (R.id.backtoshop):
                onBackPressed();
                break;
            case (R.id.buyfromshop):
                setItemBought();
                break;
        }
    }

    public int checkItemBought(){
        //Returns 0 is not purchased
        //Returns 1 if purchased but not activated
        //Returns 2 if purchased and activated
        String coinData = FileTools.readCoinsFromFile(coindatafilePath);
        String[] allData = coinData.split("=");
        String[] itemData = allData[1].split("-");
        if (Integer.parseInt(itemData[index]) == 0){
            return 0;
        }
        if (Integer.parseInt(itemData[index]) == 1){
            return 1;
        }
        return 2;
    }

    public boolean achievementVerified(int index){
        switch(index){
            case(12):
                if (FileTools.readSpecificAchievementFromFile(0,achievementdatafilePath)){
                    return true;
                }
                break;
            case(13):
                if (FileTools.readSpecificAchievementFromFile(4,achievementdatafilePath)){
                    return true;
                }
                break;
            case(14):
                if (FileTools.readSpecificAchievementFromFile(8,achievementdatafilePath)){
                    return true;
                }
                break;
            case(15):
                if (FileTools.readSpecificAchievementFromFile(26,achievementdatafilePath)){
                    return true;
                }
                break;
            case(16):
                if (FileTools.readSpecificAchievementFromFile(15,achievementdatafilePath)){
                    return true;
                }
                break;
            case(17):
                if (FileTools.readSpecificAchievementFromFile(19,achievementdatafilePath)){
                    return true;
                }
                break;
            case(18):
                if (FileTools.readSpecificAchievementFromFile(14,achievementdatafilePath)){
                    return true;
                }
                break;
            case(19):
                if (FileTools.readSpecificAchievementFromFile(16,achievementdatafilePath)){
                    return true;
                }
                break;
            case(20):
                if (FileTools.readSpecificAchievementFromFile(20,achievementdatafilePath)){
                    return true;
                }
                break;
            default:
                return true;
        }
        return false;
    }

    public void setItemBought(){
        //Returns 0 is not purchased
        //Returns 1 if purchased but not activated
        //Returns 2 if purchased and activated
        String newValue;
        System.out.println(IntCost);
        switch (checkItemBought()){
            case 0:
                if (IntyourCoins.compareTo(IntCost) != -1){
                    writeNewItem("1");
                    BigInteger minus1 = BigInteger.valueOf(-1);
                    FileTools.writeCoinsToFile(IntCost.multiply(minus1), coindatafilePath, coindatafile);
                    Toast.makeText(this, getResources().getString(R.string.buy_announcement), Toast.LENGTH_LONG).show();
                } else{
                    writeNewItem("0");
                    Toast.makeText(this, getResources().getString(R.string.cannot_afford_announcement), Toast.LENGTH_LONG).show();
                }
                break;
            case 1:
                //Need to also set every other value (for skins at least) to 1 here.
                if (page == 1){
                    writeNewItemUpdateOld("2");
                }else{
                    writeNewItem("2");
                }
                break;
            case 2:
                writeNewItem("1");
                break;
            default:
                writeNewItem("0");
                break;
        }
        setImage();



    }

    public void writeNewItem(String newValue){
        String coinData = FileTools.readCoinsFromFile(coindatafilePath);
        String[] allData = coinData.split("=");
        String[] itemData = allData[1].split("-");
        String replacementString = "";
        for (int ii = 0; ii < index; ii ++){
            replacementString = replacementString + itemData[ii] + "-";
        }
        replacementString = replacementString + newValue + "-";
        for (int jj = index + 1; jj < purchasables; jj ++){
            replacementString = replacementString + itemData[jj] + "-";
        }
        replacementString = allData[0] + "=" + replacementString;
        FileTools.writeToFile(replacementString, coindatafile);
        FileTools.getYourCoins(coindatafilePath);
    }

    public void writeNewItemUpdateOld(String newValue){
        String coinData = FileTools.readCoinsFromFile(coindatafilePath);
        String[] allData = coinData.split("=");
        String[] itemData = allData[1].split("-");
        String replacementString = "";
        for (int ii = 0; ii < 12; ii ++){
            replacementString = replacementString + itemData[ii] + "-";
        }
        for (int ii = 12; ii < index; ii ++){
            String oldValue = itemData[ii];
            if (oldValue.contains("2")){
                oldValue = "1";
            }
            replacementString = replacementString + oldValue + "-";
        }
        replacementString = replacementString + newValue + "-";
        for (int ii = index + 1; ii < 24; ii ++){
            String oldValue = itemData[ii];
            if (oldValue.contains("2")){
                oldValue = "1";
            }
            replacementString = replacementString + oldValue + "-";
        }
        for (int ii = 24; ii < purchasables; ii ++){
            replacementString = replacementString + itemData[ii] + "-";
        }
        replacementString = allData[0] + "=" + replacementString;
        FileTools.writeToFile(replacementString,coindatafile);
        FileTools.getYourCoins(coindatafilePath);
    }


}

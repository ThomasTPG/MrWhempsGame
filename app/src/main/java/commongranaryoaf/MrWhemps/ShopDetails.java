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
    String StrCost;
    BigInteger IntCost;
    BigInteger IntyourCoins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coindatafilePath = getFilesDir() + "/" + coindatafilename;
        coindatafile = new File(coindatafilePath);
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
                        Bitmap facialhair = BitmapFactory.decodeResource(this.getResources(), R.drawable.costume_moustache_button);
                        imageToBuy.setImageBitmap(facialhair);
                        description.setText(this.getString(R.string.facial_hair_description));
                        StrCost = this.getString(R.string.facial_hair_cost);
                        break;
                    case(2):
                        Bitmap newspaper = BitmapFactory.decodeResource(this.getResources(), R.drawable.costume_newspaper_button);
                        imageToBuy.setImageBitmap(newspaper);
                        description.setText(this.getString(R.string.newspaper_description));
                        requirements.setText("Achievements required: " + this.getString(R.string.under_hard_platform_title));
                        StrCost = this.getString(R.string.newspaper_cost);
                        break;
                    case(3):
                        Bitmap blacktie = BitmapFactory.decodeResource(this.getResources(), R.drawable.costume_blacktie_button);
                        imageToBuy.setImageBitmap(blacktie);
                        description.setText(this.getString(R.string.black_tie_description));
                        StrCost = this.getString(R.string.black_tie_cost);
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
                if (IntyourCoins.compareTo(IntCost) != -1){
                    buyFromShop.setText("Buy");
                } else{
                    buyFromShop.setText("Not enough coins");
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
                } else{
                    writeNewItem("0");
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

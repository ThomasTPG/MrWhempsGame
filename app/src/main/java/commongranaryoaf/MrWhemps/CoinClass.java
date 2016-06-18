package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Thomas on 06/03/2016.
 */
public class CoinClass {
    int coinRadius;
    int canvasHeight;
    int canvasWidth;
    Canvas canvas;
    int coinsPerRow = 10;
    int[][] coinArray = new int[coinsPerRow][coinsPerRow];
    int spriteX;
    int spriteY;
    int spriteDimension;
    double ySpacing;
    int score;
    int count = 0;
    double scoreMultiplier = 1;
    //The coin type is set in the coin type class
    int coinType;
    int iteration = 0;
    Bitmap coinSheet;


    public CoinClass(Canvas c, int spriteDim, int coinType, Context ctx){
        this.coinType = coinType;
        score = 0;
        spriteDimension = spriteDim;
        canvas = c;
        canvasWidth = c.getWidth();
        canvasHeight = c.getHeight();
        coinRadius = canvasWidth/(2*coinsPerRow);
        ySpacing = (canvasHeight-4*coinRadius)/coinsPerRow;
        switch (this.coinType){
            case(CoinType.NORMAL):
                coinSheet = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.coinsheetgold);
                int x = (int) Math.floor(Math.random()*10);
                int y = (int) Math.floor(Math.random()*10);
                for (int ii = 0; ii < 10; ii++){
                    for (int jj = 0; jj < 10; jj++){
                        coinArray[ii][jj] = 0;
                    }
                }
                coinArray[x][y] = 10;
                break;
            case(CoinType.EVERYWHERE):
                coinSheet = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.coinsheetbronze);
                for (int ii = 0; ii < 10; ii++){
                    for (int jj = 0; jj < 10; jj++){
                        coinArray[ii][jj] = 1;
                    }
                }
                break;
            case(CoinType.TUTORIAL):
                coinSheet = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.coinsheetgold);
                break;
        }

    }

    public void onDraw(){
        // Draw coin to the canvas
        count = count + 1;
        if (count == 7){
            iteration = (iteration + 1) % 4;
            count = 0;
        }

        Rect whichCoin = new Rect((iteration) * coinSheet.getWidth()/4,0,(iteration+1) * coinSheet.getWidth()/4,coinSheet.getHeight());
        for (int ii = 0; ii < 10; ii++){
            for (int jj = 0; jj < 10; jj++){
                if (coinArray[ii][jj] >0){
                    Rect position = new Rect((2 * (ii) * coinRadius), (int) (2 * coinRadius + (jj) * ySpacing + ySpacing / 2) - coinRadius, (2 * (ii) * coinRadius) + 2 * coinRadius, (int) (2 * coinRadius + (jj) * ySpacing + ySpacing / 2) + coinRadius);
                    canvas.drawBitmap(coinSheet, whichCoin, position, null);
                }
            }
        }
    }

    public void checkCoins(int x, int y){
        spriteX = x;
        spriteY = y;
        float xProportion = (float) spriteX/canvasWidth;
        float yProportion = (float) (spriteY-2*coinRadius)/(canvasHeight-4*coinRadius);
        int lowerXBound = Math.max((int) (Math.floor(xProportion * 0.7) * 10), 0);
        int upperXBound = Math.min((int) (Math.ceil(xProportion * 1.3) * 10), coinsPerRow-1);
        int lowerYBound = Math.max((int) (Math.floor(yProportion * 0.7) * 10), 0);
        int upperYBound = Math.min((int) (Math.ceil(yProportion * 1.3) * 10),coinsPerRow-1);

        for (int ii = lowerXBound;ii<= upperXBound;ii++){
            for (int jj = lowerYBound; jj<=upperYBound;jj++){
                if (coinArray[ii][jj] > 0){
                    int xDistance = (coinRadius + 2*(ii)*coinRadius) - spriteX;
                    int yDistance = (int) (2*coinRadius + (jj)*ySpacing + ySpacing/2) - spriteY;
                    if (Math.pow(xDistance,2) + Math.pow(yDistance,2) < Math.pow(spriteDimension/2+coinRadius,2)){
                        score += coinArray[ii][jj]; //* scoreMultiplier;
                        coinArray[ii][jj] = 0;
                        replenishCoins();
                    }
                }
            }
        }
    }

    public int getScore(){
        return score;
    }

    public void replenishCoins(){
        switch (coinType){
            case(CoinType.NORMAL):
                int x = (int) Math.floor(Math.random()*10);
                int y = (int) Math.floor(Math.random()*10);
                coinArray[x][y] = 10;
                break;
            case(CoinType.EVERYWHERE):
                if (score % 100 == 0){
                    for (int ii = 0; ii < 10; ii++){
                        for (int jj = 0; jj < 10; jj++){
                            coinArray[ii][jj] = 1;
                        }
                    }
                }
                break;
            case(CoinType.TUTORIAL):
                break;
        }
    }

    public void checkMultiplier(double mult){
        scoreMultiplier = mult;
    }

    public void coinTutorial(int x,int y){
        for (int ii = 0; ii < 10; ii++){
            for (int jj = 0; jj < 10; jj++){
                coinArray[ii][jj] = 0;
            }
        }
        coinArray[x][y] = 10;
    }

}
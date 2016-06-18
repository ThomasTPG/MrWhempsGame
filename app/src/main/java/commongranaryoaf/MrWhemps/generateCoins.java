package commongranaryoaf.MrWhemps;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Thomas on 07/02/2016.
 */
public class generateCoins {
    public  int coinRadius;
    public float coinX;
    public float coinY;
    Canvas canvas;
    Bitmap coinAnimate;
    Paint colour;

    public generateCoins(Canvas c){
        canvas = c;
        coinRadius = c.getWidth()/20;
        colour = new Paint();
        colour.setColor(Color.BLUE);
        colour.setStyle(Paint.Style.STROKE);
    }

    public void onDraw(){
        // Draw coin to the canvas

        canvas.drawCircle(coinX,coinY,coinRadius,colour);
    }

    public void newCoin(){
        //Randomly generate a new coin
        boolean distanceOkay = false;
        while (!distanceOkay){
            double newX = ((canvas.getWidth()-2*coinRadius) * (Math.random())) + coinRadius;
            double newY = ((canvas.getHeight()-6*coinRadius) * (Math.random())) + 3*coinRadius;
            double distance = (newX - coinX)*(newX - coinX) + (newY - coinY)*(newY - coinY);
            //Check that the distance between the new and old coin is far enough away
            if (distance>coinRadius*coinRadius*4){
                distanceOkay = true;
                coinX = (float)newX;
                coinY = (float)newY;
            }

        }

    }

    public float getX(){
        return coinX;
    }

    public float getY(){
        return coinY;
    }

    public int getR(){
        return coinRadius;
    }
}

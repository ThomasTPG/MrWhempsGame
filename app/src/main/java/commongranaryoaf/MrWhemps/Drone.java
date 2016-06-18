package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Thomas on 04/03/2016.
 */
public class Drone {
    int spriteX;
    int spriteY;
    int droneX;
    int droneY;
    // The drone direction has N=1,E=2,S=3,W=4
    int droneDirection;
    int droneDimension;
    double droneSpeed;
    double droneDistanceDifference;
    int cWidth;
    int cHeight;
    boolean hunting;
    boolean huntingWait = false;
    Rect position;
    Rect whichImage;
    Bitmap droneImage;
    int bitmapWidth;
    int bitmapHeight;
    float startTime = 0;
    int spriteDimension;

    public Drone(int canvasWidth, int canvasHeight, Context context, int spriteDim, int initPos){
        droneImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.dronesheet);
        bitmapWidth = droneImage.getWidth();
        bitmapHeight = droneImage.getHeight();
        cWidth = canvasWidth;
        cHeight = canvasHeight;
        droneDimension = canvasWidth/12;
        droneDirection = 2;
        hunting = false;
        droneSpeed = 2;
        spriteDimension = spriteDim;
        initialLocation(initPos);
    }

    public void initialLocation(int Pos){
        switch (Pos){
            case(1):
                droneX = droneDimension/2;
                droneY = droneDimension/2;
                break;
            case(2):
                droneX = cWidth/2;
                droneY = droneDimension/2;
                break;
            case(3):
                droneX = cWidth - droneDimension/2;
                droneY = droneDimension/2;
                break;
            case(4):
                droneX = droneDimension/2;
                droneY = cHeight/2;
                break;
            case(5):
                droneX = cWidth - droneDimension/2;
                droneY = cHeight/2;
                break;
            case(6):
                droneX = droneDimension/2;
                droneY = cHeight - droneDimension/2;
                break;
            case(7):
                droneX = cWidth/2;
                droneY = cHeight - droneDimension/2;
                break;
            case(8):
            default:
                droneX = cWidth - droneDimension/2;
                droneY = cHeight - droneDimension/2;
                break;
        }
    }

    public void onDraw(Canvas c, int x, int y){
        System.out.println("dronedir = " + droneDirection);
        spriteX = x;
        spriteY = y;
        if (huntingWait) {
            huntingTimer();
        }
        else if (!hunting){
            huntPlayer();
        }
        move();
        droneCollision();
        position = new Rect(droneX -droneDimension/2, droneY -droneDimension / 2, droneX + droneDimension / 2, droneY + droneDimension/2);
        switch (droneDirection){
            case(1):
                if (hunting || huntingWait){
                    whichImage = new Rect(0,0,bitmapWidth/4,bitmapHeight/2);
                }
                else{
                    whichImage = new Rect(0,bitmapHeight/2,bitmapWidth/4,bitmapHeight);
                }
                break;
            case(2):
                if(hunting || huntingWait){
                    whichImage = new Rect(bitmapWidth/4,0,bitmapWidth/2,bitmapHeight/2);
                }
                else{
                    whichImage = new Rect(bitmapWidth/4,bitmapHeight/2,bitmapWidth/2,bitmapHeight);
                }
                break;
            case(3):
                if (hunting || huntingWait){
                    whichImage = new Rect(bitmapWidth/2,0,3*bitmapWidth/4,bitmapHeight/2);
                }
                else{
                    whichImage = new Rect(bitmapWidth/2,bitmapHeight/2,3*bitmapWidth/4,bitmapHeight);
                }
                break;
            case(4):
                if (hunting || huntingWait){
                    whichImage = new Rect(3*bitmapWidth/4,0,bitmapWidth,bitmapHeight/2);
                }
                else{
                    whichImage = new Rect(3*bitmapWidth/4,bitmapHeight/2,bitmapWidth,bitmapHeight);
                }
                break;
            default:
                break;
        }
        c.drawBitmap(droneImage, whichImage, position, null);
    }

    public void move(){
        //first correct for rounding errors in the speed
        int droneSpeedCorrected = (int) Math.floor(droneSpeed);

        //Correction for only being able to move integer pixels
        droneDistanceDifference = droneDistanceDifference + droneSpeed - Math.floor(droneSpeed);

        if (droneDistanceDifference > 1){
            droneSpeedCorrected++;
            droneDistanceDifference--;
        }
        if (hunting){
            droneSpeedCorrected = droneSpeedCorrected * 3;
        }
        //now move
        switch (droneDirection){
            case(1):
                droneY = (int) (droneY - Math.round(droneSpeedCorrected));
                break;
            case(2):
                droneX = (int) (droneX + Math.round(droneSpeedCorrected));
                break;
            case(3):
                droneY = (int) (droneY + Math.round(droneSpeedCorrected));
                break;
            case(4):
                droneX = (int) (droneX - Math.round(droneSpeedCorrected));
                break;
            default:
                break;

        }
    }

    public void huntingEnd(){
        if (hunting){
            hunting = false;
            huntingWait = true;
            startTime = System.nanoTime();
        }
    }

    public void huntingTimer(){
        if((System.nanoTime() - startTime)/1000000000 > 1){
            huntingWait = false;
        }
    }

    public void huntPlayer(){
        if (Math.abs(spriteX-droneX)<droneDimension){
            hunting = true;
            if (spriteY > droneY){
                droneDirection = 3;
            }
            else{
                droneDirection = 1;
            }
        }
        if (Math.abs(spriteY-droneY)<droneDimension){
            hunting = true;
            if (spriteX > droneX){
                droneDirection = 2;
            }
            else{
                droneDirection = 4;
            }
        }
    }

    public void droneCollision(){
        switch(droneDirection){
            case(1):
                if (droneY - droneDimension/2 < 0){
                    huntingEnd();
                    if (droneX < cWidth/2){
                        droneDirection = 2;
                    }
                    else{
                        droneDirection = 4;
                    }
                }
                break;
            case(2):
                if (droneX + droneDimension/2 > cWidth){
                    huntingEnd();
                    if (droneY < cHeight/2){
                        droneDirection = 3;
                    }
                    else{
                        droneDirection = 1;
                    }
                }
                break;
            case(3):
                if (droneY + droneDimension/2 > cHeight){
                    huntingEnd();
                    if (droneX < cWidth/2){
                        droneDirection = 2;
                    }
                    else{
                        droneDirection = 4;
                    }
                }
                break;
            case(4):
                if (droneX - droneDimension/2 < 0){
                    huntingEnd();
                    if (droneY < cHeight/2){
                        droneDirection = 3;
                    }
                    else{
                        droneDirection = 1;
                    }
                }
                break;
            default:
                break;
        }
    }

    public boolean checkLose(){
        int xDistance = Math.abs(droneX-spriteX);
        int yDistance = Math.abs(droneY-spriteY);
        if (Math.pow(xDistance,2) + Math.pow(yDistance,2) < Math.pow(droneDimension/2 + spriteDimension/2,2)){
            return true;
        }
        else{
            return false;
        }
    }

}

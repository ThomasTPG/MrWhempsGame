package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Thomas on 04/03/2016.
 */
public class GuardDrone {
    int spriteX;
    int spriteY;
    int droneX=0;
    int droneY =0;
    int droneDimension;
    double droneSpeed;
    int cWidth;
    int cHeight;
    Rect position;
    Bitmap droneImage;
    int bitmapWidth;
    int bitmapHeight;
    int spriteDimension;
    double pi = 3.14159;
    float angle;
    boolean active;
    Canvas c;
    int rotatingCentreX;
    int rotatingCentreY;
    int droneDisplacement = 0;
    boolean appearing;
    int alpha;
    int FPS = 45;
    int MAXALPHA = 255;
    int alphaDif;

    public GuardDrone(Canvas canvas, Context context, int spriteDim){
        droneImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.guard_drone);
        bitmapWidth = droneImage.getWidth();
        bitmapHeight = droneImage.getHeight();
        cWidth = canvas.getWidth();
        cHeight = canvas.getHeight();
        c = canvas;
        droneSpeed = 8;
        spriteDimension = spriteDim;
        droneDimension = cWidth/12;
    }

    public void initialisePosition(int initPos, int spriteInitX, int spriteInitY){
        droneX = 0;
        droneY = 0;
        double SECS = 0.3;
        alphaDif = (int) (MAXALPHA / (FPS* SECS));
        alpha = 0;
        appearing = true;
        active = true;
        droneDisplacement = 0;
        initialLocation(initPos);

        //Now we calculate the angle at which the drone moves
        int xDif = rotatingCentreX- spriteInitX;
        int yDif = rotatingCentreY - spriteInitY;

        double tan = (double) yDif/xDif;

        if(xDif >= 0){
            //Here we have that the character is to the left of the drone
            angle = (float) (270 + 180/pi * Math.atan(tan));
        } else{
            //Otherwise the character is to the right of the drone
            angle = (float) (90 + 180/pi * Math.atan(tan));
        }
    }

    public void initialLocation(int Pos){
        int initialX;
        int initialY;
        switch (Pos){
            case(1):
                initialX = droneDimension/2;
                initialY = droneDimension/2;
                break;
            case(2):
                initialX = cWidth/2;
                initialY = droneDimension/2;
                break;
            case(3):
                initialX = cWidth - droneDimension/2;
                initialY = droneDimension/2;
                break;
            case(4):
                initialX = droneDimension/2;
                initialY = cHeight/2;
                break;
            case(5):
                initialX = cWidth - droneDimension/2;
                initialY = cHeight/2;
                break;
            case(6):
                initialX = droneDimension/2;
                initialY = cHeight - droneDimension/2;
                break;
            case(7):
                initialX = cWidth/2;
                initialY = cHeight - droneDimension/2;
                break;
            case(8):
            default:
                initialX = cWidth - droneDimension/2;
                initialY = cHeight - droneDimension/2;
                break;
        }
        rotatingCentreX = initialX;
        rotatingCentreY = initialY;
        droneX = initialX;
        droneY = initialY;
    }

    public void fadeIn(){
        alpha = alpha + alphaDif;
        if(alpha > MAXALPHA){
            appearing = false;
            alpha = 255;
        }
        Paint paint = new Paint();
        paint.setAlpha(alpha);
        position = new Rect( rotatingCentreX - droneDimension/2,rotatingCentreY - droneDimension/2, rotatingCentreX + droneDimension/2,rotatingCentreY + droneDimension/2);
        c.save();
        c.rotate(angle, rotatingCentreX, rotatingCentreY);
        c.drawBitmap(droneImage, null, position, paint);
        c.restore();
    }

    public void onDraw(int x, int y){
        if (!hasDroneHitWall()){
            if(appearing){
                fadeIn();
            } else {
                spriteX = x;
                spriteY = y;
                droneDisplacement = (int) (droneDisplacement - droneSpeed);
                position = new Rect( rotatingCentreX - droneDimension/2, droneDisplacement + rotatingCentreY - droneDimension/2, rotatingCentreX + droneDimension/2, droneDisplacement + rotatingCentreY + droneDimension/2);
                c.save();
                c.rotate(angle, rotatingCentreX, rotatingCentreY);
                c.drawBitmap(droneImage, null, position, null);
                c.restore();
                droneX = (int) (rotatingCentreX - Math.sin(angle * pi / 180) * droneDisplacement);
                droneY = (int) (rotatingCentreY + Math.cos(angle * pi / 180) * droneDisplacement);
            }
        }
        else{
            active = false;
        }
    }

    public boolean hasDroneHitWall(){
        return (droneY - droneDimension/2 < 0       ||
                droneX + droneDimension/2 > cWidth  ||
                droneY + droneDimension/2 > cHeight ||
                droneX - droneDimension/2 < 0);
    }

    public boolean checkLose(){
        int xDistance = Math.abs(droneX-spriteX);
        int yDistance = Math.abs(droneY-spriteY);
        return (Math.pow(xDistance,2) + Math.pow(yDistance,2) < Math.pow(droneDimension/2 + spriteDimension/2,2));
    }

}

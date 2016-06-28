package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Thomas on 28/06/2016.
 */
public class LaserGates {

    int gateHeight;
    int numberGates = 4;
    // In each row of the wall array, we store the y value, the type of wall, the start and end x value, the movement direction, extra details column
    int gateAttributes = 4;
    //The attributes are; 1) Y VALUE 2) X LEFT 3) X RIGHT 4) PERCENTAGE
    int yArray[][] = new int[numberGates][gateAttributes];
    Canvas canvas;
    int screenWidth;
    int screenHeight;
    Bitmap laserGate;
    Bitmap laserGateEnds;
    int laserGateMinWidth;
    int laserGateMaxWidth;
    int laserGatePercentage;
    boolean newGate;
    int laserGateBmpWidth;
    int laserGateBmpHeight;
    int shift = 0;

    public LaserGates(Canvas c, Context context) {
        canvas = c;
        screenWidth = canvas.getWidth();
        screenHeight = canvas.getHeight();
        gateHeight = screenHeight/50;
        laserGate = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser2);
        laserGateEnds = BitmapFactory.decodeResource(context.getResources(), R.drawable.laser_gate_ends);
        laserGateBmpHeight = laserGate.getHeight();
        laserGateBmpWidth = laserGate.getWidth();
        for (int ii = 0; ii < numberGates; ii ++){
            for (int jj = 0; jj < gateAttributes; jj++){
                yArray[ii][jj] = 0;
            }
        }

    }

    public void initializeLaserGate(int percent, int maxWidth, int minWidth){
        laserGatePercentage = percent;
        laserGateMinWidth = minWidth;
        laserGateMaxWidth = maxWidth;
    }

    public void updateYValues(Walls walls){
        int[][] wallArray = walls.getWallArray();
        if (wallArray[2][0] - wallArray[1][0] > 2 * wallArray[0][0]){
            //Here we have that the first wall has moved down enough to fit in a laser gate above it
            newGate = true;
            for(int ii = 0; ii<numberGates; ii++){
                yArray[ii][0] = wallArray[1][0] + (2*ii-1) * (wallArray[2][0] - wallArray[1][0])/2;
            }
        } else{
            if (newGate){
                for (int jj = numberGates-1; jj > 0; jj--){
                    yArray[jj][1] = yArray[jj-1][1];
                    yArray[jj][2] = yArray[jj-1][2];
                    yArray[jj][3] = yArray[jj-1][3];
                }
                double widthRange = Math.random() * (laserGateMaxWidth - laserGateMinWidth);
                double leftX = Math.random() * (100 - widthRange);
                yArray[0][1] = (int) leftX;
                yArray[0][2] = (int) (leftX + laserGateMinWidth + widthRange);
                double randint = Math.random() * 100;
                if (randint < laserGatePercentage){
                    yArray[0][3] = 1;
                } else{
                    yArray[0][3] = 0;
                }

                newGate = false;
            }
            for(int ii = 0; ii<wallArray.length; ii++){
                yArray[ii][0] = wallArray[1][0] + (2*ii-3) * (wallArray[2][0] - wallArray[1][0])/2;
            }
        }

    }


    public void onDraw(Walls walls){
        updateYValues(walls);
        for (int jj = 0; jj <numberGates; jj++){
             if(yArray[jj][3] == 1){
                Rect wall = new Rect(screenWidth*yArray[jj][1]/100,yArray[jj][0],screenWidth*yArray[jj][2]/100,yArray[jj][0] + gateHeight);
                if(shift < laserGateBmpWidth/2){
                    shift ++;
                }else{
                    shift = 0;
                }
                Rect whichImage1 = new Rect(shift,0,laserGateBmpWidth/2 + shift,laserGateBmpHeight);
                Rect whichImage2 = new Rect(laserGateBmpWidth/2 - shift,0,laserGateBmpWidth - shift,laserGateBmpHeight);
                canvas.drawBitmap(laserGate,whichImage1,wall,null);
                canvas.drawBitmap(laserGate,whichImage2,wall,null);
                Rect end1 = new Rect(screenWidth*yArray[jj][1]/100 - gateHeight/2,yArray[jj][0],screenWidth*yArray[jj][1]/100 + gateHeight/2,yArray[jj][0] + 2*gateHeight);
                Rect end2 = new Rect(screenWidth*yArray[jj][2]/100 - gateHeight/2,yArray[jj][0],screenWidth*yArray[jj][2]/100 + gateHeight/2,yArray[jj][0] + 2*gateHeight);
                canvas.drawBitmap(laserGateEnds,null,end1,null);
                canvas.drawBitmap(laserGateEnds,null,end2,null);
            }
        }


    }

}

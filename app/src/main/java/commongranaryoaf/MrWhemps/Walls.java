package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Walls {


    int wallHeight;
    double wallHeightFactor = 24.5;
    int walls = 4;
    // In each row of the wall array, we store the y value, the type of wall, the start and end x value, the movement direction, extra details column
    int wallAttributes = 6;
    int yArray[][] = new int[walls][wallAttributes];
    float wallSpeed = 0;
    int screenWidth;
    int screenHeight;
    long startTime;
    long timeNow;
    Canvas canvas;
    double YdistanceDifference = 0;
    double XdistanceDifference = 0;
    int howManyTypes = 1;
    int hardWallCompletePercentage = 0;
    int completeHardWidth = 0;
    int partialNormalWallPercentage = 0;
    int partialNormalWidth = 0;
    int movingNormalWallPercentage = 0;
    int movingNormalWidth = 0;
    int hardWallPartialPercentage = 0;
    int partialHardWidth = 0;
    int movingHardWallPercentage = 0;
    int movingHardWidth = 0;
    int staticSandGPercentage = 0;
    int staticSandGWidth = 0;
    Bitmap normalWallBitmap;
    Bitmap hardWallBitmap;
    Bitmap stopAndGoBitmap;
    int stopAndGoBmpHeight;
    int stopandGoBmpWidth;
    double speedMultiplier = 1;
    int wallSpeedType;
    double wallXSpeed;
    int switchColours = 0;
    int wallNumber = 0;


    public Walls(Canvas c, int ourlevel, Context context) {
        canvas = c;
        screenWidth = canvas.getWidth();
        screenHeight = canvas.getHeight();
        wallHeight = (int) (screenHeight / wallHeightFactor);
        normalWallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.normalwall);
        hardWallBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hardwall);
        stopAndGoBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.stopandgosheet);
        stopAndGoBmpHeight = stopAndGoBitmap.getHeight();
        stopandGoBmpWidth = stopAndGoBitmap.getWidth();
        speedMultiplier = 1;

        for (int kk = 0; kk < walls; kk++){
            yArray[kk][0] = screenHeight*kk/walls;
            yArray[kk][1] = WallTypes.NORMAL;
            yArray[kk][2] = 0;
            yArray[kk][3] = 100;
        }
        startTime = System.nanoTime();

    }

    public double getSpeedMultiplier(){ return speedMultiplier;}

    public void setSpeedMultiplier(double sMult){
        speedMultiplier = sMult;
    }

    public void setWallSpeedType(int type){
        wallSpeedType = type;
    }

    public void initializeHardWallsComplete(int percent, int width){
        hardWallCompletePercentage = percent;
        completeHardWidth = width;
    }

    public void initializeHardWallsPartial(int percent, int width){
        hardWallPartialPercentage = percent;
        partialHardWidth = width;
    }


    public void initializePartialNormalWalls(int percent, int width){
        partialNormalWallPercentage = percent;
        partialNormalWidth = width;

    }

    public void initializeNormalMovingWall(int percent, int width, double speed){
        movingNormalWallPercentage = percent;
        movingNormalWidth = width;
        wallXSpeed = speed;
    }

    public void initializeHardMovingWall(int percent, int width, double speed){
        movingHardWallPercentage = percent;
        movingHardWidth = width;
        wallXSpeed = speed;
    }

    public void initializeStopAndGoStationary(int percent, int width){
        staticSandGPercentage = percent;
        staticSandGWidth = width;
    }

    int[][] getWallArray(){
        return yArray;
    }

    public int getWallHeight(){
        return wallHeight;
    }

    public void onDraw(){
        for (int jj = 0; jj <walls; jj++){
            Rect wall = new Rect(screenWidth*yArray[jj][2]/100,yArray[jj][0],screenWidth*yArray[jj][3]/100,yArray[jj][0] + wallHeight);
            switch (yArray[jj][1]){
                case(WallTypes.NORMAL):
                case(WallTypes.NORMALPARTIAL):
                case(WallTypes.MOVINGNORMAL):
                    canvas.drawBitmap(normalWallBitmap,null,wall,null);
                    break;
                case(WallTypes.HARDCOMPLETE):
                    Rect wallLeft = new Rect(0,yArray[jj][0],screenWidth*yArray[jj][2]/100,yArray[jj][0] + wallHeight);
                    Rect wallRight = new Rect(screenWidth*yArray[jj][3]/100,yArray[jj][0],screenWidth,yArray[jj][0] + wallHeight);
                    canvas.drawBitmap(hardWallBitmap,null,wall,null);
                    canvas.drawBitmap(normalWallBitmap,null,wallLeft,null);
                    canvas.drawBitmap(normalWallBitmap,null,wallRight,null);
                    break;
                case(WallTypes.HARDPARTIAL):
                case(WallTypes.MOVINGHARD):
                    canvas.drawBitmap(hardWallBitmap,null,wall,null);
                    break;
                case(WallTypes.STOPANDGOSTATIONARY):
                    Rect whichWall;
                    if (switchColours % 100 > 50){
                        switchColours ++;
                        switch (yArray[jj][5]){
                            case(WallTypes.STOPANDGOLEFTUP):
                                whichWall = new Rect(0, 0, stopandGoBmpWidth/2, stopAndGoBmpHeight/2);
                                break;
                            case(WallTypes.STOPANDGOLEFTDOWN):
                            default:
                                whichWall = new Rect(0, stopAndGoBmpHeight/2, stopandGoBmpWidth/2, stopAndGoBmpHeight);
                                break;
                        }
                    } else{
                        switchColours ++;
                        switch (yArray[jj][5]){
                            case(WallTypes.STOPANDGOLEFTUP):

                                whichWall = new Rect(stopandGoBmpWidth/2, 0, stopandGoBmpWidth, stopAndGoBmpHeight/2);
                            break;
                            case(WallTypes.STOPANDGOLEFTDOWN):
                            default:
                                whichWall = new Rect(stopandGoBmpWidth/2, stopAndGoBmpHeight/2, stopandGoBmpWidth, stopAndGoBmpHeight);

                            break;
                        }

                    }
                    canvas.drawBitmap(stopAndGoBitmap,whichWall,wall, null);
                    break;
                default:
                    canvas.drawBitmap(normalWallBitmap,null,wall,null);
                    break;
            }

        }


    }

    public void updateWalls(){
        calculateWallSpeed();

        //Here we account for the fact that we can't move fractional pixels. We calculate how much less we have moved due
        //to rounding, and then occasionally move again
        YdistanceDifference = YdistanceDifference + wallSpeed - Math.floor(wallSpeed);
        if (YdistanceDifference > 1){
            wallSpeed++;
            YdistanceDifference--;
        }

        //Here we check if the wall has gone out of the screen
        for (int ii = 0; ii <walls; ii++){

            yArray[ii][0] += wallSpeed;

            if (yArray[ii][0] > screenHeight){
                for (int jj = ii; jj>0; jj--){
                    System.arraycopy(yArray[jj-1],0,yArray[jj],0,wallAttributes);
                }
                createNewWall();
            }
        }
    }

    public void calculateWallSpeed(){
        timeNow = System.nanoTime();
        float timeElapsed = timeNow - startTime;
        switch (wallSpeedType){
            case(WallTypes.CUBEROOTSPEED):
                wallSpeed = (float) (1 + (speedMultiplier * 0.4*Math.cbrt(timeElapsed / 100000000)));
                break;
            case(WallTypes.SINUSOIDAL):
                wallSpeed = (float) (speedMultiplier * 0.25*(1+Math.sin(Math.sqrt(timeElapsed / 100000000))) * Math.sqrt(timeElapsed / 100000000));
                break;
            case(WallTypes.SQUAREROOTSPEED):
                wallSpeed = (float) (speedMultiplier * (1 + ( 0.4*Math.sqrt(timeElapsed / 100000000))));
                break;
            default:
                wallSpeed = (float) (1 + (0.4*Math.cbrt(timeElapsed / 100000000)));
                break;
        }
        //scale
        wallSpeed = (int) Math.ceil((double) (wallSpeed * screenHeight)/(double) 720);
        System.out.println("WIDTH" + screenWidth);
    }

    public void createNewWall(){
        //Creates a new wall depending on which types of walls are allowed on the current level
        yArray[0][0]=0;
        double randomVariable = Math.random();
        if (randomVariable < (double)hardWallCompletePercentage/100){
            yArray[0][1] = WallTypes.HARDCOMPLETE;
            //Check that we can fit through the game
            if (completeHardWidth > 66){
                double randX = Math.random();
                if(randX <= 0.5){
                    yArray[0][2] = 0;
                    yArray[0][3] = completeHardWidth;
                }
                else{
                    yArray[0][2] = 100 - completeHardWidth;
                    yArray[0][3] = 100;
                }
            }
            else{
                double randX = Math.random() * (100 - completeHardWidth);
                yArray[0][2] = (int) randX;
                yArray[0][3] = (int) randX + completeHardWidth;
            }
        } else if ( randomVariable < (double) ( hardWallCompletePercentage + partialNormalWallPercentage)/100){
            yArray[0][1] = WallTypes.NORMALPARTIAL;
            double randX = Math.random() * (100 - partialNormalWidth);
            yArray[0][2] = (int) randX;
            yArray[0][3] = (int) randX + partialNormalWidth;
        } else if (randomVariable < (double) ( movingNormalWallPercentage + hardWallCompletePercentage + partialNormalWallPercentage)/100){
            yArray[0][1] = WallTypes.MOVINGNORMAL;
            double randX = Math.random() * (100 - movingNormalWidth);
            yArray[0][2] = (int) randX;
            yArray[0][3] = (int) randX + movingNormalWidth;
            yArray[0][4] = 2*((int) Math.round(randomVariable)) - 1;
        } else if (randomVariable < (double) ( hardWallPartialPercentage + movingNormalWallPercentage + hardWallCompletePercentage + partialNormalWallPercentage)/100){
            yArray[0][1] = WallTypes.HARDPARTIAL;
            double randX = Math.random() * (100 - partialHardWidth);
            yArray[0][2] = (int) randX;
            yArray[0][3] = (int) randX + partialHardWidth;
        } else if (randomVariable < (double) ( movingHardWallPercentage + hardWallPartialPercentage + movingNormalWallPercentage + hardWallCompletePercentage + partialNormalWallPercentage)/100) {
            yArray[0][1] = WallTypes.MOVINGHARD;
            double randX = Math.random() * (100 - movingHardWidth);
            yArray[0][2] = (int) randX;
            yArray[0][3] = (int) randX + movingHardWidth;
            yArray[0][4] = 2 * ((int) Math.round(randomVariable)) - 1;
            //We store how many hard partial walls have passed here (for the acheivement in level 16)
            yArray[0][5] = wallNumber;
            wallNumber = wallNumber + 1;
        } else if (randomVariable < (double) ( staticSandGPercentage + movingHardWallPercentage + hardWallPartialPercentage + movingNormalWallPercentage + hardWallCompletePercentage + partialNormalWallPercentage)/100) {
            yArray[0][1] = WallTypes.STOPANDGOSTATIONARY;
            double randX = Math.random() * (100 - staticSandGWidth);
            yArray[0][2] = (int) randX;
            yArray[0][3] = (int) randX + staticSandGWidth;
            //If 5th element is zero, stop side on the left, if 5th element is 1, stop side on the right
            yArray[0][5] = ((int) Math.round(randomVariable));
        } else{
            yArray[0][1]= WallTypes.NORMAL;
            yArray[0][2] = 0;
            yArray[0][3]= 100;

        }

    }

    public void moveMovingWalls(){
        //scale for screen
        wallXSpeed = (int) Math.ceil((double) (wallXSpeed * screenWidth)/(double) 480);
        double wallXSpeedCorrected = wallXSpeed;

        //Correction for only being able to move integer pixels
        XdistanceDifference = XdistanceDifference + wallXSpeedCorrected - Math.floor(wallXSpeedCorrected);

        if (XdistanceDifference > 1){
            wallXSpeedCorrected++;
            XdistanceDifference--;
        }
        for (int ii = 0; ii<walls;ii++){
            switch (yArray[ii][1]){
                case(WallTypes.MOVINGHARD):
                case(WallTypes.MOVINGNORMAL):
                case(WallTypes.STOPANDGOMOVING):
                    if (yArray[ii][4] == 1){

                        //The 4th component is positive, so we move in the positive x direction
                        yArray[ii][2] += Math.round(wallXSpeedCorrected);
                        yArray[ii][3] += Math.round(wallXSpeedCorrected);
                        if (yArray[ii][3] >= 100){
                            yArray[ii][4] = -1;
                        }
                    }
                    else{
                        //Move in the negative x direction
                        yArray[ii][2] -= Math.round(wallXSpeedCorrected);
                        yArray[ii][3] -= Math.round(wallXSpeedCorrected);
                        if (yArray[ii][2] <=0){
                            yArray[ii][4] = 1;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void startTutorial(){
        yArray[3][1] = WallTypes.NORMALPARTIAL;
        yArray[3][2] = 0;
        yArray[3][3] = 20;
        yArray[2][1] = WallTypes.HARDPARTIAL;
        yArray[2][2] = 0;
        yArray[2][3] = 20;
        yArray[1][1]= WallTypes.NORMALPARTIAL;
        yArray[1][2] = 40;
        yArray[1][3] = 60;
        yArray[0][1]= WallTypes.NORMALPARTIAL;
        yArray[0][2] = 0;
        yArray[0][3] = 0;
        speedMultiplier = 0;
    }

    public void tutorialChangeType(){
        yArray[1][1] = WallTypes.HARDPARTIAL;
    }

    public void tutorialComplete(){
        yArray[0][2] = 0;
        yArray[0][3] = 0;
    }

}

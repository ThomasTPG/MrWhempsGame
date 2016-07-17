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
    int gateAttributes = 5;
    //The attributes are; 1) Y VALUE 2) X LEFT 3) X RIGHT 4) PERCENTAGE  5) HAVE WE HIT THE WALL?
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
    int spriteX;
    int spriteY;
    int spriteDim;
    GuardDrone drone1;
    GuardDrone drone2;
    GuardDrone drone3;
    GuardDrone drone4;
    boolean drone1running = false;
    boolean drone2running = false;
    boolean drone3running = false;
    boolean drone4running = false;
    boolean lose = false;
    int consecutiveDrones = 0;
    int consecutiveClear = 0;

    public LaserGates(Canvas c, Context context, int spriteDimension) {
        spriteDim = spriteDimension;
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
        drone1 = new GuardDrone(c, context, spriteDimension);
        drone2 = new GuardDrone(c, context, spriteDimension);
        drone3 = new GuardDrone(c, context, spriteDimension);
        drone4 = new GuardDrone(c, context, spriteDimension);
    }

    public void initializeLaserGate(int percent, int maxWidth, int minWidth){
        laserGatePercentage = percent;
        laserGateMinWidth = minWidth;
        laserGateMaxWidth = maxWidth;
    }

    public void updateYValues(Walls walls){
        int[][] wallArray = walls.getWallArray();
        if (screenHeight/(2*numberGates)> wallArray[0][0]){
            //Here we have that the first wall has not moved down enough to fit in a laser gate above it
            newGate = true;
            for(int ii = 0; ii<numberGates; ii++){
                yArray[ii][0] = wallArray[1][0] + (2*ii-1) * screenHeight/(2*numberGates);
            }
        } else{
            if (newGate){
                if(yArray[3][3] == 1){
                    if (yArray[3][4] == 0 ){
                        //We have not hit a wall
                        consecutiveDrones = 0;
                        consecutiveClear ++;
                    }
                    else {
                        consecutiveClear = 0;
                    }
                }
                for (int jj = numberGates-1; jj > 0; jj--){
                    for (int kk = 1; kk < gateAttributes; kk++){
                        //If we have a new gate, we move the old gates down in the array
                        yArray[jj][kk] = yArray[jj-1][kk];
                    }
                }
                double widthRange = Math.random() * (laserGateMaxWidth - laserGateMinWidth);
                double leftX = Math.random() * (100 - widthRange - laserGateMinWidth);
                yArray[0][1] = (int) leftX;
                yArray[0][2] = (int) (leftX + laserGateMinWidth + widthRange);
                double randint = Math.random() * 100;
                if (randint < laserGatePercentage){
                    yArray[0][3] = 1;
                } else{
                    yArray[0][3] = 0;
                }
                yArray[0][4] = 0;
                newGate = false;
            }
            for(int ii = 0; ii<wallArray.length; ii++){
                yArray[ii][0] = wallArray[1][0] + (2*ii-3) * screenHeight/(2*numberGates);
            }
        }

    }


    public void onDraw(Walls walls, int spriteXCoord, int spriteYCoord){
        spriteX = spriteXCoord;
        spriteY = spriteYCoord;
        updateYValues(walls);
        for (int jj = 0; jj <numberGates; jj++){
             if(yArray[jj][3] == 1){
                Rect wall = new Rect(screenWidth*yArray[jj][1]/100,yArray[jj][0]-gateHeight/2,screenWidth*yArray[jj][2]/100,yArray[jj][0] + gateHeight/2);
                if(shift < 3*laserGateBmpWidth/4){
                    shift ++;
                }else{
                    shift = 0;
                }
                Rect whichImage1 = new Rect(shift,0,laserGateBmpWidth/4 + shift,laserGateBmpHeight);
                Rect whichImage2 = new Rect(3*laserGateBmpWidth/4 - shift,0,laserGateBmpWidth - shift,laserGateBmpHeight);
                if (yArray[jj][4] == 0){
                    canvas.drawBitmap(laserGate,whichImage1,wall,null);
                    canvas.drawBitmap(laserGate,whichImage2,wall,null);
                }
                Rect end1 = new Rect(screenWidth*yArray[jj][1]/100 - gateHeight/2,yArray[jj][0] - gateHeight,screenWidth*yArray[jj][1]/100 + gateHeight/2,yArray[jj][0] + gateHeight);
                Rect end2 = new Rect(screenWidth*yArray[jj][2]/100 - gateHeight/2,yArray[jj][0] - gateHeight,screenWidth*yArray[jj][2]/100 + gateHeight/2,yArray[jj][0] + gateHeight);
                canvas.drawBitmap(laserGateEnds,null,end1,null);
                canvas.drawBitmap(laserGateEnds,null,end2,null);
            }
        }
        if(checkLaserCollision()){
            activateDrone();
        }
        runDrones();
    }

    public boolean checkLaserCollision(){
        for(int ii = 0; ii < numberGates; ii++){
            if (yArray[ii][3] == 1 && yArray[ii][4] == 0){
                if( spriteY + spriteDim/2 > yArray[ii][0] - gateHeight &&
                        spriteY - spriteDim/2 < yArray[ii][0] + gateHeight){
                    //We are alligned in the y direction
                    if(((spriteX + spriteDim/2 > yArray[ii][1]*screenWidth/100) && (spriteX + spriteDim/2 < yArray[ii][2]*screenWidth/100)) ||
                            ((spriteX - spriteDim/2 > yArray[ii][1]*screenWidth/100) && (spriteX - spriteDim/2 < yArray[ii][2]*screenWidth/100)) ||
                             (spriteX - spriteDim/2 < yArray[ii][1]*screenWidth/100 && spriteX + spriteDim/2 >yArray[ii][2]*screenWidth/100 )){
                        //We are alligned in the x direction
                        yArray[ii][4] = 1;
                        consecutiveDrones ++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void activateDrone(){
        int randint;
        if (spriteY > screenHeight/2){
            randint = (int) Math.ceil(Math.random() * 5);
        } else{
            randint = (int) Math.ceil(3 + Math.random() * 5);
        }

        if (!drone1running){
            drone1running = true;
            drone1.initialisePosition(randint,spriteX,spriteY);
        } else if(!drone2running){
            drone2running = true;
            drone2.initialisePosition(randint,spriteX,spriteY);
        } else if(!drone3running){
            drone3running = true;
            drone3.initialisePosition(randint,spriteX,spriteY);
        } else if(!drone4running){
            drone4running = true;
            drone4.initialisePosition(randint,spriteX,spriteY);
        }
    }

    public void runDrones(){
        if(drone1running){
            drone1.onDraw(spriteX,spriteY);
            lose = (lose || drone1.checkLose());
            drone1running = drone1.active;
        }
        if(drone2running){
            drone2.onDraw(spriteX,spriteY);
            lose = (lose || drone2.checkLose());
            drone2running = drone2.active;
        }
        if(drone3running){
            drone3.onDraw(spriteX,spriteY);
            lose = (lose || drone3.checkLose());
            drone3running = drone3.active;
        }
        if(drone4running){
            drone4.onDraw(spriteX,spriteY);
            lose = (lose || drone4.checkLose());
            drone4running = drone4.active;
        }
    }

    public boolean check4drones(){
        return ( drone1running && drone2running && drone3running && drone4running);
    }

    public boolean checkConsecutiveWalls24(){
        return (consecutiveClear  >= 5);
    }

    public boolean checkConsecutiveWalls25(){
        return (consecutiveDrones >= 15);
    }

}

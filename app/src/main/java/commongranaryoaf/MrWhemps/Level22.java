package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Canvas;

import java.io.File;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Level22 extends Sprite{

    CoinClass Coins;
    Walls walls;
    Context context;
    Drone drone1, drone2, drone3;
    int droneDimension;
    String achievementfilename = "Achievement_data.txt";
    File achievementdatafile;
    String achievementdatafilePath;
    boolean achievementUnlocked;
    boolean announceAchievement = false;
    boolean drone1IntersectDrone2 = false;
    boolean drone2IntersectDrone3 = false;
    boolean drone1IntersectDrone3 = false;

    public Level22(Context c, int level){
        super(c,level);
        context = c;
        achievementdatafilePath = context.getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
        achievementUnlocked = FileTools.readSpecificAchievementFromFile(19,achievementdatafilePath);
    }

    public void onDraw(Canvas c, float x){
        if(!levelCreated){
            walls = new Walls(c, level, context);
            walls.initializeNormalMovingWall(100, 50, 0.2);
            walls.setWallSpeedType(WallTypes.NORMAL);
            super.createLevel(c);
            Coins = new CoinClass(c, spriteDimensions, CoinType.NORMAL, context);
            drone1 = new Drone(cWidth,cHeight,context, spriteDimensions,1);
            drone2 = new Drone(cWidth,cHeight,context, spriteDimensions,6);
            drone3 = new Drone(cWidth,cHeight,context, spriteDimensions,8);
            droneDimension = drone1.getDroneDimension();


        }
        Coins.onDraw();
        super.move(x);
        walls.updateWalls();
        walls.moveMovingWalls();
        walls.onDraw();
        checkSides();
        checkOrdinaryWalls();
        checkOrdinaryPartialWalls();
        drone1.onDraw(c, spriteX, spriteY);
        drone2.onDraw(c, spriteX, spriteY);
        drone3.onDraw(c, spriteX, spriteY);
        super.onDraw(c, x);
        Coins.onDraw();
        Coins.checkCoins(spriteX, spriteY);
        super.drawLowerBoundary(c);
        if(!achievementUnlocked){
            int x12Distance = Math.abs(drone1.getDroneX() - drone2.getDroneX());
            int y12Distance = Math.abs(drone1.getDroneY() - drone2.getDroneY());
            drone1IntersectDrone2 = (Math.pow(x12Distance,2) + Math.pow(y12Distance,2) < Math.pow(droneDimension,2));

            int x13Distance = Math.abs(drone1.getDroneX() - drone3.getDroneX());
            int y13Distance = Math.abs(drone1.getDroneY() - drone3.getDroneY());
            drone1IntersectDrone3 = (Math.pow(x13Distance,2) + Math.pow(y13Distance,2) < Math.pow(droneDimension,2));

            int x23Distance = Math.abs(drone2.getDroneX() - drone3.getDroneX());
            int y23Distance = Math.abs(drone2.getDroneY() - drone3.getDroneY());
            drone2IntersectDrone3 = (Math.pow(x23Distance,2) + Math.pow(y23Distance,2) < Math.pow(droneDimension,2));

            if(drone1IntersectDrone3 && drone1IntersectDrone2 && drone2IntersectDrone3){
                FileTools.writeAchievementToFile(19,achievementdatafilePath,achievementdatafile);
                achievementUnlocked = true;
                announceAchievement = true;
            }
        }
    }

    public void checkOrdinaryWalls(){
        super.checkOrdinaryWalls(walls);
    }

    public void checkOrdinaryPartialWalls(){
        super.checkOrdinaryPartialWalls(walls);
    }

    public void checkSides(){
        super.checkSides();
    }

    public void jump(){
        super.jump();
    }

    public boolean getLose(){
        return (super.getLose() || drone1.checkLose() || drone2.checkLose() || drone3.checkLose());
    }

    public int getAchievement(){
        if (announceAchievement){
            return 13;
        }
        return -1;
    }

    public int getScore(){
        return Coins.getScore();
    }

    public float getSpeed() { return walls.wallSpeed; }
}
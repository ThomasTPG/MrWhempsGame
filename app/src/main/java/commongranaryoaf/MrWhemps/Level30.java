package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Canvas;

import java.io.File;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Level30 extends Sprite{

    CoinClass Coins;
    Walls walls;
    Context context;
    LaserGates laserGate;
    Drone drone1;
    String achievementfilename = "Achievement_data.txt";
    File achievementdatafile;
    String achievementdatafilePath;
    boolean achievementUnlocked;
    boolean announceAchievement = false;

    public Level30(Context c, int level){
        super(c,level);
        context = c;
        achievementdatafilePath = context.getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
    }

    public void onDraw(Canvas c, float x){
        if(!levelCreated){
            walls = new Walls(c, level, context);
            walls.initializeNormalMovingWall(20,30,0.2);
            walls.initializeStopAndGoStationary(20, 30);
            walls.initializeHardWallsComplete(20, 30);
            walls.initializeHardWallsPartial(20, 30);
            walls.initializeHardMovingWall(20, 30, 0.3);
            walls.setWallSpeedType(WallTypes.CUBEROOTSPEED);
            walls.setSpeedMultiplier(0.8);
            super.createLevel(c);
            Coins = new CoinClass(c, spriteDimensions, CoinType.EVERYWHERE, context);
            laserGate = new LaserGates(c,context, spriteDimensions);
            laserGate.initializeLaserGate(50,20,10);
            drone1 = new Drone(c.getWidth(),c.getHeight(),context,spriteDimensions,1);
            achievementUnlocked = FileTools.readSpecificAchievementFromFile(17,achievementdatafilePath);
        }
        Coins.onDraw();
        super.move(x);
        walls.updateWalls();
        walls.moveMovingWalls();
        walls.onDraw();
        drone1.onDraw(c,spriteX,spriteY);
        laserGate.onDraw(walls, spriteX, spriteY);
        checkSides();
        checkOrdinaryWalls();
        super.checkOrdinaryPartialWalls(walls);
        super.checkHardWalls(walls,walls.getWallHeight());
        super.checkStopAndGo(walls);
        super.checkOrdinaryWalls(walls);
        super.onDraw(c, x);
        Coins.onDraw();
        Coins.checkMultiplier(walls.getSpeedMultiplier());
        Coins.checkCoins(spriteX, spriteY);
        super.drawLowerBoundary(c);
        if(!achievementUnlocked){
            if(Coins.level30achievement()){
                FileTools.writeAchievementToFile(17,achievementdatafilePath,achievementdatafile);
                achievementUnlocked = true;
                announceAchievement = true;
            }
        };
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
        return (super.getLose() || laserGate.lose || drone1.checkLose());
    }

    public int getScore(){
        return Coins.getScore();
    }

    public int getAchievement(){
        if (announceAchievement){
            return 10;
        }
        return -1;
    }

    public float getSpeed() { return walls.wallSpeed; }
}
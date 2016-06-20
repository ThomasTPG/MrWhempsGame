package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Canvas;

import java.io.File;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Level23 extends Sprite{

    CoinClass Coins;
    Walls walls;
    Context context;
    Drone drone1;
    String achievementfilename = "Achievement_data.txt";
    File achievementdatafile;
    String achievementdatafilePath;
    boolean achievementUnlocked;
    boolean announceAchievement = false;

    public Level23(Context c, int level){
        super(c,level);
        context = c;
        achievementdatafilePath = context.getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
    }

    public void onDraw(Canvas c, float x){
        if(!levelCreated){
            walls = new Walls(c, level, context);
            walls.initializeStopAndGoStationary(100, 100);
            walls.setWallSpeedType(WallTypes.SQUAREROOTSPEED);
            super.createLevel(c);
            Coins = new CoinClass(c, spriteDimensions, CoinType.EVERYWHERE, context);
            drone1 = new Drone(cWidth,cHeight,context, spriteDimensions,1);
            achievementUnlocked = FileTools.readSpecificAchievementFromFile(11,achievementdatafilePath);

        }
        Coins.onDraw();
        super.move(x);
        walls.updateWalls();
        walls.moveMovingWalls();
        walls.onDraw();
        checkSides();
        checkOrdinaryWalls();
        super.checkStopAndGo(walls);
        super.onDraw(c, x);
        Coins.onDraw();
        Coins.checkMultiplier(walls.getSpeedMultiplier());
        Coins.checkCoins(spriteX, spriteY);
        super.drawLowerBoundary(c);
        drone1.onDraw(c, spriteX, spriteY);
        if(!achievementUnlocked){
            if(Coins.level23achievement()){
                FileTools.writeAchievementToFile(11,achievementdatafilePath,achievementdatafile);
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
        return (super.getLose() || drone1.checkLose());
    }

    public int getScore(){
        return Coins.getScore();
    }

    public int getAchievement(){
        if (announceAchievement){
            return 9;
        }
        return -1;
    }

    public float getSpeed() { return walls.wallSpeed; }
}
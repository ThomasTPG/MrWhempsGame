package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Canvas;

import java.io.File;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Level11 extends Sprite{

    CoinClass Coins;
    Walls walls;
    Context context;
    String achievementfilename = "Achievement_data.txt";
    File achievementdatafile;
    String achievementdatafilePath;
    boolean achievementUnlocked;
    boolean announceAchievement = false;

    public Level11(Context c, int level){
        super(c,level);
        context = c;
        achievementdatafilePath = context.getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
    }

    public void onDraw(Canvas c, float x){
        if(!levelCreated){
            walls = new Walls(c, level, context);
            walls.setWallSpeedType(WallTypes.CUBEROOTSPEED);
            walls.initializeHardWallsComplete(100, 80);
            super.createLevel(c);
            Coins = new CoinClass(c, spriteDimensions,CoinType.EVERYWHERE, context);
            achievementUnlocked = FileTools.readSpecificAchievementFromFile(13,achievementdatafilePath);
        }
        Coins.onDraw();
        super.move(x);
        walls.updateWalls();
        walls.onDraw();
        checkSides();
        checkOrdinaryWalls();
        checkHardWallsComplete();
        super.onDraw(c, x);
        Coins.onDraw();
        Coins.checkCoins(spriteX, spriteY);
        super.drawLowerBoundary(c);
        if(!achievementUnlocked){
            if(Coins.level11achievement()){
                FileTools.writeAchievementToFile(13,achievementdatafilePath,achievementdatafile);
                achievementUnlocked = true;
                announceAchievement = true;
            }
        };
    }

    public void checkOrdinaryWalls(){
        super.checkOrdinaryWalls(walls);
    }

    public void checkHardWallsComplete(){
        int wallHeight = walls.getWallHeight();
        super.checkHardWalls(walls, wallHeight);
    }

    public int getAchievement(){
        if (announceAchievement){
            return 13;
        }
        return -1;
    }

    public void checkSides(){
        super.checkSides();
    }

    public void jump(){
        super.jump();
    }

    public boolean getLose(){
        return super.getLose();
    }

    public int getScore(){
        return Coins.getScore();
    }

    public float getSpeed() { return walls.wallSpeed; }
}
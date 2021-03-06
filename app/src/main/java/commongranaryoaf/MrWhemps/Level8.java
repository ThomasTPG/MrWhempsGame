package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Level8 extends Sprite{

    CoinClass Coins;
    Walls walls;
    Context context;
    String achievementfilename = "Achievement_data.txt";
    File achievementdatafile;
    String achievementdatafilePath;
    boolean achievementUnlocked;
    boolean announceAchievement = false;

    public Level8(Context c, int level){
        super(c, level);
        context = c;
        achievementdatafilePath = context.getFilesDir() + "/" + achievementfilename;
        achievementdatafile = new File(achievementdatafilePath);
    }

    public void onDraw(Canvas c, float x){
        if(!levelCreated){
            walls = new Walls(c, level, context);
            walls.setWallSpeedType(WallTypes.CUBEROOTSPEED);
            walls.initializeNormalMovingWall(80, 50, 0.3);
            walls.initializePartialNormalWalls(20, 20);
            super.createLevel(c);
            Coins = new CoinClass(c, spriteDimensions, CoinType.EVERYWHERE, context);
            achievementUnlocked = FileTools.readSpecificAchievementFromFile(12,achievementdatafilePath);

        }

        super.move(x);
        walls.updateWalls();
        walls.moveMovingWalls();
        walls.onDraw();
        checkSides();
        checkOrdinaryWalls();
        super.checkOrdinaryPartialWalls(walls);
        super.onDraw(c, x);
        Coins.onDraw();
        Coins.checkCoins(spriteX, spriteY);
        if(!achievementUnlocked){
            if(Coins.level8achievement()){
                FileTools.writeAchievementToFile(12,achievementdatafilePath,achievementdatafile);
                achievementUnlocked = true;
                announceAchievement = true;
            }
        }
        super.drawLowerBoundary(c);
    }

    public void checkOrdinaryWalls(){
        super.checkOrdinaryWalls(walls);
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

    public int getAchievement(){
        if (announceAchievement){
            return 9;
        }
        return -1;
    }

    public int getScore(){
        return Coins.getScore();
    }

    public float getSpeed() { return walls.wallSpeed; }

}
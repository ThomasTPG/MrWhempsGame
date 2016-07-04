package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Canvas;

import java.io.File;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Level25 extends Sprite{

    CoinClass Coins;
    Walls walls;
    Context context;
    LaserGates laserGate;

    public Level25(Context c, int level){
        super(c,level);
        context = c;
    }

    public void onDraw(Canvas c, float x){
        if(!levelCreated){
            walls = new Walls(c, level, context);
            walls.initializeNormalMovingWall(50,40,0.2);
            walls.initializeHardMovingWall(50,20,0.2);
            walls.setWallSpeedType(WallTypes.CUBEROOTSPEED);
            super.createLevel(c);
            Coins = new CoinClass(c, spriteDimensions, CoinType.NORMAL, context);
            laserGate = new LaserGates(c,context, spriteDimensions);
            laserGate.initializeLaserGate(30,60,30);
        }
        Coins.onDraw();
        super.move(x);
        walls.updateWalls();
        walls.moveMovingWalls();
        walls.onDraw();
        laserGate.onDraw(walls, spriteX, spriteY);
        checkSides();
        checkOrdinaryWalls();
        super.checkOrdinaryPartialWalls(walls);
        super.checkHardWalls(walls,walls.getWallHeight());
        super.onDraw(c, x);
        Coins.onDraw();
        Coins.checkMultiplier(walls.getSpeedMultiplier());
        Coins.checkCoins(spriteX, spriteY);
        super.drawLowerBoundary(c);
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
        return (super.getLose() || laserGate.lose);
    }

    public int getScore(){
        return Coins.getScore();
    }

    public float getSpeed() { return walls.wallSpeed; }
}
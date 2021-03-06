package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Canvas;

import java.io.File;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Level29 extends Sprite{

    CoinClass Coins;
    Walls walls;
    Context context;
    LaserGates laserGate;
    Drone drone1;
    Drone drone2;

    public Level29(Context c, int level){
        super(c,level);
        context = c;
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
            Coins = new CoinClass(c, spriteDimensions, CoinType.NORMAL, context);
            laserGate = new LaserGates(c,context, spriteDimensions);
            laserGate.initializeLaserGate(20,50,10);
            drone1 = new Drone(c.getWidth(),c.getHeight(),context,spriteDimensions,1);
            drone2 = new Drone(c.getWidth(),c.getHeight(),context,spriteDimensions,5);
        }
        Coins.onDraw();
        super.move(x);
        walls.updateWalls();
        walls.moveMovingWalls();
        walls.onDraw();
        drone1.onDraw(c, spriteX, spriteY);
        drone2.onDraw(c,spriteX,spriteY);
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
        return (super.getLose() || laserGate.lose || drone1.checkLose() || drone2.checkLose());
    }

    public int getScore(){
        return Coins.getScore();
    }

    public float getSpeed() { return walls.wallSpeed; }
}
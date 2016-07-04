package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Canvas;

import java.io.File;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Level27 extends Sprite{

    CoinClass Coins;
    Walls walls;
    Context context;
    LaserGates laserGate;
    Drone drone1;

    public Level27(Context c, int level){
        super(c,level);
        context = c;
    }

    public void onDraw(Canvas c, float x){
        if(!levelCreated){
            walls = new Walls(c, level, context);
            walls.initializePartialNormalWalls(40,40);
            walls.initializeHardWallsPartial(10,20);
            walls.initializeStopAndGoStationary(10,40);
            walls.initializeNormalMovingWall(40,40,0.1);
            walls.setWallSpeedType(WallTypes.CUBEROOTSPEED);
            super.createLevel(c);
            Coins = new CoinClass(c, spriteDimensions, CoinType.NORMAL, context);
            laserGate = new LaserGates(c,context, spriteDimensions);
            laserGate.initializeLaserGate(30,60,30);
            drone1 = new Drone(cWidth,cHeight,context, spriteDimensions,1);
        }
        Coins.onDraw();
        super.move(x);
        walls.updateWalls();
        walls.moveMovingWalls();
        walls.onDraw();
        drone1.onDraw(c, spriteX, spriteY);
        laserGate.onDraw(walls, spriteX, spriteY);
        checkSides();
        checkOrdinaryWalls();
        super.checkOrdinaryPartialWalls(walls);
        super.checkStopAndGo(walls);
        super.checkHardWalls(walls,walls.getWallHeight());
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
        return (super.getLose() || laserGate.lose || drone1.checkLose());
    }

    public int getScore(){
        return Coins.getScore();
    }

    public float getSpeed() { return walls.wallSpeed; }
}
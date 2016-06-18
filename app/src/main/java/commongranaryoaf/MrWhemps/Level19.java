package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Level19 extends Sprite{

    CoinClass Coins;
    Walls walls;
    Context context;
    Drone drone;

    public Level19(Context c, int level){
        super(c,level);
        context = c;
    }

    public void onDraw(Canvas c, float x){
        if(!levelCreated){
            walls = new Walls(c, level, context);
            walls.initializeNormalMovingWall(40, 50, 0.2);
            walls.initializeHardMovingWall(30,20,0.1);
            walls.initializeHardWallsPartial(30,20);
            walls.setWallSpeedType(WallTypes.NORMAL);
            super.createLevel(c);
            Coins = new CoinClass(c, spriteDimensions, CoinType.NORMAL, context);
            drone = new Drone(cWidth,cHeight,context,spriteDimensions,1);

        }
        Coins.onDraw();
        super.move(x);
        walls.updateWalls();
        walls.moveMovingWalls();
        walls.onDraw();
        checkSides();
        checkOrdinaryWalls();
        checkOrdinaryPartialWalls();
        checkHardWalls(walls,walls.getWallHeight());
        drone.onDraw(c, spriteX, spriteY);
        super.onDraw(c, x);
        Coins.onDraw();
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
        return (super.getLose() || drone.checkLose());
    }

    public int getScore(){
        return Coins.getScore();
    }

    public float getSpeed() { return walls.wallSpeed; }
}
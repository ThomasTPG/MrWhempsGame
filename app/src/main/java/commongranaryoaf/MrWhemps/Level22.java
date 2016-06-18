package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Level22 extends Sprite{

    CoinClass Coins;
    Walls walls;
    Context context;
    Drone drone1, drone2, drone3;

    public Level22(Context c, int level){
        super(c,level);
        context = c;
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

    public int getScore(){
        return Coins.getScore();
    }

    public float getSpeed() { return walls.wallSpeed; }
}
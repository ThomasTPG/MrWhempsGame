package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Level10 extends Sprite{

    CoinClass Coins;
    Walls walls;
    Context context;

    public Level10(Context c, int level){
        super(c,level);
        context = c;
    }

    public void onDraw(Canvas c, float x){
        if(!levelCreated){
            walls = new Walls(c, level, context);
            walls.setWallSpeedType(WallTypes.CUBEROOTSPEED);
            walls.initializeHardWallsComplete(100,66);
            super.createLevel(c);
            Coins = new CoinClass(c, spriteDimensions,CoinType.NORMAL,context);
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
    }

    public void checkOrdinaryWalls(){
        super.checkOrdinaryWalls(walls);
    }

    public void checkHardWallsComplete(){
        int wallHeight = walls.getWallHeight();
        super.checkHardWalls(walls, wallHeight);
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
package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Canvas;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Level8 extends Sprite{

    CoinClass Coins;
    Walls walls;
    Context context;

    public Level8(Context c, int level){
        super(c,level);
        context = c;
    }

    public void onDraw(Canvas c, float x){
        if(!levelCreated){
            walls = new Walls(c, level, context);
            walls.setWallSpeedType(WallTypes.CUBEROOTSPEED);
            walls.initializeNormalMovingWall(80, 50, 0.3);
            walls.initializePartialNormalWalls(20, 20);
            super.createLevel(c);
            Coins = new CoinClass(c, spriteDimensions, CoinType.EVERYWHERE, context);

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

    public int getScore(){
        return Coins.getScore();
    }

    public float getSpeed() { return walls.wallSpeed; }

}
package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Thomas on 06/02/2016.
 */
public class Level1 extends Sprite{

    CoinClass Coins;
    Walls walls;
    Context context;
    Paint bgroundColour = new Paint();
    Paint textColour = new Paint();
    int stage=0;
    boolean stageComplete = true;
    String AdviceString;
    int score = 0;
    Bitmap pointer;
    int whichPointerInt = 1;
    int count = 0;

    public Level1(Context c, int level){
        super(c, level);
        context = c;
        bgroundColour.setColor(Color.WHITE);
        textColour.setColor(Color.BLACK);
        pointer = BitmapFactory.decodeResource(context.getResources(), R.drawable.pointer_sheet);
    }

    public void onDraw(Canvas c, float x){
        if(!levelCreated){
            walls = new Walls(c, level, context);
            walls.setWallSpeedType(WallTypes.CUBEROOTSPEED);
            walls.startTutorial();
            super.createLevel(c);
            Coins = new CoinClass(c, spriteDimensions, CoinType.TUTORIAL, context);
        }
        if(stageComplete){
            tutorialNewStage();
        }
        stageSpecifics(c);

        Coins.onDraw();
        super.move(x);
        walls.onDraw();
        checkSides();
        checkOrdinaryWalls();
        super.checkOrdinaryPartialWalls(walls);
        super.checkHardWalls(walls, walls.getWallHeight());
        super.onDraw(c, x);
        Coins.onDraw();
        Coins.checkCoins(spriteX, spriteY);
    }

    public void tutorialNewStage(){
        stage ++;
        stageComplete = false;
        switch(stage){
            case(1):
                Coins.coinTutorial(9, 9);
                AdviceString = "Mr Whemps will move inline with your finger. Collect 5 coins.";
                break;
            case(2):
                Coins.coinTutorial(9,7);
                AdviceString = "When you take your finger off the screen, Mr Whemps will jump. Collect 5 more coins.";
                break;
            case(3):
                Coins.coinTutorial(0,6);
                AdviceString = "Jump on to the platform to get the coin. You can jump through this sort of platform.";
                break;
            case(4):
                Coins.coinTutorial(0,3);
                AdviceString = "You cannot jump through this next type of platform. Jump around it instead.";
                break;
            case(5):
                Coins.coinTutorial(9,1);
                AdviceString = "Jump to get the next coin. Tap on the guide dot.";
                break;
            case(6):
                Coins.coinTutorial(0,1);
                AdviceString = "Jump to get the next coin. Tap on the guide dot.";
                break;
            case(7):
                Coins.coinTutorial(9,1);
                AdviceString = "The top wall has changed and you now can't jump through it. Get the final coin.";
                break;
            case(8):
                AdviceString = "What doesn't kill you can only make you stronger? Mr Whemps will survive the fire for now, but if he touches it again he will die.";
                break;
        }
    }

    public void stageSpecifics(Canvas c){
        c.drawRect(cWidth / 2, cHeight / 2, cWidth, 7 * cHeight / 10, bgroundColour);
        textColour.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.tutorial_font_size));
        final float densityMultiplier = context.getResources().getDisplayMetrics().density;
        final float scaledPx = 14 * densityMultiplier;
        textColour.setTextSize(scaledPx);
        String[] words = AdviceString.split(" ");
        String currentLine = "";
        int y = (int) (5*cHeight/10 - 1.3*(textColour.ascent() + textColour.descent()));
        for (int ii = 0; ii < words.length; ii++){
            if (textColour.measureText(currentLine  + words[ii] + " ") < cWidth/2){
                currentLine = currentLine + words[ii]+ " ";
            }
            else{
                c.drawText(currentLine,cWidth/2,y,textColour);
                y -= 1.3*(textColour.ascent() + textColour.descent());
                currentLine = words[ii] + " ";
            }
        }
        c.drawText(currentLine,cWidth/2,y,textColour);
        y -= 2.6*(textColour.ascent() + textColour.descent());
        Rect whichPointer = new Rect((pointer.getWidth()/3) * (whichPointerInt - 1), 0, (pointer.getWidth()/3) * (whichPointerInt), pointer.getHeight());

        count ++;
        if (count > 10)
        {
            count = 0;
            whichPointerInt = ((whichPointerInt + 1) % 3) + 1;
        }
        switch (stage){
            case(1):
                c.drawText("Coins remaining: " + Math.max((50 - score)/10, 0), cWidth / 2, y, textColour);
                break;
            case(2):
                c.drawText("Coins remaining: " + Math.max((100-score)/10,0),cWidth/2,y,textColour);
                break;
            case(3):
                c.drawText("Coins remaining: " + Math.max((110-score)/10,0),cWidth/2,y,textColour);
                break;
            case(4):
                if ((Math.abs(cHeight*0.75 - spriteDimensions/2 - spriteY) < 3) && (readyToJump)){
                    Rect pointer_location = new Rect(cWidth/3 - cWidth/20, cHeight/2 - cWidth/20, cWidth/3 + cWidth/20, cHeight/2 + cWidth/20);
                    c.drawBitmap(pointer, whichPointer, pointer_location, null);
                }
                if ((Math.abs(cHeight*0.5 - spriteDimensions/2 - spriteY) < cHeight*0.1)){
                    Rect pointer_location = new Rect(cWidth/10 - cWidth/20, 4*cHeight/10 - cWidth/20, cWidth/10 + cWidth/20, 4*cHeight/10 + cWidth/20);
                    c.drawBitmap(pointer, whichPointer, pointer_location, null);
                }
                c.drawText("Coins remaining: " + Math.max((120-score)/10,0),cWidth/2,y,textColour);
                break;
            case(5):
                if ((Math.abs(cHeight*0.5 - spriteDimensions/2 - spriteY) < 3) && (readyToJump)){
                    Rect pointer_location = new Rect(9*cWidth/10 - cWidth/20, cHeight/20 - cWidth/20, 9*cWidth/10 + cWidth/20, cHeight/20 + cWidth/20);
                    c.drawBitmap(pointer, whichPointer, pointer_location, null);
                }
                c.drawText("Coins remaining: " + Math.max((130-score)/10,0),cWidth/2,y,textColour);
                break;
            case(6):
                c.drawText("Coins remaining: " + Math.max((140-score)/10,0),cWidth/2,y,textColour);
                break;
            case(7):
                if ((Math.abs(cHeight*0.5 - spriteDimensions/2 - spriteY) < 3) && (readyToJump)){
                    Rect pointer_location = new Rect(cWidth/10 - cWidth/20, cHeight/20 - cWidth/20, cWidth/10 + cWidth/20, cHeight/20 + cWidth/20);
                    c.drawBitmap(pointer, whichPointer, pointer_location, null);
                }
                if ((Math.abs(cHeight*0.1 - spriteDimensions/2 - spriteY) < cHeight * 0.2)){
                    Rect pointer_location = new Rect(9*cWidth/10 - cWidth/20, cHeight/20 - cWidth/20, 9*cWidth/10 + cWidth/20, cHeight/20 + cWidth/20);
                    c.drawBitmap(pointer, whichPointer,pointer_location,null);
                }
                c.drawText("Coins remaining: " + Math.max((150-score)/10,0),cWidth/2,y,textColour);
                break;
            case(8):
                super.drawLowerBoundary(c);
                walls.updateWalls();
                walls.tutorialComplete();
                break;
        }
    }

    public void checkOrdinaryWalls(){
        super.checkOrdinaryWalls(walls);
    }

    public void checkSides(){
        super.checkSides();
    }

    public float getSpeed() { return walls.wallSpeed; }

    public void jump(){
        super.jump();
    }

    public boolean getLose(){
        boolean complete = (stage == 8);
        return (super.getLose() && complete);
    }

    public int getScore(){
        if (Coins.getScore() > score){
            score = Coins.getScore();
            switch(stage){
                case(1):
                    Coins.coinTutorial(((score*13)%9),9);
                    if (score == 50){
                        tutorialNewStage();
                    }
                    break;
                case(2):
                    Coins.coinTutorial(2+((score*7)%6),8-((score*13)%2));
                    if (score == 100){
                        tutorialNewStage();
                    }
                    break;
                case(3):
                    if (score == 110){
                        tutorialNewStage();
                    }
                    break;
                case(4):
                    if (score == 120){
                        tutorialNewStage();
                    }
                    break;
                case(5):
                    if (score == 130){
                        tutorialNewStage();
                    }
                    break;
                case(6):
                    if (score == 140){
                        tutorialNewStage();
                        walls.tutorialChangeType();
                    }
                    break;
                case(7):
                    if (score == 150){
                        lose = false;
                        tutorialNewStage();
                        walls.setSpeedMultiplier(1);
                        Coins.coinTutorial(2+((score*5)%6),8-((score*7)%2));
                    }
                    break;
                case(8):
                    Coins.coinTutorial(2+((score*5)%6),8-((score*5)%8));
                    break;
                default:
                    break;
            }
        }

        return score;
    }
}
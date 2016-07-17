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
public class Sprite{

    Bitmap character;
    Rect whichImage;
    Rect position;
    int spriteX;
    int spriteY;
    int bitmapWidth;
    int bitmapHeight;
    int spriteDimensions;
    int ySpeed;
    int xSpeed = 0;
    int gravity = 15;
    boolean inAir = false;
    boolean readyToJump = true;
    int jumpCount = 0;
    int jumpLimit = 11;
    int jumpFactor = 5;
    boolean levelCreated = false;
    int cWidth;
    int cHeight;
    int lvl16AchCount = -1;
    int lvl16AchPreviousWall = -10;
    String coindatafilename = "Coin_data.txt";
    String coindatafilePath;


    int level;
    boolean lose = false;
    LevelSpecifics levelSpecifics;


    public Sprite(Context context,int ourLevel) {
        coindatafilePath = context.getFilesDir() + "/" + coindatafilename;
        int costume = FileTools.readCostumeFromFile(coindatafilePath);
        switch (costume){
            case(0):
                character = BitmapFactory.decodeResource(context.getResources(), R.drawable.bronze_costume);
                break;
            case(1):
                character = BitmapFactory.decodeResource(context.getResources(), R.drawable.silver_costume);
                break;
            case(2):
                character = BitmapFactory.decodeResource(context.getResources(), R.drawable.gold_costume);
                break;
            case(3):
                character = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_newspaper);
                break;
            case(4):
                character = BitmapFactory.decodeResource(context.getResources(), R.drawable.cubist_costume);
                break;
            case(5):
                character = BitmapFactory.decodeResource(context.getResources(), R.drawable.cyborg_costume);
                break;
            case(6):
                character = BitmapFactory.decodeResource(context.getResources(), R.drawable.neon_costume);
                break;
            case(7):
                character = BitmapFactory.decodeResource(context.getResources(), R.drawable.star_man_costume);
                break;
            case(8):
                character = BitmapFactory.decodeResource(context.getResources(), R.drawable.wealthy_costume);
                break;
            default:
                character = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite1);
                break;

        }
        bitmapWidth = character.getWidth();
        bitmapHeight = character.getHeight();
        spriteX =0;
        spriteY =0;
        ySpeed = 0;
        xSpeed = 0;
        level = ourLevel;
        levelSpecifics = new LevelSpecifics(level);


    }

    public void createLevel(Canvas canvas){
        spriteX = canvas.getWidth()/2;
        spriteY = canvas.getHeight()/2;
        levelCreated = true;
        cWidth = canvas.getWidth();
        cHeight = canvas.getHeight();
        spriteDimensions = cWidth/6;
    }

    public void onDraw(Canvas canvas, float xFromTouch){
        if (!levelCreated){
            createLevel(canvas);
        }
        update();

        position = new Rect(spriteX -spriteDimensions/2, spriteY -spriteDimensions / 2, spriteX + spriteDimensions / 2, spriteY + spriteDimensions/2);
        canvas.drawBitmap(character, whichImage, position, null);

    }

    public void drawLowerBoundary(Canvas canvas){
        //Draw at the bottom of the map to indicate danger
        Paint redpaint = new Paint();
        redpaint.setColor(Color.RED);
        canvas.drawRect(0, (float) (cHeight * 0.98), cWidth, cHeight, redpaint);
    }

    public void drawUpperBoundary(Canvas canvas){
        //Draw at the top of the map to indicate danger
        Paint redpaint = new Paint();
        redpaint.setColor(Color.RED);
        canvas.drawRect(0,0,cWidth,(float)(cHeight*0.02),redpaint);
    }

    public void move(float xFromTouch){
        //Move the sprite towards the finger
        speedCalculator(xFromTouch);
        spriteX = spriteX + xSpeed;
        spriteY += ySpeed;
    }

    public void speedCalculator(float xFromTouch){
        xSpeed = (Math.round(xFromTouch) - spriteX) /3;

        if (inAir){
            if (jumpCount < jumpLimit){
                ySpeed = gravity - jumpFactor*(jumpLimit - jumpCount);
                jumpCount++;
            }
            else{
                jumpCount = 0;
                inAir = false;
            }
        }
        else{
            ySpeed = gravity;
        }
    }



    public void jump(){
        if (readyToJump){
            jumpCount = 0;
            inAir = true;
            readyToJump = false;
        }
    }

    public void update(){
        whichImage = new Rect(0,0,bitmapWidth,bitmapHeight);
    }


    public void checkOrdinaryWalls(Walls walls){
        if (ySpeed > 0){
            int[][] wallArray = walls.getWallArray();
            for (int ii = 0; ii<wallArray.length; ii++){
                if (wallArray[ii][1] == WallTypes.NORMAL){
                        //Check y bounds
                        if ((wallArray[ii][0] < spriteY + spriteDimensions/2) && (wallArray[ii][0] > spriteY)){
                            spriteY = wallArray[ii][0] - spriteDimensions/2;
                            readyToJump = true;
                        }
                }
            }
        }
    }

    public void checkOrdinaryPartialWalls(Walls walls){
        if (ySpeed > 0){
            int[][] wallArray = walls.getWallArray();
            for (int ii = 0; ii<wallArray.length; ii++){
                switch (wallArray[ii][1]){
                    case(WallTypes.NORMALPARTIAL):
                    case(WallTypes.MOVINGNORMAL):
                        //Check x bounds
                        if ((spriteX+spriteDimensions/2 > (double) wallArray[ii][2] * cWidth/100) && (spriteX - spriteDimensions/2 < (double) wallArray[ii][3] * cWidth/100)){
                            //Check y bounds
                            if ((wallArray[ii][0] < spriteY + spriteDimensions/2) && (wallArray[ii][0] > spriteY)){
                                spriteY = wallArray[ii][0] - spriteDimensions/2;
                                readyToJump = true;
                            }
                        }
                        break;
                }

            }
        }
    }

    public void checkStopAndGo(Walls walls){
        //Returns 0 for nothing, 1 for go and -1 for stop
        int whatToDo = WallTypes.DONOTHING;
        if (ySpeed > 0){
            int[][] wallArray = walls.getWallArray();
            for (int ii = 0; ii<wallArray.length; ii++){
                switch (wallArray[ii][1]){
                    case(WallTypes.STOPANDGOSTATIONARY):
                        //Check x bounds
                        if ((spriteX+spriteDimensions/2 > (double) wallArray[ii][2] * cWidth/100) && (spriteX - spriteDimensions/2 < (double) wallArray[ii][3] * cWidth/100)){
                            //Check y bounds
                            if ((wallArray[ii][0] < spriteY + spriteDimensions/2) && (wallArray[ii][0] > spriteY)){
                                spriteY = wallArray[ii][0] - spriteDimensions/2;
                                readyToJump = true;
                                boolean right = (spriteX > (wallArray[ii][3] + wallArray[ii][2]) * cWidth/200);
                                if (right){
                                    if (wallArray[ii][5] == WallTypes.STOPANDGOLEFTUP){
                                        whatToDo = WallTypes.MULTIPLIERINCREASE;
                                    } else{
                                        whatToDo = WallTypes.MULTIPLIERDECREASE;
                                    }
                                }
                                else{
                                    if (wallArray[ii][5] == WallTypes.STOPANDGOLEFTUP){
                                        whatToDo = WallTypes.MULTIPLIERDECREASE;
                                    } else{
                                        whatToDo = WallTypes.MULTIPLIERINCREASE;
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        }
        double currentMultiplier = walls.getSpeedMultiplier();
        switch(whatToDo){
            case(WallTypes.MULTIPLIERDECREASE):
                currentMultiplier -= 0.01;
                break;
            case(WallTypes.MULTIPLIERINCREASE):
                currentMultiplier += 0.01;
                break;
            case(WallTypes.DONOTHING):
            default:
                break;
        }
        if (currentMultiplier < 0.1){
            currentMultiplier = 0.1;
        }
        walls.setSpeedMultiplier(currentMultiplier);
    }


    public void checkHardWalls(Walls walls, int wallHeight){
        if (ySpeed > 0){
            int[][] wallArray = walls.getWallArray();
            for (int ii = 0; ii<wallArray.length; ii++){
                switch (wallArray[ii][1]){
                    case(WallTypes.MOVINGHARD):
                    case(WallTypes.HARDPARTIAL):
                        //Check x bounds
                        if ((spriteX+spriteDimensions/2 > (double)wallArray[ii][2]*cWidth/100) && (spriteX - spriteDimensions/2 < (double)wallArray[ii][3]*cWidth/100)){
                            //Check y bounds
                            if ((wallArray[ii][0] < spriteY + spriteDimensions/2) && (wallArray[ii][0] > spriteY)){
                                spriteY = wallArray[ii][0] - spriteDimensions/2;
                                readyToJump = true;
                            }
                        }
                        break;
                    case(WallTypes.HARDCOMPLETE):

                        //Check y bounds
                        if ((wallArray[ii][0] < spriteY + spriteDimensions/2) && (wallArray[ii][0] > spriteY)){
                            spriteY = wallArray[ii][0] - spriteDimensions/2;
                            readyToJump = true;
                        }
                        break;
                    default:
                        break;

                }

            }
        }
        else{
            int[][] wallArray = walls.getWallArray();
            for (int ii = 0; ii<wallArray.length; ii++){
                switch (wallArray[ii][1]){
                    case(WallTypes.HARDCOMPLETE):
                        if ((spriteX+spriteDimensions/2 > (double)wallArray[ii][2]*cWidth/100) && (spriteX - spriteDimensions/2 < (double)wallArray[ii][3]*cWidth/100)){
                            //Check y bounds
                            if ((wallArray[ii][0] + wallHeight > spriteY - spriteDimensions/2) && (wallArray[ii][0] + wallHeight < spriteY)){
                                spriteY = wallArray[ii][0] + wallHeight + spriteDimensions/2;
                                inAir = false;
                            }
                        }
                        break;
                    case(WallTypes.MOVINGHARD):
                    case(WallTypes.HARDPARTIAL):
                        if ((spriteX+spriteDimensions/2 > (double)wallArray[ii][2]*cWidth/100) && (spriteX - spriteDimensions/2 < (double)wallArray[ii][3]*cWidth/100)){
                            //Check y bounds
                            if ((wallArray[ii][0] + wallHeight > spriteY - spriteDimensions/2) && (wallArray[ii][0] + wallHeight < spriteY)){
                                spriteY = wallArray[ii][0] + wallHeight + spriteDimensions/2;
                                inAir = false;
                                if (wallArray[ii][5] == lvl16AchPreviousWall + 1){

                                    lvl16AchCount++;
                                    lvl16AchPreviousWall++;
                                } else if (wallArray[ii][5] == lvl16AchPreviousWall){

                                }
                                else {
                                    lvl16AchCount = 1;
                                    lvl16AchPreviousWall = wallArray[ii][5];

                                }
                            }

                        }
                        break;
                    default:
                        break;
                }

            }
        }

    }

    public void checkSides(){
        //Check we are still within the x bounds
        if (spriteX + spriteDimensions /2 > cWidth){
            spriteX = cWidth- spriteDimensions /2;
        }
        else if (spriteX - spriteDimensions /2 < 0){
            spriteX = spriteDimensions /2;
        }
        //Check we are still within the y bounds
        if (spriteY +spriteDimensions/2 > cHeight){
            spriteY = cHeight-spriteDimensions/2;
            lose = true;
            readyToJump = true;
            inAir = false;
        }
        else if (spriteY - spriteDimensions/2<0){
            inAir = false;
            spriteY = spriteDimensions/2+gravity;
        }
    }

    public boolean getLose(){
        return lose;
    }


}

package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Thomas on 09/02/2016.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    GameThread thread;
    SurfaceHolder holder;
    boolean running = false;
    float spriteX = -1;
    int level;
    boolean jumpCommand;
    int score = 0;
    boolean lose = false;
    float speed = 0;
    int numberOfBackgrounds = 3;
    Bitmap backgroundOld;
    Bitmap backgroundNew;
    int displacement = 0;
    int bgroundDisplacement;
    int backgroundWidth;
    int backgroundHeight;
    int width;
    int height;
    Bitmap background1;
    Bitmap background2;
    Bitmap background3;
    Bitmap space;
    Bitmap[] backgroundList = new Bitmap[numberOfBackgrounds];
    int achievement = -1;

    public GameView(Context context) {
        super(context);
        constructor(context);
    }

    public GameView(Context context, AttributeSet attrs){
        super(context,attrs);
        constructor(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        constructor(context);
    }

    private void constructor(Context context){
        holder = getHolder();
        holder.addCallback(this);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        initializeBackgrounds();
        thread = new GameThread(this.getContext());
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public void initializeBackgrounds(){
        space = decodeSampledBitmapFromResource(this.getContext().getResources(), R.drawable.space3,width,height);
        background1 = decodeSampledBitmapFromResource(this.getContext().getResources(), R.drawable.background1,width,height);
        background2 = decodeSampledBitmapFromResource(this.getContext().getResources(), R.drawable.background2,width,height);
        background3 = decodeSampledBitmapFromResource(this.getContext().getResources(), R.drawable.background3,width,height);
        backgroundList[0] = background1;
        backgroundList[1] = background2;
        backgroundList[2] = background3;
        backgroundWidth = background1.getWidth();
        backgroundHeight = background1.getHeight();
    }

    public void setLevel(int newlevel){
        level = newlevel;
    }

    public void setSpriteX(float x){
        spriteX = x;
    }

    public void spriteJump(){
        jumpCommand = true;

    }

    public void onPause(){
        if (thread.isAlive()){
            thread.setRunning(false);

            running = false;

            while(true)
                try{
                    thread.join();
                    break;
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
            //thread = null;
        }

    }

    public void onResume(){
        running = true;
        thread = new GameThread(this.getContext());
        if (thread.getState() == Thread.State.NEW)
        {
            thread.start();
        }
    }

    public Bitmap whichBackground(){
        int randint = (int) Math.floor(Math.random() * numberOfBackgrounds);
        if (randint == numberOfBackgrounds){
            randint = numberOfBackgrounds - 1;
        }
        return backgroundList[randint];
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new GameThread(this.getContext());
        thread.setRunning(true);
        thread.start();
    }

    public int getScore(){
        return score;
    }

    public int getAchievement(){
        return achievement;
    }

    public boolean getLose(){
        return lose;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //this.onPause();

    }

    public class GameThread extends Thread{
        Bitmap sprite;
        //Sprite ourSprite;
        boolean running;
        long timeBefore;
        long difference;
        int fps = 45;
        long timeNow;
        Context c;



        public GameThread(Context context){
            c = context;
            timeNow = System.nanoTime();
        }

        public void setRunning(boolean b){
            running = b;
        }

        @Override
        public void run() {
            super.run();
            Object ourLevel;
            Method jumpMethod;
            Method onDrawMethod;
            Method getScoreMethod;
            Method getLoseMethod;
            Method getSpeedMethod;
            Method getAchievementMethod;
            Rect positionTop;
            Rect whichImageTop;
            Rect positionBottom;
            Rect whichImageBottom;
            Rect positionSpace;
            Rect whichImageSpace = new Rect(0,0,space.getWidth(),space.getHeight());

            try{
                Class<?> ourLevelClass = Class.forName("commongranaryoaf.MrWhemps.Level" + level);
                Constructor<?> constructor = ourLevelClass.getDeclaredConstructor(Context.class, int.class);
                ourLevel = constructor.newInstance(c, level);
                jumpMethod = ourLevelClass.getDeclaredMethod("jump");
                onDrawMethod = ourLevelClass.getDeclaredMethod("onDraw", Canvas.class, float.class);
                getScoreMethod = ourLevelClass.getDeclaredMethod("getScore");
                getLoseMethod = ourLevelClass.getDeclaredMethod("getLose");
                getSpeedMethod = ourLevelClass.getDeclaredMethod("getSpeed");
                backgroundNew = whichBackground();
                backgroundOld = whichBackground();
                while(running){
                    timeCheck();
                    if(!holder.getSurface().isValid()){
                        continue;
                    }

                    Canvas c = holder.lockCanvas();
                    positionSpace = new Rect(0, 0, c.getWidth(), c.getHeight());
                    c.drawBitmap(space, whichImageSpace, positionSpace, null);

                    displacement += speed;
                    if (displacement >= c.getHeight()){
                        displacement = 0;
                        backgroundOld = backgroundNew;
                        backgroundNew = whichBackground();
                    }
                    bgroundDisplacement = Math.round((backgroundHeight * displacement)/c.getHeight());
                    positionBottom = new Rect(0, displacement, c.getWidth(), c.getHeight());
                    whichImageBottom = new Rect(0,0,backgroundWidth,background1.getHeight() - bgroundDisplacement);

                    positionTop = new Rect(0, 0, c.getWidth(), displacement);
                    whichImageTop = new Rect(0,backgroundHeight - bgroundDisplacement,backgroundWidth,backgroundHeight);

                    c.drawBitmap(backgroundOld, whichImageBottom, positionBottom, null);
                    c.drawBitmap(backgroundNew, whichImageTop, positionTop, null);




                    if (jumpCommand) {
                        jumpMethod.invoke(ourLevel);
                        jumpCommand = false;
                    }
                    if (spriteX < 0){
                        //A workaround that means we don;t move to the left initially
                        spriteX = c.getWidth()/2;
                    }


                    onDrawMethod.invoke(ourLevel, c, spriteX);
                    score = (int) getScoreMethod.invoke(ourLevel);
                    speed = (float) getSpeedMethod.invoke(ourLevel);
                    if (level == 8 || level == 11 || level == 16 || level == 17 || level == 22 || level == 23 || level == 24 || level == 25 || level==26 || level == 28 || level == 30){
                        getAchievementMethod = ourLevelClass.getDeclaredMethod("getAchievement");
                        achievement = (int) getAchievementMethod.invoke(ourLevel);
                    }
                    holder.unlockCanvasAndPost(c);
                    if ( (boolean) getLoseMethod.invoke(ourLevel)){
                        running = false;
                        lose = true;
                    }

                }

            } catch (Exception e){
                e.printStackTrace();
            }



        }

        public void timeCheck(){
            timeBefore = timeNow;
            timeNow = System.nanoTime();
            difference = timeNow-timeBefore;

            if (difference/1000000 - 1000/fps < 0){
                try{
                    Thread.sleep(1000/fps - difference/1000000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
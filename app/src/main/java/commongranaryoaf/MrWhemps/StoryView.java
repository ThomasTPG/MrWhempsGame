package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Thomas on 09/02/2016.
 */
class StoryView extends SurfaceView implements SurfaceHolder.Callback{

    GameThread thread;
    SurfaceHolder holder;
    boolean running = false;
    int storyNumber;
    boolean stop = false;
    boolean storyEnded = false;
    Bitmap storyBoard;
    int storyBoardWidth;
    int storyBoardHeight;
    int framesAcross;
    int framesDown;
    int storyLength;
    int page = 1;
    int movementCount = 0;
    int MAX_MOVEMENT = 20;

    public StoryView(Context context) {
        super(context);
        constructor();
    }

    public StoryView(Context context, AttributeSet attrs){
        super(context, attrs);
        constructor();
    }

    public StoryView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        constructor();
    }

    private void constructor(){
        holder = getHolder();
        holder.addCallback(this);

        thread = new GameThread(this.getContext());
    }

    public void setStory(int story){
        storyNumber = story;
        getStoryDetails();
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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new GameThread(this.getContext());
        thread.setRunning(true);
        thread.start();
    }

    public boolean getStop(){
        return stop;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //this.onPause();

    }

    public void getStoryDetails(){
        switch (storyNumber) {
            case (1):
                storyBoard = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.story1);
                storyLength = 6;
                framesAcross = 3;
                framesDown = 2;
                break;
            case (2):
                storyBoard = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.story2);
                storyLength = 6;
                framesAcross = 3;
                framesDown = 2;
                break;
            case (3):
                storyBoard = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.story3);
                storyLength = 2;
                framesAcross = 2;
                framesDown = 1;
                break;
            case (4):
                storyBoard = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.story4);
                storyLength = 2;
                framesAcross = 2;
                framesDown = 1;
                break;
        }
        storyBoardWidth = storyBoard.getWidth();
        storyBoardHeight = storyBoard.getHeight();

    }

    public void nextImage(){
        if(page == storyLength){
            running = false;
            stop = true;
        }
        if (movementCount == MAX_MOVEMENT){
            page++;
            movementCount = 0;
        }

    }

    public class GameThread extends Thread{
        long timeBefore;
        long difference;
        int fps = MAX_MOVEMENT;
        long timeNow;
        Context c;
        Rect screen;
        Rect whichPanel;

        int currentAcross;
        int previousAcross;
        int currentDown;
        int previousDown;

        public GameThread(Context context){
            c = context;
            timeNow = System.nanoTime();
        }

        public void setRunning(boolean b){
            running = b;
        }

        public void getCurrentPreviousCoords(){
            currentAcross = ((page - 1)% framesAcross);
            currentDown = ((page - 1) / framesAcross);
            if (page > 1){
                previousAcross = ((page  - 2)% framesAcross);
                previousDown = ((page - 2) / framesAcross);
            } else{
                previousAcross = 0;
                previousDown = 0;
            }

        }

        public Rect whichFrame(){
            getCurrentPreviousCoords();
            float percentMoved = (float) movementCount/MAX_MOVEMENT;
            int left = Math.round((previousAcross + (currentAcross - previousAcross) * percentMoved)* storyBoardWidth/framesAcross);
            int up = Math.round((previousDown + (currentDown - previousDown) * percentMoved)* storyBoardHeight/framesDown);
            int right = left + storyBoardWidth/framesAcross;
            int down = up + storyBoardHeight/framesDown;
            whichPanel= new Rect(left,up,right,down);
            System.out.println(percentMoved);
            return whichPanel;
        }

        @Override
        public void run() {
            super.run();

            try{


                while(running){
                    timeCheck();
                    if(!holder.getSurface().isValid()){
                        continue;
                    }

                    Canvas c = holder.lockCanvas();
                    whichPanel= whichFrame();
                    if (movementCount < 20){
                        movementCount ++;
                    }
                    screen = new Rect(0,0,c.getWidth(),c.getHeight());
                    c.drawBitmap(storyBoard, whichPanel, screen, null);

                    holder.unlockCanvasAndPost(c);
                    if (storyEnded){
                        running = false;
                        stop = true;
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
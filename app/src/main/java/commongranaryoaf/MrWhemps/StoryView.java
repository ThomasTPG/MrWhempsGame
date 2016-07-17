package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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

    public StoryView(Context context) {
        super(context);
        constructor();
    }

    public StoryView(Context context, AttributeSet attrs){
        super(context,attrs);
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

    public void nextImage(){

    }

    public class GameThread extends Thread{
        boolean running;
        long timeBefore;
        long difference;
        int fps = 20;
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

            try{


                while(running){
                    timeCheck();
                    if(!holder.getSurface().isValid()){
                        continue;
                    }

                    Canvas c = holder.lockCanvas();




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
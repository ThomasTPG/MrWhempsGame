package commongranaryoaf.MrWhemps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Thomas on 16/07/2016.
 */
public class LevelView extends View implements View.OnTouchListener
{
    Context context;
    int r = 50;
    Canvas c = null;
    int xPos;

    public LevelView(Context context) {
        super(context);
        this.context = context;
        setOnTouchListener(this);
    }

    public LevelView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        setOnTouchListener(this);
    }

    public LevelView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        this.context = context;
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (c == null){
            c = canvas;
        }
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(getResources().getColor(R.color.bronze));
        c.drawCircle(xPos, c.getHeight() / 2, r, p);

    }

    public void setX(int x){
        xPos = x;

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        System.out.println("touched");
        setX(event.getX());
        invalidate();
        return true;
    }
}

package tw.org.iii.leo.leo06;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.LinkedList;

public class MyView extends View {
    private Paint paint ;

    private LinkedList<LinkedList<HashMap<String,Float>>>  lines;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //setBackgroundColor(Color.GREEN);

        lines = new LinkedList<>();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //forEach
        for(LinkedList<HashMap<String,Float>> line : lines){
            //十個點有九條線
            for(int i =1 ; i<line.size(); i++){
                HashMap<String,Float> p0 = line.get(i-1);
                HashMap<String,Float> p1 = line.get(i);
                canvas.drawLine(p0.get("x"),p0.get("y"),p1.get("x"),p1.get("y"),paint);

            }
        }





    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //螢幕點下去我做一條新的線
        if (event.getAction() == MotionEvent.ACTION_DOWN){
//            Log.v("leo","down");
            LinkedList<HashMap<String,Float>> line = new LinkedList<>(); //新的線
            //將點放入線中
            lines.add(line);
        }


        float ex = event.getX(), ey= event.getY();
        Log.v("leo",ex+" x "+ey);
        //點的結構
        HashMap<String,Float> point = new HashMap<>();
        point.put("x",ex); point.put("y",ey);

        lines.getLast().add(point);






        invalidate();    //讓ondraw方法再度被呼叫（相當於java的 repaint()方法）
        return true; //return super.onTouchEvent(event); return true ; return false; 有三種招,第一種還會叫到onclick跟onlongclick
    }
}

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
    private LinkedList<HashMap<String,Float>> line;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //setBackgroundColor(Color.GREEN);

        line = new LinkedList<>();

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.v("leo","onDraw()");
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
//        canvas.drawLine(0,0,200,200,paint);
        //十個點有九條線
        for(int i =1 ; i<line.size(); i++){
            HashMap<String,Float> p0 = line.get(i-1);
            HashMap<String,Float> p1 = line.get(i);
            canvas.drawLine(p0.get("x"),p0.get("y"),p1.get("x"),p1.get("y"),paint);

        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float ex = event.getX(), ey= event.getY();
        Log.v("leo",ex+" x "+ey);
        //點的結構
        HashMap<String,Float> point = new HashMap<>();
        point.put("x",ex); point.put("y",ey);
        //將點放入線中
        line.add(point);

        invalidate();    //讓ondraw方法再度被呼叫（相當於java的 repaint()方法）
        return true; //return super.onTouchEvent(event); return true ; return false; 有三種招,第一種還會叫到onclick跟onlongclick
    }
}

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
    private LinkedList<LinkedList<HashMap<String,Float>>>  lines , recycler;
    private int color = Color.BLUE ;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //setBackgroundColor(Color.GREEN);

        lines = new LinkedList<>();
        recycler = new LinkedList<>();
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(10);

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //forEach
        for(LinkedList<HashMap<String,Float>> line : lines){
            //目前有添加lines的顏色資訊 所以在尋訪時額外取出來
            HashMap<String,Float> color = line.get(0);
            //Float 如何轉為int?
            paint.setColor(color.get("color").intValue());
            //十個點有九條線
            //因為第一個是顏色資訊了所以從第二個開始
            for(int i =2 ; i<line.size(); i++){
                HashMap<String,Float> p0 = line.get(i-1);
                HashMap<String,Float> p1 = line.get(i);
                canvas.drawLine(p0.get("x"),p0.get("y"),p1.get("x"),p1.get("y"),paint);

            }
        }

    }

    //顏色對外提供
    public void setColor(int newColor){
        color = newColor;
        invalidate();
    }

    public int getColor(){
        return color;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //螢幕點下去我做一條新的線
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            recycler.clear();
//            Log.v("leo","down");
            LinkedList<HashMap<String,Float>> line = new LinkedList<>(); //新的線
            //將lines的第一個資料為顏色資料
            HashMap<String,Float> setting = new HashMap<>();
            setting.put("color",(float)color);   //int 丟到 float ＝>ok; int -> Integer x-> Float 。做轉型 int-> float -> Float
            line.add(setting);
            //將點放入線中
            lines.add(line);
        }


        float ex = event.getX(), ey= event.getY();
//        Log.v("leo",ex+" x "+ey);
        //點的結構
        HashMap<String,Float> point = new HashMap<>();
        point.put("x",ex); point.put("y",ey);

        lines.getLast().add(point);

        invalidate();    //讓ondraw方法再度被呼叫（相當於java的 repaint()方法）
        return true; //return super.onTouchEvent(event); return true ; return false; 有三種招,第一種還會叫到onclick跟onlongclick
    }

    //重新弄個方法讓lines清除乾淨
    public void clear(){
        lines.clear();
        invalidate();
    }

    public void undo(){
        if(lines.size()>0){
            recycler.add(lines.removeLast());
            invalidate();
        }


    }

    public void redo(){
        if (recycler.size()>0){
            lines.add(recycler.removeLast());
            invalidate();
        }
    }
}

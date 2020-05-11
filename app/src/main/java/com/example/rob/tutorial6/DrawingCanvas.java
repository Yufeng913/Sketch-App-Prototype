package com.example.rob.tutorial6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.LinkedList;

// Drawing Canvas Java file for the stroke path and count
public class DrawingCanvas extends View {
    private Paint mPaint;
    private Path mPath;

    LinkedList<Paint> paintContainer;
    LinkedList<Path> pathContainer;

    public int pathColour = Color.BLUE;
    public int strokeWidth = 10;

    public DrawingCanvas(Context context, AttributeSet attrs){
        super(context, attrs);

        paintContainer = new LinkedList<Paint>();
        pathContainer = new LinkedList<Path>();

        mPaint = new Paint();
        mPath = new Path();
        setDrawingCacheEnabled(true);
    }

    public Bitmap get(){
        return this.getDrawingCache();
    }

    // Setting up the drawing of Canvas onDraw (reverse chronological order)
    protected void onDraw(Canvas canvas) {
        if(pathContainer.size() > 0) {
            for(int i = pathContainer.size()-1; i >= 0; i--)
                canvas.drawPath(pathContainer.get(i), paintContainer.get(i));
        }

        super.onDraw(canvas);
    }

    // On Touch, strokes are made here
    public boolean onTouchEvent(MotionEvent me)
    {
        int touchCount = me.getPointerCount();

        mPaint.setColor(pathColour);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        // Pressure
        mPaint.setStrokeWidth(strokeWidth*me.getPressure());

        // If the touch count is 1 (single stroke) draw line in accordance to stroke
        if(touchCount == 1) {

            switch (me.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    pathContainer.addFirst(mPath);
                    paintContainer.addFirst(mPaint);

                    pathContainer.getFirst().moveTo(me.getX(), me.getY());
                    break;

                case MotionEvent.ACTION_MOVE:
                    pathContainer.getFirst().lineTo(me.getX(), me.getY());
                    invalidate();
                    break;

                case MotionEvent.ACTION_UP:
                    mPaint = new Paint();
                    mPath = new Path();
                    break;
            }
        }

        // If double stroke, create a line where the two fingers are
        else if (touchCount == 2)
        {
            switch (me.getAction())
            {
                case MotionEvent.ACTION_DOWN:


                    pathContainer.addFirst(mPath);
                    paintContainer.addFirst(mPaint);

                    pathContainer.getFirst().moveTo(me.getX(0), me.getY(0));
                    pathContainer.getFirst().lineTo(me.getX(1), me.getY(1));
                    break;

                case MotionEvent.ACTION_MOVE:

                    pathContainer.getFirst().reset();
                    pathContainer.getFirst().moveTo(me.getX(0), me.getY(0));
                    pathContainer.getFirst().lineTo(me.getX(1), me.getY(1));
                    invalidate();
                    break;

                case MotionEvent.ACTION_UP:
                    mPaint = new Paint();
                    mPath = new Path();

                    break;
            }

        }

        // If triple count stroke, draw a triangle at the points of the fingers
        else if (touchCount == 3)
        {
            switch(me.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    mPaint.setColor(pathColour);
                    mPaint.setStyle(Paint.Style.FILL);
                    mPaint.setStrokeJoin(Paint.Join.ROUND);
                    mPaint.setStrokeCap(Paint.Cap.ROUND);
                    mPaint.setStrokeWidth(strokeWidth);
                    pathContainer.addFirst(mPath);
                    paintContainer.addFirst(mPaint);

                    pathContainer.getFirst().moveTo(me.getX(0), me.getY(0));
                    pathContainer.getFirst().lineTo(me.getX(1), me.getY(1));
                    pathContainer.getFirst().lineTo(me.getX(2), me.getY(2));
                    pathContainer.getFirst().close();
                    invalidate();

                    break;

                case MotionEvent.ACTION_MOVE:

                    pathContainer.getFirst().reset();
                    pathContainer.getFirst().moveTo(me.getX(0), me.getY(0));
                    pathContainer.getFirst().lineTo(me.getX(1), me.getY(1));
                    pathContainer.getFirst().lineTo(me.getX(2), me.getY(2));
                    pathContainer.getFirst().close();
                    invalidate();

                    break;

                case MotionEvent.ACTION_UP:
                    pathContainer.getFirst().reset();
                    pathContainer.getFirst().moveTo(me.getX(0), me.getY(0));
                    pathContainer.getFirst().lineTo(me.getX(1), me.getY(1));
                    pathContainer.getFirst().lineTo(me.getX(2), me.getY(2));
                    pathContainer.getFirst().close();
                    invalidate();

                    mPaint = new Paint();
                    mPath = new Path();
                    break;

            }
        }

        return true;

    }

}

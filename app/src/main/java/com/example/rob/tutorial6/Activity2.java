package com.example.rob.tutorial6;

import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.Prediction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

// The text drawing mode is made possible by the use of a new activity. This is a tweaked version of my tutorial 07 file

public class Activity2 extends AppCompatActivity implements OnGesturePerformedListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private GestureLibrary gLibrary;
    TextView inputText;
    ImageView imageView;
    private GestureDetectorCompat mDetector; //

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    // Initializing everything, gesture library, etc
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        imageView = findViewById(R.id.Commands);
        mDetector = new GestureDetectorCompat(this, this);
        mDetector.setOnDoubleTapListener(this);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);

                return true;
            }
        });

        inputText = findViewById(R.id.textView);

        gLibrary = GestureLibraries.fromRawResource(this, R.raw.gesture);
        gLibrary.load();

        GestureOverlayView gOverLay = (GestureOverlayView) findViewById(R.id.gOverlay);
        gOverLay.addOnGesturePerformedListener(this);
    }

    // On gesture performed, essentially getting the respective letter corresponding to the gesture (based on gesture.txt)
    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

        ArrayList<Prediction> predictions = gLibrary.recognize(gesture);

        if (predictions.size() > 0 && predictions.get(0).score > 3){
            String action = predictions.get(0).name;
            String newText = inputText.getText() + action;
            inputText. setText(newText);
            Toast.makeText(this, action, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {

        //String text = inputText.getText() + ".";
        //inputText.setText(text);

        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    // Single tap for comma
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {

        String text = inputText.getText() + ",";
        inputText.setText(text);

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    // Long press for simulated enter key (indentation)
    @Override
    public void onLongPress(MotionEvent motionEvent) {
        String text = inputText.getText() + "\n";
        inputText.setText(text);
    }

    // Fling implementation (swipes of left and right and etc. It takes the coordinates of the finger movement as well as velocity to determine what direction of swipe the user input was
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    result = true;
                }
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
                result = true;
            }
        } catch (Exception exception) {

            exception.printStackTrace();
        }

        return result;
    }

    // Swiping right will create a space in the text
    public void onSwipeRight() {

        String text = inputText.getText() + " ";
        inputText.setText(text);

    }

    public String onSwipeLeftLol() {

        String str = (String) inputText.getText();
        inputText.setText(str);

        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 'x') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    // Swiping left will go back to the main activity (canvas drawing)
    public void onSwipeLeft(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }




    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }

}

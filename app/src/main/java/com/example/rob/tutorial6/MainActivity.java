package com.example.rob.tutorial6;

/*
YuFeng Yao
100882040
IMD4008A Assignment 03

NOTE: This assignment was built upon Tutorial 06 of CULearn

    Main Features:
        Shape drawing (triangle) using multi-touch
        *Multi-touch painting is NOT supported
        Eraser
        
    Extra features:
        Add text drawing (activated through new activity by pressing button)
        Add built-in gestures (tutorial upon text drawing page)
        Save Image functionality (screenshot) utilizing a button

    Sources:
        CULearn Tutorial Material (06 and 07)
        Screenshot functionality: https://www.youtube.com/watch?v=frfsNSzuhvc
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    // Initializing variables
    private Button textMode;

    DrawingCanvas dCanvas;
    Spinner spinner;
    SeekBar seekBar;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Screenshot section
        imageView = (ImageView) findViewById(R.id.imageView);
        Button screenShot = (Button) findViewById(R.id.take_screenshot);
        screenShot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Bitmap b = Screenshot.takeScreenShotOfRootView(imageView);
                imageView.setImageBitmap(b);

            }
        });

        // Text mode section
        textMode = findViewById(R.id.text_mode);
        textMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterTextMode();
            }
        });

        // Canvas
        dCanvas = findViewById(R.id.myCanvas);
        dCanvas.pathColour = Color.YELLOW;
        dCanvas.setBackgroundColor(Color.WHITE);

        // Dropdown menu
        spinner = findViewById(R.id.spinner);
        seekBar = findViewById(R.id.seekBar);


        // Setting up the dropdown menu for the various color options of the canvas drawing app
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(parent.getItemAtPosition(position).toString())
                {
                    case "Red":
                        dCanvas.pathColour = Color.RED;
                        break;

                    case "Green":
                        dCanvas.pathColour = Color.GREEN;
                        break;

                    case "Blue":
                        dCanvas.pathColour = Color.BLUE;
                        break;

                    case "Purple":
                        dCanvas.pathColour = Color.MAGENTA;
                        break;

                    case "Black":
                        dCanvas.pathColour = Color.BLACK;
                        break;

                    case "Yellow":
                        dCanvas.pathColour = Color.YELLOW;
                        break;

                        // Eraser here
                    case "Eraser":
                        dCanvas.pathColour = Color.WHITE;
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dCanvas.strokeWidth = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void enterTextMode(){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
}

package com.scannella.quarter3final;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class Settings extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


    }

    //starts disco as freaking heck
    public void clickYes(View view){
        discoLights(500);

    }

    public void discoLights(final int time) {

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(time);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //set background colors
                                ConstraintLayout c = (ConstraintLayout) findViewById(R.id.settingsLayout);
                                c.setBackgroundColor(randColor());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

    }

    //returns a random color to set background to
    public int randColor(){

        int[] colorArray = new int[] {Color.YELLOW, Color.GREEN,
                Color.RED, Color.BLUE, Color.MAGENTA, Color.BLACK, Color.CYAN};

        return colorArray[(int)(Math.random() * colorArray.length) ];
    }
}
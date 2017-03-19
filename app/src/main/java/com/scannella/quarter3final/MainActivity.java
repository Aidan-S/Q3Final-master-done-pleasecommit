package com.scannella.quarter3final;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener  {

    //final Handler handler = new Handler();
    private Button button;
    private Activity thisName;
    private int maxWidth;
    private int maxHeight;
    private RelativeLayout relativeLayout;
    private View myView;
    private GestureDetectorCompat GestureDetector;
    private TextView word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btnClick);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        final MediaPlayer play = MediaPlayer.create(this,R.raw.SMB);

        word = (TextView) findViewById(R.id.txtView);

        thisName = this;

        relativeLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int l, int r, int t, int b, int oldL, int oldR, int oldT, int OldB){
                //If l, r, t, and b are all 0 then the layout is not ready - just return
                if (l == 0 && r == 0 && t == 0 && b == 0) return;
                Log.d("Points","R: ("+relativeLayout.getWidth()+", "+ relativeLayout.getHeight()+")");
                Log.d("Points","B: " + button.getWidth()+ " -- " + button.getHeight());
                maxHeight = relativeLayout.getHeight() - button.getHeight() - relativeLayout.getPaddingTop() - relativeLayout.getPaddingBottom()+ 1;
                maxWidth = relativeLayout.getWidth() - button.getWidth() - relativeLayout.getPaddingLeft() - relativeLayout.getPaddingRight()+ 1;
                Log.d("Points","M: " + maxWidth + ", " + maxHeight);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonRandomPosition();
                if(play.isPlaying()) {
                    play.pause();
                    play.seekTo(0);
                }
                play.start();

                word.setText("You Got Me");
            }
        });

        handler.postDelayed(runnable,3000);


        this.GestureDetector = new GestureDetectorCompat(this, this);
        GestureDetector.setOnDoubleTapListener(this);

    }

    private void missSound(){
        final MediaPlayer miss = MediaPlayer.create(this,R.raw.exclamation);
        if(miss.isPlaying()) {
            miss.pause();
            miss.seekTo(0);
        }

        miss.start();

    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        @Override
        public void run() {
            setButtonRandomPosition();
            handler.postDelayed(runnable, 3000);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.itmSettings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }

    private void setButtonRandomPosition(){
        int newX = (int)(Math.random() * maxWidth);
        int newY = (int)(Math.random() * maxHeight);
        Log.d("Points","("+newX+","+newY+")");
        button.setX(newX);
        button.setY(newY);
    }

    private void startRandomButton() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setButtonRandomPosition();
            }
        }, 0, 3000);//Update button every three seconds
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) { return false; }

    @Override
    public boolean onDoubleTap(MotionEvent e) {return false;}

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        word.setText("Thats 2 strikes right there");
        missSound();
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        word.setText("You missed");
        missSound();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        missSound();
        word.setText("Where do you think the button is?");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        GestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


}
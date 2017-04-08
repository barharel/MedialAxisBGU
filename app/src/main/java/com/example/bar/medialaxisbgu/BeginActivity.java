package com.example.bar.medialaxisbgu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.widget.TextView;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;


public class BeginActivity extends AppCompatActivity {
    private static final String TAG = "ProjectMessage";
    private ImageView m_image;
    private ImageView m_image2;
    private ImageView m_image3;
    private ImageView m_image4;
    private int m_counter = 0;
    private ImageButton m_go_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_begin);
        m_image = (ImageView) findViewById(R.id.imageView);
        m_image2 = (ImageView) findViewById(R.id.imageView2);
        m_image3 = (ImageView) findViewById(R.id.imageView4);
        m_image4 = (ImageView) findViewById(R.id.imageView3);
        m_go_button = (ImageButton) findViewById(R.id.btnChangeImage);
        m_go_button.setVisibility(View.INVISIBLE);

        addListenerOnView(m_image);
        addListenerOnView(m_image2);
        addListenerOnView(m_image3);
        addListenerOnView(m_image4);


    }

    protected void improveColoring(){
        Drawable imgDrawable = ((ImageView)m_image).getDrawable();
        Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);//&lt;--true makes copy mutable
        bitmap = mutableBitmap;
        int i, j;
        for (i=0; i<bitmap.getHeight(); ++i)
            for(j=0; j<bitmap.getWidth(); ++j) {
                int pixel = bitmap.getPixel(j,i);
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);
                if(redValue < 40 && greenValue < 40 && blueValue < 40)
                    continue;
                if (!((redValue>240) && (blueValue>240) && (greenValue>240))) {
                    bitmap.setPixel(j, i, Color.rgb(191,191,191));
                }
            }

        m_image.setImageBitmap(bitmap);
    }



    public boolean isWhitePixel(float eventX, float eventY, ImageView image) {
        float[] eventXY = new float[] {eventX, eventY};

        Matrix invertMatrix = new Matrix();
        ((ImageView)image).getImageMatrix().invert(invertMatrix);

        invertMatrix.mapPoints(eventXY);
        int x = Integer.valueOf((int) eventXY[0]);
        int y = Integer.valueOf((int) eventXY[1]);

        Drawable imgDrawable = ((ImageView)image).getDrawable();
        Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();
        //Limit x, y range within bitmap
        if(x < 0){
            return true;
        }else if(x > bitmap.getWidth()-1){
            return true;
        }

        if(y < 0){
            return true;
        }else if(y > bitmap.getHeight()-1){
            return true;
        }

        int pixel = bitmap.getPixel(x, y);
        int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);
        String string = "isWhitePixel (" + String.valueOf(x) + ","  + String.valueOf(y) +
                ") - (" + String.valueOf(redValue) + "," + String.valueOf(blueValue) + "," +
                String.valueOf(greenValue) + ")";
        Log.i(TAG, string);
        Boolean result = (!(((redValue>190) && (blueValue>190) && (greenValue>190)
                && (redValue<210) && (blueValue<210) && (greenValue<210))
                || ((redValue<10) && (blueValue<10) && (greenValue<10)))
        );
        Log.i(TAG, "isWhitePixel: Result is " + result);
        return result;


    }



    public boolean isInMask(float eventX, float eventY, ImageView image) {
        float[] eventXY = new float[] {eventX, eventY};
        switch(image.getId()){
            case R.id.imageView:
                return true;
            case R.id.imageView2:
                image.setImageResource(R.drawable.example_2_mask);
                break;
            case R.id.imageView3:
                image.setImageResource(R.drawable.example_4_mask);
                break;
            case R.id.imageView4:
                return true;
        }
        Matrix invertMatrix = new Matrix();

        //image.setImageBitmap(BitmapFactory.decodeFile("drawable/example_1.bmp"));
        //image.setImageResource(R.drawable.example_1);

        ((ImageView)image).getImageMatrix().invert(invertMatrix);

        invertMatrix.mapPoints(eventXY);
        int x = Integer.valueOf((int) eventXY[0]);
        int y = Integer.valueOf((int) eventXY[1]);

        Drawable imgDrawable = ((ImageView)image).getDrawable();
        Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();
        //Limit x, y range within bitmap
        if(x < 0){
            return true;
        }else if(x > bitmap.getWidth()-1){
            return true;
        }

        if(y < 0){
            return true;
        }else if(y > bitmap.getHeight()-1){
            return true;
        }

        int pixel = bitmap.getPixel(x, y);
        int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);
        String string = "inMask (" + String.valueOf(x) + ","  + String.valueOf(y) +
                ") - (" + String.valueOf(redValue) + "," + String.valueOf(blueValue) + "," +
                String.valueOf(greenValue) + ")";
        Log.i(TAG, string);
        Boolean result = !((redValue<240) || (blueValue>10) || (greenValue>10));

        Log.i(TAG, "inMask: Result is " + result);

        switch(image.getId()){
            case R.id.imageView:
                image.setImageResource(R.drawable.example_1);
                break;
            case R.id.imageView2:
                image.setImageResource(R.drawable.example_2);
                break;
            case R.id.imageView3:
                image.setImageResource(R.drawable.example_4);
                break;
            case R.id.imageView4:
                image.setImageResource(R.drawable.example_3);
                break;

        }
        return result;


    }


    public void addListenerOnView(final ImageView image) {

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    final String text_to_be_sent = String.valueOf(event.getX()) + "," + String.valueOf(event.getY());
                    String thanks = "All Done!\n " +
                            "Thank you for participating in the experiment";

                    if (!isWhitePixel(event.getX(), event.getY(), image) && isInMask(event.getX(), event.getY(), image)) {
                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.good);
                        mp.start();
                        m_counter++;
                        image.setVisibility(View.INVISIBLE);
                        if(m_counter>3){
                            m_go_button.setVisibility(View.VISIBLE);
                        }

                    } else {
                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.bad);
                        mp.start();
                    }
                }

                return true;
            }
        });


        m_go_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(BeginActivity.this, ShapeActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("key",1);
                    intent.putExtras(b);
                    startActivity(intent);
                    // finish();
                    m_counter = 0;
                    m_go_button.setVisibility(View.INVISIBLE);
                    m_image.setVisibility(View.VISIBLE);
                    m_image2.setVisibility(View.VISIBLE);
                    m_image3.setVisibility(View.VISIBLE);
                    m_image4.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
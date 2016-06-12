package com.example.bar.medialaxisbgu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
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
import android.widget.Button;
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


public class ShapeActivity extends AppCompatActivity {
    private static final String TAG = "ProjectMessage";
    private String m_text;
    private ImageView m_image;
    private int m_imageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);
        m_image = (ImageView) findViewById(R.id.imageView1);
        addListenerOnView();

        Bundle b = getIntent().getExtras();
        int imageId = b.getInt("key");
        m_imageId = imageId;
        System.out.println("Image Id_________________________" + imageId);
        loadShape(imageId);
        improveColoring();
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




    protected void loadShape(int imageId) {
        switch(imageId){
            case 1:
                m_image.setImageResource(R.drawable.triangle);
                break;
            case 2:
                m_image.setImageResource(R.drawable.two_rectangles);
                break;
            case 3:
                m_image.setImageResource(R.drawable.rectangle);
                break;
            case 4:
                m_image.setImageResource(R.drawable.rectangle_missing);
                break;
            case 5:
                m_image.setImageResource(R.drawable.rectangle_missing_2);
                break;
            case 6:
                m_image.setImageResource(R.drawable.circle);
                break;
            case 11:
                m_image.setImageResource(R.drawable.triangle_hidden);
                break;
            case 12:
                m_image.setImageResource(R.drawable.two_rectangles_hidden);
                break;
            case 13:
                m_image.setImageResource(R.drawable.rectangle_hidden);
                break;
            case 14:
                m_image.setImageResource(R.drawable.rectangle_missing_hidden);
                break;
            case 15:
                m_image.setImageResource(R.drawable.rectangle_missing_2_hidden);
                break;
            case 16:
                m_image.setImageResource(R.drawable.circle_hidden);
                break;
        }
    }
    public void colorCoord(float eventX, float eventY) {
        Drawable imgDrawable = ((ImageView)m_image).getDrawable();
        Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);//&lt;--true makes copy mutable
        bitmap = mutableBitmap;
        float[] eventXY = new float[] {eventX, eventY};

        Matrix invertMatrix = new Matrix();
        ((ImageView)m_image).getImageMatrix().invert(invertMatrix);

        invertMatrix.mapPoints(eventXY);
        int x = Integer.valueOf((int) eventXY[0]);
        int y = Integer.valueOf((int) eventXY[1]);



        String string = "Trying to color coord [" + x + "," + y + "]";
        Log.i(TAG, string);
        //coloring
        for(int i=0; i<20; ++i) {
            if (x + i < bitmap.getWidth()){
                int pixel = bitmap.getPixel(x+i,y);
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);
                if (!((redValue>240) && (blueValue>240) && (greenValue>240)))
                    bitmap.setPixel(x + i, y, Color.rgb(205, 92, 92));
            }
            if (x - i >= 0) {
                int pixel = bitmap.getPixel(x-i,y);
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);
                if (!((redValue>240) && (blueValue>240) && (greenValue>240)))
                    bitmap.setPixel(x - i, y, Color.rgb(205, 92, 92));
            }
            if (y + i < bitmap.getHeight()) {
                int pixel = bitmap.getPixel(x,y+i);
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);
                if (!((redValue>240) && (blueValue>240) && (greenValue>240)))
                    bitmap.setPixel(x, y + i, Color.rgb(205, 92, 92));
            }
            if (y - i >= 0) {
                int pixel = bitmap.getPixel(x,y-i);
                int redValue = Color.red(pixel);
                int blueValue = Color.blue(pixel);
                int greenValue = Color.green(pixel);
                if (!((redValue>240) && (blueValue>240) && (greenValue>240)))
                    bitmap.setPixel(x, y - i, Color.rgb(205, 92, 92));
            }
        }
        m_image.setImageBitmap(bitmap);
    }
    public boolean isWhitePixel(float eventX, float eventY) {
        /*Bitmap bitmap = Bitmap.createBitmap(m_image.getDrawingCache())
        String string = "isWhitePixel (" + String.valueOf(x) + ","  + String.valueOf(y) +
                ") - (" + String.valueOf(bitmap.getHeight()) + "," + String.valueOf(bitmap.getWidth()) + ")";
        Log.i(TAG, string);*/
        //int pixel = bitmap.getPixel((int) x, (int) y);
        /*int redValue = Color.red(pixel);
        int blueValue = Color.blue(pixel);
        int greenValue = Color.green(pixel);
        String string = "isWhitePixel (" + String.valueOf(x) + ","  + String.valueOf(y) +
                ") - (" + String.valueOf(redValue) + "," + String.valueOf(blueValue) + "," +
                String.valueOf(greenValue) + ")";
        Log.i(TAG, string);
        return ((redValue>240) && (blueValue>240) && (greenValue>240));*/
        float[] eventXY = new float[] {eventX, eventY};

        Matrix invertMatrix = new Matrix();
        ((ImageView)m_image).getImageMatrix().invert(invertMatrix);

        invertMatrix.mapPoints(eventXY);
        int x = Integer.valueOf((int)eventXY[0]);
        int y = Integer.valueOf((int)eventXY[1]);

        Drawable imgDrawable = ((ImageView)m_image).getDrawable();
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
        return ((redValue>240) && (blueValue>240) && (greenValue>240));




    }


    public void addListenerOnView() {


        m_image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    m_text =    ("Touch coordinates : " + String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
                    String bad_click = "Please tap the Shape, and not outside of it";
                    final String text_to_be_sent = String.valueOf(event.getX()) + "," + String.valueOf(event.getY());
                    if (!isWhitePixel(event.getX(), event.getY())){
                        //improveColoring();
                        //colorCoord(event.getX(), event.getY());
                        writeToFile(text_to_be_sent);
                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.good);
                        mp.start();
                        //sleeping
                        //SystemClock.sleep(5000);
                        //undoing
                        //improveColoring();
                    }
                    else {
                       /* /////////////////////////////Dialog///////////////////////////
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    break;
                            }
                            dialog.dismiss();
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(m_image.getContext());
                    builder.setMessage(bad_click).setPositiveButton("OK", dialogClickListener)
                            .show();
                        /////////////////////////End Dialog///////////////////////////*/
                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.bad);
                        mp.start();
                    }
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

    private void writeToFile(String data) {
        File file;
        FileOutputStream outputStream;

        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(m_imageId);
        String fileName = sb.toString();
        fileName = fileName + ".csv";
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "CSProject");
            if (!root.exists()) {
                root.mkdirs();
            }
            file = new File(root, fileName);
            if(!file.exists()) {
                data = "X,Y\n" + data ;
            }
            BufferedWriter bW;

            bW = new BufferedWriter(new FileWriter(file, true));
            bW.write(data);
            bW.newLine();
            bW.flush();
            bW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
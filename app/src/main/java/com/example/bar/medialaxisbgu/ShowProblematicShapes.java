package com.example.bar.medialaxisbgu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowProblematicShapes extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ProjectMessage";
    ArrayList<UserInput> m_list_of_clicks;
    private ImageView m_image;
    private TextView m_text;
    private int m_index_in_list;
    private Button m_next;
    private Button m_previous;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_problematic_shapes);
        m_list_of_clicks = (ArrayList<UserInput>) getIntent().getSerializableExtra("mylist");
        m_image = (ImageView) findViewById(R.id.imageView5);
        m_text = (TextView) findViewById(R.id.textView3);
        m_next = (Button) findViewById(R.id.button2);
        m_previous = (Button) findViewById(R.id.button3);
        m_index_in_list = 0;
        loadShape();
        colorCord();
        setButtonAvailability();

        m_next.setOnClickListener(this);
        m_previous.setOnClickListener(this);
    }

    protected void setButtonAvailability() {
        m_previous.setEnabled(true);
        m_next.setEnabled(true);
        if (m_index_in_list==0){
            m_previous.setEnabled(false);
        }
        if (m_index_in_list==m_list_of_clicks.size() - 1){
            m_next.setEnabled(false);
        }
    }

    protected void loadShape() {
        m_text.setText(m_index_in_list + 1 + "/" + m_list_of_clicks.size());
        int image_id = m_list_of_clicks.get(m_index_in_list).id_of_image;
        switch(image_id) {
            case 11:
                m_image.setImageResource(R.drawable.new_1_hidden);
                break;
            case 12:
                m_image.setImageResource(R.drawable.new_2_hidden);
                break;
            case 13:
                m_image.setImageResource(R.drawable.new_3_hidden);
                break;
            case 14:
                m_image.setImageResource(R.drawable.new_4_hidden);
                break;
            case 15:
                m_image.setImageResource(R.drawable.new_5_hidden);
                break;
            case 16:
                m_image.setImageResource(R.drawable.new_6_hidden);
                break;
            case 17:
                m_image.setImageResource(R.drawable.new_7_hidden);
                break;
            case 18:
                m_image.setImageResource(R.drawable.new_8_hidden);
                break;
        }

        m_image.setBackgroundResource(R.drawable.border);
    }

    protected void colorCord(){
        float eventX = m_list_of_clicks.get(m_index_in_list).x;
        float eventY = m_list_of_clicks.get(m_index_in_list).y;
        Log.i(TAG, "colorCord: (" + eventX + "," + eventY + ")");

        Drawable imgDrawable = ((ImageView)m_image).getDrawable();
        Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);//&lt;--true makes copy mutable
        bitmap = mutableBitmap;
        float[] eventXY = new float[] {eventX, eventY};

        Matrix invertMatrix = new Matrix();
        ((ImageView)m_image).getImageMatrix().invert(invertMatrix);

        invertMatrix.mapPoints(eventXY);
        //int x = Integer.valueOf((int) eventXY[0]);
        //int y = Integer.valueOf((int) eventXY[1]);
        int x = (int) eventX;
        int y = (int) eventY;
        Log.i(TAG, "colorCord:  Inverted: (" + x + "," + y + ")");
        //coloring
        for(int i=0; i<65; ++i) {
            for(int j=0; j<20; ++j) {
                if (x + i < bitmap.getWidth()
                        && x + i >= 0
                        && y + j < bitmap.getHeight()
                        && y - j >= 0) {
                    bitmap.setPixel(x + i, y + j, Color.rgb(205, 92, 92));
                    bitmap.setPixel(x + i, y - j, Color.rgb(205, 92, 92));
                }
                if (x - i >= 0
                        && x - i < bitmap.getWidth()
                        && y + j < bitmap.getHeight()
                        && y - j >= 0) {
                    bitmap.setPixel(x - i, y + j, Color.rgb(205, 92, 92));
                    bitmap.setPixel(x - i, y - j, Color.rgb(205, 92, 92));
                }
                if (y + i < bitmap.getHeight()
                        && y + i >= 0
                        && x + j < bitmap.getWidth()
                        && x - j > 0) {
                    bitmap.setPixel(x + j, y + i, Color.rgb(205, 92, 92));
                    bitmap.setPixel(x - j, y + i, Color.rgb(205, 92, 92));
                }
                if (y - i >= 0
                        && y - i < bitmap.getHeight()
                        && x + j < bitmap.getWidth()
                        && x - j > 0) {
                    bitmap.setPixel(x + j, y - i, Color.rgb(205, 92, 92));
                    bitmap.setPixel(x - j, y - i, Color.rgb(205, 92, 92));
                }
            }
        }
        m_image.setImageBitmap(bitmap);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button2:
                buttonClick("next");
                break;
            case R.id.button3:
                buttonClick("previous");
                break;

        }
    }

    protected void buttonClick(String which_button){
        switch (which_button){
            case "next":
                m_index_in_list++;
                break;
            case "previous":
                m_index_in_list--;
                break;
        }
        loadShape();
        colorCord();
        setButtonAvailability();
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "finish problematic shapes");
        Intent intent = new Intent();
        if (getParent() == null) {
            setResult(Activity.RESULT_OK, intent);
        } else {
            getParent().setResult(Activity.RESULT_OK, intent);
        }
        finish();
        return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_problematic_shapes, menu);
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

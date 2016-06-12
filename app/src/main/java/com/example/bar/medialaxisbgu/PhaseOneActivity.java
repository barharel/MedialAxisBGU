package com.example.bar.medialaxisbgu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class PhaseOneActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ProjectMessage";
    ImageView triangle;
    ImageView two_rectangles;
    ImageView rectangle;
    ImageView rectangle_missing;
    ImageView rectangle_missing_2;
    ImageView circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phaseone);
        shapesClickes();
    }

    protected void shapesClickes()
    {
        triangle = (ImageView)findViewById(R.id.triangle);
        triangle.setOnClickListener((View.OnClickListener) this);

        two_rectangles = (ImageView)findViewById(R.id.two_rectangles);
        two_rectangles.setOnClickListener((View.OnClickListener) this);

        rectangle = (ImageView)findViewById(R.id.rectangle);
        rectangle.setOnClickListener((View.OnClickListener) this);

        rectangle_missing = (ImageView)findViewById(R.id.rectangle_missing);
        rectangle_missing.setOnClickListener((View.OnClickListener) this);

        rectangle_missing_2 = (ImageView)findViewById(R.id.rectangle_missing_2);
        rectangle_missing_2.setOnClickListener((View.OnClickListener) this);

        circle = (ImageView)findViewById(R.id.circle);
        circle.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "view Id:" + v.getId());
        switch(v.getId())
        {
            case R.id.triangle:
                pictureButtonClick(1);
                break;

            case R.id.two_rectangles:
                pictureButtonClick(2);
                break;

            case R.id.rectangle:
                pictureButtonClick(3);
                break;

            case R.id.rectangle_missing:
                pictureButtonClick(4);
                break;

            case R.id.rectangle_missing_2:
                pictureButtonClick(5);
                break;

            case R.id.circle:
                pictureButtonClick(6);
                break;
        }
    }

    private void pictureButtonClick(int id){
        Intent intent = new Intent(PhaseOneActivity.this, ShapeActivity.class);
        Bundle b = new Bundle();
        b.putInt("key",id);
        intent.putExtras(b);
        startActivity(intent);
        //finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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

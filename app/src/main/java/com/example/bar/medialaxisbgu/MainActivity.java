package com.example.bar.medialaxisbgu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button button1;
    Button button2;
    Button button3;
    private static final String TAG = "ProjectMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        button1 = (Button)findViewById(R.id.btnDisplay);
        button1.setOnClickListener(this);
        button2 = (Button)findViewById(R.id.button);
        button2.setOnClickListener(this);
        button3 = (Button)findViewById(R.id.instructions);
        button3.setOnClickListener(this);
        Log.i(TAG,"onCreate");
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

    private void button1Click(){
        Intent intent = new Intent(MainActivity.this, PhaseOneActivity.class);
        Bundle b = new Bundle();
        b.putInt("key",1);
        intent.putExtras(b);
        startActivity(intent);
        // finish();
    }


    private void button2Click(){
        Intent intent = new Intent(MainActivity.this, PhaseTwoActivity.class);
        Bundle b = new Bundle();
        b.putInt("key",2);
        intent.putExtras(b);
        startActivity(intent);
        // finish();
    }

    private void button3Click(){
        Intent intent = new Intent(MainActivity.this, BeginActivity.class);
        Bundle b = new Bundle();
        b.putInt("key",3);
        intent.putExtras(b);
        startActivity(intent);
        // finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnDisplay:
                button1Click();
                break;
            case R.id.button:
                button2Click();
                break;
            case R.id.instructions:
                button3Click();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }

}
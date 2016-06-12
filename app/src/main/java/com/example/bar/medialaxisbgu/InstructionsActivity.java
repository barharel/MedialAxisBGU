package com.example.bar.medialaxisbgu;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

public class InstructionsActivity extends Activity
        implements MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener,View.OnTouchListener {

        private VideoView mVV;
        private static final String TAG = "ProjectMessage";
        @Override
        public void onCreate(Bundle b) {
            super.onCreate(b);

            setContentView(R.layout.activity_instructions);

            int fileRes=1;

            mVV = (VideoView)findViewById(R.id.myvideoview);
            mVV.setOnCompletionListener(this);
            mVV.setOnPreparedListener(this);
            mVV.setOnTouchListener(this);

            if (!playFileRes(fileRes)) return;

            mVV.start();
        }

        protected void onNewIntent(Intent intent) {
            super.onNewIntent(intent);
            setIntent(intent);
            int fileRes = 1;
            playFileRes(fileRes);
        }

        private boolean playFileRes(int fileRes) {
            if (fileRes==0) {

                Log.i(TAG, "====================wtf================================");
                stopPlaying();
                return false;
            } else {
                String path = "android.resource://" + getPackageName() + "/" + R.raw.instructionsvideo;
                mVV.setVideoURI(Uri.parse(path));
                return true;
            }
        }

        public void stopPlaying() {
            mVV.stopPlayback();
            this.finish();
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            finish();
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            stopPlaying();
            return true;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.setLooping(true);
        }
    }
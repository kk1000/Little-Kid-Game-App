package com.example.android.animation_assignment4;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by CuiCui on 4/24/2016.
 */
public class MusicServer extends Service {
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        mediaPlayer.start();
        return null;
    }

    @Override
    public void onCreate() {
        mediaPlayer=MediaPlayer.create(this,R.raw.music);

        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mediaPlayer.stop();
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            //mediaPlayer=MediaPlayer.create(this,R.raw.music);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        super.onStartCommand(intent,flags,startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
       // super.onDestroy();
            mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }
}

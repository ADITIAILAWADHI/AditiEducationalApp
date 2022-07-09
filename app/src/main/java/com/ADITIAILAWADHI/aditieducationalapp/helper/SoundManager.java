package com.ADITIAILAWADHI.aditieducationalapp.helper;

import android.content.Context;
import android.media.SoundPool;

public class SoundManager {
    private SoundPool pool;
    private Context context;

    public SoundManager(Context context){
        this.context = context;
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(10); // max 10 audio files can play at the time
        pool = builder.build();
    }

    public int addSound(int resourceID){
        return pool.load(context, resourceID, 1);
    }

    public void play(int soundID, float rate){
        //rate: from 0.5 to 2.0 --> Speed sound
        //play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        pool.play(soundID, 1, 1, 1, 0, rate); // loop X repeat X time
    }

    public void releaseSoundPool(){
        if (pool != null){
            pool.release();
            pool = null;
        }
    }
}

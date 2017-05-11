package com.example.user1.beatsmaker;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

/**
 * Created by tbaic on 5/1/2017.
 */

public class BeatBit implements SPPlayable{//represents a file and a time period in which it will be played
    private static SoundPool sp = new SoundPool(99, AudioManager.STREAM_MUSIC, 0);
    private String filePath;
    private Integer frequency;
    private int spid;

    public BeatBit(String filePath, Integer frequency) {
        this.filePath = filePath;
        this.frequency = frequency;
    }

    public String getFilePath() {
        return filePath;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void play(Context con){//plays the file
        spid = sp.load(filePath, 1);
        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                sp.play(spid, 1, 1, 1, 0, 1);
                //soundpool.play(id,leftV,rightV,priority,loop,rate)
                Log.d("filepath being played",filePath);
            }
        });
        sp.release();
    }
}

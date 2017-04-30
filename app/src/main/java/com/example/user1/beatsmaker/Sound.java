package com.example.user1.beatsmaker;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

/**
 * Created by USER1 on 30/04/2017.
 */
public class Sound {

    private static SoundPool sp = new SoundPool(99, AudioManager.STREAM_MUSIC, 0);
    private final static File EMPTYFILE=new File("empty.gpp3");
    private Button btn;
    private File fileout;
    private int spid;



    public void play(Context con) {
        Log.d("file is", fileout.getPath());
        if (fileout.canRead()) {
            spid = sp.load(fileout.getPath(), 1);
            sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    sp.play(spid, 10, 10, 1, 0, 1);
                    //soundpool.play(id,leftV,rightV,priority,loop,rate)
                }
            });

        }
        else
            Toast.makeText(con, "you have not yet recorded this sound", Toast.LENGTH_SHORT).show();
    }

    public Sound(File fileout, Button btn) {
        this.fileout = fileout;
        this.btn = btn;
        this.spid = 0;
    }

    public Button getBtn() {
        return btn;
    }

    public File getFileout() {
        return fileout;
    }
}

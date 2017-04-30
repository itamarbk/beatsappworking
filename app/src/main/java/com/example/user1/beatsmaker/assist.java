package com.example.user1.beatsmaker;

import android.util.Log;

/**
 * Created by USER1 on 30/04/2017.
 */
public class assist {

    public static void waitFor(final int millisec){
        Thread thread=  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        Log.d("waiting", millisec + "millisecdos");
                        wait(millisec);
                    }
                }
                catch(InterruptedException ex){
                }

                // TODO
            }
        };

        thread.start();
    }
}

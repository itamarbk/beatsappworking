package com.example.user1.beatsmaker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {
        Sound[] sounds=new Sound[9];
        /**
         * ATTENTION: This was auto-generated to implement the App Indexing API.
         * See https://g.co/AppIndexing/AndroidStudio for more information.
         */

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }
            new LongOperation().execute("");
            setContentView(R.layout.activity_main);
            {
                //gpp3 is an easy media file type
                sounds[0] = new Sound(new File(MainActivity.this.getFilesDir(), "sound1.gpp3"), (Button) findViewById(R.id.btn1));
                sounds[1] = new Sound(new File(MainActivity.this.getFilesDir(), "sound2.gpp3"), (Button) findViewById(R.id.btn2));
                sounds[2] = new Sound(new File(MainActivity.this.getFilesDir(), "sound3.gpp3"), (Button) findViewById(R.id.btn3));
                sounds[3] = new Sound(new File(MainActivity.this.getFilesDir(), "sound4.gpp3"), (Button) findViewById(R.id.btn4));
                sounds[4] = new Sound(new File(MainActivity.this.getFilesDir(), "sound5.gpp3"), (Button) findViewById(R.id.btn5));
                sounds[5] = new Sound(new File(MainActivity.this.getFilesDir(), "sound6.gpp3"), (Button) findViewById(R.id.btn6));
                sounds[6] = new Sound(new File(MainActivity.this.getFilesDir(), "sound7.gpp3"), (Button) findViewById(R.id.btn7));
                sounds[7] = new Sound(new File(MainActivity.this.getFilesDir(), "sound8.gpp3"), (Button) findViewById(R.id.btn8));
                sounds[8] = new Sound(new File(MainActivity.this.getFilesDir(), "sound9.gpp3"), (Button) findViewById(R.id.btn9));
            }
            Button btnBeat=(Button)findViewById(R.id.btn_beat);


            for(final Sound sound : sounds) {
                sound.getBtn().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sound.play(MainActivity.this);
                    }
                });
                sound.getBtn().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent myIntent=new Intent(MainActivity.this,RecordActivity.class);
                        myIntent.putExtra("file",sound.getFileout());
                        startActivity(myIntent);
                        return false;
                    }
                });
            }
            btnBeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sounds[0].play(MainActivity.this);
                    sounds[1].play(MainActivity.this);
                }
            });
        }
    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(MainActivity.this,"ready to go", Toast.LENGTH_SHORT).show();
            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "loading...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
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
}
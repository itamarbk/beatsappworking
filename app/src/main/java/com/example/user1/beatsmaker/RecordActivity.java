package com.example.user1.beatsmaker;

import android.content.Intent;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.io.File;

public class RecordActivity extends AppCompatActivity {
    private MediaRecorder record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        final String LOOP_EXPLAIN = "poop on that loop";
        final Button btn = (Button) findViewById(R.id.btn_record);
        Button btn_done = (Button) findViewById(R.id.btn_done);
        Button btn_add=(Button) findViewById(R.id.add_btn);
        Button btn_dlt=(Button) findViewById(R.id.dlt_btn);
        final EditText freq=(EditText) findViewById(R.id.freq);
        btn.setText("record");
        Intent stillMyIntent = getIntent();
        final File fileout = (File) stillMyIntent.getExtras().get("file");
        Log.d("the file is", fileout.getPath());
        /*                                       record                                         */
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//records sound
                if (btn.getText() == "record") {//if not already recording
                    if (fileout != null)
                        fileout.delete();//deletes any file that exists at the location
                    if (record != null)
                        record.release();//resets the mediarecorder
                    record = new MediaRecorder();
                    record.setAudioSource(MediaRecorder.AudioSource.MIC);//sets audio source to the microphone
                    record.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//sets format to match file type
                    record.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    record.setOutputFile(fileout.getPath());
                    try {
                        record.prepare();
                    } catch (Exception e) {
                    }
                    record.start();//start recording to file
                    btn.setText("stop");
                } else {//if already recording
                    if (record != null) {
                        record.stop();//stop recording to file
                        record.release();
                        btn.setText("record");
                        return;
                    }
                }
            }
        });
    /*                                       go back                              */
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//send back to mainactivity
                try {
                    record.stop();
                    record.release();
                }
                catch(Exception e) {

                }
                startActivity(new Intent(RecordActivity.this, MainActivity.class));
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {//adds file to database
            @Override
            public void onClick(View view) {
                try {
                    if (freq.getText().toString() != null || freq.getText().toString() != "") {
                        dbHandler handle = new dbHandler(RecordActivity.this, null);
                        if (handle.existsInDB(fileout))
                            handle.updateSound(fileout, Integer.parseInt(freq.getText().toString()));
                        else
                            handle.addSound(fileout, Integer.parseInt(freq.getText().toString()));
                    }
                }
                catch(Exception e){}
                }
        });

        btn_dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//delets file from database
                dbHandler handle=new dbHandler(RecordActivity.this, null);
                if(handle.existsInDB(fileout))
                    handle.deleteSound(fileout);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record, menu);
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

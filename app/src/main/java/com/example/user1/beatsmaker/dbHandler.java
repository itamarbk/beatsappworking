package com.example.user1.beatsmaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tbaic on 4/30/2017.
 */

public class dbHandler extends SQLiteOpenHelper {
    private final static int DATABASE_VERSION=1;
    private final static String DATABASE_NAME="BEATS_MAKER_DB.db";
    private final static String TABLE_NAME="Sounds";

    private final static String COLUMN_Id="id";
    private final static String COLUMN_SOUND_FILE_PATH="file";
    private final static String COLUMN_PLAY_EVERY="frequency";

    public dbHandler(Context context, SQLiteDatabase.CursorFactory factory ){
        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE="";
        CREATE_TABLE+="CREATE TABLE "+TABLE_NAME+" (";
        CREATE_TABLE+=COLUMN_Id+" INTEGER PRIMARY KEY,";
        CREATE_TABLE+=COLUMN_SOUND_FILE_PATH+" TEXT,";
        CREATE_TABLE+=COLUMN_PLAY_EVERY+" INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addSound(Sound snd, Integer frequency){
        if(snd.getFileout()!=null) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SOUND_FILE_PATH, snd.getFileout().getPath());
            values.put(COLUMN_PLAY_EVERY, frequency);
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_NAME, null, values);
            db.close();
        }
    }

    public void deleteSound(Sound snd) {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,COLUMN_SOUND_FILE_PATH+"= ?",new String[]{String.valueOf(snd.getFileout().getPath())});
        db.close();
    }
    public void updateSound(Sound snd, Integer frequency){
        if(snd.getFileout()!=null) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_SOUND_FILE_PATH, snd.getFileout().getPath());
            values.put(COLUMN_PLAY_EVERY, frequency);
            SQLiteDatabase db = this.getWritableDatabase();
            db.update(TABLE_NAME, values, COLUMN_SOUND_FILE_PATH + "= ?", new String[]{String.valueOf(snd.getFileout().getPath())});
            db.close();
        }
    }

    public BeatBit[] getArrayForBeat() {
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT = "";
        SELECT += "SELECT " + COLUMN_SOUND_FILE_PATH + ","
                + COLUMN_PLAY_EVERY + " FROM "
                + TABLE_NAME;
        Cursor crsr = db.rawQuery(SELECT, null);
        BeatBit[] MyBits = new BeatBit[9];
        int index = 0;
        if (crsr != null) {
            if (crsr.moveToFirst()) {
                do {
                    if(index<MyBits.length)
                        MyBits[index] = new BeatBit(crsr.getString(crsr.getColumnIndex(COLUMN_SOUND_FILE_PATH)),
                            crsr.getInt(crsr.getColumnIndex(COLUMN_PLAY_EVERY)));
                    index++;
                } while (crsr.moveToNext());
            }
        }
        return MyBits;
    }
}

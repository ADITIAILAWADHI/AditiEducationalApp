package com.ADITIAILAWADHI.aditieducationalapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME ="AditiGame.db";
    public static final String TABLE_NAME = "users";
    static final String ID_COL = "_id";
    public static final String USERNAME_COL = "username";
    public static final String DURATION_COL = "duration";
    public static final String LEVEL_COL = "level";
    public static final String DATE_COL = "date";
    public static final String SCORE_COL = "score"; //higher is better

    public DBHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "create table " + TABLE_NAME
                + "("
                + ID_COL + " integer primary key autoincrement,"
                + USERNAME_COL + " text not null,"
                + DURATION_COL + " int default 0,"
                + LEVEL_COL + " text default '1',"
                + DATE_COL + " timestamp default CURRENT_TIMESTAMP,"
                + SCORE_COL + " int default 0"
                + ")";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " +  TABLE_NAME);
        onCreate(db);
    }

    public boolean insertPlayer(String usr, int duration, String level, int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME_COL, usr);
        values.put(SCORE_COL, score);
        values.put(DURATION_COL, duration);
        values.put(LEVEL_COL, level);
        db.insert(TABLE_NAME, null, values);
        return true;
    }

    public Cursor getPlayer(String usr){
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlStr = "select * from " + TABLE_NAME + " where "
                + USERNAME_COL + " = " + "'" + usr + "'";
        Cursor cursor = db.rawQuery(sqlStr, null);
        return cursor;
    }

    public Cursor getAllPlayers(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlStr = "select * from " + TABLE_NAME
                + " order by "
                + SCORE_COL + " DESC, "
                + DURATION_COL + " DESC, "
                + USERNAME_COL + " ASC";
        return db.rawQuery(sqlStr, null);
    }
}
package com.example.android.animation_assignment4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.animation_assignment4.FdContract.*;

/**
 * Created by CuiCui on 5/2/2016.
 */
public class DateBaseHelper extends SQLiteOpenHelper {

    public DateBaseHelper(Context context) {
        super(context,FeedEntryC.DB_NAME, null, FeedEntryC.DB_VERSION);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String qrC="CREATE TABLE "+FeedEntryC.TABLE_NAME
                + "(" +FeedEntryC.COL_ID
                +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +FeedEntryC.COL_USER+" TEXT,"
                +FeedEntryC.COL_PWD+" TEXT,"
                + FeedEntryC.COL_LEVEL+" INT,"
                + FeedEntryC.COL_STAGE+" INT)";

        String qrP="CREATE TABLE "+FeedEntryP.TABLE_NAME
                + "(" +FeedEntryP.COL_ID
                +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +FeedEntryP.COL_USER+" TEXT,"
                +FeedEntryP.COL_PWD+" TEXT)";

        db.execSQL(qrC);
        db.execSQL(qrP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ FeedEntryC.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ FeedEntryP.TABLE_NAME);
        onCreate(db);
    }

    public boolean insertDataC(String username, String pwd){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(FeedEntryC.COL_USER, username);
        contentValues.put(FeedEntryC.COL_PWD, pwd);
        contentValues.put(FeedEntryC.COL_LEVEL, 0);
        contentValues.put(FeedEntryC.COL_STAGE, 0);
        long result=db.insert(FeedEntryC.TABLE_NAME, null, contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }

    public boolean insertDataP(String username, String pwd){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(FeedEntryP.COL_USER, username);
        contentValues.put(FeedEntryP.COL_PWD, pwd);
        long result=db.insert(FeedEntryP.TABLE_NAME, null, contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllDataC(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+FeedEntryC.TABLE_NAME, null);
        return res;
    }

    public Cursor getAllDataP(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+FeedEntryP.TABLE_NAME, null);
        return res;
    }

    public Cursor getRecByUsernameC(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        try {
            Cursor res=db.rawQuery("SELECT * FROM "+FeedEntryC.TABLE_NAME +
                    " WHERE "+FeedEntryC.COL_USER +"='"+username+"';", null);
            return res;
        }catch (Exception e){
            Log.v("CCR", "fix the getRecByUsername");
        }
        return null;
    }

    public Cursor getRecByUsernameP(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        try {
            Cursor res=db.rawQuery("SELECT * FROM "+FeedEntryP.TABLE_NAME +
                    " WHERE "+FeedEntryP.COL_USER +"='"+username+"';", null);
            return res;
        }catch (Exception e){
            Log.v("CCR", "fix the getRecByUsername");
        }
        return null;
    }

    public boolean updateByUsername(String username,int level, int stage){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(FeedEntryC.COL_LEVEL, level);
        contentValues.put(FeedEntryC.COL_STAGE, stage);
        long result=db.update(FeedEntryC.TABLE_NAME, contentValues, "username = ?", new String[]{username});
        if (result==-1)
            return false;
        else
            return true;
    }
}

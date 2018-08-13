package com.mojafarin.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mojafarin.Modal.Notify;

import java.util.ArrayList;
/**
 * *
 * *          ____  ____ _____ ___   ____
 * *         | \ \ / / |/ _  || \ \ / / |
 * *         | |\ V /| | (_| || |\ V /| |
 * *         |_| \_/ |_|\__,_||_| \_/ |_|
 * *
 * Created by Mohammad Ali Mirshahbazi
 */
public class DatabaseHandler
        extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "abrin";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_Delete = "del";
    private static final String KEY_Id = "id";
    private static final String KEY_Img = "img";
    private static final String KEY_Link = "link";
    private static final String KEY_Msg = "msg";
    private static final String KEY_Title = "title";
    private static final String KEY_View = "view";
    private static final String TABLE_Notify = "notify";

    public DatabaseHandler(Context paramContext) {
        super(paramContext, DATABASE_NAME, null, 1);
    }

    public void addNotify(Notify paramNotify) {
        SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put(KEY_Id, paramNotify.id);//0
        localContentValues.put(KEY_Title, paramNotify.title);//1
        localContentValues.put(KEY_Msg, paramNotify.msg);//2
        localContentValues.put(KEY_Img, paramNotify.img);//3
        localContentValues.put(KEY_Link, paramNotify.link);//4
        localContentValues.put(KEY_View, Integer.valueOf(paramNotify.view));//5
        localContentValues.put(KEY_Delete, Integer.valueOf(0));//6
        localSQLiteDatabase.insert(TABLE_Notify, null, localContentValues);
        localSQLiteDatabase.close();
    }

    public void clearNotify() {
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("del", Integer.valueOf(1));
        localSQLiteDatabase.update("notify", localContentValues, null, null);
        localSQLiteDatabase.close();
    }

    public ArrayList<Notify> getNotify() {
        ArrayList localArrayList = new ArrayList();
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT DISTINCT id ,title ,msg,img,link,view FROM notify WHERE del=0 ORDER BY id", null);
        //SELECT DISTINCT name FROM info WHERE status = 1 ORDER BY id
        localCursor.moveToFirst();
        for (int i = 0; i < localCursor.getCount(); i++) {
            localArrayList.add(new Notify(localCursor.getString(0), localCursor.getString(1), localCursor.getString(2), localCursor.getString(3), localCursor.getString(4), localCursor.getInt(5)));
            localCursor.moveToNext();
        }
        localSQLiteDatabase.close();
        localCursor.close();
        return localArrayList;
    }

    public boolean isNew() {
        boolean bool = false;
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM notify WHERE view=0 AND del=0", null);
        if (localCursor.getCount() > 0) {
            bool = true;
        }
        localCursor.close();
        localSQLiteDatabase.close();
        return bool;
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
        paramSQLiteDatabase.execSQL("CREATE TABLE notify(id TEXT, title TEXT, msg TEXT,img TEXT,link TEXT,view INTEGER,del INTEGER)");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
    }

    public void viewNews(String paramString) {
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put(KEY_View, Integer.valueOf(1));
        localSQLiteDatabase.update(TABLE_Notify, localContentValues, "id=" + paramString, null);
        localSQLiteDatabase.close();
    }
}

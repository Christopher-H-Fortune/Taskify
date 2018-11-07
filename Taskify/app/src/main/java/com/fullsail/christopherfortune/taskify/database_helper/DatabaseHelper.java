// Christopher Fortune
// DVP5 -1811
// DatabaseHelper.java

package com.fullsail.christopherfortune.taskify.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_FILE = "tasksDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TASK = "task";
    public static final String COLUMN_COMPLETED = "completion";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                                            TABLE_NAME + " (" +
                                            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                            COLUMN_TASK + "TEXT, " +
                                            COLUMN_COMPLETED + "BOOLEAN)";

    private SQLiteDatabase taskDatabase;

    private DatabaseHelper(Context context){
        super(context, DATABASE_FILE, null, DATABASE_VERSION);

        taskDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private static DatabaseHelper INSTANCE = null;

    public static DatabaseHelper getInstance (Context context) {
        if(INSTANCE == null){
            INSTANCE = new DatabaseHelper(context);
        }

        return INSTANCE;
    }

    public long insertTask(String task, Boolean completed){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TASK, task);
        contentValues.put(COLUMN_COMPLETED, completed);
        return taskDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getAllData(){
        return taskDatabase.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}

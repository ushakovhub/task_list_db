package ru.ushakov.tasklist.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBAdapter {
    private DBHelper dbHelper;
    private SQLiteDatabase database;


    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context.getApplicationContext());
    }

    public DBAdapter open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public long addTask(String s) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, s);
        return database.insert(dbHelper.TASK_TABLE_NAME, null, cv);
    }

    public void insert(String task){
        database.rawQuery("INSERT INTO "
                + dbHelper.TASK_TABLE_NAME +
                "("+ dbHelper.COLUMN_NAME +") " +
                "VALUES ('"+task+"');", null);
    }

    public ArrayList<String> seleclAll(){
        ArrayList<String> allTasks = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+ dbHelper.TASK_TABLE_NAME +";", null);
        while (cursor.moveToNext()) {
            String taskName = cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_NAME));
            allTasks.add(taskName);
        }
        return allTasks;
    }

    public int deleteTask(String task){
        return database.delete(dbHelper.TASK_TABLE_NAME, DBHelper.COLUMN_NAME + " = '" + task+"'", null);
    }

}

package ru.ushakov.tasklist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import ru.ushakov.tasklist.data.Task;

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

    public long addTask(Task task) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_NAME, task.getTaskName());
        return database.insert(dbHelper.TASK_TABLE_NAME, null, cv);
    }

    public ArrayList<Task> seleclAll(){
        ArrayList<Task> allTasks = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+ dbHelper.TASK_TABLE_NAME +";", null);
        while (cursor.moveToNext()) {
            String taskName = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME));
            Integer taskId = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
            allTasks.add(new Task(taskName, taskId));
        }
        return allTasks;
    }

    public int deleteTask(String taskId){
        return database.delete(dbHelper.TASK_TABLE_NAME, DBHelper.COLUMN_ID + " = '" + taskId +"'", null);
    }
}
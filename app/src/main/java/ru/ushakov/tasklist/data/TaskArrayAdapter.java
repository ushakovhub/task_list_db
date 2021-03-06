package ru.ushakov.tasklist.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import ru.ushakov.tasklist.R;
import ru.ushakov.tasklist.db.DBAdapter;
import ru.ushakov.tasklist.db.DBHelper;

public class TaskArrayAdapter extends ArrayAdapter<Task> {
    private LayoutInflater inflater;
    private int layoutResource;
    private ArrayList<Task> tasks;

    Button btnDelTask;
    DBAdapter dbAdapter;

    TaskArrayAdapter taskArrayAdapter;

    public TaskArrayAdapter(@NonNull Context context, int resource, ArrayList<Task> tasks, DBAdapter dbAdapter) {
        super(context, resource, tasks);
        this.layoutResource = resource;
        this.tasks = tasks;
        this.inflater = LayoutInflater.from(context);
        this.dbAdapter = dbAdapter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(this.layoutResource, parent, false);
        TextView taskNameView = (TextView) view.findViewById(R.id.taskName);
        btnDelTask = (Button) view.findViewById(R.id.btnDel);
        Task task = tasks.get(position);

        taskNameView.setText(task.getTaskName());

        btnDelTask.setTag(task.getTaskID().toString());
        taskArrayAdapter = this;

        btnDelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdapter.deleteTask(v.getTag().toString());
                tasks.clear();
                tasks.addAll(dbAdapter.seleclAll());
                taskArrayAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}
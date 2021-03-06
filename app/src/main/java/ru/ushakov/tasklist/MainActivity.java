package ru.ushakov.tasklist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import ru.ushakov.tasklist.data.Task;
import ru.ushakov.tasklist.data.TaskArrayAdapter;
import ru.ushakov.tasklist.db.DBAdapter;

public class MainActivity extends AppCompatActivity {
    DBAdapter dbAdapter;

    ArrayList<Task> tasks = new ArrayList<>();
    ArrayList<Task> selectedTasks = new ArrayList<>();

    TaskArrayAdapter adapter;
    ListView tasksList;

    Button btnAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        tasks = dbAdapter.seleclAll();

        tasksList = (ListView) findViewById(R.id.tasksList);
        btnAddTask = (Button) findViewById(R.id.btnAdd);

        adapter = new TaskArrayAdapter(this, R.layout.tasks_list, tasks, dbAdapter);
        tasksList.setAdapter(adapter);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText taskNameInput = (EditText) findViewById(R.id.taskName);
                String taskName = taskNameInput.getText().toString();
                if(!taskName.isEmpty()){
                    dbAdapter.addTask(new Task(taskName));
                    taskNameInput.setText("");
                    tasks.clear();
                    tasks.addAll(dbAdapter.seleclAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }
}
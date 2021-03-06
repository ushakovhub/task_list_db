package ru.ushakov.tasklist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import ru.ushakov.tasklist.db.DBAdapter;

public class MainActivity extends AppCompatActivity {
    DBAdapter dbAdapter;

    ArrayList<String> tasks = new ArrayList<>();
    ArrayList<String> selectedTasks = new ArrayList<>();

    ArrayAdapter<String> adapter;
    ListView tasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        tasks = dbAdapter.seleclAll();

        tasksList = (ListView) findViewById(R.id.tasksList);
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                tasks);
        tasksList.setAdapter(adapter);


        tasksList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String task = adapter.getItem(position);
                if(tasksList.isItemChecked(position))
                    selectedTasks.add(task);
                else
                    selectedTasks.remove(task);
            }
        });
    }

    public void add(View view){
        EditText taskName = (EditText) findViewById(R.id.taskName);
        String task = taskName.getText().toString();
        if(!task.isEmpty()){
            dbAdapter.addTask(task);
            taskName.setText("");
            tasks.clear();
            tasks.addAll(dbAdapter.seleclAll());
            adapter.notifyDataSetChanged();
        }
    }
    public void remove(View view){
        for(int i=0; i< selectedTasks.size();i++){
            dbAdapter.deleteTask(selectedTasks.get(i));
        }
        tasksList.clearChoices();
        selectedTasks.clear();
        tasks.clear();
        tasks.addAll(dbAdapter.seleclAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }
}
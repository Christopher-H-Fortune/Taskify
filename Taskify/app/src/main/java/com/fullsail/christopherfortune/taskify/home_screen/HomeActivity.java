// HomeActivity.java
// DVP5 - 1811
// HomeActivity.java

package com.fullsail.christopherfortune.taskify.home_screen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.fullsail.christopherfortune.taskify.R;
import com.fullsail.christopherfortune.taskify.selected_task_screen.SelectedTaskActivity;
import com.fullsail.christopherfortune.taskify.task_data_class.TaskData;
import com.fullsail.christopherfortune.taskify.options_screen.OptionsActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class HomeActivity extends WearableActivity {

    // ArrayList to store the data from the internal storage
    private ArrayList<TaskData> taskDataArrayList = new ArrayList<>();

    // ListView to display the task the user has created and saved on the device
    private ListView tasksEnteredListView;

    // Context to store the context of the application
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Store the application context to the context variable
        context = getApplicationContext();

        // Obtain the options button to allow the user to view the options activity
        ImageButton optionsButton = findViewById(R.id.options_menu_button);

        // Set the options button click listener to options_menu_listener
        optionsButton.setOnClickListener(options_menu_listener);

        // Obtain the listView to set the adapter and allow the user to select a task from the list view
        tasksEnteredListView = findViewById(R.id.task_list_view);

        // Allow the user to select a task and view it in a detailed view
        tasksEnteredListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Get the task Text from the list view
                String taskSelected = taskDataArrayList.get(i).getTask();

                // Intent the user to the Selected Task Screen with the string saved above
                Intent taskSelectedIntent = new Intent(context, SelectedTaskActivity.class);

                // Assign the task chosen text to send the task text to the selected task screen
                taskSelectedIntent.putExtra("task", taskSelected);
                taskSelectedIntent.putExtra("taskNumber", i);

                // Start the activity with the intent created above
                startActivity(taskSelectedIntent);
            }
        });

        tasksEnteredListView.setOnItemLongClickListener(longClickListener);

        // Call the getTaskData method
        getTaskData();

        // Enables Always-on
        setAmbientEnabled();
    }

    private final AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            // Get the string of the task chosen
            String task = taskDataArrayList.get(i).getTask();

            // Update the boolean value at the task selected
            taskDataArrayList.set(i, new TaskData(task, true));

            try {

                // Create the fileOutputStream with the file name tasks to internal storage
                FileOutputStream fileOutputStream = openFileOutput("tasks", MODE_PRIVATE);

                // ObjectOutputStream to write the array to the storage
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

                // Write the taskData arrayList to the file
                objectOutputStream.writeObject(taskDataArrayList);

                // Close the fileOutputStream and ObjectOutputStream
                objectOutputStream.close();
                fileOutputStream.close();

                TaskListAdapter taskListAdapter = new TaskListAdapter(context, taskDataArrayList);

                tasksEnteredListView.setAdapter(taskListAdapter);

                // Catch any error that may occur
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }
    };

    private final View.OnClickListener options_menu_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Intent to send the user to the options menu
            Intent optionsIntent = new Intent(getApplicationContext(), OptionsActivity.class);

            // Start the Options Activity with the intent created above
            startActivity(optionsIntent);
        }
    };

    // Method to obtain the tasks from the internal storage and display them in the ListView
    private void getTaskData(){
        // Obtain the task file
        try {

            // File Input Stream to get the tasks file
            FileInputStream fileInputStream = openFileInput("tasks");

            // Object Input Stream to get the object from the task file
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            // Store the object from the ObjectInputStream as an ArrayList to the taskDataArrayList variable
            taskDataArrayList = (ArrayList) objectInputStream.readObject();

            // Close the fileInputStream and ObjectInputStream
            fileInputStream.close();
            objectInputStream.close();

            // Create the TaskListAdapter to display the tasks in the task ListView
            TaskListAdapter taskListAdapter = new TaskListAdapter(context, taskDataArrayList);

            // Set the tasksEnteredListView adapter to the adapter created above
            tasksEnteredListView.setAdapter(taskListAdapter);

        // Catch any error that might occur
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // ArrayAdapter class to display the data in the listView
    class TaskListAdapter extends ArrayAdapter<TaskData> {

        // TaskListAdapter Constructor
        TaskListAdapter(Context context, ArrayList<TaskData> taskDataArrayList) {
            super(context, 0, taskDataArrayList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            // Get the task from the array
            TaskData task = getItem(position);

            // If the cell layout is null
            if(convertView == null){

                // Inflate the cell with the task_cell layout
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_cell, parent, false);
            }

            // Get the textView to set the task text view in the cell
            TextView taskTextView = convertView.findViewById(R.id.list_task_text_view);

            // If the task isn't null
            if (task != null) {

                // Display the task in the taskTextView
                taskTextView.setText(task.getTask());

                // if the task is completed
                if(task.getCompletedBoolean()){

                    // Put a strike through line through the text
                    taskTextView.setPaintFlags(taskTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    // Set the text color to green
                    taskTextView.setTextColor(getResources().getColor(R.color.completed_color, null));
                }
            }

            // Return convertView to display in the ListView
            return convertView;
        }

    }
}

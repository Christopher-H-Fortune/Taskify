// Christopher Fortune
// DVP5 - 1811
// OptionsActivity.java

package com.fullsail.christopherfortune.taskify.options_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fullsail.christopherfortune.taskify.R;
import com.fullsail.christopherfortune.taskify.add_task_screen.AddTaskActivity;
import com.fullsail.christopherfortune.taskify.completed_task_count_screen.CompletedTaskCountActivity;
import com.fullsail.christopherfortune.taskify.home_screen.HomeActivity;
import com.fullsail.christopherfortune.taskify.task_data_class.TaskData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class OptionsActivity extends WearableActivity {

    // Context to store the context for the intents
    private Context activityContext;

    // Array List to store the tasks from the storage
    private ArrayList<TaskData> taskDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        // Get the Add Button to allow the user to add a task
        Button addTaskButton = findViewById(R.id.add_button);

        // Set the addTaskButton on click listener to add_task_listener
        addTaskButton.setOnClickListener(add_task_listener);

        // Get the Home Button to allow the user to go back to the home screen
        ImageButton goHomeButton = findViewById(R.id.home_button);

        // Set the goHomeButton on click listener to go_home_listener
        goHomeButton.setOnClickListener(go_home_listener);

        // Get the deleteAllButton to allow the user to delete all the tasks the user has created
        Button deleteAllButton = findViewById(R.id.delete_all_button);

        // Set the deleteAllButton on click listener to delete_all_listener
        deleteAllButton.setOnClickListener(delete_all_listener);

        // Get the completeAllButton to allow the user to complete all the tasks they have created
        Button completeAllButton = findViewById(R.id.complete_all_button);

        // Set the completeAllButton on click listener to complete_all_listener
        completeAllButton.setOnClickListener(complete_all_listener);

        // Get the completedTaskCountButton to allow the user to see the count of the completed tasks
        Button completedTaskCountButton = findViewById(R.id.completed_task_count_button);

        // Set the completedTaskCountButton on click listener to completed_task_count_listener
        completedTaskCountButton.setOnClickListener(completed_task_count_listener);

        // Store the base context
        activityContext = getBaseContext();

        // Enables Always-on
        setAmbientEnabled();
    }

    // OnClickListener to set to the add task button
    private final View.OnClickListener add_task_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Intent to send the user to the Add Task Screen
            Intent addTaskIntent = new Intent(activityContext, AddTaskActivity.class);

            // Send the user to the add task screen
            startActivity(addTaskIntent);
        }
    };

    // onClickListener to set to the go home button
    private final View.OnClickListener go_home_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Finish the activity to send the user back to the home screen
            finish();
        }
    };

    // onClickListener to set to the delete all button
    private final View.OnClickListener delete_all_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Delete all the task's that the user has created
            File taskFile = new File(getApplicationContext().getFilesDir(), "tasks");

            // Intent to send the user back to the home screen
            Intent homeIntent = new Intent(getBaseContext(), HomeActivity.class);

            // IF the taskFile exists
            if(taskFile.exists()){

                // Delete the file
                Boolean deleted = taskFile.delete();

                // Alert the user the tasks have been deleted
                Toast.makeText(getBaseContext(), R.string.tasks_deleted, Toast.LENGTH_SHORT).show();

                // Send the user back to the home screen
                startActivity(homeIntent);

                // If the file doesn't exist
            } else {

                // Alert the user there are no tasks to delete
                Toast.makeText(getBaseContext(), R.string.no_tasks, Toast.LENGTH_SHORT).show();

                // Send the user back to the home screen
                startActivity(homeIntent);
            }
        }
    };

    // onClickListener to set to the complete all button
    private final View.OnClickListener complete_all_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Call the obtainTaskData method to get the arrayList of task data
            obtainTaskData();

            // Loop through the taskDaaArrayList
            for(int i = 0; i < taskDataArrayList.size(); i++){

                // Obtain the string to from the task to re-save to the array
                String taskString = taskDataArrayList.get(i).getTask();

                // Set the values of the index currently in the array to the temp string variable and set the value to true
                taskDataArrayList.set(i, new TaskData(taskString, true));
            }

            // Re-save the array list to the internal storage
            saveTaskData();

            // Intent to send the user back to the home screen
            Intent homeIntent = new Intent(activityContext, HomeActivity.class);

            // Start the home screen activity with the intent created above
            startActivity(homeIntent);
        }
    };

    // onClickListener to set to the completed task count button
    private final View.OnClickListener completed_task_count_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Display the completed task count screen
            Intent taskCountIntent = new Intent(activityContext, CompletedTaskCountActivity.class);

            // Start the activity with the intent created above
            startActivity(taskCountIntent);
        }
    };

    // Method to obtain the data from the internal storage
    private void obtainTaskData() {
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

            // Catch any error that might occur
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to save the data to the internal storage
    private void saveTaskData(){
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

            // Catch any error that may occur
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

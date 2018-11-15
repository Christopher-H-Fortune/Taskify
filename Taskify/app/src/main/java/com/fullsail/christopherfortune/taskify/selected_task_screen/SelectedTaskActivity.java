// Christopher Fortune
// DVP5 - 1811
// SelectedTaskActivity.java

package com.fullsail.christopherfortune.taskify.selected_task_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fullsail.christopherfortune.taskify.R;
import com.fullsail.christopherfortune.taskify.edit_task_screen.EditTaskActivity;
import com.fullsail.christopherfortune.taskify.home_screen.HomeActivity;
import com.fullsail.christopherfortune.taskify.task_data_class.TaskData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SelectedTaskActivity extends WearableActivity {

    // Context to store the context of the application
    private Context context;

    // Int variable to store the task selected number
    private int taskSelectedNumber;

    // String variable to store the task selected string
    private String taskSelected;

    // ArrayList to store the data from the internal storage
    private ArrayList<TaskData> taskDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_task);

        // Store the application context to the context variable
        context = getApplicationContext();

        // Get the intent of the passing Activity
        Intent selectedTaskIntent = getIntent();

        // Get the task from the intent string extra
        taskSelected = selectedTaskIntent.getStringExtra("task");

        // Get the task number chosen from the intent int extra
        taskSelectedNumber = selectedTaskIntent.getIntExtra("taskNumber", 0);

        // Obtain the text field to display the task the user selected
        TextView taskSelectedTextView = findViewById(R.id.task_selected_text_view);

        // Display the text the user chose
        taskSelectedTextView.setText(taskSelected);

        // Obtain the image button to allow the user to go back to the home screen
        ImageButton backImageButton = findViewById(R.id.home_image_button);

        // Set the onClickListener of the backImageButton to the back_image_listener
        backImageButton.setOnClickListener(back_image_listener);

        // Get the complete button to allow the user to complete the task
        Button completeButton = findViewById(R.id.complete_task_button);

        // Set the onClickListener of the completeButton to the complete_task_listener
        completeButton.setOnClickListener(complete_task_listener);

        // Get the Edit Task Button to allow the user to edit the selected task
        Button editTaskButton = findViewById(R.id.edit_task_button);

        // Set the onClickListener of the editTaskButton to the edit_task_listener
        editTaskButton.setOnClickListener(edit_task_listener);

        // Get the Delete Button to allow the user to delete the selected task
        Button deleteButton = findViewById(R.id.delete_selected_task_button);

        // Set the onClickListener of the deleteButton to the delete_task_listener
        deleteButton.setOnClickListener(delete_task_listener);

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

        // Enables Always-on
        setAmbientEnabled();
    }

    // ClickListener to allow the user to go back to the home screen
    private final View.OnClickListener back_image_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Send the user back to the home screen
            finish();
        }
    };

    // ClickListener to allow the user to complete the selected task
    private final View.OnClickListener complete_task_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Update the completed boolean value in the array list
            taskDataArrayList.set(taskSelectedNumber, new TaskData(taskSelected, true));

            // Call the saveTaskData method to save the data to the internal storage
            saveTaskData();

            // Intent to send the user back to the home screen
            Intent homeIntent = new Intent(context, HomeActivity.class);

            // Start the Home Screen Activity with the intent created above
            startActivity(homeIntent);
        }
    };

    // ClickListener to allow the user to edit the selected task
    private final View.OnClickListener edit_task_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Intent to send the user to the Edit Task Screen to edit the selected task
            Intent editSelectedTaskIntent = new Intent(context, EditTaskActivity.class);

            // Put the task chosen int to the intent to send to the Edit Task Screen
            editSelectedTaskIntent.putExtra("taskSelected", taskSelectedNumber);

            // Start the Edit Task Screen with the intent created above
            startActivity(editSelectedTaskIntent);
        }
    };

    // ClickListener to allow the user to delete the selected task
    private final View.OnClickListener delete_task_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Delete the selected task
            taskDataArrayList.remove(taskSelectedNumber);

            // Call the saveTaskData method to save the data to the internal storage
            saveTaskData();

            // Intent to send the user back to the home screen
            Intent homeIntent = new Intent(context, HomeActivity.class);

            // Start the Home Screen Activity with the intent created above
            startActivity(homeIntent);
        }
    };

    // Method to save the data to the internal storage
    private void saveTaskData(){
        // Save the data back to the watch
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

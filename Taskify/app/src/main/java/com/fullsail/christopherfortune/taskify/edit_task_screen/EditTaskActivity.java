// Christopher Fortune
// DVP5 - 1811
// EditTask.java

package com.fullsail.christopherfortune.taskify.edit_task_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fullsail.christopherfortune.taskify.R;
import com.fullsail.christopherfortune.taskify.home_screen.HomeActivity;
import com.fullsail.christopherfortune.taskify.task_data_class.TaskData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EditTaskActivity extends WearableActivity {

    // ArrayList to store the data from the internal storage
    ArrayList<TaskData> taskDataArrayList = new ArrayList<>();

    // EditText field to display the text to the user and save the data from it
    EditText taskEditText;

    // Int variable to save the task number the user chose
    int taskNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Get the intent of the passing Activity
        Intent editTaskIntent = getIntent();

        // Get the task number for the array
        taskNumber = editTaskIntent.getIntExtra("taskSelected", 0);

        // Obtain the EditText field to display the text to the user
        taskEditText = findViewById(R.id.edit_task_edit_text);

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

            // Display the task chosen in the edit text
            taskEditText.setText(taskDataArrayList.get(taskNumber).getTask());

            // Catch any error that might occur
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Obtain the home button
        ImageButton homeButton = findViewById(R.id.home_image_button);

        // Set the onClickListener to homeButton to home_listener
        homeButton.setOnClickListener(home_listener);

        // Get the cancel button to allow the user to save the task they have edited
        Button cancelButton = findViewById(R.id.cancel_button);

        // Set the onClickListener to the cancelButton to cancel_edit_listener
        cancelButton.setOnClickListener(cancel_edit_listener);

        // Get the save button to allow the user to save the task they have edited
        Button saveEditButton = findViewById(R.id.save_edit_button);

        // Set the onClickListener to the saveEditButton to save_edit_listener
        saveEditButton.setOnClickListener(save_edit_listener);

        // Enables Always-on
        setAmbientEnabled();
    }

    // OnClickListener to allow the user to save the task they have edited
    private final View.OnClickListener save_edit_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Make sure the EditText field isn't empty or filled with spaces
            if (taskEditText.getText().toString().equals("") || taskEditText.getText().toString().trim().equals("")){

                // Alert the user they need to fill in the field correctly
                Toast.makeText(getBaseContext(), "Enter Task", Toast.LENGTH_SHORT).show();

                // Display the task chosen in the edit text
                taskEditText.setText(taskDataArrayList.get(taskNumber).getTask());

            // If the EditText field has valuable data in it to save
            } else {

                // Update the task in the ArrayList
                taskDataArrayList.set(taskNumber, new TaskData(taskEditText.getText().toString(), false));

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
    };

    // OnClickListener to allow the user to go back to the selected task screen
    private final View.OnClickListener home_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Send the user back to the selected task screen
            finish();
        }
    };

    // OnClickListener to allow the user to cancel editing the task and go back to the selected task screen
    private final View.OnClickListener cancel_edit_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Send the user back to the selected task screen
            finish();
        }
    };
}

// Christopher Fortune
// DVP5 - 1811
// AddTaskActivity.java

package com.fullsail.christopherfortune.taskify.add_task_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.fullsail.christopherfortune.taskify.R;
import com.fullsail.christopherfortune.taskify.task_data_class.TaskData;
import com.fullsail.christopherfortune.taskify.database_helper.DatabaseHelper;
import com.fullsail.christopherfortune.taskify.home_screen.HomeActivity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AddTaskActivity extends WearableActivity {

    // Set the instance of the DatabaseHelper
    DatabaseHelper databaseHelper;

    // ArrayList of type TaskData to store the tasks the user enters
    ArrayList<TaskData> taskData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Set the instance of the DatabaseHelper
        databaseHelper = DatabaseHelper.getInstance(this);

        // Obtain the save task button
        Button saveTaskButton = findViewById(R.id.add_task_button);

        // Set the saveTaskButton onClickListener
        saveTaskButton.setOnClickListener(save_task_listener);

        // Obtain the back button
        ImageButton goBackButton = findViewById(R.id.home_image_button);

        // Set the goBackButton onClickListener to go_back_listener
        goBackButton.setOnClickListener(go_back_listener);

        // Enables Always-on
        setAmbientEnabled();
    }

    // OnClickListener to allow the user to go back to the options screen
    private final View.OnClickListener go_back_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Finish the activity and send the user back to the options screen
            finish();
        }
    };

    // OnClickListener for the saveTaskButton
    private final View.OnClickListener save_task_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // EditText object to obtain the text entered by the user
            EditText taskEditText = findViewById(R.id.task_edit_text);

            // Check if the taskEditText doesn't have data within the view,
            if (taskEditText.getText().toString().equals("") || taskEditText.getText().toString().trim().equals("")) {

                // Alert the user they need to fill in the field correctly
                Toast.makeText(getBaseContext(), "Enter Task", Toast.LENGTH_SHORT).show();

                // Clear the taskEditText
                taskEditText.setText(null);

            // If the edit text has data within it,
            } else {

                // Get the data from the taskEditText
                String taskString = taskEditText.getText().toString();

                // Clear the taskEditText
                taskEditText.setText(null);

                // File Object that's storing the ArrayList
                File taskFile = new File(getApplicationContext().getFilesDir(), "tasks");

                // If the taskFile exists
                if(taskFile.exists()){

                    // Obtain the task file
                    try {

                        // File Input Stream to get the tasks file
                        FileInputStream fileInputStream = openFileInput("tasks");

                        // Object Input Stream to get the object from the task file
                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                        // Store the object from the ObjectInputStream as an ArrayList to the taskData variable
                        taskData = (ArrayList) objectInputStream.readObject();

                        // Close the fileInputStream and ObjectInputStream
                        fileInputStream.close();
                        objectInputStream.close();

                        // Call the getData method passing the taskString
                        getDataMethod(taskString);

                    // Catch any error that might occur
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }


                // If the file doesn't exist
                } else {

                    // Call the getData method passing the taskString
                    getDataMethod(taskString);
                }

                // Insert the task to the tasks database
                // databaseHelper.insertTask(taskString, false);

                // Intent to go back to the home screen to see the newly saved task
                Intent goHomeIntent = new Intent(getBaseContext(), HomeActivity.class);

                // Start the activity with the intent created above
                startActivity(goHomeIntent);
            }

        }
    };

    public void getDataMethod(String taskString){
        try {

            // Create the fileOutputStream with the file name tasks to internal storage
            FileOutputStream fileOutputStream = openFileOutput("tasks", MODE_PRIVATE);

            // ObjectOutputStream to write the array to the storage
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Add the task to the taskData array
            taskData.add(new TaskData(taskString, false));

            // Write the taskData arrayList to the file
            objectOutputStream.writeObject(taskData);

            // Close the fileOutputStream and ObjectOutputStream
            objectOutputStream.close();
            fileOutputStream.close();

        // Catch any error that may occur
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

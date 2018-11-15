// Christopher Fortune
// DVP5 - 1811
// CompletedTaskCountActivity.java

package com.fullsail.christopherfortune.taskify.completed_task_count_screen;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fullsail.christopherfortune.taskify.R;
import com.fullsail.christopherfortune.taskify.task_data_class.TaskData;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class CompletedTaskCountActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_task_count);

        // Get the completedCountTextView to display the completed count of the tasks
        TextView completedCountTextView = findViewById(R.id.completed_count_text_view);

        // Obtain the task file
        try {

            // File Input Stream to get the tasks file
            FileInputStream fileInputStream = openFileInput("tasks");

            // Object Input Stream to get the object from the task file
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            // Store the object from the ObjectInputStream as an ArrayList to the taskDataArrayList variable
            ArrayList<TaskData> taskDataArrayList = (ArrayList) objectInputStream.readObject();

            // Close the fileInputStream and ObjectInputStream
            fileInputStream.close();
            objectInputStream.close();

            // Int variable to store the count of the completed task count
            int completedCount = 0;

            // For loop to loop through the taskDataArrayList
            for(TaskData task: taskDataArrayList){

                // If the task is completed
                if(task.getCompletedBoolean()){

                    // Add one to the completedCount variable
                    completedCount += 1;
                }
            }

            // Display the completedCount to the user
            completedCountTextView.setText(getString(R.string.countMessage, completedCount));

            // Catch any error that might occur
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Get the go back button to allow the user to go back to the options screen
        ImageButton goBackButton = findViewById(R.id.go_back_image_button);

        // Set the onClickListener to the goBackButton to go_back_listener
        goBackButton.setOnClickListener(go_back_listener);

        // Enables Always-on
        setAmbientEnabled();
    }

    // OnClickListener to allow the user to go back to the options screen
    private final View.OnClickListener go_back_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Send the user back to the options screen
            finish();
        }
    };
}

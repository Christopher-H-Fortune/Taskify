// Christopher Fortune
// DVP5 - 1811
// SelectedTaskActivity.java

package com.fullsail.christopherfortune.taskify.selected_task_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fullsail.christopherfortune.taskify.R;

public class SelectedTaskActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_task);

        // Get the intent of the passing Activity
        Intent selectedTaskIntent = getIntent();

        // Get the task from the intent string extra
        String taskSelected = selectedTaskIntent.getStringExtra("task");

        // Get the task number chosen from the intent int extra
        int taskSelectedNumber = selectedTaskIntent.getIntExtra("taskNumber", 0);

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

        // Enables Always-on
        setAmbientEnabled();
    }

    // ClickListener to allow the user to go back to the home screen
    private View.OnClickListener back_image_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Send the user back to the home screen
            finish();
        }
    };

    // ClickListener to allow the user to complete the selected task
    private View.OnClickListener complete_task_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


        }
    };

    // ClickListener to allow the user to edit the selected task
    private View.OnClickListener edit_task_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    // ClickListener to allow the user to delete the selected task
    private View.OnClickListener delete_task_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
}

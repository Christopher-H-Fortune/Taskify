// Christopher Fortune
// DVP5 - 1811
// SelectedTaskActivity.java

package com.fullsail.christopherfortune.taskify.selected_task_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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

        // TODO: Change the EditText to a TextView to display the selected task
        // Obtain the text field to display the task the user selected
        EditText taskSelectedEditText = findViewById(R.id.chosen_task_multiline_text);

        // Display the text the user chose
        taskSelectedEditText.setText(taskSelected);

        // Obtain the image button to allow the user to go back to the home screen
        ImageButton backImageButton = findViewById(R.id.home_image_button);

        // Set the onClickListener of the backImageButton to the back_image_listener
        backImageButton.setOnClickListener(back_image_listener);

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
}

// Christopher Fortune
// DVP5 - 1811
// SelectedTaskActivity.java

package com.fullsail.christopherfortune.taskify.selected_task_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.EditText;
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

        // Obtain the text field to display the task the user selected
        EditText taskSelectedEditText = findViewById(R.id.chosen_task_multine_text);

        // Display the text the user chose
        taskSelectedEditText.setText(taskSelected);

        // Enables Always-on
        setAmbientEnabled();
    }
}
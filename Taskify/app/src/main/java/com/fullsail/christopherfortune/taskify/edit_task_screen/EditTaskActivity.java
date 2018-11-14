// Christopher Fortune
// DVP5 - 1811
// EditTask.java

package com.fullsail.christopherfortune.taskify.edit_task_screen;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fullsail.christopherfortune.taskify.R;

public class EditTaskActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Obtain the home button
        ImageButton homeButton = findViewById(R.id.home_image_button);

        // Set the onClickListener to homeButton to home_listener
        homeButton.setOnClickListener(home_listener);

        // Enables Always-on
        setAmbientEnabled();
    }

    // OnClickListener to allow the user to go back to the home screen
    private final View.OnClickListener home_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Send the user back to the home screen
            finish();
        }
    };
}

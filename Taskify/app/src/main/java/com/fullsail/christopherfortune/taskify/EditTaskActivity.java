// Christopher Fortune
// DVP5 - 1811
// EditTask.java

package com.fullsail.christopherfortune.taskify;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

public class EditTaskActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        // Enables Always-on
        setAmbientEnabled();
    }
}

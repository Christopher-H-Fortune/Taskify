// Christopher Fortune
// DVP5 - 1811
// CompletedTaskCountActivity.java

package com.fullsail.christopherfortune.taskify.completed_task_count_screen;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import com.fullsail.christopherfortune.taskify.R;

public class CompletedTaskCountActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_task_count);

        // Enables Always-on
        setAmbientEnabled();
    }
}

// Christopher Fortune
// DVP5 - 1811
// TaskData.java

package com.fullsail.christopherfortune.taskify.task_data_class;

import java.io.Serializable;

public class TaskData implements Serializable {

    // Variables to create a task object
    private final String task;
    private final Boolean completedBoolean;

    // TaskData Constructor
    public TaskData(String task, Boolean completedBoolean){
        this.task = task;
        this.completedBoolean = completedBoolean;
    }

    // Getter to get the task string from the object
    public String getTask() {
        return task;
    }

    public Boolean getCompletedBoolean() {
        return completedBoolean;
    }
}

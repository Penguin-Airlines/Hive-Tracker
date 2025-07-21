package com.penguinairlines.hivetraker.data.providers

import com.penguinairlines.hivetraker.data.models.Task
import java.util.Calendar

interface TaskProvider {
    fun getTask(name: String, dueDate: Calendar): Task
    fun getTasks(): List<Task>

    fun setTask(task: Task)
    fun setTasks(tasks: List<Task>)
}
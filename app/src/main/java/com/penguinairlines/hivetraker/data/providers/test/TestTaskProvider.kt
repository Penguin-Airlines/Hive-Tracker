package com.penguinairlines.hivetraker.data.providers.test

import com.penguinairlines.hivetraker.BuildConfig
import com.penguinairlines.hivetraker.data.models.Task
import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.TaskProvider
import java.util.Calendar

object TestTaskProvider: TaskProvider {
    private var yard = Yard(
        "Test Yard",
        User(
            "Burt Miller",
            "burtmiller@burtsbees.com"
        )
    )
    private var tasks = if (BuildConfig.DEBUG_VARIANT == "DEFAULT") {
        mutableListOf(
        Task(
            "Task 1",
            yard,
            dueDate = Calendar.getInstance(),
            description = "A simple task to do",
        ),Task(

            "Task 3",
            yard,
            dueDate = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, 1)
            },
            description = "A task due tomorrow",
        ),
        Task(
            "Task 2",
            yard,
            dueDate = Calendar.getInstance(),
            description = "Another task to complete",
        ),
        Task(
            "Task 6",
            yard,
            dueDate = Calendar.getInstance().apply {
                add(Calendar.MONTH, 2)
            },
            description = "A task due in two months",
        ),

        Task(
            "Task 4",
            yard,
            dueDate = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_MONTH, 2)
            },
            description = "A task due in two days",
        ),
        Task(
            "Task 5",
            yard,
            dueDate = Calendar.getInstance().apply {
                add(Calendar.MONTH, 1)
            },
            description = "A task due in a month",
        )
    ) }
    else if (BuildConfig.DEBUG_VARIANT == "MANY_TASKS") {
       List(10000) {
           Task(
               "Task $it",
               yard,
               dueDate = Calendar.getInstance().apply {
                   add(Calendar.DAY_OF_MONTH, it)
               },
               description = "A task number $it to complete",
           )
       }.toMutableList()
    }
    else {
        mutableListOf()
    }

    fun copyTask(task: Task): Task {
        return task.copy(
            yard = yard
        )
    }

    override fun getTask(
        name: String,
        dueDate: Calendar
    ): Task {
        tasks.forEach { task ->
            if (task.name == name && task.dueDate == dueDate) {
                return copyTask(task)
            }
        }
        throw NoSuchElementException("Task with name $name and due date $dueDate not found")
    }

    override fun getTasks(): List<Task> {
        return tasks.map { task -> copyTask(task) }
    }

    override fun setTask(task: Task) {
        tasks.removeIf { it === task }
        tasks.add(task.copy())
    }

    override fun setTasks(tasks: List<Task>) {
        for (task in tasks) {
            setTask(task)
        }
    }

}
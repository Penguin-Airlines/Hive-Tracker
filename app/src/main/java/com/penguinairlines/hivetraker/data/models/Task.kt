package com.penguinairlines.hivetraker.data.models

import java.util.Calendar

data class Task(
    val name: String,
    val yard: Yard,
    val description: String,
    val hive: Hive? = null,
    val isCompleted: Boolean = false,
    val createdDate: Calendar = Calendar.getInstance(),
    val dueDate: Calendar,
    val notes: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Task) return false

        if (name != other.name) return false
        if (yard != other.yard) return false
        if (dueDate != other.dueDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isCompleted.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + yard.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + (hive?.hashCode() ?: 0)
        result = 31 * result + createdDate.hashCode()
        result = 31 * result + dueDate.hashCode()
        result = 31 * result + (notes?.hashCode() ?: 0)
        return result
    }
}
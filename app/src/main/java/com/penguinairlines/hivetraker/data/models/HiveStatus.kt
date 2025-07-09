package com.penguinairlines.hivetraker.data.models

import com.penguinairlines.hivetraker.R

enum class HiveStatus(val text: String, val color: Int) {
    CRITICAL("Critical", R.color.critical_color),
    IN_PROGRESS("In Progress", R.color.in_progress_color),
    OKAY("OK", R.color.okay_color);

}
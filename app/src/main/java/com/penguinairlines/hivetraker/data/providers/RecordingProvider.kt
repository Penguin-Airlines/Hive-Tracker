package com.penguinairlines.hivetraker.data.providers

import com.penguinairlines.hivetraker.data.models.Recording

interface RecordingProvider {
    fun getRecording(name: String): Recording
    fun getRecordings(): List<Recording>

    fun setRecording(Recording: Recording)
    fun setRecordings(Recordings: List<Recording>)
}
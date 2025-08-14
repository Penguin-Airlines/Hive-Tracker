package com.penguinairlines.hivetraker.data.providers

import com.penguinairlines.hivetraker.data.models.Recording

interface RecordingProvider {
    fun getRecording(name: String): Recording
    fun getRecordings(): List<Recording>

    fun addRecording(recording: Recording)
    fun setRecordings(recordings: List<Recording>)
    fun removeRecording(recording: Recording) : Boolean
}
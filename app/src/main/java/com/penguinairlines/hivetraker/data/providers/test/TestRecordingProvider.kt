package com.penguinairlines.hivetraker.data.providers.test

import com.penguinairlines.hivetraker.data.models.Recording
import com.penguinairlines.hivetraker.data.models.User
import com.penguinairlines.hivetraker.data.models.Yard
import com.penguinairlines.hivetraker.data.providers.RecordingProvider

object TestRecordingProvider : RecordingProvider {
    var yard = Yard(
        "Test Yard",
        User(
            "Burt Miller",
            "burtmiller@burtsbees.com"
        )
    )
    private var recordings = mutableListOf<Recording>()
    override fun getRecording(name: String): Recording {
        recordings.forEach(){
            rec -> if (rec.text.equals(name)){
                return rec
        }
        }
        throw NoSuchElementException("Recording $name not found")
    }
    override fun getRecordings(): List<Recording> {
        return recordings
    }

    override fun addRecording(recording: Recording) {

    }
    override fun setRecordings(recordings: List<Recording>) {

    }
    override fun removeRecording(recording: Recording) : Boolean {
        return recordings.remove(recording)
    }
}
package com.penguinairlines.hivetraker

import android.content.Context
import kotlinx.coroutines.*
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.SpeechService
import java.io.File
import java.io.FileOutputStream

suspend fun testVoskInitialization(context: Context) {
    withContext(Dispatchers.IO) {
        try {
            // Copy model from assets to filesDir
            val modelDir = File(context.filesDir, "vosk-model-small-en-us-0.15")
            if (!modelDir.exists()) {
                modelDir.mkdirs()
                fun copyFolder(srcPath: String, destFile: File) {
                    context.assets.list(srcPath)?.forEach { name ->
                        val srcName = "$srcPath/$name"
                        val destName = File(destFile, name)
                        if (context.assets.list(srcName)?.isNotEmpty() == true) {
                            destName.mkdirs()
                            copyFolder(srcName, destName)
                        } else {
                            context.assets.open(srcName).use { input ->
                                FileOutputStream(destName).use { output ->
                                    input.copyTo(output)
                                }
                            }
                        }
                    }
                }
                copyFolder("vosk-model-small-en-us-0.15", modelDir)
            }

            // Initialize model and recognizer
            val model = Model(modelDir.absolutePath)
            val recognizer = Recognizer(model, 16000.0f)

            // Test SpeechService creation
            val speechService = SpeechService(recognizer, 16000.0f)

            println("Vosk initialized successfully!")

            // Clean up
            speechService.shutdown()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

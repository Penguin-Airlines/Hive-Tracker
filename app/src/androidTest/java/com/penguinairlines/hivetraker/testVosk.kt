package com.penguinairlines.hivetraker

import android.Manifest
import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.SpeechService
import java.io.File
import java.io.FileOutputStream

@RunWith(AndroidJUnit4::class)
class VoskTest {

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.RECORD_AUDIO
    )

    private fun copyAssetFolder(context: Context, assetPath: String, dest: File) {
        val assetManager = context.assets
        val files = assetManager.list(assetPath) ?: return

        if (files.isEmpty()) {
            // It's a file, copy it
            assetManager.open(assetPath).use { input ->
                FileOutputStream(dest).use { output ->
                    input.copyTo(output)
                }
            }
        } else {
            // It's a directory
            if (!dest.exists()) dest.mkdirs()
            for (file in files) {
                copyAssetFolder(context, "$assetPath/$file", File(dest, file))
            }
        }
    }

    @Test
    fun testVoskInitialization() = runBlocking {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val modelDir = File(context.filesDir, "vosk-model-small-en-us-0.15")

        // Copy fresh model if missing or incomplete
        if (!File(modelDir, "am/final.mdl").exists()) {
            println("Copying Vosk model to filesDir...")
            if (modelDir.exists()) modelDir.deleteRecursively()
            modelDir.mkdirs()
            copyAssetFolder(context, "vosk-model-small-en-us-0.15", modelDir)
        }

        try {
            val model = Model(modelDir.absolutePath)
            val recognizer = Recognizer(model, 16000.0f)
            val speechService = SpeechService(recognizer, 16000.0f)

            println("✅ Vosk initialized successfully!")
            speechService.shutdown()
            recognizer.close()
            model.close()

        } catch (e: Exception) {
            e.printStackTrace()
            fail("Vosk initialization failed: ${e.message}")
        }
    }
}

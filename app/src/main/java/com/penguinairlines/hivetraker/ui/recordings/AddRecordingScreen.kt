package com.penguinairlines.hivetraker.ui.recordings
import android.content.Intent
import android.speech.RecognizerIntent
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.SpeechRecognizer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.penguinairlines.hivetraker.data.models.Recording
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordingScreen(
    onBack: () -> Unit,
    onSave: (Recording) -> Unit
) {
    val context = LocalContext.current
    val audioManager = androidx.core.content.ContextCompat.getSystemService(context, AudioManager::class.java)
    var isRecording by remember { mutableStateOf(false) }
    var recordedText by remember { mutableStateOf("") }
    var currentChunk by remember { mutableStateOf("") }
    var showTitleDialog by remember { mutableStateOf(false) }
    var recordingTitle by remember { mutableStateOf("") }
    var micPermissionGranted by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted -> micPermissionGranted = granted }

    LaunchedEffect(Unit) {
        micPermissionGranted = androidx.core.content.ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (!micPermissionGranted) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    val recognizerIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
    }

    // Helper to restart listening safely


    // Start listening with system beep muted
    fun startListening() {
        if (!micPermissionGranted || !isRecording) return

        val audioManager = androidx.core.content.ContextCompat.getSystemService(context, AudioManager::class.java)
        audioManager?.let { am ->
            val originalVolume = am.getStreamVolume(AudioManager.STREAM_SYSTEM)
            am.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0) // mute beep

            try {
                speechRecognizer.startListening(recognizerIntent)
            } catch (e: Exception) { e.printStackTrace() }

            // Restore volume after short delay
            Handler(Looper.getMainLooper()).postDelayed({
                am.setStreamVolume(AudioManager.STREAM_SYSTEM, originalVolume, 0)
            }, 100)
        } ?: run {
            // fallback if AudioManager is null
            speechRecognizer.startListening(recognizerIntent)
        }
    }
    fun restartListening() {
        if (!isRecording) return
        try {
            speechRecognizer.cancel()
            Handler(Looper.getMainLooper()).postDelayed({ startListening() }, 300)
        } catch (e: Exception) { e.printStackTrace() }
    }

    DisposableEffect(Unit) {
        val listener = object : android.speech.RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                // Do nothing; restart only in onResults or onError
            }

            override fun onError(error: Int) {
                if (isRecording) {
                    when (error) {
                        SpeechRecognizer.ERROR_CLIENT,
                        SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> isRecording = false
                        else -> restartListening()
                    }
                }
            }

            override fun onResults(results: Bundle?) {
                val finalText = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.joinToString(" ")
                if (!finalText.isNullOrBlank()) {
                    recordedText += if (recordedText.isNotBlank()) " $finalText" else finalText
                }
                currentChunk = ""
                if (isRecording) restartListening()
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val partialText = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.joinToString(" ")
                if (!partialText.isNullOrBlank()) currentChunk = partialText
            }

            override fun onEvent(eventType: Int, params: Bundle?) {}
        }

        speechRecognizer.setRecognitionListener(listener)
        onDispose { speechRecognizer.destroy() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Recording") },
                actions = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") } }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                FloatingActionButton(
                    onClick = {
                        if (!micPermissionGranted) { permissionLauncher.launch(Manifest.permission.RECORD_AUDIO); return@FloatingActionButton }

                        if (isRecording) {
                            isRecording = false
                            speechRecognizer.stopListening()
                        } else {
                            isRecording = true
                            startListening()
                        }
                    },
                    containerColor = if (isRecording) Color.Red else MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                        contentDescription = if (isRecording) "Stop" else "Record",
                        tint = Color.White
                    )
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Recorded Text:", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text((recordedText + if (currentChunk.isNotBlank()) " $currentChunk" else ""), style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.weight(1f))
            Button(onClick = { showTitleDialog = true }, modifier = Modifier.fillMaxWidth(), enabled = recordedText.isNotBlank()) {
                Text("Save Recording")
            }
        }
    }

    if (showTitleDialog) {
        AlertDialog(
            onDismissRequest = { showTitleDialog = false },
            title = { Text("Enter Recording Title") },
            text = {
                OutlinedTextField(
                    value = recordingTitle,
                    onValueChange = { recordingTitle = it },
                    label = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (recordingTitle.isNotBlank()) {
                            onSave(Recording(text = recordedText, title = recordingTitle, date = Date()))
                            showTitleDialog = false
                        }
                    }) { Text("Save") }
            },
            dismissButton = { TextButton(onClick = { showTitleDialog = false }) { Text("Cancel") } }
        )
    }
}


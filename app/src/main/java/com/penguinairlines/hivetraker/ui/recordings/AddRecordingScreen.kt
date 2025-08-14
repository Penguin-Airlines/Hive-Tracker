package com.penguinairlines.hivetraker.ui.recordings

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.penguinairlines.hivetraker.data.models.Recording
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import java.io.File
import java.io.FileOutputStream
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordingScreen(
    onBack: () -> Unit,
    onSave: (Recording) -> Unit
) {
    val context = LocalContext.current
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
            context, Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (!micPermissionGranted) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    var speechService by remember { mutableStateOf<SpeechService?>(null) }

    // Recursive copy of model folder
    suspend fun copyModelToFiles(context: Context, assetFolder: String): File = withContext(Dispatchers.IO) {
        val outDir = File(context.filesDir, assetFolder)
        if (!outDir.exists()) outDir.mkdirs()

        fun copyFolder(srcPath: String, destFile: File) {
            context.assets.list(srcPath)?.forEach { name ->
                val srcName = "$srcPath/$name"
                val destName = File(destFile, name)
                if (context.assets.list(srcName)?.isNotEmpty() == true) {
                    destName.mkdirs()
                    copyFolder(srcName, destName)
                } else {
                    context.assets.open(srcName).use { input ->
                        FileOutputStream(destName).use { output -> input.copyTo(output) }
                    }
                }
            }
        }

        copyFolder(assetFolder, outDir)
        outDir
    }

    // Vosk listener
    val voskListener = remember {
        object : RecognitionListener {
            override fun onPartialResult(hypothesis: String?) {
                if (!hypothesis.isNullOrBlank()) currentChunk = hypothesis
            }

            override fun onResult(hypothesis: String?) {
                if (!hypothesis.isNullOrBlank()) {
                    recordedText += if (recordedText.isNotBlank()) " $hypothesis" else hypothesis
                }
                currentChunk = ""
            }

            override fun onFinalResult(hypothesis: String?) {
                if (!hypothesis.isNullOrBlank()) {
                    recordedText += if (recordedText.isNotBlank()) " $hypothesis" else hypothesis
                }
                currentChunk = ""
            }

            override fun onError(exception: Exception?) {
                exception?.printStackTrace()
            }

            override fun onTimeout() {}
        }
    }

    // Start/stop listening
    LaunchedEffect(isRecording) {
        if (isRecording && micPermissionGranted) {
            try {
                val modelDir = copyModelToFiles(context, "vosk-model-small-en-us-0.15")
                val model = Model(modelDir.absolutePath)
                val recognizer = Recognizer(model, 16000.0f)
                speechService = SpeechService(recognizer, 16000.0f)
                speechService?.startListening(voskListener)
            } catch (e: Exception) {
                e.printStackTrace()
                isRecording = false
            }
        } else {
            speechService?.shutdown()
            speechService = null
            currentChunk = ""
        }
    }

    DisposableEffect(Unit) {
        onDispose { speechService?.shutdown() }
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
                        if (!micPermissionGranted) {
                            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            return@FloatingActionButton
                        }
                        isRecording = !isRecording
                    },
                    containerColor = if (isRecording) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                        contentDescription = if (isRecording) "Stop" else "Record",
                        tint = MaterialTheme.colorScheme.onPrimary
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

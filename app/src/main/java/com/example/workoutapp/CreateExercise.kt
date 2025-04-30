package com.example.workoutapp

import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewExercise(navController: NavController) {
    var exerciseName by remember { mutableStateOf(TextFieldValue("")) }
    var isTimerEnabled by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(30) }
    var selectedColor by remember { mutableStateOf(Color.Transparent) }
    var showTimePicker by remember { mutableStateOf(false) } // для отображения диалога выбора времени
    var selectedMinutes by remember { mutableStateOf(0) }
    var selectedSeconds by remember { mutableStateOf(0) }
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val totalTimeInSeconds = selectedMinutes * 60 + selectedSeconds

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text("Exercise") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                TextButton(onClick = { /* Handle done action */ }) {
                    Text("Done")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = exerciseName,
            onValueChange = { exerciseName = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                .padding(16.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Timer")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isTimerEnabled,
                onCheckedChange = { isTimerEnabled = it }
            )
        }

        if (isTimerEnabled) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
                    .clickable { showTimePicker = true }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Time", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text("%02d:%02d".format(selectedMinutes, selectedSeconds), modifier = Modifier.padding(top = 16.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Exercise Repeats")
            Spacer(modifier = Modifier.weight(1f))
            Text("No")
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Arrow")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
        ) {
            Text("Exercise Color")
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf(Color.Magenta, Color.Blue, Color.Green, Color.Yellow, Color.Red).forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color, shape = CircleShape)
                            .border(
                                width = if (selectedColor == color) 2.dp else 0.dp,
                                color = Color.Black,
                                shape = CircleShape
                            )
                            .clickable { selectedColor = color }
                    )
                }
            }
        }
    }
    
    if (showTimePicker) {
        ModalBottomSheet(onDismissRequest = { showTimePicker = false }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AndroidView(factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 0
                            maxValue = 59
                            value = selectedMinutes
                            setOnValueChangedListener { _, _, newVal ->
                                selectedMinutes = newVal
                            }
                        }
                    })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Min")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    AndroidView(factory = { context ->
                        NumberPicker(context).apply {
                            minValue = 0
                            maxValue = 59
                            value = selectedSeconds
                            setOnValueChangedListener { _, _, newVal ->
                                selectedSeconds = newVal
                            }
                        }
                    })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sec")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { showTimePicker = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Done")
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewNewExercise() {
    NewExercise(navController = rememberNavController())
}

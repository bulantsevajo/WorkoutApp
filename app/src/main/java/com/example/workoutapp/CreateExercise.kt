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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewExercise(navController: NavController) {
    var exerciseName by remember { mutableStateOf(TextFieldValue("")) }
    var isTimerEnabled by remember { mutableStateOf(false) }
    var selectedMinutes by remember { mutableStateOf(0) }
    var selectedSeconds by remember { mutableStateOf(0) }
    var showTimePicker by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf(Color(0xFF9C27B0)) } // фиолетовый по умолчанию

    val colors = listOf(
        Color(0xFF9C27B0), // Фиолетовый
        Color(0xFF2196F3), // Синий
        Color(0xFF4CAF50), // Зелёный
        Color(0xFFFFC107), // Жёлтый
        Color(0xFFF44336)  // Красный
    )

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
                TextButton(onClick = { /* TODO: Save logic */ }) {
                    Text("Done")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Поле ввода
        BasicTextField(
            value = exerciseName.text,
            onValueChange = { exerciseName = TextFieldValue(it) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), shape = MaterialTheme.shapes.medium)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            decorationBox = { innerTextField ->
                if (exerciseName.text.isEmpty()) {
                    Text("Exercise name", color = Color.Gray)
                }
                innerTextField()
            },
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
        )


        Spacer(modifier = Modifier.height(16.dp))

        // Переключатель таймера
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Timer", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isTimerEnabled,
                onCheckedChange = { isTimerEnabled = it }
            )
        }

        if (isTimerEnabled) {
            Spacer(modifier = Modifier.height(8.dp))
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
                Text(
                    "%02d:%02d".format(selectedMinutes, selectedSeconds),
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Exercise Repeats
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
                .clickable { /* TODO: Go to repeats screen */ },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Exercise Repeats", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.weight(1f))
            Text("No", color = Color.Gray)
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Arrow")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Цвет упражнения
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
        ) {
            Text("Exercise Color", fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color, shape = CircleShape)
                            .border(
                                width = if (selectedColor == color) 3.dp else 0.dp,
                                color = if (selectedColor == color) Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable { selectedColor = color }
                    )
                }
            }
        }
    }

    // Таймер-пикер внизу
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
            Spacer(modifier = Modifier.height(42.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewExercise() {
    NewExercise(navController = rememberNavController())
}

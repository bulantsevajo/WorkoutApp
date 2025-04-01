package com.example.workoutapp

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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewExercise(navController: NavController) {
    // State for the text field
    var exerciseName by remember { mutableStateOf(TextFieldValue("")) }
    // State for the switch
    var isTimerEnabled by remember { mutableStateOf(false) }
    // State for the selected color
    var selectedColor by remember { mutableStateOf(Color.Transparent) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top bar with back and done buttons
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

        // Exercise name input
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

        // Timer switch
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

        Spacer(modifier = Modifier.height(16.dp))

        // Exercise repeats
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

        // Exercise color selection
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
}

@Preview(showBackground = true)
@Composable
fun PreviewNewExercise() {
    NewExercise(navController = rememberNavController())
}

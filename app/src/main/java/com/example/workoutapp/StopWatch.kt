package com.example.workoutapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StopwatchCircuit() {
    var timeInSeconds by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(true) }

    // Tick every second
    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000)
            timeInSeconds++
        }
    }

    val totalMarks = 60
    val activeMarks = timeInSeconds % totalMarks

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Отжимания",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        // Stroke timer
        Box(contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.size(260.dp)) {
                val radius = size.minDimension / 2
                val center = Offset(size.width / 2, size.height / 2)
                val strokeWidth = 4.dp.toPx()
                val markLength = 12.dp.toPx()
                val gap = 6f

                for (i in 0 until totalMarks) {
                    val angle = Math.toRadians(i * (360.0 / totalMarks) - 90)
                    val lineColor = if (i < activeMarks) Color.Red else Color.LightGray

                    val start = Offset(
                        x = center.x + (radius - markLength) * cos(angle).toFloat(),
                        y = center.y + (radius - markLength) * sin(angle).toFloat()
                    )
                    val end = Offset(
                        x = center.x + radius * cos(angle).toFloat(),
                        y = center.y + radius * sin(angle).toFloat()
                    )

                    drawLine(
                        color = lineColor,
                        start = start,
                        end = end,
                        strokeWidth = strokeWidth
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val minutes = timeInSeconds / 60
                val seconds = timeInSeconds % 60
                Text(
                    text = String.format("%02d:%02d", minutes, seconds),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Отжимания на брусьях",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        // Info cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoCard(title = "Repeats", value = "1/2")
            InfoCard(title = "Next", value = "Pause")
        }

        // Buttons
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = { /* handle next */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("NEXT", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { isRunning = !isRunning }) {
                Text(if (isRunning) "PAUSE" else "RESUME", color = Color.Black)
            }
        }
    }
}

@Composable
fun InfoCard(title: String, value: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF5F5F5),
        modifier = Modifier
            .width(140.dp)
            .height(64.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = title, fontSize = 14.sp, color = Color.Gray)
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewStopwatchCircuit() {
    StopwatchCircuit()
}

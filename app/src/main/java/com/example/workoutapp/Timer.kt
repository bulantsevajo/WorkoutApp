package com.example.workoutapp

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@Composable
fun TimerCircuit(navController: NavController) {
    var timeLeft by remember { mutableStateOf(18) }
    var progress by remember { mutableStateOf(1f) }
    var isPaused by remember { mutableStateOf(false) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
    )

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
            progress = timeLeft / 18f
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = "Интервалы",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Timer Circle
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(100.dp))
        ) {
            CircularProgressIndicator(
                progress = 1f - animatedProgress, // Progress animates from right to left
                color = Color(0xFF9C27B0),
                strokeWidth = 8.dp,
                modifier = Modifier.size(200.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = String.format("00:%02d", timeLeft),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9C27B0)
                )
                Text(
                    text = "Ускорение",
                    fontSize = 16.sp,
                    color = Color(0xFF9C27B0)
                )
            }
        }

        // Info Boxes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp, 60.dp)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Repeats", fontSize = 12.sp)
                    Text(text = "5/10", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            Box(
                modifier = Modifier
                    .size(100.dp, 60.dp)
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Next", fontSize = 12.sp)
                    Text(text = "Замедление", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Pause Button
        if (!isPaused) {
            Button(
                onClick = { isPaused = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("PAUSE", color = Color.White, fontWeight = FontWeight.Bold)
            }
        } else {
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { isPaused = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("RESUME", color = Color.White, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = { navController?.navigate("main") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("EXIT", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerCircuitPreview() {
    TimerCircuit(navController = rememberNavController())
}
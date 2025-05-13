package com.example.workoutapp


// Necessary imports
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

// Main Composable function
@Composable
fun WorkoutsEmpty(onAddClick: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Workouts",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(Color.Black, shape = CircleShape)
                    .clickable { onAddClick() },
                contentAlignment = Alignment.Center
            ) {
                Text("+", fontSize = 36.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = "Add your first workout", fontSize = 16.sp, color = Color.Black)

            Spacer(modifier = Modifier.weight(2f))
        }
}

// Preview function
@Preview(showBackground = true)
@Composable
fun PreviewWorkoutsEmpty() {
    val navController = rememberNavController()
    WorkoutsEmpty(onAddClick = {
        navController.navigate("newExercise")
    }
    )
}

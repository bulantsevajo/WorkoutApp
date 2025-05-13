import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workoutapp.NewExercise
import com.example.workoutapp.TimerCircuit
import com.example.workoutapp.WorkoutsEmpty

@Composable
fun Workout(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Workouts",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Бег",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = { navController.navigate("timer") },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text(text = "Start", color = Color.White)
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    text = "3 Circuits",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .padding(bottom = 8.dp)
                )

                if (expanded) {
                    ExerciseItem("Разминка, 10:00", "1 Exercise, No Repeats", navController)
                    ExerciseItem("Интервалы, 40:00", "2 Exercises, 10 Repeats", navController)
                    ExerciseItem("Cool Down, 10:00", "1 Exercise, No Repeats", navController)
                }
            }
        }

        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.fillMaxSize()
        ) {
            FloatingActionButton(
                onClick = { navController.navigate("newExercise") },
                shape = CircleShape,
                containerColor = Color.Black,
                modifier = Modifier
                    .padding(16.dp)
                    .size(56.dp)
            ) {
                Text(text = "+", color = Color.White, fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun ExerciseItem(title: String, subtitle: String, navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = subtitle, fontSize = 14.sp, color = Color.Gray)
        }
        Button(
            onClick = { navController.navigate("timer") },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(text = "Start", color = Color.White)
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val hasWorkouts = remember { mutableStateOf(false) }

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            if (hasWorkouts.value) {
                Workout(navController)
            } else {
                WorkoutsEmpty(onAddClick = {
                    navController.navigate("newExercise")
                    hasWorkouts.value = true
                })
            }
        }
        composable("timer") { TimerCircuit() }
        composable("newExercise") { NewExercise(navController) }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWorkout() {
    MainScreen()
}
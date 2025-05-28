import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore

data class WorkoutData(
    val title: String,
    val circuits: List<Pair<String, String>>,
    var expanded: Boolean = false
)


@Composable
fun Workout(navController: NavController) {
    val workouts = remember {
        mutableStateListOf(
            WorkoutData(
                title = "Бег",
                circuits = listOf(
                    "Разминка, 10:00" to "1 Exercise, No Repeats",
                    "Интервалы, 40:00" to "2 Exercises, 10 Repeats",
                    "Cool Down, 10:00" to "1 Exercise, No Repeats"
                )
            )
        )
    }

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

        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(workouts) { index, workout ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F4F4)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = workout.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Button(
                                onClick = { navController.navigate("timer") },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text("Start", color = Color.White)
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { workouts[index] = workout.copy(expanded = !workout.expanded) },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${workout.circuits.size} Circuits",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = if (workout.expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null
                            )
                        }

                        if (workout.expanded) {
                            Spacer(Modifier.height(8.dp))
                            workout.circuits.forEach { (title, subtitle) ->
                                ExerciseItem(title, subtitle, navController)
                            }
                        }
                    }
                }
            }
        }

        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.fillMaxWidth()
        ) {
            FloatingActionButton(
                onClick = { navController.navigate("newExercise") },
                shape = CircleShape,
                containerColor = Color.Black,
                modifier = Modifier
                    .size(72.dp), // крупный размер
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp) // крупный плюс
                )
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

    val hasWorkouts = remember { mutableStateOf(true) }

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
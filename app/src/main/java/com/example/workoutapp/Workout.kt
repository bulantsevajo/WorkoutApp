package com.example.workoutapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import kotlinx.coroutines.launch
import kotlin.math.abs
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen() {
    val items = remember {
        mutableStateListOf(
            "Разминка, 10:00" to "1 Exercise, No Repeats",
            "Интервалы, 40:00" to "2 Exercises, 10 Repeats",
            "Заминка, 10:00" to "1 Exercise, No Repeats"
        )
    }

    val draggedItemIndex = remember { mutableStateOf<Int?>(null) }
    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }
    var indexToDelete by remember { mutableStateOf<Int?>(null) }

    if (showSheet && indexToDelete != null) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = "Delete Circuit",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clickable {
                            items.removeAt(indexToDelete!!)
                            showSheet = false
                        }
                )
                Divider()
                Text(
                    text = "Cancel",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clickable { showSheet = false }
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Menu, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = {}) {
                        Text("Done", color = Color.Black)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}, containerColor = Color.Black) {
                Icon(Icons.Default.Menu, contentDescription = "Add", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TextField(
                value = "Бег", onValueChange = {}, modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )
            Spacer(Modifier.height(16.dp))

            LazyColumn {
                itemsIndexed(items, key = { _, item -> item.first }) { index, (title, subtitle) ->
                    var offsetX by remember { mutableStateOf(0f) }
                    val isDragging = draggedItemIndex.value == index
                    val swipeThreshold = 100f

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                    ) {
                        // Delete icon background
                        if (offsetX < -40f) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(Color(0xFFF5F5F5)),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    modifier = Modifier.padding(end = 16.dp),
                                    tint = Color.Black
                                )
                            }
                        }

                        Card(
                            modifier = Modifier
                                .offset { IntOffset(offsetX.toInt(), 0) }
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .graphicsLayer { alpha = if (isDragging) 0.5f else 1f }
                                .pointerInput(Unit) {
                                    detectDragGestures(
                                        onDragEnd = {
                                            if (offsetX < -swipeThreshold) {
                                                indexToDelete = index
                                                showSheet = true
                                            }
                                            offsetX = 0f
                                        },
                                        onDragCancel = {
                                            offsetX = 0f
                                        },
                                        onDrag = { change, dragAmount ->
                                            change.consume()
                                            offsetX = (offsetX + dragAmount.x).coerceAtMost(0f)
                                        }
                                    )
                                }
                                .pointerInput(Unit) {
                                    detectDragGesturesAfterLongPress(
                                        onDragStart = { draggedItemIndex.value = index },
                                        onDragEnd = { draggedItemIndex.value = null },
                                        onDragCancel = { draggedItemIndex.value = null },
                                        onDrag = { _, dragAmount ->
                                            val targetIndex = (index + (dragAmount.y / 100).toInt())
                                                .coerceIn(0, items.lastIndex)
                                            if (targetIndex != index) {
                                                scope.launch {
                                                    val movedItem = items.removeAt(index)
                                                    items.add(targetIndex, movedItem)
                                                    draggedItemIndex.value = targetIndex
                                                }
                                            }
                                        }
                                    )
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Drag",
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    Text(subtitle, fontSize = 14.sp, color = Color.Gray)
                                }
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Go",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWorkout() {
    WorkoutScreen()
}

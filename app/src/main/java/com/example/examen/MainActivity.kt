package com.example.examen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen.ui.theme.ExamenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenTheme {
                TaskInterface()
            }
        }
    }
}

data class Task(
    val title: String,
    var hecha: Boolean = false
)

@Composable
fun TaskInterface(modifier: Modifier = Modifier) {
    var newTaskTitle by remember { mutableStateOf("") }
    var taskList by remember { mutableStateOf(listOf<Task>()) }
    var filterOption by remember { mutableStateOf(FilterOption.ALL) } // Ajouter l'état du filtre
    var languageOption by remember { mutableStateOf(LanguageOption.SPANISH) } // État de la langue

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (languageOption == LanguageOption.SPANISH) "Lista de tareas" else "Task List",
                fontSize = 32.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            TextField(
                value = newTaskTitle,
                onValueChange = { if (it.length <= 50) newTaskTitle = it },
                label = { Text(if (languageOption == LanguageOption.SPANISH) "Nueva tarea" else "New task") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            Button(
                onClick = {
                    if (newTaskTitle.isNotBlank()) {
                        val newTask = Task(title = newTaskTitle)
                        taskList = taskList + newTask
                        newTaskTitle = ""
                    }
                },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(if (languageOption == LanguageOption.SPANISH) "Añadir tarea" else "Add task")
            }

            Row(modifier = Modifier.padding(bottom = 16.dp)) {
                Button(
                    onClick = { filterOption = FilterOption.UNDONE },
                    colors = if (filterOption == FilterOption.UNDONE) ButtonDefaults.buttonColors(containerColor = Color.Gray) else ButtonDefaults.buttonColors()
                ) {
                    Text(if (languageOption == LanguageOption.SPANISH) "Tareas no hechas" else "Undone tasks")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { filterOption = FilterOption.DONE },
                    colors = if (filterOption == FilterOption.DONE) ButtonDefaults.buttonColors(containerColor = Color.Gray) else ButtonDefaults.buttonColors()
                ) {
                    Text(if (languageOption == LanguageOption.SPANISH) "Tareas hechas" else "Done tasks")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { filterOption = FilterOption.ALL },
                    colors = if (filterOption == FilterOption.ALL) ButtonDefaults.buttonColors(containerColor = Color.Gray) else ButtonDefaults.buttonColors()
                ) {
                    Text(if (languageOption == LanguageOption.SPANISH) "Todas tareas" else "All tasks")
                }
            }

            // Bouton pour changer la langue
            Button(
                onClick = {
                    languageOption = if (languageOption == LanguageOption.SPANISH) LanguageOption.ENGLISH else LanguageOption.SPANISH
                },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(if (languageOption == LanguageOption.SPANISH) "English" else "Español")
            }
            val context = LocalContext.current
            Button(
                onClick = {
                    val intent = Intent(context, PantallaInicio::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.home), fontSize = 20.sp)
            }

            // Filtrer les tâches en fonction de l'option sélectionnée
            val filteredTasks = when (filterOption) {
                FilterOption.UNDONE -> taskList.filter { !it.hecha }
                FilterOption.DONE -> taskList.filter { it.hecha }
                FilterOption.ALL -> taskList.sortedBy { it.hecha } // Trier pour que les non effectuées soient en haut
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredTasks) { task ->
                    TaskItem(
                        task = task,
                        onDone = {
                            taskList = taskList.map {
                                if (it == task) it.copy(hecha = !it.hecha) else it
                            }
                        },
                        onDelete = {
                            taskList = taskList.filter { it != task }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
fun TaskItem(task: Task, onDone: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = task.title,
            modifier = Modifier.weight(1f),
            fontSize = 20.sp
        )

        Button(
            onClick = onDone,
            colors = if (task.hecha) ButtonDefaults.buttonColors(containerColor = Color.Green)
            else ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text(if (task.hecha) "OK" else "TODO")
        }

        Spacer(modifier = Modifier.width(8.dp)) // Espace entre les boutons

        Button(
            onClick = onDelete,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(if (task.hecha) "Eliminar" else "Delete")
        }
    }
}

enum class FilterOption {
    UNDONE, DONE, ALL
}

enum class LanguageOption {
    SPANISH, ENGLISH
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExamenTheme {
        TaskInterface()
    }
}

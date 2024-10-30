package com.example.examen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.examen.ui.theme.ExamenTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

class ExTresLista : ComponentActivity() {
    private val tareas = mutableListOf<Tarea>() // Liste des tâches

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenTheme {
                ListaTareasScreen(tareas) { tarea ->
                    val intent = Intent(this, ExTresDesc::class.java)
                    intent.putExtra("nombre", tarea.nombre)
                    intent.putExtra("descripcion", tarea.descripcion)
                    intent.putExtra("fecha", tarea.fecha)
                    intent.putExtra("coste", tarea.coste)
                    intent.putExtra("prioridad", tarea.prioridad)
                    startActivity(intent)
                }
            }
        }

        val tarea = intent?.extras?.let {
            Tarea(
                nombre = it.getString("nombre", ""),
                descripcion = it.getString("descripcion", ""),
                fecha = it.getString("fecha", ""),
                coste = it.getString("coste", "0"),
                prioridad = it.getString("prioridad", "1")
            )
        }
        tarea?.let { tareas.add(it) }
    }
}

@Composable
fun ListaTareasScreen(tareas: List<Tarea>, onTareaClick: (Tarea) -> Unit) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Button(
            onClick = {
                val intent = Intent(context, PantallaInicio::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = stringResource(R.string.home), fontSize = 20.sp)
        }

        Button(
            onClick = {
                val intent = Intent(context, ExTres::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(text = stringResource(R.string.Ex3), fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Liste des tâches
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tareas) { tarea ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTareaClick(tarea) }
                        .padding(16.dp)
                ) {
                    Text(text = tarea.nombre)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListaTareasScreenPreview() {
    ExamenTheme {
        ListaTareasScreen(listOf()) {}
    }
}

data class Tarea(
    val nombre: String,
    val descripcion: String,
    val fecha: String,
    val coste: String,
    val prioridad: String
)

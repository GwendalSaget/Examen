package com.example.examen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.examen.ui.theme.ExamenTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

class ExTres : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenTheme {
                NuevaTareaScreen()
            }
        }
    }
}

@Composable
fun NuevaTareaScreen() {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var coste by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = coste,
            onValueChange = { coste = it.filter { it.isDigit() || it == '.' } },
            label = { Text("Coste") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = prioridad,
            onValueChange = { prioridad = it.filter { it.isDigit() } },
            label = { Text("Prioridad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton pour créer une tâche
        Button(
            onClick = {
                if (nombre.isNotBlank() && descripcion.isNotBlank() && fecha.isNotBlank() && coste.isNotBlank() && prioridad.isNotBlank()) {
                    val intent = Intent(context, ExTresLista::class.java).apply {
                        putExtra("nombre", nombre)
                        putExtra("descripcion", descripcion)
                        putExtra("fecha", fecha)
                        putExtra("coste", coste)
                        putExtra("prioridad", prioridad)
                    }
                    context.startActivity(intent)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear tarea")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, PantallaInicio::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.home), fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, ExTresLista::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.Ex3Lista), fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NuevaTareaScreenPreview() {
    ExamenTheme {
        NuevaTareaScreen()
    }
}

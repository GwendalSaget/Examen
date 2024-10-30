package com.example.examen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen.ui.theme.ExamenTheme
import androidx.compose.ui.platform.LocalContext

class ExTresDesc : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenTheme {
                val nombre = intent.getStringExtra("nombre") ?: ""
                val descripcion = intent.getStringExtra("descripcion") ?: ""
                val fecha = intent.getStringExtra("fecha") ?: ""
                val coste = intent.getStringExtra("coste") ?: "0"
                val prioridad = intent.getStringExtra("prioridad") ?: "1"

                TareaDetallesScreen(
                    nombre = nombre,
                    descripcion = descripcion,
                    fecha = fecha,
                    coste = coste,
                    prioridad = prioridad
                )
            }
        }
    }
}

@Composable
fun TareaDetallesScreen(
    nombre: String,
    descripcion: String,
    fecha: String,
    coste: String,
    prioridad: String
) {
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Nombre: $nombre")
        Text(text = "Descripción: $descripcion")
        Text(text = "Fecha: $fecha")
        Text(text = "Coste: $coste")
        Text(text = "Prioridad: $prioridad")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, ExTresLista::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            Text(text = stringResource(R.string.Ex3Lista), fontSize = 20.sp)
        }

        Button(
            onClick = {
                val intent = Intent(context, ExTres::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            Text(text = stringResource(R.string.Ex3), fontSize = 20.sp)
        }

        Button(
            onClick = {
                val intent = Intent(context, PantallaInicio::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.home), fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TareaDetallesScreenPreview() {
    ExamenTheme {
        TareaDetallesScreen(
            nombre = "Tarea 1",
            descripcion = "Descripción de la tarea",
            fecha = "01/01/2024",
            coste = "100",
            prioridad = "Alta"
        )
    }
}

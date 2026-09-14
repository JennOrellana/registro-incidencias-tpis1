package sv.edu.utec.etps1.registroincidencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sv.edu.utec.etps1.registroincidencias.ui.theme.RegistroIncidenciasTheme

//PRIMER AVANCE DE INVESTIGACIÓN Y EVIDENCIA TÉCNICA DEL PROYECTO
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistroIncidenciasTheme {
                RegistroIncidenciasApp()
            }
        }
    }
}

@Composable
fun RegistroIncidenciasApp() {
    // 1. VARIABLES DE MEMORIA (ESTADO DE LA INTERFAZ)
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("Aún no hay reporte creado") }

    // 2. DISEÑO VISIBLE (COLUMNA REACTIVA)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título de la app e instrucción
        Text(
            text = "Registro de Incidencias Utec",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Por favor, detalle el problema técnico detectado en las instalaciones.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Campo para capturar el Título de la Incidencia
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título de incidencia") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo para capturar la Descripción de la Incidencia
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botón de acción principal
        Button(
            onClick = {
                // Al interactuar, el estado del mensaje cambia recordando lo que el usuario escribió
                mensaje = "Reporte preparado: $titulo"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear reporte")
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Zona de Retroalimentación dinámica
        Text(
            text = "Estado del Reporte:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(6.dp))

        // Este texto se redibuja automáticamente gracias al cambio de estado
        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroIncidenciasPreview() {
    RegistroIncidenciasTheme {
        RegistroIncidenciasApp()
    }
}


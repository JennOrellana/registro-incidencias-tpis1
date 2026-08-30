package sv.edu.utec.etps1.registroincidencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sv.edu.utec.etps1.registroincidencias.ui.theme.RegistroIncidenciasTheme

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. TÍTULO PROPIO DE LA NECESIDAD
        Text(
            text = "Reserva de Cubículos Utec",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(12.dp))

        // 2. TEXTO DESCRIPTIVO DE LA APLICACIÓN
        Text(
            text = "Gestiona y aparta tus espacios de estudio individual o grupal " +
                    "en la biblioteca central para tus proyectos académicos."
        )
        Spacer(modifier = Modifier.height(24.dp))

        // 3. MODIFICACIÓN DEL ESTADO INICIAL Y MENSAJE DE LA TARJETA
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Disponibilidad de hoy",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = "No tienes ninguna reserva activa programada para este día.")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // 4. CAMBIO EN EL BOTÓN Y SEGUNDA LÍNEA DE TEXTO DE LA VERSIÓN
        Button(onClick = { /* Se implementará en una unidad posterior */ }) {
            Text(text = "Solicitar espacio")
        }
        Spacer(modifier = Modifier.height(12.dp)) // Espacio estético para separar el texto del botón
        Text(
            text = "Prototipo inicial — Unidad 1",
            style = MaterialTheme.typography.bodySmall
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

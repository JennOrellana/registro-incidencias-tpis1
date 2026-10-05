package sv.edu.utec.etps1.registroincidencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sv.edu.utec.etps1.registroincidencias.ui.theme.RegistroIncidenciasTheme

// ACTIVIDAD EVALUADA: TECLADO Y PANTALLA TÁCTIL EN REGISTROINCIDENCIAS - SEMANA 10
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
    var prioridad by remember { mutableStateOf("Ninguna") } // Nuevo estado para interacción táctil
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

        // REQUERIMIENTO B: Campo configurado con KeyboardOptions (Teclado Contextual)
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título de incidencia") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences, // Mayúscula al iniciar frases
                imeAction = ImeAction.Next // Acción IME coherente para avanzar de campo
            )
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

        // REQUERIMIENTO C: Incorporación de Interacción Táctil de Alto Nivel (Prioridad)
        Text(
            text = "Seleccione la Prioridad (Interacción Táctil):",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Tarjeta de Prioridad Alta
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { prioridad = "Alta" }, // Modificador de alto nivel para toques
                colors = CardDefaults.cardColors(
                    containerColor = if (prioridad == "Alta") Color(0xFFFADBD8) else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = "🔴 Alta",
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Tarjeta de Prioridad Media
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { prioridad = "Media" },
                colors = CardDefaults.cardColors(
                    containerColor = if (prioridad == "Media") Color(0xFFFCF3CF) else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = "🟡 Media",
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Tarjeta de Prioridad Baja
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { prioridad = "Baja" },
                colors = CardDefaults.cardColors(
                    containerColor = if (prioridad == "Baja") Color(0xFFD5F5E3) else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = "🟢 Baja",
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de acción principal
        Button(
            onClick = {
                // Modificado para capturar también la prioridad seleccionada
                mensaje = "Reporte preparado: $titulo [Prioridad: $prioridad]"
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

        // Muestra retroalimentación tanto de la prioridad seleccionada al tocar como del mensaje final
        Text(
            text = "Prioridad seleccionada en pantalla: $prioridad\n$mensaje",
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

package sv.edu.utec.etps1.registroincidencias

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sv.edu.utec.etps1.registroincidencias.ui.theme.RegistroIncidenciasTheme
import kotlin.math.sqrt

// ACTIVIDAD EVALUADA: SEGUNDO AVANCE DE INVESTIGACIÓN Y EVIDENCIA TÉCNICA CON SENSOR - SEMANA 11
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
    val context = LocalContext.current

    // Variables de estado del formulario (Semana 10)
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf("Ninguna") }
    var mensaje by remember { mutableStateOf("Aún no hay reporte creado") }

    // Variables de estado del sensor (Semana 11)
    val sensorService = remember { context.getSystemService(Context.SENSOR_SERVICE) }
    val sensorManager = remember { sensorService as? SensorManager }
    val acelerometro = remember { sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }

    var sensorDisponible by remember { mutableStateOf(acelerometro != null) }
    var mensajeSensor by remember { mutableStateOf(if (acelerometro != null) "Sensor listo. Agita suavemente para limpiar el formulario." else "Acelerómetro no disponible en este entorno.") }

    // Control del ciclo de vida del hardware mediante DisposableEffect aislado
    if (sensorManager != null && acelerometro != null) {
        DisposableEffect(acelerometro) {
            val listener = object : SensorEventListener {
                private var ultimoCalculo: Long = 0
                private var ultimaX = 0f
                private var ultimaY = 0f
                private var ultimaZ = 0f
                private val UMBRAL_AGITACION = 12

                override fun onSensorChanged(event: SensorEvent) {
                    val tiempoActual = System.currentTimeMillis()
                    if ((tiempoActual - ultimoCalculo) > 100) {
                        val diferenciaTiempo = tiempoActual - ultimoCalculo
                        ultimoCalculo = tiempoActual

                        val x = event.values[0]
                        val y = event.values[1]
                        val z = event.values[2]

                        val velocidad = sqrt((x - ultimaX) * (x - ultimaX) + (y - ultimaY) * (y - ultimaY) + (z - ultimaZ) * (z - ultimaZ)) / diferenciaTiempo * 10000

                        if (velocidad > UMBRAL_AGITACION) {
                            // Gesto de agitación detectado: Restablecer interfaz de forma reactiva
                            titulo = ""
                            descripcion = ""
                            prioridad = "Ninguna"
                            mensaje = "Aún no hay reporte creado"
                            mensajeSensor = "¡Formulario restablecido mediante el acelerómetro!"
                        }
                        ultimaX = x
                        ultimaY = y
                        ultimaZ = z
                    }
                }
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }
            sensorManager.registerListener(listener, acelerometro, SensorManager.SENSOR_DELAY_NORMAL)
            onDispose { sensorManager.unregisterListener(listener) }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Registro de Incidencias Utec",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Por favor, detalle el problema técnico detectado en las instalaciones.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título de incidencia") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Seleccione la Prioridad (Interacción Táctil):",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f).clickable { prioridad = "Alta" },
                colors = CardDefaults.cardColors(
                    containerColor = if (prioridad == "Alta") Color(0xFFFADBD8) else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(text = "🔴 Alta", modifier = Modifier.padding(12.dp).align(Alignment.CenterHorizontally), style = MaterialTheme.typography.bodyMedium)
            }

            Card(
                modifier = Modifier.weight(1f).clickable { prioridad = "Media" },
                colors = CardDefaults.cardColors(
                    containerColor = if (prioridad == "Media") Color(0xFFFCF3CF) else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(text = "🟡 Media", modifier = Modifier.padding(12.dp).align(Alignment.CenterHorizontally), style = MaterialTheme.typography.bodyMedium)
            }

            Card(
                modifier = Modifier.weight(1f).clickable { prioridad = "Baja" },
                colors = CardDefaults.cardColors(
                    containerColor = if (prioridad == "Baja") Color(0xFFD5F5E3) else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(text = "🟢 Baja", modifier = Modifier.padding(12.dp).align(Alignment.CenterHorizontally), style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { mensaje = "Reporte preparado: $titulo [Prioridad: $prioridad]" },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear reporte")
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Estado del Reporte:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Prioridad seleccionada en pantalla: $prioridad\n$mensaje",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // PANEL DE TELEMETRÍA DEL SENSOR (REQUERIMIENTO OBLIGATORIO SEMANA 11)
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (sensorDisponible) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.errorContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "⚙️ Telemetría de Sensores",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (sensorDisponible) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onErrorContainer
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = mensajeSensor,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (sensorDisponible) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroIncidenciasPreview() {
    RegistroIncidenciasTheme {
        RegistroIncidenciasApp()
    }
}

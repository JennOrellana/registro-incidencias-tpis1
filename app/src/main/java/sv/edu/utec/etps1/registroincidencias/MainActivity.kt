package sv.edu.utec.etps1.registroincidencias

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel // Importación agregada
import sv.edu.utec.etps1.registroincidencias.ui.theme.RegistroIncidenciasTheme
import kotlin.math.sqrt

// ACTIVIDAD EVALUADA: INSTRUMENTACIÓN DEL CICLO DE VÍA CON LOGCAT - SEMANA 14
class MainActivity : ComponentActivity() {

    private val tag = "TPIS1_LIFECYCLE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate: Instanciando la Activity y construyendo el árbol de Compose.")
        setContent {
            RegistroIncidenciasTheme {
                RegistroIncidenciasApp()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart: La interfaz gráfica pasa a ser visible para el usuario de la UTEC.")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume: La Activity posee el foco interactivo y el acelerómetro está operativo.")
    }

    override fun onPause() {
        Log.d(tag, "onPause: Perdiendo el foco táctil de la pantalla. Transición inminente a segundo plano.")
        super.onPause()
    }

    override fun onStop() {
        Log.d(tag, "onStop: La aplicación ha dejado de ser visible por completo en el terminal.")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(tag, "onDestroy: Liberando memoria y destruyendo la instancia de la MainActivity.")
        super.onDestroy()
    }
}

@Composable
fun RegistroIncidenciasApp(vm: IncidenciaViewModel = viewModel()) {
    val context = LocalContext.current

    // Variables de estado locales que NO están en el ViewModel
    var descripcion by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("Aún no hay reporte creado") }

    // Variables de estado del sensor (Semana 11)
    val sensorService = remember { context.getSystemService(Context.SENSOR_SERVICE) }
    val sensorManager = remember { sensorService as? SensorManager }
    val acelerometro = remember { sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }

    var sensorDisponible by remember { mutableStateOf(acelerometro != null) }
    var mensajeSensor by remember { mutableStateOf(if (acelerometro != null) "Sensor listo. Agita suavemente para limpiar el formulario." else "Acelerómetro no disponible en este entorno.") }

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
                            //   vm.actualizarTitulo("") // Actualiza mediante ViewModel
                            //  descripcion = ""
                            //  vm.actualizarPrioridad("Ninguna") // Actualiza mediante ViewModel
                            // mensaje = "Aún no hay reporte creado"
                            // mensajeSensor = "¡Formulario restablecido mediante el acelerómetro!"
                        }
                        ultimaX = x
                        ultimaY = y
                        ultimaZ = z
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            sensorManager.registerListener(listener, acelerometro, SensorManager.SENSOR_DELAY_NORMAL)

            onDispose {
                sensorManager.unregisterListener(listener)
            }
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
            value = vm.titulo, // Conectado al ViewModel
            onValueChange = { vm.actualizarTitulo(it) }, // Conectado al ViewModel
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
                modifier = Modifier
                    .weight(1f)
                    .clickable { vm.actualizarPrioridad("Alta") },
                colors = CardDefaults.cardColors(
                    containerColor = if (vm.prioridad == "Alta") Color(0xFFFADBD8) else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(text = "🔴 Alta", modifier = Modifier.padding(12.dp).align(Alignment.CenterHorizontally), style = MaterialTheme.typography.bodyMedium)
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { vm.actualizarPrioridad("Media") },
                colors = CardDefaults.cardColors(
                    containerColor = if (vm.prioridad == "Media") Color(0xFFFCF3CF) else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(text = "🟡 Media", modifier = Modifier.padding(12.dp).align(Alignment.CenterHorizontally), style = MaterialTheme.typography.bodyMedium)
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { vm.actualizarPrioridad("Baja") },
                colors = CardDefaults.cardColors(
                    containerColor = if (vm.prioridad == "Baja") Color(0xFFD5F5E3) else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(text = "🟢 Baja", modifier = Modifier.padding(12.dp).align(Alignment.CenterHorizontally), style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { mensaje = "Reporte preparado: ${vm.titulo} [Prioridad: ${vm.prioridad}]" },
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
            text = "Prioridad seleccionada en pantalla: ${vm.prioridad}\n$mensaje",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(24.dp))

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
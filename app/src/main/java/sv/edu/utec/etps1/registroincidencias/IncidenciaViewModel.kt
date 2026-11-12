package sv.edu.utec.etps1.registroincidencias

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class IncidenciaViewModel : ViewModel() {

    // Estado para el título
    var titulo by mutableStateOf("")
        private set

    fun actualizarTitulo(valor: String) {
        titulo = valor
    }

    // Estado para la prioridad
    var prioridad by mutableStateOf("Baja")
        private set

    fun actualizarPrioridad(valor: String) {
        prioridad = valor
    }
}
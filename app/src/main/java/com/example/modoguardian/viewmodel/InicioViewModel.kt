package com.example.modoguardian.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CamaraUi(
    val id: String,
    val nombre: String,
    val zona: String,
    val enLinea: Boolean,
    val grabando: Boolean,
    val senal: Int
)

data class EventoUi(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val hora: String,
    val critico: Boolean,
    val confirmado: Boolean
)

data class InicioUiState(
    val camaras: List<CamaraUi> = listOf(
        CamaraUi("CAM-01", "Entrada Principal", "Perímetro Norte", true, true, 96),
        CamaraUi("CAM-02", "Patio de Bodega", "Zona Sur", true, true, 91),
        CamaraUi("CAM-03", "Acceso Vehicular", "Perímetro Sur", true, false, 88),
        CamaraUi("CAM-04", "Perímetro Norte", "Sector B", false, false, 0)
    ),
    val eventos: List<EventoUi> = listOf(
        EventoUi(
            id = "EV-1042",
            titulo = "Movimiento no autorizado",
            descripcion = "Perímetro Norte - Cámara 04",
            hora = "14:32",
            critico = true,
            confirmado = false
        ),
        EventoUi(
            id = "EV-1041",
            titulo = "Acceso autorizado",
            descripcion = "Entrada Principal - Cámara 01",
            hora = "14:18",
            critico = false,
            confirmado = true
        ),
        EventoUi(
            id = "EV-1040",
            titulo = "Cambio de turno registrado",
            descripcion = "Panel central",
            hora = "14:00",
            critico = false,
            confirmado = true
        )
    )
) {
    val totalCamaras: Int get() = camaras.size
    val camarasActivas: Int get() = camaras.count { it.enLinea }
    val camarasAtencion: Int get() = camaras.count { !it.enLinea }
}

class InicioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InicioUiState())
    val uiState: StateFlow<InicioUiState> = _uiState.asStateFlow()
}

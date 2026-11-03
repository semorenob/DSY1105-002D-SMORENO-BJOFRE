package com.example.modoguardian.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modoguardian.navigation.NavigationEvent
import com.example.modoguardian.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
   private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    // Expone channel como Flow de solo lectura
   val navigationEvents: SharedFlow<NavigationEvent> = _navigationEvents.asSharedFlow()

   // Emite evento de navegacion hacia la ruta deseada
   fun navigateTo(screen: Screen) {
       viewModelScope.launch {
           _navigationEvents.emit(NavigationEvent.NavigateTo(screen))
       }
   }

   // Volver atras
   fun navigateBack() {
       viewModelScope.launch {
           _navigationEvents.emit(NavigationEvent.PopBackStack)
       }
   }

   // Navegar arriba (padre)
   fun navigateUp() {
       viewModelScope.launch {
           _navigationEvents.emit(NavigationEvent.NavigateUp)
       }
   }
}
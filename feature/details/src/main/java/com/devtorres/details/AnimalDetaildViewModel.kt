package com.devtorres.details

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devtorres.data.respository.details.DetailsRepository
import com.devtorres.details.nav.AnimalDetailArgs
import com.devtorres.model.AnimalInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimalDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailRepository: DetailsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Idle)
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    private val _animalInfo = MutableStateFlow<AnimalInfo?>(null)
    val animalInfo: StateFlow<AnimalInfo?> = _animalInfo.asStateFlow()

    init {
        loadAnimalInfo(AnimalDetailArgs(savedStateHandle).animalId)
    }

    private fun loadAnimalInfo(id: String) {
        viewModelScope.launch {
            val info = detailRepository.getAnimalInfo(
                animalId = id,
                onStart = { _uiState.value = DetailsUiState.Loading },
                onComplete = { _uiState.value = DetailsUiState.Idle },
                onError = { _uiState.value = DetailsUiState.Error(it) }
            )

            Log.d("AnimalDetailsViewModel", "loadAnimalInfo: ${info}")

            info?.let {
                Log.d("AnimalDetailsViewModel", "entro: ${it}")
                _animalInfo.value = it
            }
        }
    }
}

@Stable
sealed interface DetailsUiState {

    data object Idle : DetailsUiState

    data object Loading : DetailsUiState

    data class Error(val message: String?) : DetailsUiState
}
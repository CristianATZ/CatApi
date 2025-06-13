package com.devtorres.home

import android.util.Log
import androidx.annotation.MainThread
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devtorres.data.respository.home.HomeRepository
import com.devtorres.model.Animal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState : MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val pageFetchingIndex : MutableStateFlow<Int> = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val animalList: StateFlow<List<Animal>> = pageFetchingIndex.flatMapLatest { page ->
        Log.d("HomeRepositoryImpl", "inicio list")
        homeRepository.fetchAnimalList(
            page = page,
            breedIds = "",
            categoryIds = "",
            onStart = { _uiState.update { HomeUiState.Loading } },
            onComplete = { _uiState.update { HomeUiState.Idle } },
            onError = { error ->
                _uiState.update { HomeUiState.Error(error) }
            }
        )
    }.scan(emptyList<Animal>()) { oldList, newList ->
        oldList + newList
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    @MainThread
    fun fetchNextAnimalPage() {
        if(_uiState.value != HomeUiState.Loading) {
            pageFetchingIndex.value++
        }
    }
}

@Stable
sealed interface HomeUiState {

    data object Idle : HomeUiState

    data object Loading : HomeUiState

    data class Error(val message: String?) : HomeUiState
}
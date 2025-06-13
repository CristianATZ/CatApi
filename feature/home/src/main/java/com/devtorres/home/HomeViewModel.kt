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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState : MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _filter : MutableStateFlow<AnimalFilter> = MutableStateFlow(
        AnimalFilter(
            breeds = emptySet(),
            category = ""
        )
    )
    val filter: StateFlow<AnimalFilter> = _filter.asStateFlow()

    private val pageFetchingIndex : MutableStateFlow<Int> = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    // Este flujo emite nuevos datos según el filtro y la página actual
    private val paginatedDataFlow: Flow<List<Animal>> = combine(
        _filter, pageFetchingIndex
    ) { filter, page ->
        filter to page
    }.flatMapLatest { (filter, page) ->
        if(page == -1) {
            pageFetchingIndex.value = 0
            flowOf(emptyList())
        } else {
            homeRepository.fetchAnimalList(
                page = page,
                breedIds = filter.breeds.joinToString(","),
                categoryIds = filter.category,
                onStart = { _uiState.update { HomeUiState.Loading } },
                onComplete = { _uiState.update { HomeUiState.Idle } },
                onError = { error -> _uiState.update { HomeUiState.Error(error) } }
            )
        }
    }.scan(emptyList<Animal>()) { oldList, newList ->
        if (pageFetchingIndex.value == 0) {
            newList
        } else {
            oldList + newList.filter { animal ->
                animal.id !in oldList.map { it.id }
            }
        }
    }


    // Este flujo observa los cambios de filtro y reinicia la lista cuando cambian
    @OptIn(ExperimentalCoroutinesApi::class)
    val animalList: StateFlow<List<Animal>> = paginatedDataFlow.stateIn(
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

    @MainThread
    fun updateFilters(newFilters: AnimalFilter) {
        viewModelScope.launch {
            if(newFilters != _filter.value) {
                pageFetchingIndex.value = -1
            }
            _filter.update { current ->
                newFilters
            }
        }
    }
}

@Stable
sealed interface HomeUiState {

    data object Idle : HomeUiState

    data object Loading : HomeUiState

    data class Error(val message: String?) : HomeUiState
}

data class AnimalFilter(
    val breeds: Set<String>,
    val category: String
)
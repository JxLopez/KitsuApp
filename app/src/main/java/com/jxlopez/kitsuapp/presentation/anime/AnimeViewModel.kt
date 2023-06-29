package com.jxlopez.kitsuapp.presentation.anime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxlopez.kitsuapp.data.Status
import com.jxlopez.kitsuapp.domain.usecase.GetAnimeUseCase
import com.jxlopez.kitsuapp.domain.usecase.GetAnimesPaginatedUseCase
import com.jxlopez.kitsuapp.domain.usecase.GetSearchAnimeUseCase
import com.jxlopez.kitsuapp.model.AnimeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val getAnimeUseCase: GetAnimeUseCase,
    private val getSearchAnimeUseCase: GetSearchAnimeUseCase,
    private val getAnimesPaginatedUseCase: GetAnimesPaginatedUseCase
) : ViewModel() {
    private val _viewState = MutableLiveData<AnimeViewState>()
    fun getViewState() = _viewState
    val pageLimit = MutableLiveData(10)
    val offset = MutableLiveData(0)
    val maxOffset = MutableLiveData(0)
    val searching = MutableLiveData(false)
    private val listAnimes = MutableLiveData<ArrayList<AnimeItem>>(arrayListOf())

    init {
        getAnimes()
    }

    fun getAnimes() {
        maxOffset.value = 0
        offset.value = 0
        searching.value = false
        viewModelScope.launch {
            getAnimeUseCase().collect {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { data ->
                            _viewState.value = AnimeViewState.AnimeList(data.anime)
                            maxOffset.value = data.maxItems
                            offset.value = offset.value?.plus(10)
                            listAnimes.value?.clear()
                            listAnimes.value?.addAll(data.anime.toTypedArray())
                        }
                    }
                    Status.ERROR -> _viewState.value = AnimeViewState.Error(it.error?.errorMessage ?: "")
                    Status.LOADING -> _viewState.value = AnimeViewState.Loading
                }
            }
        }
    }

    fun getAnimesPaginatedUseCase() {
        viewModelScope.launch {
            getAnimesPaginatedUseCase(pageLimit.value?: 10, offset.value ?: 0).collect {
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { list ->
                            offset.value = offset.value?.plus(10)
                            listAnimes.value?.addAll(list.toTypedArray())
                            _viewState.value = AnimeViewState.AnimeListPaginated(listAnimes.value?.toList() ?: listOf())
                        }
                    }
                    Status.ERROR -> _viewState.value = AnimeViewState.Error(it.error?.errorMessage ?: "")
                    Status.LOADING -> _viewState.value = AnimeViewState.Loading
                }
            }
        }
    }

    fun searchAnimes(query: String) {
        viewModelScope.launch {
            getSearchAnimeUseCase(query).collect {
                searching.value = true
                when(it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { list ->
                            _viewState.value = AnimeViewState.AnimeList(list)
                        }
                    }
                    Status.ERROR -> _viewState.value = AnimeViewState.Error(it.error?.errorMessage ?: "")
                    Status.LOADING -> _viewState.value = AnimeViewState.Loading
                }
            }
        }
    }

}
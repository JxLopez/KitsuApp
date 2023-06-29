package com.jxlopez.kitsuapp.presentation.anime.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jxlopez.kitsuapp.data.Status
import com.jxlopez.kitsuapp.domain.usecase.GetAnimeByIdUseCase
import com.jxlopez.kitsuapp.domain.usecase.GetCompanyByProductionIdUseCase
import com.jxlopez.kitsuapp.domain.usecase.GetPersonsByStaffId
import com.jxlopez.kitsuapp.domain.usecase.GetProductionsByAnimeIdUseCase
import com.jxlopez.kitsuapp.domain.usecase.GetStaffByAnimeIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val getAnimeByIdUseCase: GetAnimeByIdUseCase,
    private val getProductionsByAnimeIdUseCase: GetProductionsByAnimeIdUseCase,
    private val getCompanyByProductionIdUseCase: GetCompanyByProductionIdUseCase,
    private val getStaffByAnimeIdUseCase: GetStaffByAnimeIdUseCase,
    private val getPersonsByStaffId: GetPersonsByStaffId
): ViewModel() {
    private val _viewState = MutableLiveData<AnimeDetailViewState>()
    fun getViewState() = _viewState

    private val _idAnime = MutableLiveData<String>()
    private val _idMediaProduction = MutableLiveData<String>()
    private val _idStaff = MutableLiveData<String>()

    fun setIdAnime(idAnime: String) {
        _idAnime.value = idAnime
    }

    fun setIdMediaProduction(idMediaProduction: String) {
        _idMediaProduction.value = idMediaProduction
    }

    fun setIdStaff(idStaff: String) {
        _idStaff.value = idStaff
    }

    fun getAnimeById() {
        viewModelScope.launch {
            _idAnime.value?.let {
                getProductionByAnimeId()
                getStaffByAnimeIdUseCase()
                getAnimeByIdUseCase(it).collect {
                    when(it.status) {
                        Status.SUCCESS -> {
                            _viewState.value = it.data?.let { it1 -> AnimeDetailViewState.Anime(it1) }
                        }
                        Status.ERROR -> _viewState.value = AnimeDetailViewState.Error(it.error?.errorMessage ?: "")
                        Status.LOADING -> _viewState.value = AnimeDetailViewState.Loading
                    }
                }
            }
        }
    }

    fun getProductionByAnimeId() {
        viewModelScope.launch {
            _idAnime.value?.let {
                getProductionsByAnimeIdUseCase(it).collect {
                    when(it.status) {
                        Status.SUCCESS -> {
                            it.data?.let { it1 ->
                                setIdMediaProduction(it1)
                            }
                            getCompanyByProductionId()
                        }
                        Status.ERROR -> _viewState.value = AnimeDetailViewState.Error(it.error?.errorMessage ?: "")
                        Status.LOADING -> _viewState.value = AnimeDetailViewState.Loading
                    }
                }
            }
        }
    }

    fun getCompanyByProductionId() {
        viewModelScope.launch {
            _idMediaProduction.value?.let {
                getCompanyByProductionIdUseCase(it).collect {
                    when(it.status) {
                        Status.SUCCESS -> {
                            _viewState.value = it.data?.let { it1 -> AnimeDetailViewState.Company(it1) }
                        }
                        Status.ERROR -> _viewState.value = AnimeDetailViewState.Error(it.error?.errorMessage ?: "")
                        Status.LOADING -> _viewState.value = AnimeDetailViewState.Loading
                    }
                }
            }
        }
    }

    fun getStaffByAnimeIdUseCase() {
        viewModelScope.launch {
            _idAnime.value?.let {
                getStaffByAnimeIdUseCase(it).collect {
                    when(it.status) {
                        Status.SUCCESS -> {
                            it.data?.let { it1 ->
                                setIdStaff(it1)
                            }
                            getPersonsByStaffId()
                        }
                        Status.ERROR -> _viewState.value = AnimeDetailViewState.Error(it.error?.errorMessage ?: "")
                        Status.LOADING -> _viewState.value = AnimeDetailViewState.Loading
                    }
                }
            }
        }
    }

    fun getPersonsByStaffId() {
        viewModelScope.launch {
            _idStaff.value?.let {
                getPersonsByStaffId(it).collect {
                    when(it.status) {
                        Status.SUCCESS -> {
                            _viewState.value = it.data?.let { it1 -> AnimeDetailViewState.Director(it1) }
                        }
                        Status.ERROR -> _viewState.value = AnimeDetailViewState.Error(it.error?.errorMessage ?: "")
                        Status.LOADING -> _viewState.value = AnimeDetailViewState.Loading
                    }
                }
            }
        }
    }
}
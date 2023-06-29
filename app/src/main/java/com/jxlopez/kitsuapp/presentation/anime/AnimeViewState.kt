package com.jxlopez.kitsuapp.presentation.anime

import com.jxlopez.kitsuapp.model.AnimeItem

sealed class AnimeViewState {
    object Loading: AnimeViewState()
    class AnimeList(val animes: List<AnimeItem>) : AnimeViewState()
    class AnimeListPaginated(val animes: List<AnimeItem>) : AnimeViewState()
    class Error(val error: String) : AnimeViewState()
}
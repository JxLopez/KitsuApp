package com.jxlopez.kitsuapp.presentation.anime.detail

import com.jxlopez.kitsuapp.model.AnimeItem

sealed class AnimeDetailViewState {
    object Loading: AnimeDetailViewState()
    class Anime(val anime: AnimeItem) : AnimeDetailViewState()
    class Company(val name: String) : AnimeDetailViewState()
    class Director(val name: String) : AnimeDetailViewState()
    class Error(val error: String) : AnimeDetailViewState()
}

package com.jxlopez.kitsuapp.model

data class AnimeItem(
    val id: String = "",
    val posterImage: String = "",
    val coverImage: String = "",
    val title: String = "",
    val titles: String = "",
    val duration: String = "",
    val description: String = "",
    val releaseDate: String = "",
    val rating: Float = 0.0f,
    val youtubeVideoId: String = ""
)

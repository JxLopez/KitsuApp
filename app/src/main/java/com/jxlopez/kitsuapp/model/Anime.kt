package com.jxlopez.kitsuapp.model

import com.google.gson.annotations.SerializedName

data class Animes(
    val data: List<Anime> = emptyList(),
    val meta: Meta
)

data class AnimeResponse(
    val data: Anime
)

data class Meta(
    val count: Int
)

data class Anime(
    val id: String,
    val attributes: Attributes

)

data class Attributes(
    val description: String?,
    val posterImage: PosterImage?,
    val canonicalTitle: String?,
    val startDate: String?,
    val titles: Titles?,
    val coverImage: CoverImage?,
    val averageRating: String?,
    val youtubeVideoId: String?
)
data class PosterImage(
    val tiny: String,
    val small: String,
    val medium: String,
    val large: String,
    val original: String
)

data class CoverImage(
    val tiny: String,
    val small: String,
    val medium: String,
    val large: String,
    val original: String
)

data class Titles(
    val en: String?,
    @SerializedName("en_jp")
    val enJp: String?,
    @SerializedName("ja_jp")
    val jaJp: String?
)

fun Anime.toUiModel(): AnimeItem {
    val titles = "${attributes.titles?.en ?: "-"}, ${attributes.titles?.enJp ?: "-"}, ${attributes.titles?.jaJp ?: "-"}"
    return AnimeItem(
        id,
        attributes.posterImage?.tiny ?: "",
        attributes.coverImage?.tiny ?: "",
        attributes.canonicalTitle ?: "-",
        titles,
        "-",
        attributes.description ?: "-",
        attributes.startDate?.substring(0,4) ?: "-",
        attributes.averageRating?.toFloatOrNull()?.div(20) ?: 0f,
        attributes.youtubeVideoId ?: ""
    )
}


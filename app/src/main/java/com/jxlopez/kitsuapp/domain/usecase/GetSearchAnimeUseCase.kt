package com.jxlopez.kitsuapp.domain.usecase

import com.jxlopez.kitsuapp.data.Resource
import com.jxlopez.kitsuapp.data.datasource.repository.AnimeRepository
import com.jxlopez.kitsuapp.model.AnimeItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchAnimeUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {
    suspend operator fun invoke(query: String): Flow<Resource<List<AnimeItem>>> =
        animeRepository.getSearchAnime(query)
}
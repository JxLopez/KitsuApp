package com.jxlopez.kitsuapp.domain.usecase

import com.jxlopez.kitsuapp.data.Resource
import com.jxlopez.kitsuapp.data.datasource.repository.AnimeRepository
import com.jxlopez.kitsuapp.model.AnimeItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimeByIdUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {
    suspend operator fun invoke(id: String): Flow<Resource<AnimeItem>> =
        animeRepository.getAnimeById(id)
}
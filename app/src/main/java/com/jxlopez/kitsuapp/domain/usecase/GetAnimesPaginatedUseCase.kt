package com.jxlopez.kitsuapp.domain.usecase

import com.jxlopez.kitsuapp.data.Resource
import com.jxlopez.kitsuapp.data.datasource.repository.AnimeRepository
import com.jxlopez.kitsuapp.model.AnimeItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAnimesPaginatedUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {
    suspend operator fun invoke(limit: Int, offset: Int): Flow<Resource<List<AnimeItem>>> =
        animeRepository.getAnimesPaginated(limit, offset)
}
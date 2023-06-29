package com.jxlopez.kitsuapp.domain.usecase

import com.jxlopez.kitsuapp.data.Resource
import com.jxlopez.kitsuapp.data.datasource.repository.AnimeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCompanyByProductionIdUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
) {
    suspend operator fun invoke(id: String): Flow<Resource<String>> =
        animeRepository.getCompanyByProductionId(id)
}
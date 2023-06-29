package com.jxlopez.kitsuapp.data.datasource.repository

import com.jxlopez.kitsuapp.data.Resource
import com.jxlopez.kitsuapp.model.AnimeDataItem
import com.jxlopez.kitsuapp.model.AnimeItem
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun getAnimes(): Flow<Resource<AnimeDataItem>>
    suspend fun getAnimesPaginated(limit: Int, offset: Int): Flow<Resource<List<AnimeItem>>>
    suspend fun getAnimeById(id: String): Flow<Resource<AnimeItem>>
    suspend fun getProductionsByAnimeId(id: String): Flow<Resource<String>>
    suspend fun getCompanyByProductionId(id: String): Flow<Resource<String>>
    suspend fun getStaffByAnimeId(id: String): Flow<Resource<String>>
    suspend fun getPersonsByStaffId(id: String): Flow<Resource<String>>
    suspend fun getSearchAnime(query: String): Flow<Resource<List<AnimeItem>>>
}
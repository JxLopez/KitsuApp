package com.jxlopez.kitsuapp.data.datasource.remote

import com.jxlopez.kitsuapp.data.Resource
import com.jxlopez.kitsuapp.model.Anime
import com.jxlopez.kitsuapp.model.AnimeData
import com.jxlopez.kitsuapp.model.MediaProductions
import com.jxlopez.kitsuapp.model.ProducerResponse

interface AnimeRemoteDataSource {
    suspend fun getAnimes(): Resource<AnimeData>
    suspend fun getAnimesPaginated(limit: Int, offset: Int): Resource<List<Anime>>
    suspend fun getAnimeById(id: String): Resource<Anime>
    suspend fun getProductionsByAnimeId(id: String): Resource<MediaProductions>
    suspend fun getCompanyByProductionId(id: String): Resource<ProducerResponse>
    suspend fun getStaffByAnimeId(id: String): Resource<MediaProductions>
    suspend fun getPersonsByStaffId(id: String): Resource<ProducerResponse>
    suspend fun getSearchAnime(query: String): Resource<List<Anime>>
}
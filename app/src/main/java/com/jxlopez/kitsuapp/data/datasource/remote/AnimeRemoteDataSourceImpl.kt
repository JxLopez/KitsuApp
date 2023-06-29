package com.jxlopez.kitsuapp.data.datasource.remote

import com.jxlopez.kitsuapp.data.Error
import com.jxlopez.kitsuapp.data.Resource
import com.jxlopez.kitsuapp.data.api.KitsuApi
import com.jxlopez.kitsuapp.model.Anime
import com.jxlopez.kitsuapp.model.AnimeData
import com.jxlopez.kitsuapp.model.MediaProductions
import com.jxlopez.kitsuapp.model.ProducerResponse
import javax.inject.Inject

class AnimeRemoteDataSourceImpl @Inject constructor(
    private val kitsuApi: KitsuApi
): AnimeRemoteDataSource {
    override suspend fun getAnimes(): Resource<AnimeData> {
        val result = kitsuApi.getAnimes()
        return if(result.isSuccessful) {
            Resource.success(AnimeData(result.body()?.data ?: listOf(),
                result.body()?.meta?.count ?: 0))
        } else {
            Resource.error(result.errorBody().toString(), null,
                Error(result.code(), result.errorBody().toString())
            )
        }
    }

    override suspend fun getAnimesPaginated(limit: Int, offset: Int): Resource<List<Anime>> {
        val result = kitsuApi.getAnimesPaginated(limit, offset)
        return if (result.isSuccessful) {
            Resource.success(result.body()?.data)
        } else {
            Resource.error(
                result.errorBody().toString(), null,
                Error(result.code(), result.errorBody().toString())
            )
        }
    }

    override suspend fun getAnimeById(id: String): Resource<Anime> {
        val result = kitsuApi.getAnimeById(id)
        return if(result.isSuccessful) {
            Resource.success(result.body()?.data)
        } else {
            Resource.error(result.errorBody().toString(), null,
                Error(result.code(), result.errorBody().toString())
            )
        }
    }

    override suspend fun getProductionsByAnimeId(id: String): Resource<MediaProductions> {
        val result = kitsuApi.getProductionsByAnimeId(id)
        return if(result.isSuccessful) {
            Resource.success(result.body())
        } else {
            Resource.error(result.errorBody().toString(), null,
                Error(result.code(), result.errorBody().toString())
            )
        }
    }

    override suspend fun getCompanyByProductionId(id: String): Resource<ProducerResponse> {
        val result = kitsuApi.getCompanyByProductionId(id)
        return if(result.isSuccessful) {
            Resource.success(result.body())
        } else {
            Resource.error(result.errorBody().toString(), null,
                Error(result.code(), result.errorBody().toString())
            )
        }
    }

    override suspend fun getStaffByAnimeId(id: String): Resource<MediaProductions> {
        val result = kitsuApi.getStaffByAnimeId(id)
        return if(result.isSuccessful) {
            Resource.success(result.body())
        } else {
            Resource.error(result.errorBody().toString(), null,
                Error(result.code(), result.errorBody().toString())
            )
        }
    }

    override suspend fun getPersonsByStaffId(id: String): Resource<ProducerResponse> {
        val result = kitsuApi.getPersonsByStaffId(id)
        return if(result.isSuccessful) {
            Resource.success(result.body())
        } else {
            Resource.error(result.errorBody().toString(), null,
                Error(result.code(), result.errorBody().toString())
            )
        }
    }

    override suspend fun getSearchAnime(query: String): Resource<List<Anime>> {
        val result = kitsuApi.getSearchAnime(query)
        return if(result.isSuccessful) {
            Resource.success(result.body()?.data)
        } else {
            Resource.error(result.errorBody().toString(), null,
                Error(result.code(), result.errorBody().toString())
            )
        }
    }

}
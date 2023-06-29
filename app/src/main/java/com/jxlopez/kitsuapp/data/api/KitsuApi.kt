package com.jxlopez.kitsuapp.data.api

import com.jxlopez.kitsuapp.model.AnimeResponse
import com.jxlopez.kitsuapp.model.Animes
import com.jxlopez.kitsuapp.model.MediaProductions
import com.jxlopez.kitsuapp.model.ProducerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KitsuApi {

    @GET("edge/anime")
    suspend fun getAnimes(): Response<Animes>

    @GET("edge/anime/{id}")
    suspend fun getAnimeById(
        @Path("id") id: String,
    ): Response<AnimeResponse>

    @GET("edge/anime/{id}/productions")
    suspend fun getProductionsByAnimeId(
        @Path("id") id: String,
    ): Response<MediaProductions>

    @GET("edge/media-productions/{id}/company")
    suspend fun getCompanyByProductionId(
        @Path("id") id: String,
    ): Response<ProducerResponse>

    @GET("edge/anime/{id}/staff")
    suspend fun getStaffByAnimeId(
        @Path("id") id: String,
    ): Response<MediaProductions>

    @GET("edge/media-staff/{id}/person")
    suspend fun getPersonsByStaffId(
        @Path("id") id: String,
    ): Response<ProducerResponse>

    @GET("edge/anime")
    suspend fun getSearchAnime(
        @Query("filter[text]") query: String
    ): Response<Animes>

    @GET("edge/anime")
    suspend fun getAnimesPaginated(
        @Query("page[limit]") limit: Int,
        @Query("page[offset]") offset: Int
    ): Response<Animes>
}
package com.jxlopez.kitsuapp.data.datasource.repository

import com.jxlopez.kitsuapp.data.Resource
import com.jxlopez.kitsuapp.data.Status
import com.jxlopez.kitsuapp.data.datasource.remote.AnimeRemoteDataSource
import com.jxlopez.kitsuapp.model.AnimeDataItem
import com.jxlopez.kitsuapp.model.AnimeItem
import com.jxlopez.kitsuapp.model.MediaProductionRole
import com.jxlopez.kitsuapp.model.toUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

 class AnimeRepositoryImpl @Inject constructor(
     private val animeRemoteDataSource: AnimeRemoteDataSource
     ) : AnimeRepository {

     override suspend fun getAnimes(): Flow<Resource<AnimeDataItem>> =
         flow {
             val result = animeRemoteDataSource.getAnimes()
             when (result.status) {
                 Status.SUCCESS -> {
                     val data = result.data?.anime?.map { it.toUiModel() }
                     emit(Resource.success(AnimeDataItem(
                         data!!,
                         result.data?.maxItems ?: 0
                     )))
                 }

                 Status.ERROR -> emit(
                     Resource.error(
                         result.error?.errorMessage ?: "",
                         null, result.error!!
                     )
                 )

                 Status.LOADING -> emit(Resource.loading())
             }
         }.onStart { emit(Resource.loading()) }

     override suspend fun getAnimesPaginated(
         limit: Int,
         offset: Int
     ): Flow<Resource<List<AnimeItem>>> =
         flow {
             val result = animeRemoteDataSource.getAnimesPaginated(limit, offset)
             when (result.status) {
                 Status.SUCCESS -> {
                     emit(Resource.success(result.data?.map { it.toUiModel() }))
                 }

                 Status.ERROR -> emit(
                     Resource.error(
                         result.error?.errorMessage ?: "",
                         null, result.error!!
                     )
                 )

                 Status.LOADING -> emit(Resource.loading())
             }
         }.onStart { emit(Resource.loading()) }

     override suspend fun getAnimeById(id: String): Flow<Resource<AnimeItem>> =
         flow {
             val result = animeRemoteDataSource.getAnimeById(id)
             when (result.status) {
                 Status.SUCCESS -> {
                     emit(Resource.success(result.data?.toUiModel()))
                 }

                 Status.ERROR -> emit(
                     Resource.error(
                         result.error?.errorMessage ?: "",
                         null, result.error!!
                     )
                 )

                 Status.LOADING -> emit(Resource.loading())
             }
         }.onStart { emit(Resource.loading()) }

     override suspend fun getProductionsByAnimeId(id: String):  Flow<Resource<String>> =
         flow {
             val result = animeRemoteDataSource.getProductionsByAnimeId(id)
             when (result.status) {
                 Status.SUCCESS -> {
                     val productionsStudios = result.data?.data?.filter {
                         it.attributes.role == MediaProductionRole.STUDIO.role
                     }
                     if(productionsStudios?.isNotEmpty() == true)
                        emit(Resource.success(productionsStudios[0].id))
                     else
                         emit(Resource.success(""))
                 }

                 Status.ERROR -> emit(
                     Resource.error(
                         result.error?.errorMessage ?: "",
                         null, result.error!!
                     )
                 )

                 Status.LOADING -> emit(Resource.loading())
             }
         }.onStart { emit(Resource.loading()) }

     override suspend fun getCompanyByProductionId(id: String):  Flow<Resource<String>> =
         flow {
             val result = animeRemoteDataSource.getCompanyByProductionId(id)
             when (result.status) {
                 Status.SUCCESS -> {
                     emit(Resource.success(result.data?.data?.attributes?.name))
                 }

                 Status.ERROR -> emit(
                     Resource.error(
                         result.error?.errorMessage ?: "",
                         null, result.error!!
                     )
                 )

                 Status.LOADING -> emit(Resource.loading())
             }
         }.onStart { emit(Resource.loading()) }

     override suspend fun getStaffByAnimeId(id: String): Flow<Resource<String>> =
         flow {
             val result = animeRemoteDataSource.getStaffByAnimeId(id)
             when (result.status) {
                 Status.SUCCESS -> {
                     val productionsStudios = result.data?.data?.filter {
                         it.attributes.role == MediaProductionRole.PRODUCER.role
                     }
                     if(productionsStudios?.isNotEmpty() == true)
                         emit(Resource.success(productionsStudios[0].id))
                     else
                         emit(Resource.success(""))
                 }

                 Status.ERROR -> emit(
                     Resource.error(
                         result.error?.errorMessage ?: "",
                         null, result.error!!
                     )
                 )

                 Status.LOADING -> emit(Resource.loading())
             }
         }.onStart { emit(Resource.loading()) }

     override suspend fun getPersonsByStaffId(id: String): Flow<Resource<String>> =
         flow {
             val result = animeRemoteDataSource.getPersonsByStaffId(id)
             when (result.status) {
                 Status.SUCCESS -> {
                     emit(Resource.success(result.data?.data?.attributes?.name))
                 }

                 Status.ERROR -> emit(
                     Resource.error(
                         result.error?.errorMessage ?: "",
                         null, result.error!!
                     )
                 )

                 Status.LOADING -> emit(Resource.loading())
             }
         }.onStart { emit(Resource.loading()) }

     override suspend fun getSearchAnime(query: String): Flow<Resource<List<AnimeItem>>> =
         flow {
             val result = animeRemoteDataSource.getSearchAnime(query)
             when (result.status) {
                 Status.SUCCESS -> {
                     emit(Resource.success(result.data?.map { it.toUiModel() }))
                 }

                 Status.ERROR -> emit(
                     Resource.error(
                         result.error?.errorMessage ?: "",
                         null, result.error!!
                     )
                 )

                 Status.LOADING -> emit(Resource.loading())
             }
         }.onStart { emit(Resource.loading()) }

 }
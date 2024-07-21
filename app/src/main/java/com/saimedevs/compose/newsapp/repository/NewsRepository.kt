package com.saimedevs.compose.newsapp.repository

import com.saimedevs.compose.newsapp.model.Response
import com.saimedevs.compose.newsapp.utils.Result
import com.saimedevs.compose.newsapp.network.NewsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsApi: NewsApi
) {
    fun getNews(query: String, from: String, sortBy: String, apiKey: String): Flow<Result<Response>> = flow {
        emit(Result.Loading())
        try {
            val response = newsApi.getNews(query, from, sortBy, apiKey)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }
}
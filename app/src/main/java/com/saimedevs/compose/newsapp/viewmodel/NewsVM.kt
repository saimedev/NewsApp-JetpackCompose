package com.saimedevs.compose.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saimedevs.compose.newsapp.model.Article
import com.saimedevs.compose.newsapp.model.Response
import com.saimedevs.compose.newsapp.repository.NewsRepository
import com.saimedevs.compose.newsapp.utils.Constant.API_KEY
import com.saimedevs.compose.newsapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsVM @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _newsState = MutableStateFlow<Result<Response>>(Result.Loading())
    val newsState: StateFlow<Result<Response>> = _newsState

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _filteredArticles = MutableStateFlow<List<Article>>(emptyList())
    val filteredArticles: StateFlow<List<Article>> = _filteredArticles

    private var allArticles: List<Article> = emptyList()

    fun fetchNews(query: String, from: String, sortBy: String) {
        viewModelScope.launch {
            newsRepository.getNews(query, from, sortBy, API_KEY)
                .collect { result ->
                    _newsState.value = result
                    when (result) {
                        is Result.Success -> {
                            allArticles = result.data?.articles!!
                            _filteredArticles.value = allArticles
                        }
                        is Result.Error -> {
                            _filteredArticles.value = emptyList()
                        }
                        is Result.Loading -> {
                            _filteredArticles.value = emptyList()
                        }
                    }
                }
        }
    }

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        _filteredArticles.value = allArticles.filter {
            it.title?.contains(query, ignoreCase = true) == true ||
                    it.description?.contains(query, ignoreCase = true) == true
        }
    }
}
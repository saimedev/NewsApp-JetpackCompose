package com.saimedevs.compose.newsapp.model

data class Response(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)


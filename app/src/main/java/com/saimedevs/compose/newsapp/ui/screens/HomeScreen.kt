package com.saimedevs.compose.newsapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.saimedevs.compose.newsapp.ui.composables.NewsItem
import com.saimedevs.compose.newsapp.ui.composables.SearchBar
import com.saimedevs.compose.newsapp.ui.navigation.Screens
import com.saimedevs.compose.newsapp.utils.Result
import com.saimedevs.compose.newsapp.viewmodel.NewsVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: NewsVM) {
    val newsState by viewModel.newsState.collectAsState()
    val filteredArticles by viewModel.filteredArticles.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News App") },
            )
        },
        content = { padding ->
            Column(modifier = Modifier
                .padding(padding)
                .fillMaxSize()) {
                when (val state = newsState) {
                    is Result.Success -> {
                        SearchBar(
                            query = searchQuery,
                            onQueryChanged = { query -> viewModel.updateSearchQuery(query) }
                        )
                        if (filteredArticles.isEmpty()) {
                            Text("No results found", modifier = Modifier.padding(16.dp))
                        } else {
                            LazyColumn(
                                contentPadding = PaddingValues(top = 7.dp)
                            ) {
                                items(filteredArticles) { article ->
                                    NewsItem(article = article){
                                        viewModel.selectArticle(article)
                                        navController.navigate(Screens.DetailScreen.route)
                                    }
                                }
                            }
                        }
                    }

                    is Result.Error -> {
                        Text("Error: ${state.message}")
                    }

                    is Result.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()  ,
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }

                    }
                }
            }
        }
    )
}


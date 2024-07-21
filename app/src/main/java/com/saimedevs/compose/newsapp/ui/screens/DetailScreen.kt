package com.saimedevs.compose.newsapp.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.saimedevs.compose.newsapp.viewmodel.NewsVM
import com.saimedevs.compose.newsapp.utils.Utils.reformatDate
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, viewModel: NewsVM) {
    val article by viewModel.selectedArticle.collectAsState()
    Log.d("XYZ", "$article")
    val scrollState = rememberScrollState()
    article?.let {
        Scaffold(topBar = {
            TopAppBar(title = { Text("News App") },
                navigationIcon = {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 10.dp)
                            .clickable {
                            navController.popBackStack()
                        })
                }
                )
        },
            content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 10.dp)
                    .verticalScroll(scrollState)
            ) {
                it.title?.let { it1 ->
                    Text(
                        text = it1,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }

                it.urlToImage?.let { imageUrl ->
                    SubcomposeAsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        loading = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .shimmer()
                                    .background(Color.Gray.copy(alpha = 0.5f)),
                            )
                        },
                        success = {
                            SubcomposeAsyncImageContent(
                                modifier = Modifier.fillMaxSize()
                            )
                        },
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                it.description?.let { it1 ->
                    Text(
                        text = it1,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }

                it.content?.let { it1 ->
                    Text(
                        text = it1,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }

                Text(
                    text = "Published by: ${it.author}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                )

                Text(
                    text = "Published at: ${it.publishedAt?.let { it1 -> reformatDate(it1) }}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                )

            }
        })
    } ?: run {
        Text("Loading...", modifier = Modifier.padding(8.dp))
    }
}
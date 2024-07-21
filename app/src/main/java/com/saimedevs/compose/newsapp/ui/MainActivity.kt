package com.saimedevs.compose.newsapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.saimedevs.compose.newsapp.viewmodel.NewsVM
import com.saimedevs.compose.newsapp.ui.navigation.NavHost
import com.saimedevs.compose.newsapp.ui.theme.NewsAppTheme
import com.saimedevs.compose.newsapp.utils.Utils
import com.saimedevs.compose.newsapp.utils.Utils.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val viewModel: NewsVM = hiltViewModel()
                LaunchedEffect(Unit) {
                    if (isNetworkAvailable()) {
                        viewModel.fetchNews(
                            "tesla",
                            Utils.getDate(),  //Api not show data of current date so I fetch the previous day data
                            "publishedAt"
                        )
                    }else{
                        Toast.makeText(this@MainActivity,
                            "Please check your internet connection", Toast.LENGTH_SHORT).show()
                    }
                }
                NavHost(navController = rememberNavController(), viewModel)
            }
        }
    }
}


package com.fedorov.andrii.andriiovych.qachallenge.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fedorov.andrii.andriiovych.qachallenge.presentation.screens.BooleanQuizScreen
import com.fedorov.andrii.andriiovych.qachallenge.presentation.screens.CategoryScreen
import com.fedorov.andrii.andriiovych.qachallenge.presentation.screens.HomeScreen
import com.fedorov.andrii.andriiovych.qachallenge.presentation.screens.MultipleQuizScreen
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.*
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackground
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.QAChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QAChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PrimaryBackground
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

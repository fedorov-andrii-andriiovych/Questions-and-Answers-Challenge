package com.fedorov.andrii.andriiovych.qachallenge

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
import com.fedorov.andrii.andriiovych.qachallenge.Screens.CategoryScreen
import com.fedorov.andrii.andriiovych.qachallenge.Screens.HomeScreen
import com.fedorov.andrii.andriiovych.qachallenge.Screens.QuizScreen
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackground
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.QAChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()
            val navController = rememberNavController()
            QAChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PrimaryBackground
                ) {
                    NavHost(navController = navController, startDestination = HOME_SCREEN) {
                        composable(HOME_SCREEN) {
                            HomeScreen(Modifier,viewModel, onClickType = {
                                viewModel.typeState.value = it
                                navController.navigate(CATEGORY_SCREEN)
                            })
                        }
                        composable(CATEGORY_SCREEN) {
                            CategoryScreen(Modifier,viewModel, onClickCategory = {
                                viewModel.categoryState.value = it
                                viewModel.getNewQuestion()
                                navController.navigate(QUIZ_SCREEN)
                            })
                        }
                        composable(QUIZ_SCREEN){
                            QuizScreen( viewModel,Modifier)
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val HOME_SCREEN = "homeScreen"
        const val CATEGORY_SCREEN = "categoryScreen"
        const val QUIZ_SCREEN = "quizScreen"
    }
}

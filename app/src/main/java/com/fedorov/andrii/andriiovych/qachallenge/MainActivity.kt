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
import com.fedorov.andrii.andriiovych.qachallenge.presentation.screens.BooleanQuizScreen
import com.fedorov.andrii.andriiovych.qachallenge.presentation.screens.CategoryScreen
import com.fedorov.andrii.andriiovych.qachallenge.presentation.screens.HomeScreen
import com.fedorov.andrii.andriiovych.qachallenge.presentation.screens.MultipleQuizScreen
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackground
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.QAChallengeTheme
import com.fedorov.andrii.andriiovych.qachallenge.domain.viewmodels.BooleanViewModel
import com.fedorov.andrii.andriiovych.qachallenge.domain.viewmodels.MultipleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()
            val navController = rememberNavController()
            val multipleViewModel: MultipleViewModel = viewModel()
            val booleanViewModel: BooleanViewModel = viewModel()
            QAChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PrimaryBackground
                ) {
                    NavHost(navController = navController, startDestination = HOME_SCREEN) {
                        composable(HOME_SCREEN) {
                            HomeScreen(Modifier, viewModel, onClickType = {
                                viewModel.typeState.value = it
                                navController.navigate(CATEGORY_SCREEN)
                            })
                        }
                        composable(CATEGORY_SCREEN) {
                            CategoryScreen(Modifier, viewModel, onClickCategory = {
                                when (viewModel.typeState.value) {
                                    QuestionType.BOOLEAN -> {
                                        booleanViewModel.difficultyState.value = QuestionDifficulty.ANY
                                        booleanViewModel.categoryState.value = it
                                        booleanViewModel.getNewQuestion()
                                        navController.navigate(BOOLEAN_QUIZ_SCREEN)
                                    }
                                    QuestionType.MULTIPLE -> {
                                        multipleViewModel.difficultyState.value = QuestionDifficulty.ANY
                                        multipleViewModel.categoryState.value = it
                                        multipleViewModel.getNewQuestion()
                                        navController.navigate(MULTIPLE_QUIZ_SCREEN)
                                    }
                                    else -> throw IllegalAccessException()
                                }

                            })
                        }
                        composable(BOOLEAN_QUIZ_SCREEN) {
                            BooleanQuizScreen(
                                booleanViewModel = booleanViewModel,
                                modifier = Modifier
                            )
                        }
                        composable(MULTIPLE_QUIZ_SCREEN) {
                            MultipleQuizScreen(
                                multipleViewModel = multipleViewModel,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val HOME_SCREEN = "homeScreen"
        const val CATEGORY_SCREEN = "categoryScreen"
        const val MULTIPLE_QUIZ_SCREEN = "multipleQuizScreen"
        const val BOOLEAN_QUIZ_SCREEN = "booleanQuizScreen"
    }
}

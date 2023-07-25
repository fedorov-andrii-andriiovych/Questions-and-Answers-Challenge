package com.fedorov.andrii.andriiovych.qachallenge.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.BooleanViewModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.MainViewModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.MultipleViewModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.QuestionType

@Composable
fun HomeScreen() {
    val mainViewModel: MainViewModel = viewModel()
    val multipleViewModel: MultipleViewModel = viewModel()
    val booleanViewModel: BooleanViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.MAIN.route) {
        composable(Screens.MAIN.route) {
            MainScreen(
                modifier = Modifier,
                mainViewModel = mainViewModel,
                onClickType = { questionType ->
                    mainViewModel.typeState.value = questionType
                    navController.navigate(Screens.CATEGORY.route)
                })
        }
        composable(Screens.CATEGORY.route) {
            CategoryScreen(
                modifier = Modifier,
                mainViewModel = mainViewModel,
                onClickCategory = { categoryModel ->
                    when (mainViewModel.typeState.value) {
                        QuestionType.BOOLEAN -> {
                            booleanViewModel.questionType = QuestionType.BOOLEAN
                            booleanViewModel.categoryState = categoryModel
                            navController.navigate(Screens.BOOLEAN_QUIZ.route)
                        }
                        QuestionType.MULTIPLE -> {
                            multipleViewModel.questionType = QuestionType.MULTIPLE
                            multipleViewModel.categoryState = categoryModel
                            navController.navigate(Screens.MULTIPLE_QUIZ.route)
                        }
                        else -> throw IllegalAccessException()
                    }
                })
        }
        composable(Screens.BOOLEAN_QUIZ.route) {
            BooleanQuizScreen(
                booleanViewModel = booleanViewModel,
                modifier = Modifier
            )
        }
        composable(Screens.MULTIPLE_QUIZ.route) {
            MultipleQuizScreen(
                multipleViewModel = multipleViewModel,
                modifier = Modifier
            )
        }
    }
}

enum class Screens(val route: String) {
    MAIN("screenMain"),
    CATEGORY("screenFavorites"),
    MULTIPLE_QUIZ("screenSettings"),
    BOOLEAN_QUIZ("screenDetailed")
}
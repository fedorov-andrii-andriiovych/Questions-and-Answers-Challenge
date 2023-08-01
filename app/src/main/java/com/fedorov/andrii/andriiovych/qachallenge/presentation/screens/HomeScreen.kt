package com.fedorov.andrii.andriiovych.qachallenge.presentation.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.CategoryModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.QuestionType

private const val TYPE = "type"
private const val CATEGORY = "category"

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MAIN.route) {
        composable(Screens.MAIN.route) {
            MainScreen(
                modifier = Modifier,
                onClickType = { questionType ->
                    val bundle = Bundle().apply { putParcelable(TYPE, questionType) }
                    navController.navigate(Screens.CATEGORY.route, bundle)
                })
        }
        composable(Screens.CATEGORY.route) { host ->
            val type = host.arguments?.getParcelable<QuestionType>(TYPE)!!
            CategoryScreen(
                modifier = Modifier,
                onClickCategory = { categoryModel ->
                    when (type) {
                        QuestionType.BOOLEAN -> {
                            val bundle = Bundle().apply {
                                putParcelable(CATEGORY, categoryModel)
                            }
                            navController.navigate(Screens.BOOLEAN_QUIZ.route, bundle)
                        }
                        QuestionType.MULTIPLE -> {
                            val bundle = Bundle().apply {
                                putParcelable(CATEGORY, categoryModel)
                            }
                            navController.navigate(Screens.MULTIPLE_QUIZ.route, bundle)
                        }
                        else -> throw IllegalAccessException()
                    }
                })
        }
        composable(Screens.BOOLEAN_QUIZ.route) { host ->
            val category = host.arguments?.getParcelable<CategoryModel>(CATEGORY)!!
            BooleanQuizScreen(
                modifier = Modifier,
                category = category
            )
        }
        composable(Screens.MULTIPLE_QUIZ.route) { host ->
            val category = host.arguments?.getParcelable<CategoryModel>(CATEGORY)!!
            MultipleQuizScreen(
                modifier = Modifier,
                category = category
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

fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}
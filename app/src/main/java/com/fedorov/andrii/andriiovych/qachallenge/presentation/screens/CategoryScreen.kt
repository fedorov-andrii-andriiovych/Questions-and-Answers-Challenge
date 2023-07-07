package com.fedorov.andrii.andriiovych.qachallenge.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fedorov.andrii.andriiovych.qachallenge.R
import com.fedorov.andrii.andriiovych.qachallenge.domain.models.CategoryModel
import com.fedorov.andrii.andriiovych.qachallenge.presentation.viewmodels.MainViewModel
import com.fedorov.andrii.andriiovych.qachallenge.ui.theme.PrimaryBackgroundPink

@Composable
fun CategoryScreen(modifier: Modifier, mainViewModel: MainViewModel, onClickCategory:(CategoryModel)->Unit) {
    Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp)){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(25.dp))
                .background(color = PrimaryBackgroundPink)
                .fillMaxWidth()
                .height(60.dp)
                .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(25.dp))

        ) {
            Text(text = stringResource(R.string.categories), fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(mainViewModel.categories) { _, category ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(25.dp))
                        .background(color = PrimaryBackgroundPink)
                        .fillMaxWidth()
                        .height(100.dp)
                        .border(BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(25.dp))
                        .clickable {
                            onClickCategory(category)
                        }

                ) {
                    Text(text = category.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White , textAlign = TextAlign.Center)
                }
            }
        }
    }
}
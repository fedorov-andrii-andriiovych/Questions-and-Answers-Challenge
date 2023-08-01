package com.fedorov.andrii.andriiovych.qachallenge.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel(
    val name: String = "Nothing",
    val id: Int = -1
) : Parcelable

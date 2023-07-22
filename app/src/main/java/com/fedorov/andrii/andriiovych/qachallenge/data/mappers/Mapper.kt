package com.fedorov.andrii.andriiovych.qachallenge.data.mappers

interface Mapper<F, T> {

    fun mapFrom(from: F): T

}

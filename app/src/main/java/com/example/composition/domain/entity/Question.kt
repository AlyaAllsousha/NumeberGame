package com.example.composition.domain.entity

data class Question(
    val sum: Int,
    val firstNumber: Int,
    val options: List<Int>
){
    val rightAnswer: Int
        get() = sum - firstNumber
}

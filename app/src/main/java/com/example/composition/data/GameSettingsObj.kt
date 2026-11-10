package com.example.composition.data

import com.example.composition.domain.entity.GameSettings

object GameSettingsObj {
    val TEST = GameSettings(
        maxSumValue = 10,
        minRightAnswersCount = 3,
        minRightAnswersPercent = 50,
        gameTime = 10
    )
    val EASY = GameSettings(
        maxSumValue = 10,
        minRightAnswersCount = 10,
        minRightAnswersPercent = 70,
        gameTime = 60
    )
    val MIDDLE = GameSettings(
        maxSumValue = 20,
        minRightAnswersCount = 20,
        minRightAnswersPercent = 80,
        gameTime = 40
    )
    val HARD = GameSettings(
        maxSumValue = 30,
        minRightAnswersCount = 30,
        minRightAnswersPercent = 90,
        gameTime = 40
    )
}
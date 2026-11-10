package com.example.composition.data

import com.example.composition.domain.GameRepository
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANS_NUMBER = 1
    override fun generateQuestion(
        maxSumValue: Int,
        optionsCount: Int
    ): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val firstNumber = Random.nextInt(MIN_ANS_NUMBER, sum)
        val options = mutableSetOf<Int>()
        val rightAnswer = sum - firstNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - optionsCount, MIN_ANS_NUMBER)
        val to = min(maxSumValue, rightAnswer + optionsCount)
        while (options.size < optionsCount) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, firstNumber, options.shuffled())
    }


    override fun getGameSettings(level: Level): GameSettings {
        return when(level) {
            Level.TEST -> GameSettingsObj.TEST
            Level.EASY -> GameSettingsObj.EASY
            Level.MIDDLE -> GameSettingsObj.MIDDLE
            Level.HARD -> GameSettingsObj.HARD
        }
    }

}
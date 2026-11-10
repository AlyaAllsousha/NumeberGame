package com.example.composition.presentation.game

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composition.R
import com.example.composition.data.GameRepositoryImpl
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.usecase.GenerateQuestionUseCase
import com.example.composition.domain.usecase.GetGameSettingsUSeCase

class GameViewModel(
    private val context: Application,
    private val level: Level
) : ViewModel() {
    private val repository = GameRepositoryImpl

    private lateinit var gameSettings: GameSettings

    private val getGameSettingsUSeCase = GetGameSettingsUSeCase(repository)
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)


    private val _gameTime = MutableLiveData<String>()
    val gameTime: LiveData<String>
        get() = _gameTime

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private val _rightAnswerPercent = MutableLiveData<Int>()
    val rightAnswerPercent: LiveData<Int>
        get() = _rightAnswerPercent

    private val _answerProgress = MutableLiveData<String>()
    val answerProgress: LiveData<String>
        get() = _answerProgress

    private val _enoughRightAnswerCount = MutableLiveData<Boolean>()
    val enoughCount: LiveData<Boolean>
        get() = _enoughRightAnswerCount

    private val _enoughRightAnswersPercent = MutableLiveData<Boolean>()
    val enoughPercent: LiveData<Boolean>
        get() = _enoughRightAnswersPercent

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent
    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private var timer: CountDownTimer? = null

    init{
        startGame()
    }
    private fun startGame() {
        initSettings()
        startTimer()
        generateQuestion()
        updateProgress()

    }

    private var questionCount = 0
    private var rightAnswersCount = 0

    private fun initSettings() {
        gameSettings = getGameSettingsUSeCase(level)
        _minPercent.value = gameSettings.minRightAnswersPercent
    }


    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTime * MILLIS_IN_SECONDS,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(p0: Long) {
                _gameTime.value = formatTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun formatTime(millis: Long): String {
        val seconds = millis / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val lastSecond = seconds - (minutes * SECONDS_IN_MINUTES)
        return String.format("%02d:%02d", minutes, lastSecond)
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun updateProgress() {

        val percent = calculateRightAnswersPercent()
        _rightAnswerPercent.value = percent
        _answerProgress.value = String.format(
            context.getString(R.string.progress_answers),
            rightAnswersCount.toString(),
            gameSettings.minRightAnswersCount.toString()
        )
        _enoughRightAnswerCount.value = rightAnswersCount >= gameSettings.minRightAnswersCount
        _enoughRightAnswersPercent.value = percent >= gameSettings.minRightAnswersPercent
    }

    private fun calculateRightAnswersPercent(): Int {
        if (questionCount == 0) return 0
        return ((rightAnswersCount / questionCount.toDouble()) * 100).toInt()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = _question.value?.rightAnswer
        if (rightAnswer == number) {
            rightAnswersCount++
        }
        questionCount++
    }



    private fun finishGame() {
        _gameResult.value = GameResult(
            winner = enoughCount.value == true && enoughPercent.value == true,
            rightAnswersCount = rightAnswersCount,
            questionsCount = questionCount,
            gameSettings = gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()

    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}
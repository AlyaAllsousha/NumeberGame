package com.example.composition.presentation

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.provider.Settings.Global.getString
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.domain.entity.GameResult

interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}

@BindingAdapter("enoughCount")
fun bindingEnoughCount(textView: TextView, enough: Boolean) {
    textView.setTextColor(getColorByState(textView.context, enough))
}

@BindingAdapter("enoughPercent")
fun bindingEnoughPercent(progressBar: ProgressBar, enough: Boolean) {
    val color = getColorByState(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

private fun getColorByState(context: Context, bool: Boolean): Int {
    val colorResId = if (bool) {
        R.color.holo_green_light
    } else {
        R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("numberAsText")
fun bindingNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionClikcListener")
fun bindOnOptionClickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}

@BindingAdapter("resultEmoji")
fun bindingResultEmoji(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getEmoji(winner))
}

@BindingAdapter("requiredAnswers")
fun bindingRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(com.example.composition.R.string.required_score),
        count
    )
}

@BindingAdapter("requiredPercenatge")
fun bindingRequiredPercentage(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(com.example.composition.R.string.required_percentage),
        count
    )
}

@BindingAdapter("score")
fun bindingScore(textView: TextView, score: Int) {
    textView.text = String.format(
        textView.context.getString(com.example.composition.R.string.score_answers),
        score
    )
}
@BindingAdapter("scorePercentage")
fun bindingScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(com.example.composition.R.string.score_percentage),
        calculatePercentage(gameResult)
    )
}



private fun getEmoji(winner: Boolean): Int {
    return if (winner) {
        com.example.composition.R.drawable.ic_smile
    } else com.example.composition.R.drawable.ic_sad
}

private fun calculatePercentage(gameResult: GameResult): Int {
    with(gameResult) {
        return if (questionsCount == 0) {
            0
        } else {
            ((rightAnswersCount / questionsCount.toDouble()) * 100).toInt()
        }
    }
}

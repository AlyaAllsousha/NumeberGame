package com.example.composition.domain.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class GameResult(
    val winner: Boolean,
    val rightAnswersCount: Int,
    val questionsCount: Int,
    val gameSettings: GameSettings
) : Parcelable

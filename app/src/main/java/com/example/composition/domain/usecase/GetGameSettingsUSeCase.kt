package com.example.composition.domain.usecase

import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.GameRepository
import com.example.composition.domain.entity.Level

class GetGameSettingsUSeCase (
    private val repository: GameRepository
){
    operator fun invoke(level: Level): GameSettings{
        return repository.getGameSettings(level)
    }
}
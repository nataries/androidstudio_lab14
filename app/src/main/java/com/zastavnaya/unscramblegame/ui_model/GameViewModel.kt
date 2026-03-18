package com.zastavnaya.unscramblegame.ui_model

import androidx.lifecycle.ViewModel
import com.zastavnaya.unscramblegame.data.GameUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUIState())
    val uiState: StateFlow<GameUIState> = _uiState.asStateFlow()

    init {
        resetGame()
    }

    fun resetGame() {

    }

}
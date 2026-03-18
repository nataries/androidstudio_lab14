package com.zastavnaya.unscramblegame.ui_model

import androidx.lifecycle.ViewModel
import com.zastavnaya.unscramblegame.data.GameUIState
import com.zastavnaya.unscramblegame.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUIState())
    val uiState: StateFlow<GameUIState> = _uiState.asStateFlow()
    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()

    init {
        resetGame()
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUIState(
            currentScrambleWord = pickRandomWordAndShuffle()
        )
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.random()
        while (usedWords.contains(currentWord)) {
            currentWord = allWords.random()
        }
        usedWords.add(currentWord)
        return shuffleCurrentWord(currentWord)
    }
}
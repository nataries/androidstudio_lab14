package com.zastavnaya.unscramblegame

import com.zastavnaya.unscramblegame.data.MAX_NO_OF_WORDS
import com.zastavnaya.unscramblegame.data.SCORE_INCREASE
import com.zastavnaya.unscramblegame.ui_model.GameViewModel
import org.junit.Assert.*
import org.junit.Test


class GameViewModelTest {
    private val viewModel = GameViewModel()

    private fun getUnscrambleWord(scrambledWord: String): String {
        return com.zastavnaya.unscramblegame.data.allWords.firstOrNull { word ->
            scrambledWord.toSet() == word.toSet()
        } ?: ""
    }

    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUIState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambleWord(currentGameUIState.currentScrambleWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUIState = viewModel.uiState.value

        assertEquals(SCORE_INCREASE, currentGameUIState.score)
        assertFalse(currentGameUIState.isGuessedWordWrong)
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        val incorrectPlayerWord = "incorrect"
        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()

        val currentGameUIState = viewModel.uiState.value

        assertEquals(0, currentGameUIState.score)
        assertTrue(currentGameUIState.isGuessedWordWrong)
    }

    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val gameUIState = viewModel.uiState.value

        val uncrambledWord = getUnscrambleWord(gameUIState.currentScrambleWord)

        assertNotEquals(uncrambledWord, gameUIState.currentScrambleWord)
        assertTrue(gameUIState.currentWordCount == 1)
        assertTrue(gameUIState.score == 0)
        assertFalse(gameUIState.isGameOver)
    }

    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly() {
        var expectedScore = 0

        var currentGameUIState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambleWord(currentGameUIState.currentScrambleWord)

        repeat(MAX_NO_OF_WORDS) {
            expectedScore +=SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord)
            viewModel.checkUserGuess()
            currentGameUIState = viewModel.uiState.value
            correctPlayerWord = getUnscrambleWord(currentGameUIState.currentScrambleWord)
            assertEquals(expectedScore, currentGameUIState.score)
        }
        assertEquals(MAX_NO_OF_WORDS, currentGameUIState.currentWordCount)
        assertTrue(currentGameUIState.isGameOver)
    }
}
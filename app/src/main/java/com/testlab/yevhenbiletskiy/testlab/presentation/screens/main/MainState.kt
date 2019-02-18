package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.MviIntent
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.MviState

data class MainState(
    val isLoading: Boolean,
    val text: String
) : MviState {

  companion object {
    fun idle() = MainState(
      isLoading = true,
      text = ""
    )
  }
}

sealed class MainIntent : MviIntent {
  object InitialIntent : MainIntent()
}

sealed class MainResult {
  data class InitialLoadResult(val text: String) : MainResult()
}

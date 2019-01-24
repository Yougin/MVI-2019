package com.testlab.yevhenbiletskiy.testlab.ui.main

import com.testlab.yevhenbiletskiy.testlab.mvi.MviIntent
import com.testlab.yevhenbiletskiy.testlab.mvi.MviState

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

sealed class MainAction {
  object InitialLoadAction : MainAction()
}

sealed class MainResult {
  // TODO-eugene why to use constructor MainResult()
  data class InitialLoadResult(val text: String) : MainResult()
}
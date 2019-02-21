package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.MviEffect
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.MviIntent
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.MviState
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.domain.MainText
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.domain.UserSession

data class MainState(
    val isLoading: Boolean = false,
    val text: String = "",
    val userSession: String? = null
) : MviState {

  companion object {
    fun idle() = MainState(
        isLoading = true,
        text = ""
    )
  }
}

sealed class MainEffect : MviEffect {
  data class ShowToastEffect(val text: String) : MainEffect()
}

sealed class MainIntent : MviIntent {
  object InitialIntent : MainIntent()
  object LoginIntent : MainIntent()
}

sealed class MainResult {
  data class InitialLoadResult(val text: MainText) : MainResult()
  data class LoginResult(val userSession: UserSession) : MainResult()
}

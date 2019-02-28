package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.ViewEffect
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.Intent
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.Result
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.ViewState
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.domain.MainText
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.domain.UserSession

data class MainState(
    val isLoading: Boolean = false,
    val text: String = "",
    val userSession: String? = null
) : ViewState {

  companion object {
    fun idle() = MainState(
        isLoading = true,
        text = ""
    )
  }
}

sealed class MainViewEffect : ViewEffect {
  data class ShowToastViewEffect(val text: String) : MainViewEffect()
}

sealed class MainIntent : Intent {
  object InitialIntent : MainIntent()
  object LoginIntent : MainIntent()
}

sealed class MainResult: Result {
  data class InitialLoadResult(val text: MainText) : MainResult()
  data class LoginResult(val userSession: UserSession) : MainResult()
}

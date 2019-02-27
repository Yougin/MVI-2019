package com.testlab.yevhenbiletskiy.testlab.presentation.screens.home

import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.MviEffect
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.MviIntent
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.MviState

data class HomeState(
    val isLoading: Boolean = false
) : MviState {

  companion object {
    fun idle() = HomeState()
  }

}

sealed class HomeIntent : MviIntent

sealed class HomeEffect : MviEffect

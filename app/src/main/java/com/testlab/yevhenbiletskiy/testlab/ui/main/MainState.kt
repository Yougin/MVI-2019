package com.testlab.yevhenbiletskiy.testlab.ui.main

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
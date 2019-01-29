package com.testlab.yevhenbiletskiy.testlab.ui.main

import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import io.reactivex.functions.BiFunction

object MainReducer {
  fun reduce(): BiFunction<MainState, Lce<out MainResult>, MainState> =
      BiFunction { viewState, lce ->
        when (lce) {
          is Lce.Loading -> onLoadingResult(viewState)
          is Lce.Content -> onContentResult(lce, viewState)
          is Lce.Error -> onErrorResult()
        }
      }

  private fun onLoadingResult(viewState: MainState) = viewState.copy(isLoading = true)

  private fun onContentResult(
      lce: Lce.Content<out MainResult>,
      viewState: MainState
  ): MainState = when (lce.packet) {
    is MainResult.InitialLoadResult -> viewState.copy(isLoading = false, text = lce.packet.text)
  }

  private fun onErrorResult(): MainState {
    TODO()
  }
}
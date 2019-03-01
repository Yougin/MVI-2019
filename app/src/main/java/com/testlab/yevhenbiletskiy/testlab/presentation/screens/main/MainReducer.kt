package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import io.reactivex.functions.BiFunction

// TODO-eugene it doesn't have to be a BiFunction, see what Roxie does:
// https://github.com/ww-tech/roxie/blob/master/roxie/src/main/kotlin/com/ww/roxie/Reducer.kt 
fun mainReducer(): BiFunction<MainState, Lce<out MainResult>, MainState> =
    BiFunction { viewState, result ->
      when (result) {
        is Lce.Loading -> onLoadingResult(viewState)
        is Lce.Content -> onContentResult(result, viewState)
        is Lce.Error -> onErrorResult()
      }
    }

private fun onLoadingResult(viewState: MainState) = viewState.copy(isLoading = true)

private fun onContentResult(
    result: Lce.Content<out MainResult>,
    viewState: MainState
): MainState = when (result.packet) {
  is MainResult.InitialLoadResult -> viewState.copy(isLoading = false, text = result.packet.text.value)
  is MainResult.LoginResult -> viewState.copy(userSession = result.packet.userSession.userSession)
}

private fun onErrorResult(): MainState {
  TODO()
}
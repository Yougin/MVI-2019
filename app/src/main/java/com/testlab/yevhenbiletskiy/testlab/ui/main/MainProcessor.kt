package com.testlab.yevhenbiletskiy.testlab.ui.main

import io.reactivex.ObservableTransformer

object MainProcessor {
  val process = ObservableTransformer<MainAction, MainResult> { upstream ->
    upstream.publish { shared ->
      shared.ofType(MainAction.InitialLoadAction::class.java).compose(onInitialLoadAction())
    }
  }

  private fun onInitialLoadAction() =
      ObservableTransformer<MainAction, MainResult> {
        it.map { MainResult.InitialLoadResult("Hello World!") }
      }
}
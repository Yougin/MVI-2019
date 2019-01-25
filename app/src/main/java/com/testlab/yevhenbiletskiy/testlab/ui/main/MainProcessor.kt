package com.testlab.yevhenbiletskiy.testlab.ui.main

import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

object MainProcessor {
  val process = ObservableTransformer<MainAction, Lce<out MainResult>> { upstream ->
    upstream.publish { shared ->
      shared.ofType(MainAction.FetchDataAction::class.java).onFetchDataAction()
    }
  }

  private fun Observable<out MainAction>.onFetchDataAction(): Observable<Lce<out MainResult>> {
    return map { Lce.Content(MainResult.InitialLoadResult("Hello World!")) }
  }

}
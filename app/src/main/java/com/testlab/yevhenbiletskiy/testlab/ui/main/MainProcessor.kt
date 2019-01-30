package com.testlab.yevhenbiletskiy.testlab.ui.main

import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

object MainProcessor {
  val process = ObservableTransformer<MainAction, Lce<out MainResult>> { upstream ->
    upstream.publish<Lce<out MainResult>> { shared ->
      shared.ofType(MainAction.FetchDataAction::class.java).onFetchDataAction()
    }
  }

  private fun Observable<out MainAction>.onFetchDataAction(): Observable<Lce<out MainResult>> =
      flatMap {
        Observable
            .just<Lce<MainResult.InitialLoadResult>>(Lce.Content(MainResult.InitialLoadResult("Hello World!")))
            .delay(3, TimeUnit.SECONDS)
            .startWith(Lce.Loading())
            .observeOn(AndroidSchedulers.mainThread())
      }
}
package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.domain

import arrow.core.Option
import io.reactivex.Observable
import javax.inject.Inject

interface GetMainData {
  operator fun invoke(): Observable<Option<MainText>>
}

class GetMainDataUseCase @Inject constructor() : GetMainData {

  override operator fun invoke(): Observable<Option<MainText>> {
    return Observable.fromCallable {
      Thread.sleep(3000)
      Option.just(MainText("Hello World, I'm Main Data."))
    }
  }
}

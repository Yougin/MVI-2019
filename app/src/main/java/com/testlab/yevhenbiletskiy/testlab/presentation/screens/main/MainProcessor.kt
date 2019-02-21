package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import arrow.core.getOrElse
import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.Processor
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.domain.GetMainData
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.domain.MainText
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.domain.UserSession
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.random.Random

class MainProcessor @Inject constructor(
    private val getMainData: GetMainData
) : Processor<MainIntent, Lce<out MainResult>> {

  override fun process() = ObservableTransformer<MainIntent, Lce<out MainResult>> { upstream ->
    upstream.publish<Lce<out MainResult>> { shared ->
      Observable.merge(
          shared.ofType(MainIntent.InitialIntent::class.java).onInitialIntentAction(),
          shared.ofType(MainIntent.LoginIntent::class.java).onLoginIntentAction())
    }
  }

  // TODO-eugene on error
  private fun Observable<out MainIntent>.onInitialIntentAction(): Observable<Lce<out MainResult>> =
      flatMap {
        getMainData()
            .map<Lce<MainResult>> {
              val text = it.getOrElse { MainText("") }
              Lce.Content(MainResult.InitialLoadResult(text))
            }
            .startWith(Lce.Loading())
            .subscribeOn(Schedulers.io())
      }
}

// TODO-eugene you can remove type
private fun Observable<out MainIntent>.onLoginIntentAction()
    : ObservableSource<Lce<out MainResult>> =
    flatMap {
      val userSession = UserSession("UserSession + ${Random.nextInt()}")
      val packet = MainResult.LoginResult(userSession)
      Observable
          .just(Lce.Content(packet))
          .subscribeOn(Schedulers.io())
    }



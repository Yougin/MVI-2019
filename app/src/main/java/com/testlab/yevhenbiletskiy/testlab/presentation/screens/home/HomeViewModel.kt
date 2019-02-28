package com.testlab.yevhenbiletskiy.testlab.presentation.screens.home

import android.arch.lifecycle.ViewModel
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.MviViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class HomeViewModel @Inject constructor(

) : ViewModel(),
    MviViewModel<HomeIntent, HomeState, HomeEffect> {

  override fun intents(intents: Observable<HomeIntent>): Disposable {
    return Observable.just("compile").subscribe()
  }

  override fun viewState(): Observable<HomeState> {
    return Observable.just(HomeState.idle())
  }

  override fun viewEffect(): Observable<HomeEffect> {
    return PublishSubject.create()
  }

}
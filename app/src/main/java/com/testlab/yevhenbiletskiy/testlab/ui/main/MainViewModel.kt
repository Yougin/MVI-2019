package com.testlab.yevhenbiletskiy.testlab.ui.main

import android.arch.lifecycle.ViewModel
import com.testlab.yevhenbiletskiy.testlab.mvi.MviViewModel
import io.reactivex.Observable

class MainViewModel : ViewModel(), MviViewModel<MainIntent, MainState> {

  override fun intents(intents: Observable<MainIntent>) {
  }

  override fun state(): Observable<MainState> {
    return Observable.empty()
  }

}
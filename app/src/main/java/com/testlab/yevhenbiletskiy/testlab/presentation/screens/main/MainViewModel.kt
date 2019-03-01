package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.BaseViewModel
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.Processor
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val mainProcessor: Processor<MainIntent, Lce<out MainResult>>
) : BaseViewModel<MainIntent, MainResult, MainState, MainViewEffect>() {

  override val processor: Processor<MainIntent, Lce<out MainResult>> get() = mainProcessor

  override val reducer get() = mainReducer()

  override val initialState get() = MainState.idle()

  override val initialIntent: Class<out MainIntent>
    get() = MainIntent.InitialIntent::class.java

  // TODO-eugene show Another Toast for button click
  // TODO-eugene publish in the parent 
  override fun resultToViewEffect() =
      ObservableTransformer<Lce<out MainResult>, MainViewEffect> { upstream ->
        upstream.publish { shared ->
          shared.filter { it is Lce.Content && it.packet is MainResult.InitialLoadResult }
              .cast(Lce.Content::class.java)
              .map<MainViewEffect> { MainViewEffect.ShowToastViewEffect("Initial Load completed") }
        }
      }
}

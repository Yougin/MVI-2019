package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.BaseViewModel
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.Processor
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class MainViewModel @Inject constructor(
    processor: Processor<MainIntent, Lce<out MainResult>>
) : BaseViewModel<MainIntent, MainResult, MainState, MainViewEffect>(processor) {

  override val defaultState get() = MainState.idle()

  override val reducer get() = mainReducer()

  override val initialIntent: Class<out MainIntent>
    get() = MainIntent.InitialIntent::class.java

  // TODO-eugene show Another Toast for button click
  override fun resultToViewEffect() =
      ObservableTransformer<Lce<out MainResult>, MainViewEffect> { upstream ->
        upstream.publish { shared ->
          shared.filter { it is Lce.Content && it.packet is MainResult.InitialLoadResult }
              .cast(Lce.Content::class.java)
              .map<MainViewEffect> { MainViewEffect.ShowToastViewEffect("Initial Load completed") }
        }
      }

}

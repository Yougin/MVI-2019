package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import com.testlab.yevhenbiletskiy.testlab.domain.Lce
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.MviViewModel
import com.testlab.yevhenbiletskiy.testlab.presentation.mvi.Processor
import io.reactivex.ObservableTransformer
import javax.inject.Inject

// TODO-eugene consider moving things up like Roxie does
class MainViewModel @Inject constructor(
    processor: Processor<MainIntent, Lce<out MainResult>>
) : MviViewModel<MainIntent, MainState, MainEffect, MainResult>(
    processor,
    MainIntent.InitialIntent::class.java
) {

  override fun getDefaultState() = MainState.idle()

  override fun getReducer() = mainReducer()

  // TODO-eugene show Another Toast for button click
  override fun resultToViewEffect() =
      ObservableTransformer<Lce<out MainResult>, MainEffect> { upstream ->
        upstream.publish { shared ->
          shared.filter { it is Lce.Content && it.packet is MainResult.InitialLoadResult }
              .cast(Lce.Content::class.java)
              .map<MainEffect> { MainEffect.ShowToastEffect("Initial Load completed") }
        }
      }

}

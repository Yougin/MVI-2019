package com.testlab.yevhenbiletskiy.testlab.ui.main

import com.testlab.yevhenbiletskiy.testlab.mvi.MviIntent

sealed class MainIntent : MviIntent {

  object InitialIntent : MainIntent()
}
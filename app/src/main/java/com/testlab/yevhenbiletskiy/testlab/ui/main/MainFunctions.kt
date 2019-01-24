package com.testlab.yevhenbiletskiy.testlab.ui.main

import com.testlab.yevhenbiletskiy.testlab.mvi.MviIntent

sealed class MainIntent : MviIntent {
  object InitialIntent : MainIntent()
}

sealed class MainAction {
  object InitialLoadAction : MainAction()
}

sealed class MainResult {
  // TODO-eugene why to use constructor MainResult()
  data class InitialLoadResult(val text: String) : MainResult()
}
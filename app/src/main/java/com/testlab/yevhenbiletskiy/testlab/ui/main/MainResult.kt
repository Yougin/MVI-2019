package com.testlab.yevhenbiletskiy.testlab.ui.main

sealed class MainResult {
  // TODO-eugene why to use constructor MainResult()
  data class InitialLoadResult(val text: String) : MainResult()
}

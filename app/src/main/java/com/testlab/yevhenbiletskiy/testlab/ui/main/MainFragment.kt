package com.testlab.yevhenbiletskiy.testlab.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testlab.yevhenbiletskiy.testlab.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlin.LazyThreadSafetyMode.NONE

class MainFragment : Fragment() {

  companion object {
    fun newInstance() = MainFragment()
  }

  private val disposables = CompositeDisposable()

  // TODO-eugene what is NONE
  private val viewModel: MainViewModel by lazy(NONE) {
    ViewModelProviders.of(this).get(MainViewModel::class.java)
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View {
    return inflater.inflate(R.layout.main_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    disposables.add(viewModel.state().subscribe { render(it) })
    viewModel.intents(intents())
  }

  private fun intents(): Observable<MainIntent> {
    return Observable.just(MainIntent.InitialIntent)
  }

  private fun render(viewModel: MainState) { // TODO-eugene implement me
  }

}

package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding3.view.clicks
import com.testlab.yevhenbiletskiy.testlab.R
import com.testlab.yevhenbiletskiy.testlab.presentation.App
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.main_fragment.*
import timber.log.Timber
import javax.inject.Inject

class MainFragment : Fragment() {

  companion object {
    fun newInstance() = MainFragment()
  }

  private val disposables = CompositeDisposable()

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var viewModel: MainViewModel

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View = inflater.inflate(R.layout.main_fragment, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    app().component.getMainFragmentComponent().inject(this)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
  }

  override fun onResume() {
    super.onResume()
    disposables.addAll(
        observeViewState(),
        observeViewEffects(),
        viewModel.intents(intents())
    )
  }

  private fun observeViewState() =
      viewModel.viewState
          .observeOn(AndroidSchedulers.mainThread())
          .doOnNext { Timber.d("----- ViewState: $it") }
          .subscribe { renderState(it) }

  private fun observeViewEffects() =
      viewModel.viewEffect
          .observeOn(AndroidSchedulers.mainThread())
          .doOnNext { Timber.d("----- ViewEffect: $it") }
          .subscribe { renderEffect(it) }

  private fun renderEffect(effect: MainViewEffect) {
    when (effect) {
      is MainViewEffect.ShowToastViewEffect -> Toast.makeText(this.context, effect.text, Toast.LENGTH_LONG).show()
    }
  }

  private fun app() = activity?.applicationContext as App

  private fun intents(): Observable<MainIntent> {
    return Observable.merge(
        Observable.just(MainIntent.InitialIntent),
        loginButton.clicks().map { MainIntent.LoginIntent }
    )
  }

  private fun renderState(viewState: MainState) {
    progressBar.visibility = if (viewState.isLoading) View.VISIBLE else View.GONE
    message.text = viewState.userSession?.let { it } ?: viewState.text
  }

  override fun onStop() {
    super.onStop()
    disposables.clear()
  }

}

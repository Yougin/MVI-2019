package com.testlab.yevhenbiletskiy.testlab.presentation.screens.main

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testlab.yevhenbiletskiy.testlab.R
import com.testlab.yevhenbiletskiy.testlab.presentation.App
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.main_fragment.*
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

    disposables.addAll(
        viewModel.viewState().observeOn(AndroidSchedulers.mainThread()).subscribe { render(it) },
        viewModel.intents(intents())
    )
  }

  private fun app() = activity?.applicationContext.let { it as App }

  private fun intents(): Observable<MainIntent> {
    return Observable.just(MainIntent.InitialIntent)
  }

  private fun render(viewModel: MainState) {
    progressBar.visibility = if (viewModel.isLoading) View.VISIBLE else View.GONE
    message.text = viewModel.text
  }

}

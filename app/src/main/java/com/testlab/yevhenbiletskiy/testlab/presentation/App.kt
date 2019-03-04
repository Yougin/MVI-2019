package com.testlab.yevhenbiletskiy.testlab.presentation

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree


open class App : Application() {
  // TODO-eugene make it accessible like using companion object like it is made in
  // https://medium.com/androiddevelopers/dependency-injection-in-a-multi-module-project-1a09511c14b7
  open lateinit var component: AppComponent

  override fun onCreate() {
    super.onCreate()
    setupTimber()

    component = DaggerAppComponent.builder()
      .appModule(AppModule(this)).build().also { it.inject(this) }
  }

  private fun setupTimber() {
    Timber.plant(DebugTree())
  }

}
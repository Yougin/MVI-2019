package com.testlab.yevhenbiletskiy.testlab.presentation

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree


open class App : Application() {
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
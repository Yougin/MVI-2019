package com.testlab.yevhenbiletskiy.testlab

import android.app.Application
import com.testlab.yevhenbiletskiy.testlab.ui.main.MainFragment
import dagger.Module
import dagger.Subcomponent
import javax.inject.Scope

open class App : Application() {
  open lateinit var component: AppComponent

  override fun onCreate() {
    super.onCreate()
    component = DaggerAppComponent.builder().appModule(AppModule(this)).build().also { it.inject(this) }
  }

}
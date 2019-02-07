package com.testlab.yevhenbiletskiy.testlab.presentation

import android.content.Context
import com.testlab.yevhenbiletskiy.testlab.presentation.screens.main.MainFragmentComponent
import dagger.*
import javax.inject.Scope
import javax.inject.Singleton

@Scope
annotation class MainFragmentScope

@Singleton
@Component(modules = [
  AppModule::class
])
interface AppComponent {
  fun inject(app: App)

  fun getMainFragmentComponent(): MainFragmentComponent
}

@Module
class AppModule(private val app: Context) {
  @Provides
  @Singleton
  fun provideApplicationContext(): Context = app
}

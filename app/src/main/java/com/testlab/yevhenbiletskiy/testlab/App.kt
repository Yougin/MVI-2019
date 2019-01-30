package com.testlab.yevhenbiletskiy.testlab

import android.app.Application
import android.content.Context
import com.testlab.yevhenbiletskiy.testlab.ui.main.MainFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope
import javax.inject.Singleton

open class App : Application() {
  open lateinit var component: AppComponent

  override fun onCreate() {
    super.onCreate()
    component = DaggerAppComponent.builder().appModule(AppModule(this)).build().also { it.inject(this) }
  }

}

@Singleton
@Component(modules = [(AppModule::class)])
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

@Scope
annotation class MainFragmentScope

@Subcomponent(modules = [(MainFragmentModule::class)])
interface MainFragmentComponent {
  fun inject(activity: MainFragment)
}

@Module()
class MainFragmentModule {

}
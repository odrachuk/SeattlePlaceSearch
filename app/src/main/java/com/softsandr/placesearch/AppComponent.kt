package com.softsandr.placesearch

import android.app.Application
import com.softsandr.placesearch.api.ApiModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Oleksandr Drachuk on 27.03.19.
 */
@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
}
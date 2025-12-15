package com.example.reciclai

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Application class para o app Android.
 *
 * @HiltAndroidApp é necessário para habilitar a injeção de dependências do Hilt.
 * Implementa Configuration.Provider para integrar WorkManager com Hilt.
 */
@HiltAndroidApp
class ReciclaiApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        // Inicializações globais aqui, se necessário
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}

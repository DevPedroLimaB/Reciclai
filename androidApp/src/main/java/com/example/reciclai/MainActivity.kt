package com.example.reciclai

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.reciclai.navigation.ReciclaiNavigation
import com.example.reciclai.service.LocationSyncService
import com.example.reciclai.ui.theme.ReciclaiTheme
import com.example.reciclai.worker.DataSyncWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import android.content.Intent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager

/**
 * MainActivity - Ponto de entrada do app Android Reciclai.
 *
 * GERENCIAMENTO DE ATIVIDADE:
 * - @AndroidEntryPoint habilita injeção de dependências do Hilt
 * - Edge-to-edge COMPLETO - sem barras, conteúdo na tela toda
 * - Gerencia ciclo de vida (onCreate, onStart, onStop, onDestroy)
 * - Inicializa serviços em background
 * - Controla WorkManager para tarefas periódicas
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Remove TODAS as barras do sistema - tela cheia
        setupFullScreenMode()

        Log.d(TAG, "onCreate: Iniciando MainActivity")

        // Inicializa serviços em background
        initBackgroundServices()

        setContent {
            ReciclaiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReciclaiNavigation()
                }
            }
        }
    }

    /**
     * Configura o modo tela cheia sem barras do sistema
     */
    private fun setupFullScreenMode() {
        // Habilita edge-to-edge
        enableEdgeToEdge()

        // Remove decoração da janela (barras do sistema)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Para Android 11+ (API 30+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
            // Define as barras como transparentes
            window.statusBarColor = android.graphics.Color.TRANSPARENT
            window.navigationBarColor = android.graphics.Color.TRANSPARENT
        } else {
            // Para versões anteriores
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
            // Define as barras como transparentes
            window.statusBarColor = android.graphics.Color.TRANSPARENT
            window.navigationBarColor = android.graphics.Color.TRANSPARENT
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Activity visível ao usuário")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Activity em primeiro plano, usuário pode interagir")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity perdendo foco, salvar dados se necessário")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Activity não está mais visível")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Finalizando Activity")
        // Serviços continuam rodando em background
    }

    /**
     * Inicializa serviços em background:
     * 1. LocationSyncService - Serviço tradicional para sync contínuo
     * 2. DataSyncWorker - WorkManager para tarefas periódicas confiáveis
     */
    private fun initBackgroundServices() {
        lifecycleScope.launch {
            try {
                // 1. Inicia serviço tradicional de sincronização
                startLocationSyncService()

                // 2. Agenda Worker para sincronização periódica
                scheduleDataSyncWorker()

                Log.d(TAG, "Serviços em background inicializados com sucesso")
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao inicializar serviços em background", e)
            }
        }
    }

    /**
     * Inicia o LocationSyncService que roda continuamente em background
     */
    private fun startLocationSyncService() {
        val serviceIntent = Intent(this, LocationSyncService::class.java)
        startService(serviceIntent)
        Log.d(TAG, "LocationSyncService iniciado")
    }

    /**
     * Agenda o DataSyncWorker para executar periodicamente
     * usando WorkManager (mais confiável e respeitando bateria)
     */
    private fun scheduleDataSyncWorker() {
        val syncWorkRequest = PeriodicWorkRequestBuilder<DataSyncWorker>(
            repeatInterval = 15, // A cada 15 minutos (mínimo permitido)
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            DataSyncWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, // Mantém o trabalho existente
            syncWorkRequest
        )

        Log.d(TAG, "DataSyncWorker agendado para execução periódica")
    }
}

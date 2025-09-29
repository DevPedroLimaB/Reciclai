package com.example.reciclai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.reciclai.ui.navigation.ReciclaiNavigation
import com.example.reciclai.ui.screen.auth.AuthScreen
import com.example.reciclai.ui.theme.ReciclaiTheme
import com.example.reciclai.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val authViewModel: AuthViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            ReciclaiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
                    
                    if (isLoggedIn) {
                        ReciclaiNavigation()
                    } else {
                        AuthScreen(authViewModel = authViewModel)
                    }
                }
            }
        }
    }
}
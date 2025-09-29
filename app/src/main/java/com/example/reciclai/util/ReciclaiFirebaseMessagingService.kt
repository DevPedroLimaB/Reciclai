package com.example.reciclai.util

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReciclaiFirebaseMessagingService : FirebaseMessagingService() {
    
    @Inject
    lateinit var preferencesManager: PreferencesManager
    
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        preferencesManager.setFcmToken(token)
        // TODO: Send token to server
    }
    
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        // Handle notification data
        remoteMessage.notification?.let { notification ->
            showNotification(
                title = notification.title ?: "",
                body = notification.body ?: "",
                data = remoteMessage.data
            )
        }
    }
    
    private fun showNotification(title: String, body: String, data: Map<String, String>) {
        // TODO: Implement notification display logic
    }
}
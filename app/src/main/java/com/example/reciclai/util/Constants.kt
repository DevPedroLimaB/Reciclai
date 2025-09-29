package com.example.reciclai.util

object Constants {
    const val BASE_URL = "https://your-api-url.com/api/v1/"
    const val SHARED_PREFS_NAME = "reciclai_prefs"
    const val AUTH_TOKEN_KEY = "auth_token"
    const val REFRESH_TOKEN_KEY = "refresh_token"
    const val USER_ID_KEY = "user_id"
    const val FCM_TOKEN_KEY = "fcm_token"
    
    // Map constants
    const val DEFAULT_ZOOM = 15f
    const val DEFAULT_LATITUDE = -23.5505
    const val DEFAULT_LONGITUDE = -46.6333
    
    // Request codes
    const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    const val CAMERA_PERMISSION_REQUEST_CODE = 1002
    const val GOOGLE_SIGN_IN_REQUEST_CODE = 1003
    
    // Material categories
    val MATERIAL_CATEGORIES = listOf(
        "Papel",
        "Plástico", 
        "Metal",
        "Vidro",
        "Eletrônicos",
        "Orgânico",
        "Óleo de Cozinha",
        "Pilhas e Baterias"
    )
    
    // Time slots for scheduling
    val TIME_SLOTS = listOf(
        "08:00 - 10:00",
        "10:00 - 12:00", 
        "14:00 - 16:00",
        "16:00 - 18:00"
    )
    
    // Points per kg by material
    val POINTS_PER_KG = mapOf(
        "Papel" to 10,
        "Plástico" to 15,
        "Metal" to 25,
        "Vidro" to 12,
        "Eletrônicos" to 50,
        "Óleo de Cozinha" to 30,
        "Pilhas e Baterias" to 100
    )
}
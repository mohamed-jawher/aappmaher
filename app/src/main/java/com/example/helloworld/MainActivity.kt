package com.example.helloworld

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.helloworld.ui.theme.HelloWorldTheme

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(getSharedPreferences("auth_prefs", Context.MODE_PRIVATE))
    }
    private val dbHelper = FeedReaderDbHelper(this);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloWorldTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(authViewModel = authViewModel, modifier = Modifier.padding(innerPadding), dbHelper = dbHelper)
                }
            }
        }
    }
}

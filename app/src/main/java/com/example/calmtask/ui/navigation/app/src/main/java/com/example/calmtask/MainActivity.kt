package com.example.calmtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.calmtask.ui.navigation.AppNavGraph
import com.example.calmtask.ui.theme.CalmTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalmTaskTheme {
                AppNavGraph()
            }
        }
    }
}

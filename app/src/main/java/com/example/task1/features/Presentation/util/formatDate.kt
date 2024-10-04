package com.example.task1.features.Presentation.util

import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun formatDate(timestamp: String): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    val date = sdf.format(timestamp.toLong())
    return date
}
package com.bez.newsfeedtabs.ui.screen_info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ScreenA(
    onNavigateToScreenB: () -> Unit
) {
    val viewModel: ScreenAViewModel = hiltViewModel() // Automatically injects ViewModel with Hilt
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Name: ${uiState.userName}", style = MaterialTheme.typography.bodySmall)
        Text(text = "Last Entry: ${uiState.lastEntryTime}", style = MaterialTheme.typography.bodyLarge)

        // Display the last clicked news title
        Text(
            text = "Last News Clicked: ${uiState.lastClickedNewsTitle ?: "None"}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onNavigateToScreenB) {
            Text("Go to Screen B")
        }
    }
}

package com.example.appartmentapp.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.appartmentapp.domain.model.Coin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                actions = {
                    TextButton(onClick = { viewModel.logout(onLogout) }) {
                        Text("Logout")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (val state = uiState) {
                is DashboardUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is DashboardUiState.Success -> {
                    val data = state.data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            Text(
                                text = data.welcomeMessage,
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                        item {
                            Text(
                                text = "Notifications: ${data.notificationsCount}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        
                        if (data.topCoins.isNotEmpty()) {
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Market Trends (Top Coins):",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                            items(data.topCoins) { coin ->
                                CoinItem(coin)
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Upcoming Events:",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        items(data.upcomingEvents) { event ->
                            Card(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = event,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
                is DashboardUiState.Error -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                        Button(onClick = { viewModel.loadDashboard() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CoinItem(coin: Coin) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = coin.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = coin.symbol.uppercase(), style = MaterialTheme.typography.bodySmall)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "$${coin.currentPrice}", style = MaterialTheme.typography.bodyLarge)
                val color = if (coin.priceChangePercentage24h >= 0) Color(0xFF4CAF50) else Color.Red
                Text(
                    text = "${if (coin.priceChangePercentage24h >= 0) "+" else ""}${coin.priceChangePercentage24h}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = color
                )
            }
        }
    }
}

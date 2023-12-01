package com.example.discard.screens
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun DeviceDetailScreen(viewModel: DeviceDetailViewModel) {
    // Represents the UI state
    val uiState by viewModel.uiState.collectAsState()

    Column {
        Text(text = "Device Address: ${uiState.deviceAddress}")
        Text(text = "Device Info: ${uiState.deviceInfo}")
        Text(text = "Group Owner: ${uiState.groupOwner}")
        Text(text = "Status: ${uiState.statusText}")

        Button(
            onClick = { viewModel.connect() },
            modifier = Modifier.visibility(uiState.connectButtonVisible)
        ) {
            Text("Connect")
        }

        Button(
            onClick = { viewModel.disconnect() },
            modifier = Modifier.visibility(!uiState.connectButtonVisible)
        ) {
            Text("Disconnect")
        }

        Button(
            onClick = { viewModel.startClient() },
            modifier = Modifier.visibility(uiState.startClientButtonVisible)
        ) {
            Text("Start Client")
        }
    }
}

@Stable
data class UiState(
    val deviceAddress: String = "",
    val deviceInfo: String = "",
    val groupOwner: String = "",
    val statusText: String = "",
    val connectButtonVisible: Boolean = true,
    val startClientButtonVisible: Boolean = false
)

class DeviceDetailViewModel : ViewModel() {
    val uiState = MutableStateFlow(UiState())

    // TODO: Implement these using Android's WifiP2p APIs
    fun connect() { /* ... */ }

    fun disconnect() { /* ... */ }

    fun startClient() { /* ... */ }
}

@Composable
fun Modifier.visibility(isVisible: Boolean): Modifier {
    return if (isVisible) this else this.alpha(0f)
}

@Preview
@Composable
fun PreviewDeviceDetailScreen() {
    DeviceDetailScreen(DeviceDetailViewModel())
}
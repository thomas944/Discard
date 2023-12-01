package com.example.discard.screens

// DeviceListScreen.kt

import android.net.wifi.p2p.WifiP2pDevice
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter

@Composable
fun DeviceListScreen(devices: List<WifiP2pDevice>, onDeviceClick: (WifiP2pDevice) -> Unit) {
    // Implementation of DeviceListScreen composable
    LazyColumn {
        items(devices) { device ->
            DeviceItem(device = device, onDeviceClick = onDeviceClick)
        }
    }
}

@Composable
fun DeviceItem(device: WifiP2pDevice, onDeviceClick: (WifiP2pDevice) -> Unit) {
    // Implementation of DeviceItem composable
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDeviceClick(device) }
            .padding(16.dp)
    ) {
        Text(text = "Device Name: ${device.deviceName}")
        Text(text = "Device Address: ${device.deviceAddress}")
        // Add more device details if needed
    }
}

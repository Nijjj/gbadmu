package app.y2kboy.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.y2kboy.data.DisplaySettings
import app.y2kboy.ui.components.AmbientBackground
import app.y2kboy.ui.components.GlassPanel

@Composable
fun SettingsScreen(displaySettings: DisplaySettings) {
    val sections = listOf(
        "Appearance" to listOf("Silver, Blue, Purple, Green, Dynamic", "Glass opacity, reflections, motion"),
        "Display" to listOf("Integer scaling, pixel perfect, fit/fill", "CRT, LCD, scanlines, sharpness"),
        "Controls" to listOf("Opacity, size, profile editor", "Portrait and landscape layouts"),
        "Audio" to listOf("Master volume, latency, sync", "Fast-forward volume behavior"),
        "Performance" to listOf("Battery saver, balanced, performance", "Rewind duration and memory budget"),
        "Saves" to listOf("Auto-save, export, backup retention", "Cloud-ready storage layer"),
        "Cheats" to listOf("Per-game lists", "GameShark and Action Replay support"),
        "Diagnostics" to listOf("Renderer, core version, export diagnostics", "Session history and crash context"),
    )

    AmbientBackground(
        colors = listOf(Color(0xFF221821), Color(0xFF31536D), Color(0xFF90D1FF)),
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Text("System Settings", style = MaterialTheme.typography.titleLarge)
                Text(
                    "Live theme controls and emulator configuration in one surface.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.72f),
                )
            }

            item {
                GlassPanel(modifier = Modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Dynamic Color Intensity", style = MaterialTheme.typography.titleMedium)
                        Slider(value = displaySettings.gradientIntensity, onValueChange = {})
                        SettingToggle("Dynamic theme from active frame", true)
                        SettingToggle("Ambient blur in unused space", true)
                    }
                }
            }

            items(sections) { (title, lines) ->
                GlassPanel(modifier = Modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(title, style = MaterialTheme.typography.titleMedium)
                        lines.forEach { line ->
                            Text(line, style = MaterialTheme.typography.bodyMedium, color = Color.White.copy(alpha = 0.74f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingToggle(label: String, checked: Boolean) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label, modifier = Modifier.padding(end = 12.dp))
        Switch(checked = checked, onCheckedChange = {})
    }
}

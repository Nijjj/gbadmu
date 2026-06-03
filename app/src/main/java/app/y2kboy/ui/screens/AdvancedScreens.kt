package app.y2kboy.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.y2kboy.data.GameRom
import app.y2kboy.data.SaveStateEntry
import app.y2kboy.ui.components.AmbientBackground
import app.y2kboy.ui.components.ChromeButton
import app.y2kboy.ui.components.GlassPanel

@Composable
fun RomDetailScreen(rom: GameRom) {
    AmbientBackground(
        colors = listOf(Color(0xFF173244), Color(0xFF62E7FF), Color(0xFF8BFFB2)),
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            GlassPanel(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(rom.title, style = MaterialTheme.typography.titleLarge)
                    Text("${rom.platform} - ${rom.hash.ifBlank { "hash pending" }}", color = Color.White.copy(alpha = 0.72f))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ChromeButton(label = "Play", onClick = {}) { Icon(Icons.Outlined.FlashOn, contentDescription = null) }
                        ChromeButton(label = "Favorite", onClick = {}) { Icon(Icons.Outlined.Star, contentDescription = null) }
                    }
                }
            }
            GlassPanel(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("ROM information", style = MaterialTheme.typography.titleMedium)
                    Text("Playtime, last played, cover art, file details, and per-game overrides live here.")
                }
            }
        }
    }
}

@Composable
fun SaveBrowserScreen(saves: List<SaveStateEntry>) {
    AmbientBackground(
        colors = listOf(Color(0xFF1D2030), Color(0xFF56C5FF), Color(0xFF88FFB9)),
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Text("Save Browser", style = MaterialTheme.typography.titleLarge)
            }
            items(saves) { save ->
                GlassPanel(modifier = Modifier.fillMaxWidth()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Icon(Icons.Outlined.Save, contentDescription = null)
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Slot ${save.slot}", style = MaterialTheme.typography.titleMedium)
                            Text(save.path, color = Color.White.copy(alpha = 0.72f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CheatBrowserScreen() {
    AmbientBackground(
        colors = listOf(Color(0xFF22182F), Color(0xFFBA9CFF), Color(0xFF66D9FF)),
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Cheat Browser", style = MaterialTheme.typography.titleLarge)
            GlassPanel(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("GameShark, Action Replay, and per-game cheat lists.")
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ChromeButton(label = "Add", onClick = {}) { Icon(Icons.Outlined.Edit, contentDescription = null) }
                        ChromeButton(label = "Import", onClick = {}) { Icon(Icons.Outlined.Download, contentDescription = null) }
                        ChromeButton(label = "Search", onClick = {}) { Icon(Icons.Outlined.Search, contentDescription = null) }
                    }
                }
            }
        }
    }
}

@Composable
fun ControllerEditorScreen() {
    AmbientBackground(
        colors = listOf(Color(0xFF16202B), Color(0xFF54B1FF), Color(0xFF8DFFE0)),
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Controller Layout Editor", style = MaterialTheme.typography.titleLarge)
            GlassPanel(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Opacity, size, position, portrait, landscape, haptics, and external controller profiles.")
                    ChromeButton(label = "Edit Layout", onClick = {}) { Icon(Icons.Outlined.Edit, contentDescription = null) }
                }
            }
        }
    }
}

@Composable
fun ThemePreviewScreen() {
    AmbientBackground(
        colors = listOf(Color(0xFF1C2630), Color(0xFF62E7FF), Color(0xFF8BFFB2)),
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Theme Preview", style = MaterialTheme.typography.titleLarge)
            Text("Silver, Blue, Purple, Green, and dynamic game-driven palettes.")
        }
    }
}

@Composable
fun DiagnosticsScreen() {
    AmbientBackground(
        colors = listOf(Color(0xFF101216), Color(0xFF314F66), Color(0xFF8BF4F8)),
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Diagnostics", style = MaterialTheme.typography.titleLarge)
            GlassPanel(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Core version, renderer, session metrics, and exportable logs.")
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ChromeButton(label = "Export", onClick = {}) { Icon(Icons.Outlined.Download, contentDescription = null) }
                        ChromeButton(label = "Clear", onClick = {}) { Icon(Icons.Outlined.Delete, contentDescription = null) }
                        ChromeButton(label = "Info", onClick = {}) { Icon(Icons.Outlined.Info, contentDescription = null) }
                    }
                }
            }
        }
    }
}

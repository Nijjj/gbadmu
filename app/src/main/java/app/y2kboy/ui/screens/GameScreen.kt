package app.y2kboy.ui.screens

import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.FastForward
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.y2kboy.data.DisplaySettings
import app.y2kboy.input.GbaButton
import app.y2kboy.nativebridge.EmulatorBridge
import app.y2kboy.core.paletteForGame
import app.y2kboy.ui.components.AmbientBackground
import app.y2kboy.ui.components.ChromeButton
import app.y2kboy.ui.components.GlassPanel

@Composable
fun GameScreen(
    displaySettings: DisplaySettings,
    onExit: () -> Unit,
) {
    val bridge = remember { EmulatorBridge() }
    var menuOpen by remember { mutableStateOf(true) }
    var paused by remember { mutableStateOf(false) }
    val palette = paletteForGame("Pokemon Emerald")
    val ambientColors = if (displaySettings.dynamicColor) {
        listOf(palette.primary, palette.secondary, palette.accent)
    } else {
        listOf(Color(0xFF10382E), Color(0xFF32A88F), Color(0xFF8FFFBE))
    }

    DisposableEffect(Unit) {
        onDispose {
            bridge.pause()
        }
    }

    AmbientBackground(
        colors = ambientColors,
        intensity = displaySettings.gradientIntensity,
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(Modifier.fillMaxSize().padding(18.dp)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    GlassPanel(padding = PaddingValues(horizontal = 14.dp, vertical = 10.dp)) {
                        Text("Pokemon Emerald", style = MaterialTheme.typography.titleMedium)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ChromeButton(label = "Fast", onClick = { bridge.setFastForward(true, 2.0f) }) {
                            Icon(Icons.Outlined.FastForward, contentDescription = null)
                        }
                        ChromeButton(label = "Menu", onClick = { menuOpen = !menuOpen }) {
                            Icon(Icons.Outlined.Menu, contentDescription = null)
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .aspectRatio(240f / 160f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Black.copy(alpha = 0.28f))
                            .border(1.dp, Color.White.copy(alpha = 0.34f), RoundedCornerShape(8.dp)),
                    ) {
                        AndroidView(
                            factory = { context ->
                                SurfaceView(context).apply {
                                    holder.addCallback(object : SurfaceHolder.Callback {
                                        override fun surfaceCreated(holder: SurfaceHolder) {
                                            bridge.attachSurface(holder.surface)
                                        }
                                        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) = Unit
                                        override fun surfaceDestroyed(holder: SurfaceHolder) = Unit
                                    })
                                }
                            },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }

                TransparentControls(
                    onPress = { button -> bridge.setInput(button.mask) },
                    onRelease = { bridge.setInput(0) },
                )
            }

            if (menuOpen) {
                InGameMenu(
                    bridge = bridge,
                    paused = paused,
                    onResume = {
                        paused = false
                        bridge.resume()
                        menuOpen = false
                    },
                    onPause = {
                        paused = true
                        bridge.pause()
                    },
                    onReset = bridge::reset,
                    onExit = onExit,
                )
            }
        }
    }
}

@Composable
private fun TransparentControls(
    onPress: (GbaButton) -> Unit,
    onRelease: (GbaButton) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        ControlCluster(
            labels = listOf("Up" to GbaButton.Up, "Left" to GbaButton.Left, "Right" to GbaButton.Right, "Down" to GbaButton.Down),
            onPress = onPress,
            onRelease = onRelease,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
            CircularControl("B", GbaButton.B, onPress, onRelease)
            CircularControl("A", GbaButton.A, onPress, onRelease)
        }
    }
}

@Composable
private fun ControlCluster(
    labels: List<Pair<String, GbaButton>>,
    onPress: (GbaButton) -> Unit,
    onRelease: (GbaButton) -> Unit,
) {
    GlassPanel(padding = PaddingValues(12.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            labels.chunked(2).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    row.forEach { (label, button) ->
                        CircularControl(label, button, onPress, onRelease)
                    }
                }
            }
        }
    }
}

@Composable
private fun CircularControl(
    label: String,
    button: GbaButton,
    onPress: (GbaButton) -> Unit,
    onRelease: (GbaButton) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(68.dp)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    listOf(
                        Color.White.copy(alpha = 0.22f),
                        Color(0x66D6F8FF),
                        Color(0x33444B59),
                    )
                )
            )
            .border(1.dp, Color.White.copy(alpha = 0.24f), CircleShape)
            .pressable(onPress = { onPress(button) }, onRelease = { onRelease(button) }),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = Color.White.copy(alpha = 0.92f))
    }
}

@Composable
private fun InGameMenu(
    bridge: EmulatorBridge,
    paused: Boolean,
    onResume: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    onExit: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        GlassPanel(
            modifier = Modifier.fillMaxWidth(0.86f),
            padding = PaddingValues(18.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text("Control Deck", style = MaterialTheme.typography.titleLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    ChromeButton(label = "Resume", onClick = onResume) {
                        Icon(Icons.Outlined.PlayArrow, contentDescription = null)
                    }
                    ChromeButton(label = "Save", onClick = { bridge.saveState(0); Unit }) {
                        Icon(Icons.Outlined.Save, contentDescription = null)
                    }
                    ChromeButton(label = "Load", onClick = { bridge.loadState(0); Unit }) {
                        Icon(Icons.Outlined.Download, contentDescription = null)
                    }
                    ChromeButton(label = "Shot", onClick = { bridge.captureScreenshot(); Unit }) {
                        Icon(Icons.Outlined.CameraAlt, contentDescription = null)
                    }
                    ChromeButton(label = "1x", onClick = { bridge.setFastForward(false, 1.0f) }) {
                        Icon(Icons.Outlined.FastForward, contentDescription = null)
                    }
                    ChromeButton(label = "Exit", onClick = onExit) {
                        Icon(Icons.Outlined.Menu, contentDescription = null)
                    }
                    ChromeButton(label = if (paused) "Paused" else "Pause", onClick = onPause) {
                        Icon(Icons.Outlined.Pause, contentDescription = null)
                    }
                    ChromeButton(label = "Reset", onClick = onReset) {
                        Icon(Icons.Outlined.Refresh, contentDescription = null)
                    }
                }
                MenuSection(title = "State") { Text("Save state, load state, and auto-save management are scaffolded for native wiring.") }
                MenuSection(title = "Display") { Text("Dynamic gradient, blur, integer scaling, shaders, scanlines, color correction.") }
                MenuSection(title = "Controls") { Text("Opacity, profiles, haptics, layout editor, controller mappings.") }
                MenuSection(title = "Cheats") { Text("GameShark and Action Replay lists stored per game.") }
                MenuSection(title = "Session") { Text("Screenshot, ROM info, playtime, FPS, performance mode, exit game.") }
            }
        }
    }
}

@Composable
private fun MenuSection(title: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        content()
    }
}

package app.y2kboy.core

import androidx.compose.runtime.Immutable
import app.y2kboy.data.AudioSettings
import app.y2kboy.data.ControlSettings
import app.y2kboy.data.DisplaySettings
import app.y2kboy.data.GameRom
import app.y2kboy.data.sampleLibrary
import app.y2kboy.data.PerformanceSettings

@Immutable
data class Y2KBoyAppState(
    val library: List<GameRom> = sampleLibrary,
    val displaySettings: DisplaySettings = DisplaySettings(),
    val controlSettings: ControlSettings = ControlSettings(),
    val audioSettings: AudioSettings = AudioSettings(),
    val performanceSettings: PerformanceSettings = PerformanceSettings(),
)

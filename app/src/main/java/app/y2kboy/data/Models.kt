package app.y2kboy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roms")
data class GameRom(
    @PrimaryKey val id: String,
    val title: String,
    val platform: String = "GBA",
    val uri: String = "",
    val hash: String = "",
    val coverUri: String? = null,
    val favorite: Boolean = false,
    val lastPlayedEpochMillis: Long = 0,
    val playtimeSeconds: Long = 0,
)

@Entity(tableName = "save_states")
data class SaveStateEntry(
    @PrimaryKey val id: String,
    val romId: String,
    val slot: Int,
    val path: String,
    val thumbnailPath: String? = null,
    val createdEpochMillis: Long,
)

@Entity(tableName = "cheats")
data class CheatEntry(
    @PrimaryKey val id: String,
    val romId: String,
    val name: String,
    val code: String,
    val type: CheatType,
    val enabled: Boolean,
)

enum class CheatType {
    GameShark,
    ActionReplay,
    CodeBreaker,
    VbaStyle,
}

data class DisplaySettings(
    val dynamicColor: Boolean = true,
    val gradientIntensity: Float = 0.78f,
    val blurIntensity: Float = 0.58f,
    val integerScaling: Boolean = false,
    val pixelPerfect: Boolean = false,
    val scaleMode: ScaleMode = ScaleMode.Fit,
    val shader: ShaderMode = ShaderMode.Lcd,
    val brightness: Float = 1.0f,
    val sharpness: Float = 0.42f,
)

enum class ScaleMode { Fit, Integer, PixelPerfect, Fill }
enum class ShaderMode { Off, Lcd, Crt, Scanlines }

data class ControlSettings(
    val opacity: Float = 0.72f,
    val size: Float = 1.0f,
    val haptics: Boolean = true,
    val turboButtons: Boolean = false,
    val externalController: Boolean = true,
)

data class AudioSettings(
    val masterVolume: Float = 0.86f,
    val fastForwardVolume: Float = 0.42f,
    val muted: Boolean = false,
    val latency: AudioLatency = AudioLatency.Balanced,
)

enum class AudioLatency { Low, Balanced, Stable }

data class PerformanceSettings(
    val mode: PerformanceMode = PerformanceMode.Balanced,
    val frameLimiter: Boolean = true,
    val fpsCounter: Boolean = false,
    val rewindEnabled: Boolean = false,
    val rewindSeconds: Int = 15,
)

enum class PerformanceMode { BatterySaver, Balanced, Performance }

val sampleLibrary = listOf(
    GameRom(id = "emerald", title = "Emerald Forest", favorite = true, playtimeSeconds = 6412),
    GameRom(id = "ocean", title = "Ocean Circuit", lastPlayedEpochMillis = 1),
    GameRom(id = "battle", title = "Battle Arena", playtimeSeconds = 1820),
)

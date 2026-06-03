package app.y2kboy.core

import androidx.compose.ui.graphics.Color

data class AmbientPalette(
    val primary: Color,
    val secondary: Color,
    val accent: Color,
)

fun paletteForGame(title: String): AmbientPalette = when {
    title.contains("emerald", ignoreCase = true) -> AmbientPalette(
        primary = Color(0xFF0E402D),
        secondary = Color(0xFF1DBB62),
        accent = Color(0xFF8FFFBE),
    )
    title.contains("ocean", ignoreCase = true) -> AmbientPalette(
        primary = Color(0xFF0B2948),
        secondary = Color(0xFF2B8FFF),
        accent = Color(0xFF66D9FF),
    )
    title.contains("battle", ignoreCase = true) -> AmbientPalette(
        primary = Color(0xFF301128),
        secondary = Color(0xFF7E58FF),
        accent = Color(0xFFFF7AE6),
    )
    else -> AmbientPalette(
        primary = Color(0xFF16202B),
        secondary = Color(0xFF3E7082),
        accent = Color(0xFF62E7FF),
    )
}

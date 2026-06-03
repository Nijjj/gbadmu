package app.y2kboy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Chrome = Color(0xFFDDE4EA)
val Frost = Color(0xCCF8FBFF)
val Gunmetal = Color(0xFF101216)
val Panel = Color(0x663A424C)
val ElectricBlue = Color(0xFF66D9FF)
val LimeGlass = Color(0xFF8BFFB2)
val VioletGlass = Color(0xFFBA9CFF)

private val Scheme = darkColorScheme(
    primary = ElectricBlue,
    secondary = LimeGlass,
    tertiary = VioletGlass,
    background = Gunmetal,
    surface = Color(0xFF171A20),
    onPrimary = Color(0xFF061015),
    onBackground = Chrome,
    onSurface = Chrome,
)

@Composable
fun Y2KBoyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = Scheme,
        typography = Y2KTypography,
        content = content,
    )
}

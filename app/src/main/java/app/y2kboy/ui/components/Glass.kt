package app.y2kboy.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.y2kboy.ui.theme.Chrome
import app.y2kboy.ui.theme.Frost

@Composable
fun AmbientBackground(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    intensity: Float = 0.8f,
    content: @Composable () -> Unit,
) {
    val transition = rememberInfiniteTransition(label = "ambient")
    val drift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 9000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "ambient-drift",
    )
    val safeColors = if (colors.size >= 3) colors else listOf(
        Color(0xFF1B2630),
        Color(0xFF4E5F68),
        Color(0xFF79E6FF),
    )

    Box(
        modifier = modifier.background(
            Brush.radialGradient(
                colors = listOf(
                    safeColors[0].copy(alpha = 0.9f * intensity),
                    safeColors[1].copy(alpha = 0.54f * intensity),
                    Color(0xFF080A0E),
                ),
                radius = 980f + drift * 420f,
            )
        )
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .blur(36.dp)
                .background(
                    Brush.linearGradient(
                        listOf(
                            safeColors[2].copy(alpha = 0.16f * intensity),
                            Color.Transparent,
                            safeColors[1].copy(alpha = 0.13f * intensity),
                        )
                    )
                )
        )
        content()
    }
}

@Composable
fun GlassPanel(
    modifier: Modifier = Modifier,
    radius: Dp = 8.dp,
    padding: PaddingValues = PaddingValues(16.dp),
    content: @Composable () -> Unit,
) {
    Box(
        modifier
            .clip(RoundedCornerShape(radius))
            .background(
                Brush.verticalGradient(
                    listOf(
                        Frost.copy(alpha = 0.18f),
                        Color.White.copy(alpha = 0.07f),
                        Color.Black.copy(alpha = 0.10f),
                    )
                )
            )
            .border(
                BorderStroke(1.dp, Color.White.copy(alpha = 0.22f)),
                RoundedCornerShape(radius),
            )
            .padding(padding)
    ) {
        content()
    }
}

@Composable
fun ChromeButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit = { Text(label) },
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .clip(CircleShape)
            .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.28f)), CircleShape)
            .background(Color.White.copy(alpha = 0.08f)),
        colors = ButtonDefaults.textButtonColors(contentColor = Chrome),
        content = content,
    )
}

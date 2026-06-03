package app.y2kboy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.y2kboy.data.GameRom
import app.y2kboy.ui.components.AmbientBackground
import app.y2kboy.ui.components.ChromeButton
import app.y2kboy.ui.components.GlassPanel

@Composable
fun HomeScreen(
    library: List<GameRom>,
    onPlay: (GameRom) -> Unit,
    onOpenSettings: () -> Unit,
) {
    AmbientBackground(
        colors = listOf(Color(0xFF102029), Color(0xFF2B505E), Color(0xFF8BF4F8)),
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column {
                            Text(
                                text = "Y2KBoy",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = "Media library for a handheld that never shipped",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.74f),
                            )
                        }
                        ChromeButton(label = "Settings", onClick = onOpenSettings) {
                            Icon(Icons.Outlined.Tune, contentDescription = null)
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    GlassPanel(
                        modifier = Modifier.fillMaxWidth(),
                        radius = 8.dp,
                        padding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Search, contentDescription = null, tint = Color.White.copy(alpha = 0.8f))
                            Spacer(Modifier.width(12.dp))
                            Text(
                                text = "Search library, collections, favorites",
                                color = Color.White.copy(alpha = 0.66f),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }

            item {
                SectionHeader(title = "Recently Tuned")
                Spacer(Modifier.height(10.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    items(library) { rom ->
                        FeaturedGameCard(rom = rom, onPlay = { onPlay(rom) })
                    }
                }
            }

            item {
                SectionHeader(title = "Favorites")
                Spacer(Modifier.height(10.dp))
            }

            items(library.filter { it.favorite }) { rom ->
                LibraryRow(rom = rom, onPlay = { onPlay(rom) })
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = Color.White.copy(alpha = 0.92f),
    )
}

@Composable
private fun FeaturedGameCard(rom: GameRom, onPlay: () -> Unit) {
    GlassPanel(
        modifier = Modifier
            .width(220.dp)
            .clickable(onClick = onPlay),
        padding = PaddingValues(14.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.82f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF91FFAE), Color(0xFF4DE2FF), Color(0xFF102029))
                        )
                    )
            ) {
                Icon(
                    imageVector = Icons.Outlined.PlayArrow,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                        .size(30.dp),
                )
            }
            Text(text = rom.title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${rom.playtimeSeconds / 60} min played",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f),
            )
        }
    }
}

@Composable
private fun LibraryRow(rom: GameRom, onPlay: () -> Unit) {
    GlassPanel(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onPlay),
        padding = PaddingValues(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(58.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(Color(0xFFDBFBFF), Color(0xFF5FE0FF), Color(0xFF21414F))
                            )
                        )
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(rom.title, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "${rom.platform} - ${rom.playtimeSeconds / 60} min",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f),
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Favorite, contentDescription = null, tint = Color(0xFFB4FFD7))
                Spacer(Modifier.width(8.dp))
                ChromeButton(label = "Play", onClick = onPlay) {
                    Icon(Icons.Outlined.PlayArrow, contentDescription = null)
                }
            }
        }
    }
}

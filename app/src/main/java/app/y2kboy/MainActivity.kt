package app.y2kboy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.y2kboy.core.Y2KBoyAppState
import app.y2kboy.ui.screens.Y2KBoyApp
import app.y2kboy.ui.theme.Y2KBoyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Y2KBoyTheme {
                Y2KBoyApp(appState = Y2KBoyAppState())
            }
        }
    }
}

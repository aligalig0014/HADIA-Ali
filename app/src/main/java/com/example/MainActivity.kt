package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.audio.RomanticMusicPlayer
import com.example.components.MagicalOpeningScreen
import com.example.screens.ProposalMainScreen
import com.example.ui.theme.MyApplicationTheme

enum class AppScreen {
  OPENING,
  MAIN_PROPOSAL
}

class MainActivity : ComponentActivity() {

  private val musicPlayer = RomanticMusicPlayer()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme(darkTheme = true) {
        var currentScreen by remember { mutableStateOf(AppScreen.OPENING) }

        AnimatedContent(
          targetState = currentScreen,
          transitionSpec = { fadeIn() togetherWith fadeOut() },
          label = "screen_navigation_transition",
          modifier = Modifier.fillMaxSize()
        ) { screen ->
          when (screen) {
            AppScreen.OPENING -> {
              MagicalOpeningScreen(
                onEnterProposalExperience = {
                  currentScreen = AppScreen.MAIN_PROPOSAL
                  musicPlayer.start()
                }
              )
            }
            AppScreen.MAIN_PROPOSAL -> {
              ProposalMainScreen(
                musicPlayer = musicPlayer,
                onBackToOpening = {
                  currentScreen = AppScreen.OPENING
                }
              )
            }
          }
        }
      }
    }
  }

  override fun onPause() {
    super.onPause()
    musicPlayer.pause()
  }

  override fun onDestroy() {
    super.onDestroy()
    musicPlayer.release()
  }
}

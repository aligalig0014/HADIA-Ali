package com.example.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterVintage
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.RomanticMusicPlayer
import com.example.components.CoupleHugVisual
import com.example.components.FallingRosePetals
import com.example.components.LoveVowsAndJourney
import com.example.components.ProposalRingStage
import com.example.components.RomanticAudioBar
import com.example.components.RomanticLoveLetter
import kotlinx.coroutines.launch

enum class ProposalTab(val title: String) {
  PROPOSAL("Proposal 💍"),
  OUR_HUG("Our Hug 💑"),
  LOVE_LETTER("Love Letter 💌"),
  SACRED_VOWS("Vows 🌹")
}

@Composable
fun ProposalMainScreen(
  musicPlayer: RomanticMusicPlayer,
  onBackToOpening: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(ProposalTab.PROPOSAL) }
  var isShowerActive by remember { mutableStateOf(false) }
  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  val isPlaying by musicPlayer.isPlaying.collectAsState()
  val isMuted by musicPlayer.isMuted.collectAsState()

  // Auto-play soft romantic music as requested when screen opens
  LaunchedEffect(Unit) {
    musicPlayer.start()
  }

  // Handle Android back button
  BackHandler {
    if (selectedTab != ProposalTab.PROPOSAL) {
      selectedTab = ProposalTab.PROPOSAL
    } else {
      onBackToOpening()
    }
  }

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      NavigationBar(
        containerColor = Color(0xFF1E0312),
        tonalElevation = 12.dp,
        modifier = Modifier.testTag("proposal_bottom_navigation")
      ) {
        NavigationBarItem(
          selected = selectedTab == ProposalTab.PROPOSAL,
          onClick = { selectedTab = ProposalTab.PROPOSAL },
          icon = {
            Icon(
              imageVector = Icons.Default.Diamond,
              contentDescription = "Proposal",
              tint = if (selectedTab == ProposalTab.PROPOSAL) Color(0xFFFFD54F) else Color(0xFFFF80AB)
            )
          },
          label = {
            Text(
              text = "Proposal",
              fontSize = 11.sp,
              fontWeight = if (selectedTab == ProposalTab.PROPOSAL) FontWeight.Bold else FontWeight.Normal
            )
          },
          colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color(0xFF880E4F),
            selectedTextColor = Color(0xFFFFD54F),
            unselectedTextColor = Color(0xFFFFB2C9)
          ),
          modifier = Modifier.testTag("nav_item_proposal")
        )

        NavigationBarItem(
          selected = selectedTab == ProposalTab.OUR_HUG,
          onClick = { selectedTab = ProposalTab.OUR_HUG },
          icon = {
            Icon(
              imageVector = Icons.Default.Favorite,
              contentDescription = "Hug",
              tint = if (selectedTab == ProposalTab.OUR_HUG) Color(0xFFFFD54F) else Color(0xFFFF80AB)
            )
          },
          label = {
            Text(
              text = "Our Hug",
              fontSize = 11.sp,
              fontWeight = if (selectedTab == ProposalTab.OUR_HUG) FontWeight.Bold else FontWeight.Normal
            )
          },
          colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color(0xFF880E4F),
            selectedTextColor = Color(0xFFFFD54F),
            unselectedTextColor = Color(0xFFFFB2C9)
          ),
          modifier = Modifier.testTag("nav_item_hug")
        )

        NavigationBarItem(
          selected = selectedTab == ProposalTab.LOVE_LETTER,
          onClick = { selectedTab = ProposalTab.LOVE_LETTER },
          icon = {
            Icon(
              imageVector = Icons.Default.Mail,
              contentDescription = "Letter",
              tint = if (selectedTab == ProposalTab.LOVE_LETTER) Color(0xFFFFD54F) else Color(0xFFFF80AB)
            )
          },
          label = {
            Text(
              text = "Love Letter",
              fontSize = 11.sp,
              fontWeight = if (selectedTab == ProposalTab.LOVE_LETTER) FontWeight.Bold else FontWeight.Normal
            )
          },
          colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color(0xFF880E4F),
            selectedTextColor = Color(0xFFFFD54F),
            unselectedTextColor = Color(0xFFFFB2C9)
          ),
          modifier = Modifier.testTag("nav_item_letter")
        )

        NavigationBarItem(
          selected = selectedTab == ProposalTab.SACRED_VOWS,
          onClick = { selectedTab = ProposalTab.SACRED_VOWS },
          icon = {
            Icon(
              imageVector = Icons.Default.VolunteerActivism,
              contentDescription = "Vows",
              tint = if (selectedTab == ProposalTab.SACRED_VOWS) Color(0xFFFFD54F) else Color(0xFFFF80AB)
            )
          },
          label = {
            Text(
              text = "7 Vows",
              fontSize = 11.sp,
              fontWeight = if (selectedTab == ProposalTab.SACRED_VOWS) FontWeight.Bold else FontWeight.Normal
            )
          },
          colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color(0xFF880E4F),
            selectedTextColor = Color(0xFFFFD54F),
            unselectedTextColor = Color(0xFFFFB2C9)
          ),
          modifier = Modifier.testTag("nav_item_vows")
        )
      }
    },
    floatingActionButton = {
      // Rose Shower Trigger Button
      FloatingActionButton(
        onClick = {
          isShowerActive = !isShowerActive
          scope.launch {
            snackbarHostState.showSnackbar(
              if (isShowerActive) "Raining 1000 Red Roses for Hadia! 🌹✨" else "Gentle Rose Breeze Mode 🌸"
            )
          }
        },
        containerColor = Color(0xFFE91E63),
        contentColor = Color.White,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
        modifier = Modifier.testTag("rose_shower_fab")
      ) {
        Icon(
          imageVector = Icons.Default.FilterVintage,
          contentDescription = "Rose Shower",
          tint = Color(0xFFFFEB3B),
          modifier = Modifier.size(26.dp)
        )
      }
    }
  ) { paddingValues ->
    Box(
      modifier = modifier
        .fillMaxSize()
        .padding(paddingValues)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFF14020C),
              Color(0xFF280718),
              Color(0xFF12020A)
            )
          )
        )
    ) {
      // Ambient Falling Rose Petals Layer
      FallingRosePetals(
        petalCount = if (isShowerActive) 60 else 32,
        isCelebrationShower = isShowerActive,
        onTouchSpawn = true
      )

      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Romantic Audio Bar at Top
        RomanticAudioBar(
          isPlaying = isPlaying,
          isMuted = isMuted,
          onTogglePlay = { musicPlayer.togglePlay() },
          onToggleMute = { musicPlayer.toggleMute() },
          modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Animated Tab Content
        AnimatedContent(
          targetState = selectedTab,
          transitionSpec = {
            fadeIn() + slideInHorizontally() togetherWith fadeOut() + slideOutHorizontally()
          },
          label = "tab_content_transition"
        ) { tab ->
          when (tab) {
            ProposalTab.PROPOSAL -> {
              ProposalRingStage(
                onYesAccepted = {
                  isShowerActive = true
                  musicPlayer.playProposalYesFanfare()
                  scope.launch {
                    snackbarHostState.showSnackbar("Hadia said YES to Ali! Eternal love sealed! 💍❤️🎉")
                  }
                }
              )
            }
            ProposalTab.OUR_HUG -> {
              CoupleHugVisual(
                onHeartbeatTrigger = {
                  musicPlayer.playHeartbeatSound()
                }
              )
            }
            ProposalTab.LOVE_LETTER -> {
              RomanticLoveLetter(
                onKissSealed = {
                  scope.launch {
                    snackbarHostState.showSnackbar("Love letter sealed with a sweet kiss for Hadia! 💋❤️")
                  }
                }
              )
            }
            ProposalTab.SACRED_VOWS -> {
              LoveVowsAndJourney(
                onVowSealed = {
                  musicPlayer.playHeartbeatSound()
                  scope.launch {
                    snackbarHostState.showSnackbar("Promise sealed in Hadia's heart forever! 🌹")
                  }
                }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(72.dp)) // Padding for bottom bar and FAB
      }
    }
  }
}

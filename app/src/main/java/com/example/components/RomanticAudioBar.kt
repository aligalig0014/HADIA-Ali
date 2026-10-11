package com.example.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin

@Composable
fun RomanticAudioBar(
  isPlaying: Boolean,
  isMuted: Boolean,
  onTogglePlay: () -> Unit,
  onToggleMute: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "audio_visualizer_transition")
  val visualizerPhase by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 6.28f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "visualizer_phase"
  )

  Surface(
    shape = RoundedCornerShape(26.dp),
    color = Color(0xFF280718).copy(alpha = 0.95f),
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      Brush.linearGradient(listOf(Color(0xFFFFD54F), Color(0xFFFF4081)))
    ),
    modifier = modifier
      .shadow(10.dp, RoundedCornerShape(26.dp))
      .testTag("romantic_audio_bar")
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      // Play/Pause circular button
      Box(
        modifier = Modifier
          .size(34.dp)
          .clip(CircleShape)
          .background(
            Brush.radialGradient(
              colors = listOf(Color(0xFFE91E63), Color(0xFF880E4F))
            )
          )
          .border(1.dp, Color(0xFFFFD54F), CircleShape)
          .clickable { onTogglePlay() }
          .testTag("audio_play_pause_button"),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
          contentDescription = if (isPlaying) "Pause" else "Play",
          tint = Color.White,
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      // Animated Equalizer Wave Bars
      Canvas(
        modifier = Modifier
          .width(42.dp)
          .height(20.dp)
          .testTag("audio_wave_canvas")
      ) {
        val barCount = 5
        val spacing = size.width / (barCount + 1)
        val h = size.height

        for (i in 0 until barCount) {
          val barX = (i + 1) * spacing
          val wave = if (isPlaying && !isMuted) {
            (sin(visualizerPhase + i * 1.3) * 0.45 + 0.55).toFloat()
          } else {
            0.15f
          }
          val barHeight = h * wave

          drawLine(
            brush = Brush.verticalGradient(
              colors = listOf(Color(0xFFFFD54F), Color(0xFFFF4081))
            ),
            start = Offset(barX, (h - barHeight) * 0.5f),
            end = Offset(barX, (h + barHeight) * 0.5f),
            strokeWidth = 3.5f,
            cap = StrokeCap.Round
          )
        }
      }

      Spacer(modifier = Modifier.width(10.dp))

      // Audio label
      Text(
        text = if (isPlaying) "Melody for Hadia 🎵" else "Music Paused 🎶",
        color = Color(0xFFFFE082),
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold
      )

      Spacer(modifier = Modifier.width(8.dp))

      // Mute toggle
      IconButton(
        onClick = onToggleMute,
        modifier = Modifier
          .size(32.dp)
          .testTag("audio_mute_toggle_button")
      ) {
        Icon(
          imageVector = if (isMuted) Icons.Default.VolumeMute else Icons.Default.VolumeUp,
          contentDescription = if (isMuted) "Unmute" else "Mute",
          tint = if (isMuted) Color(0xFFFF80AB) else Color(0xFFFFD54F),
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}

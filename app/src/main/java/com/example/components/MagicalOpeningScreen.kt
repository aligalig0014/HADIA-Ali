package com.example.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MagicalOpeningScreen(
  onEnterProposalExperience: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "opening_ambient_transition")

  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 0.94f,
    targetValue = 1.08f,
    animationSpec = infiniteRepeatable(
      animation = tween(1800, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse_scale"
  )

  val glowAlpha by infiniteTransition.animateFloat(
    initialValue = 0.4f,
    targetValue = 0.95f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "glow_alpha"
  )

  val sparkleRot by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(8000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "sparkle_rotation"
  )

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(
        Brush.radialGradient(
          colors = listOf(
            Color(0xFF3B0820), // Rich Velvet Heart
            Color(0xFF1E0312),
            Color(0xFF0C0107)  // Deep Midnight Black
          )
        )
      )
      .testTag("magical_opening_screen"),
    contentAlignment = Alignment.Center
  ) {
    // Canvas for glittering starry galaxy & blooming golden particles
    Canvas(modifier = Modifier.fillMaxSize()) {
      val w = size.width
      val h = size.height

      // Romantic Nebula Cloud
      drawCircle(
        brush = Brush.radialGradient(
          colors = listOf(
            Color(0xFFE91E63).copy(alpha = 0.22f * glowAlpha),
            Color(0xFFFFD54F).copy(alpha = 0.12f * glowAlpha),
            Color.Transparent
          ),
          center = Offset(w * 0.5f, h * 0.45f),
          radius = w * 0.55f
        ),
        center = Offset(w * 0.5f, h * 0.45f),
        radius = w * 0.55f
      )

      // Twinkling Starlight & Fireflies
      for (i in 0..30) {
        val starX = (w * ((i * 73) % 100) * 0.01f)
        val starY = (h * ((i * 47) % 100) * 0.01f)
        val sAlpha = (sin(i.toDouble() + pulseScale * 6.0) * 0.4 + 0.6).toFloat()
        drawCircle(
          color = Color(0xFFFFF9C4).copy(alpha = sAlpha),
          radius = (if (i % 3 == 0) 2.8f else 1.6f) * pulseScale,
          center = Offset(starX, starY)
        )
      }
    }

    // Centered Sacred Locket & Proposal Introduction
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      // Golden Crown & Couple Monogram
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFF38081A).copy(alpha = 0.9f),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD54F).copy(alpha = 0.6f)),
        modifier = Modifier.shadow(12.dp, RoundedCornerShape(20.dp))
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFFFD54F), modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "HADIA ❤️ ALI",
            color = Color(0xFFFFE082),
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 3.sp
          )
          Spacer(modifier = Modifier.width(8.dp))
          Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFFFD54F), modifier = Modifier.size(16.dp))
        }
      }

      Spacer(modifier = Modifier.height(28.dp))

      // The Pulsing Velvet Heart Locket
      Box(
        modifier = Modifier
          .size(170.dp)
          .scale(pulseScale)
          .shadow(24.dp, CircleShape)
          .clip(CircleShape)
          .background(
            Brush.radialGradient(
              colors = listOf(
                Color(0xFFFF2D55),
                Color(0xFFC2185B),
                Color(0xFF4A0824)
              )
            )
          )
          .border(3.dp, Color(0xFFFFD54F), CircleShape)
          .clickable { onEnterProposalExperience() }
          .testTag("opening_locket_heart"),
        contentAlignment = Alignment.Center
      ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
          val w = size.width
          val h = size.height

          // Draw ornate golden heart crest
          val hp = Path().apply {
            val cx = w * 0.5f
            val cy = h * 0.46f
            val s = 28f
            moveTo(cx, cy - s * 0.4f)
            cubicTo(cx - s * 1.1f, cy - s * 1.5f, cx - s * 2.2f, cy - s * 0.1f, cx, cy + s * 1.5f)
            cubicTo(cx + s * 2.2f, cy - s * 0.1f, cx + s * 1.1f, cy - s * 1.5f, cx, cy - s * 0.4f)
            close()
          }
          drawPath(
            path = hp,
            brush = Brush.linearGradient(
              colors = listOf(Color(0xFFFFD54F), Color(0xFFFFF9C4), Color(0xFFFFB300))
            )
          )
        }

        // Lock icon
        Icon(
          imageVector = Icons.Default.LockOpen,
          contentDescription = "Unlock",
          tint = Color(0xFF5A082A),
          modifier = Modifier.size(32.dp)
        )
      }

      Spacer(modifier = Modifier.height(28.dp))

      // Emotional Words
      Text(
        text = "Meri Pyaari Biwi Hadia...",
        color = Color(0xFFFFD54F),
        fontSize = 26.sp,
        fontWeight = FontWeight.ExtraBold,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.Serif
      )

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "\"Ali ne apne dil ke har ehsaas ko ek jagah piroya hai, sirf aapke liye.\nApni aankhein band karein aur is jaadugari safar mein qadam rakhein...\"",
        color = Color(0xFFFFE0EC),
        fontSize = 15.sp,
        textAlign = TextAlign.Center,
        fontStyle = FontStyle.Italic,
        lineHeight = 22.sp,
        modifier = Modifier.padding(horizontal = 12.dp)
      )

      Spacer(modifier = Modifier.height(32.dp))

      // Glowing Unlock Button
      ElevatedButton(
        onClick = onEnterProposalExperience,
        colors = ButtonDefaults.elevatedButtonColors(
          containerColor = Color(0xFFE91E63),
          contentColor = Color.White
        ),
        shape = RoundedCornerShape(32.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 12.dp),
        modifier = Modifier
          .fillMaxWidth(0.85f)
          .height(56.dp)
          .testTag("unlock_heart_button")
      ) {
        Icon(
          imageVector = Icons.Default.AutoAwesome,
          contentDescription = null,
          tint = Color(0xFFFFEB3B),
          modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = "Open Ali's Heart For Hadia ✨",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.MusicNote,
          contentDescription = null,
          tint = Color(0xFFFF80AB),
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Soft Romantic Music & Rose Petals Await You",
          color = Color(0xFFFF80AB),
          fontSize = 12.sp,
          fontWeight = FontWeight.Medium
        )
      }
    }
  }
}

package com.example.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin

@Composable
fun CoupleHugVisual(
  modifier: Modifier = Modifier,
  onHeartbeatTrigger: () -> Unit = {}
) {
  val infiniteTransition = rememberInfiniteTransition(label = "couple_hug_transition")

  // Gentle breathing and tender swaying
  val breatheScale by infiniteTransition.animateFloat(
    initialValue = 0.98f,
    targetValue = 1.025f,
    animationSpec = infiniteRepeatable(
      animation = tween(2800, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "breathe"
  )

  val swayAngle by infiniteTransition.animateFloat(
    initialValue = -1.5f,
    targetValue = 1.5f,
    animationSpec = infiniteRepeatable(
      animation = tween(3600, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "sway"
  )

  val auraGlow by infiniteTransition.animateFloat(
    initialValue = 0.5f,
    targetValue = 0.95f,
    animationSpec = infiniteRepeatable(
      animation = tween(2200, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "aura"
  )

  var hugLoveCount by remember { mutableIntStateOf(100) }
  var activeWhisperIndex by remember { mutableIntStateOf(0) }
  var showWhisper by remember { mutableStateOf(true) }

  val whispers = remember {
    listOf(
      "Ali: \"Hadia, meri baahon mein aate hi poori duniya ka sukoon mil jata hai... ❤️\"",
      "Hadia: \"Aapka sath meri zindagi ki sab se haseen nemat hai, Ali... 💕\"",
      "Ali: \"Khuda kare ye lamha yahin thehar jaye, aur hum hamesha aise hi rahein... 🌹\"",
      "Hadia: \"Ali, aap sirf mera pyaar nahi, meri poori kainaat ho... ✨\"",
      "Ali: \"Har dua mein, har saans mein sirf tumhara naam hai meri Hadia... 💍\"",
      "Together: \"Two Souls, One Heartbeat — Forever Ali & Hadia 💖\""
    )
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Top Title Badge
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color(0xFF38081A).copy(alpha = 0.9f),
      border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD54F).copy(alpha = 0.6f)),
      modifier = Modifier
        .shadow(8.dp, RoundedCornerShape(24.dp))
        .testTag("couple_hug_badge")
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.Favorite,
          contentDescription = "Love icon",
          tint = Color(0xFFFF2D55),
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "ALI & HADIA — OUR ETERNAL EMBRACE",
          color = Color(0xFFFFE082),
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
          imageVector = Icons.Default.Favorite,
          contentDescription = "Love icon",
          tint = Color(0xFFFF2D55),
          modifier = Modifier.size(20.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Interactive Animated Hug Stage
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(340.dp)
        .clip(RoundedCornerShape(32.dp))
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color(0xFF1E0312),
              Color(0xFF380820),
              Color(0xFF1A020E)
            )
          )
        )
        .border(
          width = 1.5.dp,
          brush = Brush.linearGradient(
            colors = listOf(Color(0xFFFFD54F), Color(0xFFFF4081), Color(0xFF880E4F))
          ),
          shape = RoundedCornerShape(32.dp)
        )
        .clickable {
          hugLoveCount += 1
          activeWhisperIndex = (activeWhisperIndex + 1) % whispers.size
          onHeartbeatTrigger()
        }
        .testTag("couple_hug_canvas_box"),
      contentAlignment = Alignment.Center
    ) {
      // Background Starlight & Moon & Fairy Lights Canvas
      Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Moonlight Glow Sphere behind couple
        drawCircle(
          brush = Brush.radialGradient(
            colors = listOf(
              Color(0xFFFFF9C4).copy(alpha = 0.28f * auraGlow),
              Color(0xFFFF80AB).copy(alpha = 0.18f * auraGlow),
              Color.Transparent
            ),
            center = Offset(w * 0.5f, h * 0.42f),
            radius = w * 0.45f
          ),
          center = Offset(w * 0.5f, h * 0.42f),
          radius = w * 0.45f
        )

        // Floating Fairy Lights in Garden
        for (i in 0..16) {
          val starX = (w * (0.05f + (i * 0.06f)))
          val starY = h * (0.1f + ((i * 37) % 70) * 0.01f)
          val twinkle = (sin(i.toDouble() + breatheScale * 8.0) * 0.4 + 0.6).toFloat()
          drawCircle(
            color = Color(0xFFFFF9C4).copy(alpha = 0.7f * twinkle),
            radius = 2.5f * twinkle,
            center = Offset(starX, starY)
          )
        }

        // Draw animated artistic couple hug silhouette
        rotate(degrees = swayAngle, pivot = Offset(w * 0.5f, h * 0.6f)) {
          drawArtisticCoupleEmbrace(
            centerX = w * 0.5f,
            centerY = h * 0.52f,
            scale = breatheScale,
            auraIntensity = auraGlow
          )
        }
      }

      // Floating Hearts indicator at top
      Row(
        modifier = Modifier
          .align(Alignment.TopCenter)
          .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color.Black.copy(alpha = 0.55f),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF80AB).copy(alpha = 0.4f))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.VolunteerActivism,
              contentDescription = "Warmth",
              tint = Color(0xFFFF4081),
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Love Warmth: $hugLoveCount% ❤️",
              color = Color.White,
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Ali's Heart: 128 BPM",
              color = Color(0xFFFFD54F),
              fontSize = 11.sp
            )
          }
        }
      }

      // Tap hint at bottom of canvas
      Text(
        text = "Tap to Feel Ali & Hadia's Warm Embrace ✨",
        color = Color(0xFFFFE082).copy(alpha = 0.9f),
        fontSize = 11.sp,
        fontStyle = FontStyle.Italic,
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(bottom = 12.dp)
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Dynamic Whisper Box
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color(0xFF280718).copy(alpha = 0.92f),
      border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF80AB).copy(alpha = 0.35f)),
      modifier = Modifier
        .fillMaxWidth()
        .shadow(6.dp, RoundedCornerShape(20.dp))
        .testTag("couple_whisper_box")
    ) {
      Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = "Whisper",
            tint = Color(0xFFFFD54F),
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "HEARTFELT WHISPERS",
            color = Color(0xFFFFB300),
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.width(6.dp))
          Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = "Whisper",
            tint = Color(0xFFFFD54F),
            modifier = Modifier.size(16.dp)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = whispers[activeWhisperIndex],
          color = Color(0xFFFFF0F5),
          fontSize = 15.sp,
          fontWeight = FontWeight.Medium,
          fontStyle = FontStyle.Italic,
          textAlign = TextAlign.Center,
          lineHeight = 22.sp,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Romantic Action Button: Hold Closer
    ElevatedButton(
      onClick = {
        hugLoveCount += 5
        activeWhisperIndex = (activeWhisperIndex + 1) % whispers.size
        onHeartbeatTrigger()
      },
      colors = ButtonDefaults.elevatedButtonColors(
        containerColor = Color(0xFFD81B60),
        contentColor = Color.White
      ),
      shape = RoundedCornerShape(28.dp),
      elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 6.dp),
      modifier = Modifier
        .fillMaxWidth(0.85f)
        .height(48.dp)
        .testTag("hold_hadia_tighter_button")
    ) {
      Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = "Hug",
        tint = Color(0xFFFFEB3B),
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = "Hold Hadia Tighter in Arms 🫂❤️",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
      )
    }
  }
}

/**
 * Draws a tender, loving, artistic couple embrace of Ali and Hadia on Canvas.
 * Beautifully stylized with glowing highlights, intertwining arms, resting heads,
 * and warm romantic crimson-gold tones.
 */
private fun DrawScope.drawArtisticCoupleEmbrace(
  centerX: Float,
  centerY: Float,
  scale: Float,
  auraIntensity: Float
) {
  val s = 1.15f * scale

  // Golden Heart halo above heads
  val heartPath = Path().apply {
    val hx = centerX
    val hy = centerY - 110f * s
    val hs = 24f * s
    moveTo(hx, hy)
    cubicTo(hx - hs, hy - hs, hx - hs * 2f, hy + hs * 0.5f, hx, hy + hs * 1.8f)
    cubicTo(hx + hs * 2f, hy + hs * 0.5f, hx + hs, hy - hs, hx, hy)
    close()
  }
  drawPath(
    path = heartPath,
    brush = Brush.radialGradient(
      colors = listOf(
        Color(0xFFFFD54F).copy(alpha = 0.95f),
        Color(0xFFFF4081).copy(alpha = 0.7f),
        Color.Transparent
      ),
      center = Offset(centerX, centerY - 100f * s),
      radius = 40f * s
    )
  )

  // Ali (Taller, protective - on left leaning in)
  // Ali's Head & Hair
  drawCircle(
    brush = Brush.radialGradient(
      colors = listOf(Color(0xFF4A1024), Color(0xFF210510)),
      center = Offset(centerX - 28f * s, centerY - 68f * s),
      radius = 36f * s
    ),
    radius = 32f * s,
    center = Offset(centerX - 28f * s, centerY - 68f * s)
  )

  // Ali's Shoulders & Body (Strong gentle frame)
  val aliBody = Path().apply {
    moveTo(centerX - 35f * s, centerY - 40f * s)
    cubicTo(centerX - 75f * s, centerY - 20f * s, centerX - 85f * s, centerY + 90f * s, centerX - 25f * s, centerY + 115f * s)
    cubicTo(centerX, centerY + 115f * s, centerX + 15f * s, centerY + 95f * s, centerX - 10f * s, centerY + 30f * s)
    close()
  }
  drawPath(
    path = aliBody,
    brush = Brush.linearGradient(
      colors = listOf(Color(0xFF38081C), Color(0xFF1F030E)),
      start = Offset(centerX - 80f * s, centerY - 30f * s),
      end = Offset(centerX + 10f * s, centerY + 115f * s)
    )
  )

  // Hadia (Tender, resting head on Ali's chest/shoulder - on right leaning in)
  // Hadia's Beautiful Hair flowing
  val hadiaHair = Path().apply {
    moveTo(centerX + 18f * s, centerY - 62f * s)
    cubicTo(centerX + 48f * s, centerY - 55f * s, centerX + 60f * s, centerY - 15f * s, centerX + 52f * s, centerY + 45f * s)
    cubicTo(centerX + 46f * s, centerY + 80f * s, centerX + 35f * s, centerY + 105f * s, centerX + 22f * s, centerY + 110f * s)
    cubicTo(centerX + 12f * s, centerY + 85f * s, centerX + 18f * s, centerY + 10f * s, centerX + 10f * s, centerY - 45f * s)
    close()
  }
  drawPath(
    path = hadiaHair,
    brush = Brush.linearGradient(
      colors = listOf(Color(0xFF420B1F), Color(0xFF1E040D)),
      start = Offset(centerX + 10f * s, centerY - 60f * s),
      end = Offset(centerX + 60f * s, centerY + 100f * s)
    )
  )

  // Hadia's Head (Resting peacefully against Ali)
  drawCircle(
    brush = Brush.radialGradient(
      colors = listOf(Color(0xFF880E4F), Color(0xFF38081C)),
      center = Offset(centerX + 14f * s, centerY - 52f * s),
      radius = 28f * s
    ),
    radius = 26f * s,
    center = Offset(centerX + 14f * s, centerY - 52f * s)
  )

  // Hadia's Dress & Body
  val hadiaBody = Path().apply {
    moveTo(centerX + 14f * s, centerY - 28f * s)
    cubicTo(centerX + 50f * s, centerY - 10f * s, centerX + 65f * s, centerY + 65f * s, centerX + 25f * s, centerY + 115f * s)
    cubicTo(centerX - 10f * s, centerY + 115f * s, centerX - 15f * s, centerY + 50f * s, centerX - 5f * s, centerY + 10f * s)
    close()
  }
  drawPath(
    path = hadiaBody,
    brush = Brush.linearGradient(
      colors = listOf(Color(0xFFAD1457), Color(0xFF6A0D38), Color(0xFF3B061D)),
      start = Offset(centerX + 40f * s, centerY - 20f * s),
      end = Offset(centerX - 10f * s, centerY + 115f * s)
    )
  )

  // Ali's Arm gently wrapping all the way around Hadia's waist/back (Protective embrace)
  val aliArm = Path().apply {
    moveTo(centerX - 45f * s, centerY)
    cubicTo(centerX - 20f * s, centerY + 28f * s, centerX + 20f * s, centerY + 38f * s, centerX + 45f * s, centerY + 22f * s)
    cubicTo(centerX + 52f * s, centerY + 28f * s, centerX + 46f * s, centerY + 40f * s, centerX + 22f * s, centerY + 48f * s)
    cubicTo(centerX - 10f * s, centerY + 45f * s, centerX - 42f * s, centerY + 22f * s, centerX - 55f * s, centerY + 8f * s)
    close()
  }
  drawPath(
    path = aliArm,
    brush = Brush.linearGradient(
      colors = listOf(Color(0xFF4A0E24), Color(0xFF6B1236)),
      start = Offset(centerX - 50f * s, centerY),
      end = Offset(centerX + 50f * s, centerY + 30f * s)
    )
  )

  // Hadia's Arm resting on Ali's chest / wrapping his neck
  val hadiaArm = Path().apply {
    moveTo(centerX + 26f * s, centerY - 12f * s)
    cubicTo(centerX + 15f * s, centerY + 5f * s, centerX - 5f * s, centerY + 8f * s, centerX - 22f * s, centerY - 6f * s)
    cubicTo(centerX - 24f * s, centerY - 14f * s, centerX - 12f * s, centerY - 18f * s, centerX, centerY - 6f * s)
    cubicTo(centerX + 10f * s, centerY + 2f * s, centerX + 20f * s, centerY - 6f * s, centerX + 30f * s, centerY - 18f * s)
    close()
  }
  drawPath(
    path = hadiaArm,
    brush = Brush.linearGradient(
      colors = listOf(Color(0xFFC2185B), Color(0xFF880E4F)),
      start = Offset(centerX + 30f * s, centerY - 15f * s),
      end = Offset(centerX - 25f * s, centerY)
    )
  )

  // Golden Rose Garland / Fairy Light accents around them
  val lightStroke = Stroke(width = 2.5f * s, cap = StrokeCap.Round)
  val garlandPath = Path().apply {
    moveTo(centerX - 60f * s, centerY + 40f * s)
    quadraticTo(centerX, centerY + 85f * s, centerX + 55f * s, centerY + 50f * s)
  }
  drawPath(
    path = garlandPath,
    brush = Brush.linearGradient(
      colors = listOf(
        Color(0xFFFFD54F).copy(alpha = 0.8f),
        Color(0xFFFF4081).copy(alpha = 0.9f),
        Color(0xFFFFD54F).copy(alpha = 0.8f)
      ),
      start = Offset(centerX - 60f * s, centerY + 40f * s),
      end = Offset(centerX + 55f * s, centerY + 50f * s)
    ),
    style = lightStroke
  )

  // Sparkling Diamond / Ring reflection on their fingers
  drawCircle(
    color = Color(0xFFFFFFFF),
    radius = 3.5f * s,
    center = Offset(centerX + 38f * s, centerY + 25f * s)
  )
  drawCircle(
    color = Color(0xFFFFD54F),
    radius = 7.5f * s,
    center = Offset(centerX + 38f * s, centerY + 25f * s),
    style = Stroke(width = 1.5f * s)
  )
}

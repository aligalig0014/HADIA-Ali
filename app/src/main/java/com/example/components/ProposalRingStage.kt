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
import androidx.compose.animation.scaleIn
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ProposalRingStage(
  modifier: Modifier = Modifier,
  onYesAccepted: () -> Unit = {}
) {
  var isBoxOpen by remember { mutableStateOf(false) }
  var isAccepted by remember { mutableStateOf(false) }
  var noButtonDodges by remember { mutableIntStateOf(0) }
  val scope = rememberCoroutineScope()

  val infiniteTransition = rememberInfiniteTransition(label = "ring_sparkle_transition")
  val sparkleAngle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(4000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "sparkle_angle"
  )

  val ringGlow by infiniteTransition.animateFloat(
    initialValue = 0.7f,
    targetValue = 1.3f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "ring_glow"
  )

  val noButtonPhrases = remember {
    listOf(
      "No 😜",
      "Are you sure Hadia? 🥺",
      "Ali loves you too much! 💕",
      "No way! You love Ali! 🥰",
      "Button transformed to YES! 💍"
    )
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (!isAccepted) {
      // Velvet Proposal Box Header
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFF38081E).copy(alpha = 0.95f),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD54F).copy(alpha = 0.5f)),
        modifier = Modifier
          .shadow(8.dp, RoundedCornerShape(20.dp))
          .testTag("proposal_header")
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFFFD54F), modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "ALI'S SACRED QUESTION FOR HADIA",
            color = Color(0xFFFFE082),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.width(8.dp))
          Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFFFD54F), modifier = Modifier.size(18.dp))
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // The Ring Display / Velvet Jewelry Box
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .shadow(16.dp, RoundedCornerShape(28.dp))
          .clip(RoundedCornerShape(28.dp))
          .border(
            width = 2.dp,
            brush = Brush.linearGradient(
              colors = listOf(Color(0xFFFFD54F), Color(0xFFFF4081), Color(0xFFFFB300))
            ),
            shape = RoundedCornerShape(28.dp)
          )
          .clickable { isBoxOpen = !isBoxOpen }
          .testTag("proposal_ring_box"),
        colors = CardDefaults.cardColors(
          containerColor = Color(0xFF260515)
        )
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Velvet Ring Cushion Stage
          Box(
            modifier = Modifier
              .size(200.dp)
              .shadow(12.dp, CircleShape)
              .clip(CircleShape)
              .background(
                Brush.radialGradient(
                  colors = listOf(
                    Color(0xFF880E4F),
                    Color(0xFF4A0824),
                    Color(0xFF1B030D)
                  )
                )
              )
              .border(3.dp, Color(0xFFFFD54F), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
              val w = size.width
              val h = size.height

              // Radiant Starlight burst behind diamond ring
              for (i in 0..11) {
                val rayAngle = (i * 30f + sparkleAngle) * (Math.PI / 180.0)
                val rayLength = w * 0.45f * ringGlow
                val rx = (w * 0.5f + cos(rayAngle) * rayLength).toFloat()
                val ry = (h * 0.5f + sin(rayAngle) * rayLength).toFloat()
                drawLine(
                  color = Color(0xFFFFF9C4).copy(alpha = 0.25f),
                  start = Offset(w * 0.5f, h * 0.5f),
                  end = Offset(rx, ry),
                  strokeWidth = 2.5f
                )
              }

              // Draw Solitaire Diamond Ring
              drawDiamondRing(
                centerX = w * 0.5f,
                centerY = h * 0.52f,
                glow = ringGlow
              )
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          Text(
            text = "✨ Pure Solitaire Diamond For Queen Hadia ✨",
            color = Color(0xFFFFD54F),
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
          )

          Spacer(modifier = Modifier.height(12.dp))

          // The Question
          Text(
            text = "Hadia, Meri Pyari Biwi...\nWill You Marry Me Again?",
            color = Color.White,
            fontSize = 21.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            lineHeight = 28.sp
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "\"Main apni zindagi ke har aane wale kal mein sirf tumhara hath thame chalna chahta hoon. Kya tum hamesha ke liye meri bano gi?\"",
            color = Color(0xFFFFC1D6),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic,
            lineHeight = 20.sp
          )

          Spacer(modifier = Modifier.height(24.dp))

          // Proposal Response Buttons
          Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            // YES BUTTON (Magnificent & Glowing)
            ElevatedButton(
              onClick = {
                isAccepted = true
                onYesAccepted()
              },
              colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color(0xFFE91E63),
                contentColor = Color.White
              ),
              shape = RoundedCornerShape(32.dp),
              elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 10.dp),
              modifier = Modifier
                .fillMaxWidth(0.92f)
                .height(56.dp)
                .testTag("proposal_yes_button")
            ) {
              Icon(Icons.Default.Favorite, contentDescription = null, tint = Color(0xFFFFEB3B), modifier = Modifier.size(24.dp))
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "YES! A Thousand Times YES! 💍❤️",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Playful NO Button (Dodges playfully)
            val currentPhrase = noButtonPhrases[noButtonDodges.coerceAtMost(noButtonPhrases.size - 1)]
            val dodgeOffsetX = if (noButtonDodges in 1..3) (if (noButtonDodges % 2 == 0) 50 else -50) else 0

            OutlinedButton(
              onClick = {
                if (noButtonDodges < noButtonPhrases.size - 1) {
                  noButtonDodges++
                } else {
                  // After teasing, it automatically accepts!
                  isAccepted = true
                  onYesAccepted()
                }
              },
              shape = RoundedCornerShape(24.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF80AB).copy(alpha = 0.5f)),
              colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFFB2C9)),
              modifier = Modifier
                .offset { IntOffset(dodgeOffsetX, 0) }
                .testTag("proposal_playful_no_button")
            ) {
              Text(text = currentPhrase, fontSize = 13.sp)
            }
          }
        }
      }
    } else {
      // THE GLORIOUS CELEBRATION & OFFICIAL LOVE CERTIFICATE
      AnimatedVisibility(
        visible = isAccepted,
        enter = fadeIn(tween(600)) + scaleIn(tween(600, easing = FastOutSlowInEasing))
      ) {
        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Celebration Announcement
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .shadow(24.dp, RoundedCornerShape(28.dp))
              .border(
                width = 3.dp,
                brush = Brush.linearGradient(
                  colors = listOf(Color(0xFFFFD54F), Color(0xFFFF4081), Color(0xFFFFD54F))
                ),
                shape = RoundedCornerShape(28.dp)
              )
              .testTag("proposal_acceptance_card"),
            colors = CardDefaults.cardColors(
              containerColor = Color(0xFFFFF9F0) // Royal Parchment Gold
            )
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Icon(
                imageVector = Icons.Default.Celebration,
                contentDescription = "Celebration",
                tint = Color(0xFFD81B60),
                modifier = Modifier.size(52.dp)
              )

              Spacer(modifier = Modifier.height(10.dp))

              Text(
                text = "SHE SAID YES! 🎉❤️",
                color = Color(0xFF880E4F),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
              )

              Text(
                text = "HADIA & ALI FOREVER AND ALWAYS",
                color = Color(0xFFC2185B),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
              )

              Spacer(modifier = Modifier.height(14.dp))

              HorizontalDivider(thickness = 1.5.dp, color = Color(0xFFFFD54F))

              Spacer(modifier = Modifier.height(16.dp))

              // Certificate Box
              Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFFFF0F5),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF80AB)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(
                  modifier = Modifier.padding(16.dp),
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {
                  Text(
                    text = "📜 OFFICIAL CERTIFICATE OF ETERNAL LOVE",
                    color = Color(0xFF880E4F),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )

                  Spacer(modifier = Modifier.height(10.dp))

                  Text(
                    text = "This sacred bond certifies that Hadia has officially stolen Ali's heart for eternity.\n\n" +
                        "Groom & Soulmate: Ali ❤️\n" +
                        "Queen of His Heart: Hadia 👑\n" +
                        "Verdict: Bound by Endless Love, Laughter & Loyalty.",
                    color = Color(0xFF4A0824),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                  )

                  Spacer(modifier = Modifier.height(12.dp))

                  Row(
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "Sealed with 1000 Red Roses & Diamond Ring",
                      color = Color(0xFF2E7D32),
                      fontSize = 12.sp,
                      fontWeight = FontWeight.SemiBold
                    )
                  }
                }
              }

              Spacer(modifier = Modifier.height(20.dp))

              ElevatedButton(
                onClick = { isAccepted = false },
                colors = ButtonDefaults.elevatedButtonColors(
                  containerColor = Color(0xFF880E4F),
                  contentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.testTag("revisit_proposal_button")
              ) {
                Icon(Icons.Default.Stars, contentDescription = null, tint = Color(0xFFFFD54F))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Relive The Proposal ✨")
              }
            }
          }
        }
      }
    }
  }
}

/**
 * Draws a sparkling golden ring with an exquisite solitaire diamond on Canvas.
 */
private fun DrawScope.drawDiamondRing(
  centerX: Float,
  centerY: Float,
  glow: Float
) {
  val ringRadius = 46f

  // Ring band (Gold)
  drawCircle(
    brush = Brush.sweepGradient(
      colors = listOf(
        Color(0xFFFFD54F),
        Color(0xFFFFB300),
        Color(0xFFFFF9C4),
        Color(0xFFFFD54F)
      ),
      center = Offset(centerX, centerY + 18f)
    ),
    radius = ringRadius,
    center = Offset(centerX, centerY + 18f),
    style = Stroke(width = 8.5f)
  )

  // Inner ring reflection
  drawCircle(
    color = Color(0xFFFFF8E1).copy(alpha = 0.6f),
    radius = ringRadius - 4f,
    center = Offset(centerX, centerY + 18f),
    style = Stroke(width = 1.5f)
  )

  // Ring Mount (Golden prongs)
  val prongPath = Path().apply {
    moveTo(centerX - 16f, centerY - 24f)
    lineTo(centerX - 22f, centerY - 46f)
    lineTo(centerX - 12f, centerY - 44f)
    lineTo(centerX - 8f, centerY - 25f)
    close()
  }
  drawPath(path = prongPath, color = Color(0xFFFFD54F))

  val prongRight = Path().apply {
    moveTo(centerX + 16f, centerY - 24f)
    lineTo(centerX + 22f, centerY - 46f)
    lineTo(centerX + 12f, centerY - 44f)
    lineTo(centerX + 8f, centerY - 25f)
    close()
  }
  drawPath(path = prongRight, color = Color(0xFFFFD54F))

  // The Solitaire Diamond
  val diamondTopY = centerY - 58f
  val diamondMidY = centerY - 42f
  val diamondBotY = centerY - 22f
  val diamondWidth = 32f

  // Diamond Base Triangle
  val baseDiamond = Path().apply {
    moveTo(centerX - diamondWidth, diamondMidY)
    lineTo(centerX + diamondWidth, diamondMidY)
    lineTo(centerX, diamondBotY)
    close()
  }
  drawPath(
    path = baseDiamond,
    brush = Brush.linearGradient(
      colors = listOf(Color(0xFFE1F5FE), Color(0xFF81D4FA), Color(0xFF29B6F6)),
      start = Offset(centerX - diamondWidth, diamondMidY),
      end = Offset(centerX, diamondBotY)
    )
  )

  // Diamond Crown (Top Trapezoid)
  val crownDiamond = Path().apply {
    moveTo(centerX - diamondWidth * 0.65f, diamondTopY)
    lineTo(centerX + diamondWidth * 0.65f, diamondTopY)
    lineTo(centerX + diamondWidth, diamondMidY)
    lineTo(centerX - diamondWidth, diamondMidY)
    close()
  }
  drawPath(
    path = crownDiamond,
    brush = Brush.linearGradient(
      colors = listOf(Color.White, Color(0xFFB3E5FC), Color.White),
      start = Offset(centerX, diamondTopY),
      end = Offset(centerX, diamondMidY)
    )
  )

  // Diamond Facet lines for realism
  drawLine(
    color = Color.White.copy(alpha = 0.9f),
    start = Offset(centerX, diamondTopY),
    end = Offset(centerX, diamondBotY),
    strokeWidth = 1.5f
  )
  drawLine(
    color = Color.White.copy(alpha = 0.8f),
    start = Offset(centerX - diamondWidth * 0.3f, diamondTopY),
    end = Offset(centerX, diamondBotY),
    strokeWidth = 1.2f
  )
  drawLine(
    color = Color.White.copy(alpha = 0.8f),
    start = Offset(centerX + diamondWidth * 0.3f, diamondTopY),
    end = Offset(centerX, diamondBotY),
    strokeWidth = 1.2f
  )

  // Gleam Star on Diamond corner
  val gleamX = centerX - 12f
  val gleamY = diamondTopY + 4f
  val gleamSize = 14f * glow
  drawLine(
    color = Color.White,
    start = Offset(gleamX - gleamSize, gleamY),
    end = Offset(gleamX + gleamSize, gleamY),
    strokeWidth = 2.5f
  )
  drawLine(
    color = Color.White,
    start = Offset(gleamX, gleamY - gleamSize),
    end = Offset(gleamX, gleamY + gleamSize),
    strokeWidth = 2.5f
  )
  drawCircle(color = Color.White, radius = 3.5f * glow, center = Offset(gleamX, gleamY))
}

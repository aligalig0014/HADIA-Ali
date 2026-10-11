package com.example.components

import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class RosePetal(
  var x: Float,
  var y: Float,
  var size: Float,
  var speedY: Float,
  var swingSpeed: Float,
  var swingAmplitude: Float,
  var angle: Float,
  var rotationSpeed: Float,
  var initialPhase: Float,
  var color: Color,
  var darkColor: Color,
  var alpha: Float = 0.95f,
  var isHeart: Boolean = false
)

@Composable
fun FallingRosePetals(
  modifier: Modifier = Modifier,
  petalCount: Int = 36,
  isCelebrationShower: Boolean = false,
  onTouchSpawn: Boolean = true
) {
  val petals = remember { mutableStateListOf<RosePetal>() }
  val targetCount = if (isCelebrationShower) petalCount * 2 else petalCount

  val crimsonPalette = remember {
    listOf(
      Pair(Color(0xFFE91E63), Color(0xFF880E4F)), // Vivid Pink to Deep Velvet Crimson
      Pair(Color(0xFFFF4081), Color(0xFFC2185B)), // Blush Rose to Rose Gold
      Pair(Color(0xFFD81B60), Color(0xFF4A0E24)), // Dark Ruby
      Pair(Color(0xFFFF80AB), Color(0xFFD81B60)), // Soft Blossom Pink
      Pair(Color(0xFFFFD54F), Color(0xFFFF8F00))  // Golden Star Petals
    )
  }

  // Animation frame loop for silky 60fps physics
  LaunchedEffect(isCelebrationShower) {
    var previousTime = System.currentTimeMillis()
    while (true) {
      withInfiniteAnimationFrameMillis { frameTime ->
        val dt = ((frameTime - previousTime) / 1000f).coerceIn(0.001f, 0.05f)
        previousTime = frameTime

        // Update positions
        val iterator = petals.iterator()
        while (iterator.hasNext()) {
          val petal = iterator.next()
          petal.y += petal.speedY * dt * 60f
          petal.angle += petal.rotationSpeed * dt * 60f

          val sway = sin(petal.y * 0.015f * petal.swingSpeed + petal.initialPhase)
          petal.x += sway * petal.swingAmplitude * dt * 60f

          // Recycle when fallen off bottom
          if (petal.y > 2400f) {
            petal.y = -60f
            petal.x = Random.nextFloat() * 1200f
          }
        }
      }
    }
  }

  Canvas(
    modifier = modifier
      .fillMaxSize()
      .testTag("falling_rose_petals_canvas")
      .pointerInput(onTouchSpawn) {
        if (!onTouchSpawn) return@pointerInput
        detectTapGestures { offset ->
          // Spawn cluster of 8 bursting petals & hearts where user touches
          for (i in 0 until 8) {
            val palette = crimsonPalette.random()
            petals.add(
              RosePetal(
                x = offset.x + Random.nextFloat() * 80f - 40f,
                y = offset.y + Random.nextFloat() * 60f - 30f,
                size = Random.nextFloat() * 18f + 16f,
                speedY = Random.nextFloat() * 3.5f + 2f,
                swingSpeed = Random.nextFloat() * 1.5f + 0.8f,
                swingAmplitude = Random.nextFloat() * 2.5f + 1.2f,
                angle = Random.nextFloat() * 360f,
                rotationSpeed = Random.nextFloat() * 4f - 2f,
                initialPhase = Random.nextFloat() * 6.28f,
                color = palette.first,
                darkColor = palette.second,
                alpha = 1.0f,
                isHeart = i % 2 == 0
              )
            )
          }
        }
      }
  ) {
    val canvasWidth = size.width
    val canvasHeight = size.height

    // Lazy initialize default petals based on screen size
    if (petals.isEmpty()) {
      for (i in 0 until targetCount) {
        val palette = crimsonPalette.random()
        petals.add(
          RosePetal(
            x = Random.nextFloat() * canvasWidth,
            y = Random.nextFloat() * (canvasHeight + 400f) - 200f,
            size = Random.nextFloat() * 20f + 18f,
            speedY = (Random.nextFloat() * 2.2f + 1.2f) * if (isCelebrationShower) 2.2f else 1f,
            swingSpeed = Random.nextFloat() * 1.2f + 0.6f,
            swingAmplitude = Random.nextFloat() * 1.8f + 0.8f,
            angle = Random.nextFloat() * 360f,
            rotationSpeed = Random.nextFloat() * 2.5f - 1.25f,
            initialPhase = Random.nextFloat() * 6.28f,
            color = palette.first,
            darkColor = palette.second,
            alpha = Random.nextFloat() * 0.25f + 0.75f,
            isHeart = Random.nextFloat() < 0.25f
          )
        )
      }
    }

    petals.forEach { petal ->
      rotate(degrees = petal.angle, pivot = Offset(petal.x, petal.y)) {
        if (petal.isHeart) {
          drawLittleHeart(petal)
        } else {
          drawRealisticPetal(petal)
        }
      }
    }
  }
}

private fun DrawScope.drawRealisticPetal(petal: RosePetal) {
  val s = petal.size
  val x = petal.x
  val y = petal.y

  val path = Path().apply {
    moveTo(x, y - s)
    // Curving teardrop petal shape
    cubicTo(
      x + s * 0.9f, y - s * 0.7f,
      x + s * 1.1f, y + s * 0.3f,
      x, y + s
    )
    cubicTo(
      x - s * 1.1f, y + s * 0.3f,
      x - s * 0.9f, y - s * 0.7f,
      x, y - s
    )
    close()
  }

  // Linear gradient gives organic curvature
  drawPath(
    path = path,
    brush = Brush.linearGradient(
      colors = listOf(petal.color.copy(alpha = petal.alpha), petal.darkColor.copy(alpha = petal.alpha)),
      start = Offset(x - s, y - s),
      end = Offset(x + s, y + s)
    )
  )

  // Subtle delicate highlight vein
  val veinPath = Path().apply {
    moveTo(x, y - s * 0.8f)
    quadraticTo(x + s * 0.1f, y, x, y + s * 0.7f)
  }
  drawPath(
    path = veinPath,
    color = Color.White.copy(alpha = petal.alpha * 0.25f),
    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.2f)
  )
}

private fun DrawScope.drawLittleHeart(petal: RosePetal) {
  val s = petal.size * 0.8f
  val x = petal.x
  val y = petal.y

  val path = Path().apply {
    moveTo(x, y - s * 0.3f)
    cubicTo(x - s * 0.7f, y - s * 1.1f, x - s * 1.4f, y - s * 0.1f, x, y + s * 0.9f)
    cubicTo(x + s * 1.4f, y - s * 0.1f, x + s * 0.7f, y - s * 1.1f, x, y - s * 0.3f)
    close()
  }

  drawPath(
    path = path,
    brush = Brush.radialGradient(
      colors = listOf(
        Color(0xFFFF80AB).copy(alpha = petal.alpha),
        Color(0xFFE91E63).copy(alpha = petal.alpha),
        Color(0xFF880E4F).copy(alpha = petal.alpha)
      ),
      center = Offset(x, y),
      radius = s
    )
  )
}

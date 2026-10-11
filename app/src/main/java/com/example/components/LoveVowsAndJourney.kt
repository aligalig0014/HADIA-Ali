package com.example.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.Loyalty
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SacredVow(
  val id: Int,
  val urduTitle: String,
  val urduPromise: String,
  val englishMeaning: String
)

@Composable
fun LoveVowsAndJourney(
  modifier: Modifier = Modifier,
  onVowSealed: () -> Unit = {}
) {
  val vows = remember {
    listOf(
      SacredVow(
        1,
        "Pehla Wada: Tumhari Muskaan",
        "Main hamesha tumhari khushi ko apni zindagi ki sab se pehli tarjeeh banaoonga.",
        "I promise your happiness will always come before everything else."
      ),
      SacredVow(
        2,
        "Doosra Wada: Hamesha Sath",
        "Har dhoop chaaon aur mushkil waqt mein tumhara hath thaam kar khada rahoonga.",
        "I promise to hold your hand firmly through every storm and trial."
      ),
      SacredVow(
        3,
        "Teesra Wada: Dil Ka Sukoon",
        "Tumhari har choti baat ko dil se sunoonga, samjhoonga aur qadar karoonga.",
        "I promise to always listen to your thoughts with patience and tenderness."
      ),
      SacredVow(
        4,
        "Choutha Wada: Izzat Aur Maan",
        "Apni har saans ke sath tumhari izzat aur hifazat karoonga, meri Malika Hadia.",
        "I promise to honor, respect and protect you with my very life."
      ),
      SacredVow(
        5,
        "Paanchwa Wada: Shukr-e-Khuda",
        "Har subha tumhara chehra dekh kar Allah Tala ka shukr ada karoonga ke usne mujhe tum diya.",
        "I promise to thank the Almighty every morning for blessing me with you."
      ),
      SacredVow(
        6,
        "Chhata Wada: Sukoon-e-Qalb",
        "Tumhe kabhi akela ya udas mehsoos nahi hone doonga, mera kandha hamesha tumhara hai.",
        "I promise you will never feel alone or forgotten in this world."
      ),
      SacredVow(
        7,
        "Saatwa Wada: Aakhri Saans Tak",
        "Zindagi ke aakhri saans tak, aur jannat mein bhi sirf aur sirf tumse mohabbat karoonga.",
        "I promise to love only you until my last breath, and into eternity."
      )
    )
  }

  val sealedVows = remember { mutableStateListOf(1, 2) }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Header
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color(0xFF38081E).copy(alpha = 0.95f),
      border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD54F).copy(alpha = 0.5f)),
      modifier = Modifier
        .shadow(8.dp, RoundedCornerShape(20.dp))
        .testTag("vows_header")
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(Icons.Default.Loyalty, contentDescription = null, tint = Color(0xFFFFD54F), modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "ALI'S 7 SACRED VOWS TO HADIA",
          color = Color(0xFFFFE082),
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    Text(
      text = "Ali's 7 Eternal Promises (Kasmein) 🌹",
      color = Color.White,
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center
    )

    Text(
      text = "Tap any promise to seal it in Hadia's heart (${sealedVows.size}/7 Sealed)",
      color = Color(0xFFFFB2C9),
      fontSize = 12.sp,
      fontStyle = FontStyle.Italic
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Vows List
    vows.forEach { vow ->
      val isSealed = sealedVows.contains(vow.id)

      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 6.dp)
          .shadow(6.dp, RoundedCornerShape(18.dp))
          .clip(RoundedCornerShape(18.dp))
          .border(
            width = if (isSealed) 1.5.dp else 1.dp,
            color = if (isSealed) Color(0xFFFFD54F) else Color(0xFF5A1030),
            shape = RoundedCornerShape(18.dp)
          )
          .clickable {
            if (isSealed) {
              sealedVows.remove(vow.id)
            } else {
              sealedVows.add(vow.id)
              onVowSealed()
            }
          }
          .testTag("vow_card_${vow.id}"),
        colors = CardDefaults.cardColors(
          containerColor = if (isSealed) Color(0xFF38081E) else Color(0xFF220512)
        )
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Heart Stamp Toggle
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(CircleShape)
              .background(
                if (isSealed) Brush.radialGradient(
                  colors = listOf(Color(0xFFE91E63), Color(0xFF880E4F))
                ) else Brush.radialGradient(
                  colors = listOf(Color(0xFF38081E), Color(0xFF1E0312))
                )
              )
              .border(
                1.dp,
                if (isSealed) Color(0xFFFFD54F) else Color(0xFF6B1236),
                CircleShape
              ),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (isSealed) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
              contentDescription = "Sealed",
              tint = if (isSealed) Color(0xFFFFEB3B) else Color(0xFFFF80AB),
              modifier = Modifier.size(22.dp)
            )
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = vow.urduTitle,
              color = Color(0xFFFFD54F),
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = vow.urduPromise,
              color = Color(0xFFFFF0F5),
              fontSize = 13.sp,
              fontWeight = FontWeight.Medium,
              lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = vow.englishMeaning,
              color = Color(0xFFFFB2C9),
              fontSize = 11.sp,
              fontStyle = FontStyle.Italic
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Love Counter / Journey Card
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .shadow(12.dp, RoundedCornerShape(24.dp))
        .border(
          width = 1.5.dp,
          brush = Brush.linearGradient(
            colors = listOf(Color(0xFFFFD54F), Color(0xFFFF4081))
          ),
          shape = RoundedCornerShape(24.dp)
        )
        .testTag("love_journey_card"),
      colors = CardDefaults.cardColors(
        containerColor = Color(0xFF280718)
      )
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFFFD54F), modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "ETERNAL LOVE TIMELINE",
            color = Color(0xFFFFD54F),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.width(8.dp))
          Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFFFD54F), modifier = Modifier.size(18.dp))
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly
        ) {
          TimeMetricPill("Forever", "Years")
          TimeMetricPill("Endless", "Days")
          TimeMetricPill("Every", "Breath")
          TimeMetricPill("Infinite", "Heartbeats")
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "\"Mohabbat wo nahi jo ginti ki jaye, mohabbat wo hai jo Hadia aur Ali ki tarah har pal badhti jaye.\" ❤️",
          color = Color(0xFFFFE0EC),
          fontSize = 13.sp,
          fontStyle = FontStyle.Italic,
          textAlign = TextAlign.Center,
          lineHeight = 19.sp
        )
      }
    }
  }
}

@Composable
private fun TimeMetricPill(value: String, label: String) {
  Surface(
    shape = RoundedCornerShape(14.dp),
    color = Color(0xFF38081E),
    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF80AB).copy(alpha = 0.4f))
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = value,
        color = Color(0xFFFFD54F),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 14.sp
      )
      Text(
        text = label,
        color = Color(0xFFFFB2C9),
        fontSize = 10.sp
      )
    }
  }
}

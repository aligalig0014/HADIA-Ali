package com.example.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RomanticLoveLetter(
  modifier: Modifier = Modifier,
  onKissSealed: () -> Unit = {}
) {
  var isLetterOpened by remember { mutableStateOf(false) }
  var kissCount by remember { mutableIntStateOf(0) }
  var customNote by remember { mutableStateOf("") }
  var showEditDialog by remember { mutableStateOf(false) }

  val defaultLoveLetterUrdu = """
Meri Pyari Biwi Hadia,
Meri Rooh, Meri Zindagi... ❤️

Sach kehte hain ke jab naseeb chamakta hai to khuda kisi ko aisi humsafar deta hai jo zindagi ko jannat bana de. Aur mere liye wo tum ho, Hadia.

Jab se tum meri zindagi mein aayi ho, mere har din ki shuruwaat tumhari hansi se hoti hai, aur har raat tumhare khayal ke sukoon mein dhal jaati hai. 

Tum meri sirf biwi nahi ho, tum meri sab se pyari dost, meri taaqat, aur meri har khushi ka wajood ho. Jab tum mere sath hoti ho, to mujhe lagta hai ke main duniya ka sab se khush-naseeb shakhs hoon.

Main chahata hoon ke hum hamesha aise hi ek doosre ka hath thamey rahein. Har khushi mein, har mushkil mein, main hamesha tumhara sahara ban kar khada rahoonga.

Yeh chota sa dil tumhara tha, tumhara hai, aur aakhri saans tak sirf tumhara rahega meri Hadia! 🌹💍
""".trimIndent()

  val lovePoetry = """
"Tere chehre ki muskaan meri har thakan mita deti hai,
Tere hathon ki narmi mujhe har gham bhula deti hai,
Rab se mangi hui har dua mein sirf tu thi Hadia,
Aur ab meri har saans sirf tere naam se chalti hai..."
""".trimIndent()

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (!isLetterOpened) {
      // Vintage Closed Envelope Presentation
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .shadow(16.dp, RoundedCornerShape(24.dp))
          .clip(RoundedCornerShape(24.dp))
          .border(
            width = 2.dp,
            brush = Brush.linearGradient(
              colors = listOf(Color(0xFFFFD54F), Color(0xFFFF4081), Color(0xFFFFB300))
            ),
            shape = RoundedCornerShape(24.dp)
          )
          .clickable { isLetterOpened = true }
          .testTag("love_letter_envelope"),
        colors = CardDefaults.cardColors(
          containerColor = Color(0xFF2E0719)
        )
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 36.dp, horizontal = 24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Wax Seal Icon
          Box(
            modifier = Modifier
              .size(80.dp)
              .shadow(12.dp, CircleShape)
              .clip(CircleShape)
              .background(
                Brush.radialGradient(
                  colors = listOf(Color(0xFFE91E63), Color(0xFF880E4F), Color(0xFF4A0820))
                )
              )
              .border(2.dp, Color(0xFFFFD54F), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Wax Seal",
                tint = Color(0xFFFFD54F),
                modifier = Modifier.size(28.dp)
              )
              Text(
                text = "A & H",
                color = Color(0xFFFFE082),
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          Text(
            text = "ROYAL LOVE LETTER",
            color = Color(0xFFFFD54F),
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 2.sp
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "To: Meri Pyari Biwi Hadia ❤️\nFrom: Ali (Aapka Humsafar)",
            color = Color(0xFFFFF0F5),
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
          )

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = "An intimate letter sealed with boundless love, loyalty, and eternal devotion.",
            color = Color(0xFFFFB2C9),
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic
          )

          Spacer(modifier = Modifier.height(24.dp))

          ElevatedButton(
            onClick = { isLetterOpened = true },
            colors = ButtonDefaults.elevatedButtonColors(
              containerColor = Color(0xFFD81B60),
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.testTag("open_love_letter_button")
          ) {
            Icon(Icons.Default.LockOpen, contentDescription = "Open", tint = Color(0xFFFFD54F))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Break The Golden Seal & Open 💌",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }
        }
      }
    } else {
      // Opened Vintage Parchment Letter
      AnimatedVisibility(
        visible = isLetterOpened,
        enter = fadeIn(tween(600)) + scaleIn(tween(600, easing = FastOutSlowInEasing))
      ) {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .shadow(20.dp, RoundedCornerShape(20.dp))
            .border(
              width = 2.dp,
              brush = Brush.linearGradient(
                colors = listOf(Color(0xFFFFD54F), Color(0xFFE91E63), Color(0xFFFFD54F))
              ),
              shape = RoundedCornerShape(20.dp)
            )
            .testTag("opened_parchment_letter"),
          colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF8F0) // Luxurious warm parchment
          )
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp)
          ) {
            // Header with crest
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "🌹 Forever Ali & Hadia",
                color = Color(0xFF880E4F),
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
              )
              Row {
                IconButton(onClick = { showEditDialog = true }) {
                  Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Personalize",
                    tint = Color(0xFF880E4F)
                  )
                }
                IconButton(onClick = { isLetterOpened = false }) {
                  Icon(
                    imageVector = Icons.Default.Mail,
                    contentDescription = "Close envelope",
                    tint = Color(0xFF880E4F)
                  )
                }
              }
            }

            HorizontalDivider(
              thickness = 1.dp,
              color = Color(0xFFFFD54F),
              modifier = Modifier.padding(vertical = 8.dp)
            )

            // Letter Body
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
            ) {
              Text(
                text = defaultLoveLetterUrdu,
                color = Color(0xFF2B0A18),
                fontSize = 15.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif
              )

              if (customNote.isNotEmpty()) {
                Spacer(modifier = Modifier.height(14.dp))
                Surface(
                  shape = RoundedCornerShape(12.dp),
                  color = Color(0xFFFFE4EC),
                  border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF80AB)),
                  modifier = Modifier.fillMaxWidth()
                ) {
                  Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                      text = "Special Note from Ali to Hadia:",
                      fontWeight = FontWeight.Bold,
                      color = Color(0xFFC2185B),
                      fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                      text = customNote,
                      color = Color(0xFF4A0820),
                      fontSize = 14.sp,
                      fontStyle = FontStyle.Italic
                    )
                  }
                }
              }

              Spacer(modifier = Modifier.height(16.dp))

              // Poetry Callout
              Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFFFF0F5),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD54F)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(
                  modifier = Modifier.padding(16.dp),
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {
                  Text(
                    text = "🌹 SHAYARI FOR HADIA 🌹",
                    color = Color(0xFFC2185B),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                  )
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                    text = lovePoetry,
                    color = Color(0xFF5A082A),
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                  )
                }
              }

              Spacer(modifier = Modifier.height(18.dp))

              // Signature
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column(horizontalAlignment = Alignment.End) {
                  Text(
                    text = "Hamesha sirf aapka,",
                    color = Color(0xFF75103E),
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic
                  )
                  Text(
                    text = "Ali ❤️",
                    color = Color(0xFFC2185B),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Cursive
                  )
                }
              }

              Spacer(modifier = Modifier.height(16.dp))

              // Interactive Action: Seal with a kiss
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
              ) {
                ElevatedButton(
                  onClick = {
                    kissCount++
                    onKissSealed()
                  },
                  colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xFFE91E63),
                    contentColor = Color.White
                  ),
                  shape = RoundedCornerShape(24.dp),
                  modifier = Modifier.testTag("seal_with_kiss_button")
                ) {
                  Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Kiss",
                    tint = Color(0xFFFFEB3B),
                    modifier = Modifier.size(18.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = if (kissCount == 0) "Seal with a Kiss 💋" else "Kissed $kissCount Times! 💋❤️",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                  )
                }
              }
            }
          }
        }
      }
    }
  }

  // Personalization Dialog for Ali
  if (showEditDialog) {
    AlertDialog(
      onDismissRequest = { showEditDialog = false },
      title = {
        Text("Add Special Note for Hadia ❤️", color = Color(0xFF880E4F), fontWeight = FontWeight.Bold)
      },
      text = {
        Column {
          Text(
            "Write a private memory, nickname, or special date for your wife Hadia:",
            fontSize = 13.sp,
            color = Color(0xFF5A082A)
          )
          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = customNote,
            onValueChange = { customNote = it },
            placeholder = { Text("e.g., Hadia, remember our first conversation...") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("custom_note_input"),
            maxLines = 4
          )
        }
      },
      confirmButton = {
        TextButton(onClick = { showEditDialog = false }) {
          Text("Save in Letter", color = Color(0xFFE91E63), fontWeight = FontWeight.Bold)
        }
      },
      dismissButton = {
        TextButton(onClick = { showEditDialog = false }) {
          Text("Cancel")
        }
      }
    )
  }
}

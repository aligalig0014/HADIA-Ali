package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.sin

/**
 * High-fidelity native synthesizer that generates an ultra-romantic, soft, emotional
 * piano and music box melody for Ali & Hadia's proposal.
 * Auto-plays smoothly without external audio asset dependencies.
 */
class RomanticMusicPlayer {

  private val _isPlaying = MutableStateFlow(false)
  val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

  private val _isMuted = MutableStateFlow(false)
  val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()

  private val _currentTrackTitle = MutableStateFlow("Melody of Eternal Love (For Hadia)")
  val currentTrackTitle: StateFlow<String> = _currentTrackTitle.asStateFlow()

  private var audioJob: Job? = null
  private var audioTrack: AudioTrack? = null
  private val scope = CoroutineScope(Dispatchers.Default)

  private val sampleRate = 22050
  private val bufferSize = AudioTrack.getMinBufferSize(
    sampleRate,
    AudioFormat.CHANNEL_OUT_MONO,
    AudioFormat.ENCODING_PCM_16BIT
  ).coerceAtLeast(4096)

  // Soft romantic arpeggio notes (Frequencies in Hz)
  // C4, E4, G4, B4, C5, D5, E5, G5, A4, F4
  private val melodyNotes = listOf(
    // Phrase 1: C Major dreamy romantic arpeggio (C4 - E4 - G4 - C5 - E5)
    261.63, 329.63, 392.00, 523.25, 659.25, 523.25, 392.00, 329.63,
    // Phrase 2: A Minor tender longing (A3 - C4 - E4 - A4 - C5)
    220.00, 261.63, 329.63, 440.00, 523.25, 440.00, 329.63, 261.63,
    // Phrase 3: F Major soft devotion (F3 - A3 - C4 - F4 - A4)
    174.61, 220.00, 261.63, 349.23, 440.00, 349.23, 261.63, 220.00,
    // Phrase 4: G Major warmth & promise (G3 - B3 - D4 - G4 - B4)
    196.00, 246.94, 293.66, 392.00, 493.88, 392.00, 293.66, 246.94,
    // Phrase 5: E Minor gentle whisper
    164.81, 246.94, 329.63, 392.00, 493.88, 392.00, 329.63, 246.94,
    // Phrase 6: Sweet romantic resolution (C4 - G4 - E5 - G5 - C6 chime)
    261.63, 392.00, 523.25, 659.25, 783.99, 1046.50, 783.99, 523.25
  )

  fun start() {
    if (_isPlaying.value) return
    _isPlaying.value = true

    audioJob?.cancel()
    audioJob = scope.launch {
      runAudioLoop()
    }
  }

  fun pause() {
    _isPlaying.value = false
    audioJob?.cancel()
    audioJob = null
    try {
      audioTrack?.pause()
      audioTrack?.flush()
    } catch (e: Exception) {
      Log.e("RomanticMusicPlayer", "Error pausing audio", e)
    }
  }

  fun togglePlay() {
    if (_isPlaying.value) pause() else start()
  }

  fun toggleMute() {
    _isMuted.value = !_isMuted.value
  }

  fun playProposalYesFanfare() {
    scope.launch {
      try {
        val fanfareTrack = AudioTrack.Builder()
          .setAudioAttributes(
            AudioAttributes.Builder()
              .setUsage(AudioAttributes.USAGE_MEDIA)
              .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
              .build()
          )
          .setAudioFormat(
            AudioFormat.Builder()
              .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
              .setSampleRate(sampleRate)
              .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
              .build()
          )
          .setBufferSizeInBytes(bufferSize)
          .setTransferMode(AudioTrack.MODE_STREAM)
          .build()

        fanfareTrack.play()
        val fanfareNotes = listOf(
          523.25, 659.25, 783.99, 1046.50, 1318.51, 1567.98
        )
        for (freq in fanfareNotes) {
          val samples = generatePianoTone(freq, durationSec = 0.22, volume = 0.85)
          fanfareTrack.write(samples, 0, samples.size)
        }
        val finalChord = generatePianoTone(1046.50, durationSec = 1.2, volume = 0.9)
        fanfareTrack.write(finalChord, 0, finalChord.size)
        fanfareTrack.stop()
        fanfareTrack.release()
      } catch (e: Exception) {
        Log.e("RomanticMusicPlayer", "Error playing fanfare", e)
      }
    }
  }

  fun playHeartbeatSound() {
    scope.launch {
      try {
        val beatTrack = AudioTrack.Builder()
          .setAudioAttributes(
            AudioAttributes.Builder()
              .setUsage(AudioAttributes.USAGE_MEDIA)
              .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
              .build()
          )
          .setAudioFormat(
            AudioFormat.Builder()
              .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
              .setSampleRate(sampleRate)
              .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
              .build()
          )
          .setBufferSizeInBytes(bufferSize)
          .setTransferMode(AudioTrack.MODE_STREAM)
          .build()

        beatTrack.play()
        // Lub-dub low frequency pulses
        val lub = generateHeartbeatPulse(65.0, durationSec = 0.12, volume = 0.7)
        val dub = generateHeartbeatPulse(55.0, durationSec = 0.15, volume = 0.5)
        beatTrack.write(lub, 0, lub.size)
        val silence = ShortArray((sampleRate * 0.08).toInt())
        beatTrack.write(silence, 0, silence.size)
        beatTrack.write(dub, 0, dub.size)
        beatTrack.stop()
        beatTrack.release()
      } catch (e: Exception) {
        Log.e("RomanticMusicPlayer", "Error playing heartbeat", e)
      }
    }
  }

  private suspend fun runAudioLoop() {
    try {
      val track = AudioTrack.Builder()
        .setAudioAttributes(
          AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        )
        .setAudioFormat(
          AudioFormat.Builder()
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setSampleRate(sampleRate)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .build()
        )
        .setBufferSizeInBytes(bufferSize)
        .setTransferMode(AudioTrack.MODE_STREAM)
        .build()

      audioTrack = track
      track.play()

      var noteIndex = 0
      while (scope.isActive && _isPlaying.value) {
        val freq = melodyNotes[noteIndex % melodyNotes.size]
        val noteDuration = 0.48 // Gentle slow tempo ~75 BPM
        val volume = if (_isMuted.value) 0.0 else 0.42

        val pcmData = generatePianoTone(freq, noteDuration, volume)
        track.write(pcmData, 0, pcmData.size)

        noteIndex++
      }

      track.stop()
      track.release()
      audioTrack = null
    } catch (e: Exception) {
      Log.e("RomanticMusicPlayer", "Audio loop exception", e)
    }
  }

  /**
   * Generates a warm, music-box / soft acoustic piano tone with harmonic overtones
   * and a smooth exponential envelope decay.
   */
  private fun generatePianoTone(freq: Double, durationSec: Double, volume: Double): ShortArray {
    val numSamples = (sampleRate * durationSec).toInt()
    val buffer = ShortArray(numSamples)
    val twoPi = 2.0 * PI

    for (i in 0 until numSamples) {
      val t = i.toDouble() / sampleRate
      // Envelope: Fast attack (0.02s) followed by smooth exponential decay
      val envelope = if (t < 0.02) {
        (t / 0.02)
      } else {
        exp(-2.8 * (t - 0.02))
      }

      // Fundamental + gentle harmonics (1st, 2nd, 3rd) for warm music box bell tone
      val fundamental = sin(twoPi * freq * t)
      val secondHarmonic = 0.35 * sin(twoPi * freq * 2.0 * t)
      val thirdHarmonic = 0.15 * sin(twoPi * freq * 3.0 * t)
      val subHarmonic = 0.1 * sin(twoPi * (freq / 2.0) * t)

      val rawSample = (fundamental + secondHarmonic + thirdHarmonic + subHarmonic) * envelope * volume
      val clamped = (rawSample * Short.MAX_VALUE).coerceIn(Short.MIN_VALUE.toDouble(), Short.MAX_VALUE.toDouble())
      buffer[i] = clamped.toInt().toShort()
    }

    return buffer
  }

  private fun generateHeartbeatPulse(freq: Double, durationSec: Double, volume: Double): ShortArray {
    val numSamples = (sampleRate * durationSec).toInt()
    val buffer = ShortArray(numSamples)
    val twoPi = 2.0 * PI
    for (i in 0 until numSamples) {
      val t = i.toDouble() / sampleRate
      val envelope = sin(PI * (t / durationSec)) // Half sine window
      val sample = sin(twoPi * freq * t) * envelope * volume
      buffer[i] = (sample * Short.MAX_VALUE).coerceIn(Short.MIN_VALUE.toDouble(), Short.MAX_VALUE.toDouble()).toInt().toShort()
    }
    return buffer
  }

  fun release() {
    _isPlaying.value = false
    audioJob?.cancel()
    try {
      audioTrack?.stop()
      audioTrack?.release()
    } catch (_: Exception) {}
    audioTrack = null
  }
}

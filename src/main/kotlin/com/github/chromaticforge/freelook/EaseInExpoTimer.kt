package com.github.chromaticforge.freelook

import kotlin.math.pow

class EaseInExpoTimer(private val durationMs: Long) {
    private var startTime = 0L

    var active: Boolean = false

    val complete: Boolean
        get() = this.timeRemaining <= 0L && this.active

    fun start() {
        this.startTime = System.currentTimeMillis()
        this.active = true
    }

    fun stop() {
        this.active = false
    }

    // had to kotlinize my kotlin code... typa shit im on.
    val currentProgress: Float
        get() = when {
            !active || startTime == 0L -> 0.0f
            complete -> 1.0f
            else -> {
                val f = ((durationMs - timeRemaining).toFloat() / durationMs).coerceIn(0.0f, 1.0f)
                (1.0f - 2.0f.pow(-10.0f * f)).coerceIn(0.0f, 1.0f)
            }
        }

    private val timeRemaining: Long
        get() = startTime + durationMs - System.currentTimeMillis()
}

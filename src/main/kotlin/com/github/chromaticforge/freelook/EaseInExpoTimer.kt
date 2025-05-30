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

    val currentProgress: Float
        get() {
            if (!this.active || startTime == 0L) {
                return 0.0f
            }
            if (this.complete) {
                return 1.0f
            }
            val f = (durationMs - this.timeRemaining).toFloat() / durationMs
            return if (f >= 1.0f) 1.0f else 1.0f - 2.0f.pow((-10.0f * f))
        }

    private val timeRemaining: Long
        get() = startTime + durationMs - System.currentTimeMillis()
}

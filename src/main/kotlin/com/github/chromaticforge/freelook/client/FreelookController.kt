package com.github.chromaticforge.freelook.client

import com.github.chromaticforge.freelook.client.config.FreelookConfig
import dev.deftu.omnicore.client.OmniClient
import dev.deftu.omnicore.client.OmniClientPlayer
import net.minecraft.util.math.MathHelper
import org.polyfrost.polyui.animate.Animation
import org.polyfrost.polyui.animate.Easing

object FreelookController {
    @JvmField
    var perspectiveToggled: Boolean = false
    private var lastPerspective: Int = 0
    private var pressStartTime: Long = 0
    private var lastUpdateTime: Long = 0
    private val timer: Animation = Easing.Expo(Easing.Type.Out, 650L, 0.0f, 1.0f)

    fun handlePressAndHold(pressed: Boolean) {
        if (pressed && pressStartTime == 0L) {
            pressStartTime = System.currentTimeMillis()
            toggle()
        } else {
            val pressDuration = System.currentTimeMillis() - pressStartTime
            pressStartTime = 0L

            if (pressDuration > FreelookConfig.Activation.holdThreshold) {
                stop()
            }
        }
    }

    fun toggle() {
        if (perspectiveToggled) {
            stop()
        } else {
            start()
        }
    }

    fun start() {
        val currentPerspective = PerspectiveManager.getCurrentPerspective()
        if (currentPerspective != lastPerspective) {
            lastPerspective = currentPerspective
        }

        val perspective = FreelookConfig.perspectiveMode

        when (FreelookConfig.changePerspective) {
            0 -> {}
            1 -> if (lastPerspective == 0) {
                PerspectiveManager.setPerspective(perspective)
            }
            2 -> if (lastPerspective != 0) {
                PerspectiveManager.setPerspective(perspective)
            }
            3 -> PerspectiveManager.setPerspective(perspective)
        }

        if (FreelookConfig.smoothCamera) {
            timer.reset()
            lastUpdateTime = System.currentTimeMillis()
        }

        val player = OmniClient.getInstance().player
        CameraStateTracker.setCameraYaw(player, OmniClientPlayer.yaw)
        CameraStateTracker.setCameraPitch(player, OmniClientPlayer.pitch)

        perspectiveToggled = true
    }

    fun stop() {
        perspectiveToggled = false
        PerspectiveManager.setPerspective(lastPerspective)
        timer.finishNow()
        //#if MC <= 1.12.2
        //$$ OmniClient.getInstance().renderGlobal.setDisplayListEntitiesDirty()
        //#endif
    }

    fun applySmoothScale(z: Float): Float {
        if (!perspectiveToggled || timer.isFinished || !FreelookConfig.smoothCamera) {
            return z
        }

        val currentTime = System.currentTimeMillis()
        val delta = currentTime - lastUpdateTime
        lastUpdateTime = currentTime
        timer.update(delta)
        val transitionProgress = timer.value
        val scale = 0.125f + transitionProgress * (1.0f - 0.125f)
        return z * scale
    }

    fun updateCameraValue(
        currentValue: Float,
        delta: Float,
        invert: Boolean,
        lock: Boolean,
        min: Float,
        max: Float
    ): Float {
        val adjustedDelta = if (invert) -delta else delta
        return if (lock) {
            MathHelper.clamp(currentValue + adjustedDelta, min, max)
        } else {
            currentValue + adjustedDelta
        }
    }
}

package com.github.chromaticforge.freelook.hook

import cc.polyfrost.oneconfig.utils.dsl.mc
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils
import com.github.chromaticforge.freelook.EaseInExpoTimer
import com.github.chromaticforge.freelook.config.FreelookConfig
import kotlin.math.pow

object FreelookHook {
    @JvmField
    val timer = EaseInExpoTimer(650L)

    @JvmField
    var perspectiveToggled = false
    @JvmField
    var cameraYaw = 0f
    @JvmField
    var cameraPitch = 0f

    private var previousPerspective = 0

    @JvmStatic
    fun togglePerspective() {
        setPerspective(!perspectiveToggled)
    }

    @JvmStatic
    fun setPerspective(enabled: Boolean) {
        if (FreelookConfig.enabled) {
            if (perspectiveToggled == enabled) return

            cameraYaw = mc.thePlayer.rotationYaw
            cameraPitch = mc.thePlayer.rotationPitch

            if (enabled) {
                previousPerspective = mc.gameSettings.thirdPersonView
                mc.gameSettings.thirdPersonView = FreelookConfig.perspective

                if (FreelookConfig.smoothCamera) timer.start()

            } else {
                mc.gameSettings.thirdPersonView = previousPerspective
                mc.renderGlobal.setDisplayListEntitiesDirty()
                timer.stop()
            }

            perspectiveToggled = enabled
        } else {
            perspectiveToggled = false
        }
    }

    @JvmStatic
    fun overrideMouse(): Boolean {
        if (perspectiveToggled && mc.currentScreen != null) setPerspective(false)

        if (mc.inGameHasFocus) {
            if (!perspectiveToggled) return true

            if (HypixelUtils.INSTANCE.isHypixel || FreelookConfig.freelook) {
                cameraYaw = mc.thePlayer.rotationYaw
                cameraPitch = mc.thePlayer.rotationPitch
                return true
            }

            mc.mouseHelper.mouseXYChange()

            val sensitivity = (mc.gameSettings.mouseSensitivity * 0.6f + 0.2f).pow(3) * 8f * 0.15f
            var yaw = mc.mouseHelper.deltaX * sensitivity
            var pitch = mc.mouseHelper.deltaY * sensitivity

            if (FreelookConfig.invertYaw) yaw = -yaw
            cameraYaw += yaw

            if (FreelookConfig.invertPitch) pitch = -pitch
            cameraPitch += pitch
            if (FreelookConfig.lockPitch) cameraPitch = cameraPitch.coerceIn(-90f, 90f)

            mc.renderGlobal.setDisplayListEntitiesDirty()
        }
        return false
    }
}
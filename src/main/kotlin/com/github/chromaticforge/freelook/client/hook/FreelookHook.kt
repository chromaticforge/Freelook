package com.github.chromaticforge.freelook.client.hook

import com.github.chromaticforge.freelook.client.config.FreelookConfig
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import org.polyfrost.oneconfig.utils.v1.dsl.mc
import kotlin.math.pow

object FreelookHook {
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
            } else {
                mc.gameSettings.thirdPersonView = previousPerspective
                mc.renderGlobal.setDisplayListEntitiesDirty()
            }

            perspectiveToggled = enabled
        } else {
            perspectiveToggled = false
        }
    }

    @JvmStatic
    fun overrideMouse(): Boolean {
        if (mc.inGameHasFocus) {
            if (!perspectiveToggled) return true

            if (HypixelUtils.isHypixel() || FreelookConfig.snaplook) {
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
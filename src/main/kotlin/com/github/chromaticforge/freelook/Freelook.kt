package com.github.chromaticforge.freelook

import com.github.chromaticforge.freelook.config.FreelookConfig
import net.minecraft.client.Minecraft
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import kotlin.math.pow

object Freelook {
    val mc: Minecraft = Minecraft.getMinecraft()

    @JvmField
    var perspectiveToggled = false
    @JvmField
    var cameraYaw = 0f
    @JvmField
    var cameraPitch = 0f

    private var previousPerspective = 0

    @JvmStatic
    fun onPressed() {
        if (FreelookConfig.enabled) {
            cameraYaw = mc.thePlayer.rotationYaw
            cameraPitch = mc.thePlayer.rotationPitch

            togglePerspective()

            mc.renderGlobal.setDisplayListEntitiesDirty()
        } else if (perspectiveToggled) {
            togglePerspective()
        }
    }

    @JvmStatic
    fun togglePerspective() {
        setPerspective(!perspectiveToggled)
    }

    @JvmStatic
    fun setPerspective(enabled: Boolean) {
        if (perspectiveToggled == enabled) return

        if (enabled) {
            previousPerspective = mc.gameSettings.thirdPersonView
            mc.gameSettings.thirdPersonView = FreelookConfig.perspective
        } else {
            mc.gameSettings.thirdPersonView = previousPerspective
            mc.renderGlobal.setDisplayListEntitiesDirty()
        }

        perspectiveToggled = enabled
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

            camera()

            mc.renderGlobal.setDisplayListEntitiesDirty()
        }
        return false
    }

    private fun camera() {
        val sensitivity = (mc.gameSettings.mouseSensitivity * 0.6f + 0.2f).pow(3) * 8f * 0.15f
        var yaw = mc.mouseHelper.deltaX * sensitivity
        var pitch = mc.mouseHelper.deltaY * sensitivity

        if (FreelookConfig.invertYaw) yaw = -yaw
        cameraYaw += yaw

        if (FreelookConfig.invertPitch) pitch = -pitch
        cameraPitch += pitch
        if (FreelookConfig.lockPitch) cameraPitch = cameraPitch.coerceIn(-90f, 90f)
    }
}

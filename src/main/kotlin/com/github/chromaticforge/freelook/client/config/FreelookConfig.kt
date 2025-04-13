package com.github.chromaticforge.freelook.client.config

import com.github.chromaticforge.freelook.FreelookConstants
import com.github.chromaticforge.freelook.client.hook.FreelookHook
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.annotations.Dropdown
import org.polyfrost.oneconfig.api.config.v1.annotations.Info
import org.polyfrost.oneconfig.api.config.v1.annotations.Keybind
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import org.polyfrost.polyui.input.KeyBinder

object FreelookConfig : Config(
    "${ FreelookConstants.ID }.json",
    FreelookConstants.NAME,
    Category.QOL
) {

    // General

    @Info(title = "Warning", description = "Freelook is disabled on Hypixel!")
    var hypixelWarning = false

    @Dropdown(title = "Perspective", options = ["First", "Third", "Reverse"])
    var perspective = 1

    @Switch(title = "Snaplook")
    var snaplook = false

    @Switch(title = "Invert Pitch (Up and Down)")
    var invertPitch = false

    @Switch(title = "Pitch Lock")
    var lockPitch = true

    @Switch(title = "Invert Yaw (Left and Right)")
    var invertYaw = false

    // General

    // Controls

    @Keybind(title = "Freelook", subcategory = "Controls")
    var keyBind = KeyBinder.Bind('f') {
        if (FreelookHook.perspectiveToggled) {
            if (mode == 0 /* HOLD */) {
                FreelookHook.setPerspective(it)
            } else {
                FreelookHook.togglePerspective()
            }
        }
        false
    }

    // TODO: Add "both" option that will toggle if pressed quickly and will hold if held
    @Dropdown(title = "Freelook Mode", options = ["Hold", "Toggle"], subcategory = "Controls")
    var mode = 0

    // Controls

    init {
        hideIf("hypixelWarning") { !HypixelUtils.isHypixel() }
        hideIf("invertPitch") { snaplook || HypixelUtils.isHypixel() }
        hideIf("lockPitch") { snaplook || HypixelUtils.isHypixel() }
        hideIf("invertYaw") { snaplook || HypixelUtils.isHypixel() }
    }
}
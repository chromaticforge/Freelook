package com.github.chromaticforge.freelook.config

import com.github.chromaticforge.freelook.Freelook
import com.github.chromaticforge.freelook.FreelookMod
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.annotations.*
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils
import org.polyfrost.polyui.input.KeyBinder

object FreelookConfig : Config("${FreelookMod.ID}.json", FreelookMod.NAME, Category.QOL) {

    @Info(title = "Warning!", description = "Freelook functionality is disabled on Hypixel.")
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

    @Keybind(title = "Freelook", subcategory = "Controls")
    var keyBind = KeyBinder.Bind('f') {
        if (mode == 0) {
            Freelook.setPerspective(it)
        } else {
            Freelook.togglePerspective()
        }
        false
    }

    @RadioButton(title = "Freelook Mode", options = ["Held", "Toggle"], subcategory = "Controls")
    var mode = 0

    init {
        hideIf("hypixelWarning") { !HypixelUtils.isHypixel() }
        hideIf("invertPitch") { snaplook || HypixelUtils.isHypixel() }
        hideIf("lockPitch") { snaplook || HypixelUtils.isHypixel() }
        hideIf("invertYaw") { snaplook || HypixelUtils.isHypixel() }

        addDependency("invertPitch", "pitch")
        addDependency("lockPitch", "pitch")
        addDependency("invertYaw", "yaw")
    }
}
package com.github.chromaticforge.freelook.config

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.annotations.Dropdown
import cc.polyfrost.oneconfig.config.annotations.Info
import cc.polyfrost.oneconfig.config.annotations.KeyBind
import cc.polyfrost.oneconfig.config.annotations.Switch
import cc.polyfrost.oneconfig.config.core.OneKeyBind
import cc.polyfrost.oneconfig.config.data.InfoType
import cc.polyfrost.oneconfig.config.data.Mod
import cc.polyfrost.oneconfig.config.data.ModType
import cc.polyfrost.oneconfig.libs.universal.UKeyboard
import cc.polyfrost.oneconfig.utils.hypixel.HypixelUtils
import com.github.chromaticforge.freelook.Freelook.onPressed
import com.github.chromaticforge.freelook.FreelookMod

object FreelookConfig : Config(
    Mod(
        FreelookMod.NAME,
        ModType.UTIL_QOL,
        "/freelook_dark.svg"
    ), FreelookMod.MODID + ".json"
) {
    @Info(text = "Freelook functionality is disabled on Hypixel!", type = InfoType.INFO, size = 2)
    var hypixelWarning = false

    @KeyBind(name = "Freelook", subcategory = "Controls")
    var keyBind = OneKeyBind(UKeyboard.KEY_LMENU)

    @Switch(name = "Hold", subcategory = "Controls")
    var hold = false

    @Dropdown(name = "Perspective", options = ["First", "Third", "Reverse"])
    var perspective = 1

    @Switch(name = "Snaplook")
    var snaplook = false

    @Switch(name = "Invert Pitch (Up and Down)")
    var invertPitch = false

    @Switch(name = "Pitch Lock")
    var lockPitch = true

    @Switch(name = "Invert Yaw (Left and Right)")
    var invertYaw = false

    init {
        initialize()

        registerKeyBind(keyBind) { onPressed() }

        hideIf("hypixelWarning") { !HypixelUtils.INSTANCE.isHypixel }
        hideIf("invertPitch") { snaplook || HypixelUtils.INSTANCE.isHypixel }
        hideIf("lockPitch") { snaplook || HypixelUtils.INSTANCE.isHypixel }
        hideIf("invertYaw") { snaplook || HypixelUtils.INSTANCE.isHypixel }

        addDependency("invertPitch", "pitch")
        addDependency("lockPitch", "pitch")
        addDependency("invertYaw", "yaw")
    }
}
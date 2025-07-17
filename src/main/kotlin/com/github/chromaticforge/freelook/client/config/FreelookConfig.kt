package com.github.chromaticforge.freelook.client.config

import com.github.chromaticforge.freelook.FreelookConstants
import com.github.chromaticforge.freelook.client.FreelookController
import dev.deftu.omnicore.client.OmniKeyboard
import dev.deftu.omnicore.client.OmniScreen
import org.polyfrost.oneconfig.api.config.v1.Config
import org.polyfrost.oneconfig.api.config.v1.annotations.Accordion
import org.polyfrost.oneconfig.api.config.v1.annotations.Include
import org.polyfrost.oneconfig.api.config.v1.annotations.Keybind
import org.polyfrost.oneconfig.api.config.v1.annotations.RadioButton
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch
import org.polyfrost.oneconfig.api.config.v1.annotations.Number
import org.polyfrost.oneconfig.api.ui.v1.keybind.KeybindManager
import org.polyfrost.polyui.input.KeyBinder
import org.polyfrost.polyui.input.KeybindHelper

object FreelookConfig : Config(
    "${ FreelookConstants.ID }.json",
    "assets/${ FreelookConstants.ID }/icon.svg",
    FreelookConstants.NAME,
    Category.QOL
) {

    @RadioButton(
        title = "Change Perspective",
        description = "Which perspective should make Freelook/Snaplook start in a different perspective",
        options = ["Never", "First Person Only", "Third Person Only", "Always"]
    )
    var changePerspective: Int = 1

    @RadioButton(
        title = "Starting Perspective",
        description = "Which camera perspective to start if changed",
        options = ["First Person", "Third Person (Back)", "Third Person (Front)"]
    )
    var perspectiveMode: Int = 1

    @Accordion(title = "Activation")
    object Activation {

        @RadioButton(
            title = "Activation Mode",
            description = "Whether you can hold, quick press, or toggle to activate",
            options = ["Hold", "Quick Press", "Toggle"],
            subcategory = "Activation"
        )
        var pressMode: Int = 1

        @Number(
            title = "Hold threshold",
            description = "How long you can hold before Freelook/Snaplook is disabled upon release",
            unit = "ms",
            min = 0f,
            max = Float.MAX_VALUE,
            subcategory = "Activation"
        )
        var holdThreshold: Long = 300
    }

    open class MovementConfig {
        @Include
        var enabled: Boolean = true

        @Switch(title = "Invert")
        var invert: Boolean = false
    }

    @Accordion(title = "Pitch Movement", index = 2)
    object Pitch : MovementConfig() {
        @Switch(title = "Lock")
        var lock: Boolean = true
    }

    @Accordion(title = "Yaw Movement", index = 2)
    object Yaw : MovementConfig() {
        @Switch(title = "Lock")
        var lock: Boolean = false
    }

    @JvmField
    @Switch(title = "Add to Perspective Cycle", description = "Add Freelook/Snaplook as part of the perspective cycle")
    var addToCameraCycle: Boolean = false

    @JvmField
    @RadioButton(
        title = "On Perspective Cycle Change",
        description = "How should Freelook/Snaplook behave when on a perspective cycle change?",
        options = ["Don't change Freelook state", "Stop Freelook", "Block Cycle Change"]
    )
    var onCycleChange: Int = 1

    @Switch(title = "Smooth Camera", description = "Animate third person when Freelook/Snaplook is enabled")
    var smoothCamera: Boolean = false

    @Switch(title = "Snaplook", description = "Use a built in perspective instead. This will be forcefully enabled on Hypixel.")
    var snaplook: Boolean = false

    @Keybind(title = "Freelook Keybind")
    var keybind: KeyBinder.Bind = KeybindHelper.builder()
        .keys(OmniKeyboard.KEY_F)
        .does{ pressed ->
            if (OmniScreen.isInScreen) {
                return@does
            }
            when (Activation.pressMode) {
                1 -> FreelookController.handlePressAndHold(pressed)
                2 -> if (pressed) FreelookController.toggle()
                else -> {
                    if (pressed && !FreelookController.perspectiveToggled) {
                        FreelookController.start()
                    } else if (!pressed && FreelookController.perspectiveToggled) {
                        FreelookController.stop()
                    }
                }
            }
        }
        .build()

    init {
        initialize(true)
        KeybindManager.registerKeybind(keybind)
    }
}

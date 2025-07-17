package com.github.chromaticforge.freelook.client

import com.github.chromaticforge.freelook.client.config.FreelookConfig

object CameraCycleHandler {
    var hasCycledFreelook = false

    @JvmStatic
    fun shouldOverrideCameraCycle(): Boolean {
        when (FreelookConfig.onCycleChange) {
            1 -> FreelookController.perspectiveToggled = false
            2 -> if (FreelookController.perspectiveToggled && !hasCycledFreelook) {
                return false
            }
        }

        if (FreelookConfig.addToCameraCycle) {
            if (hasCycledFreelook) {
                hasCycledFreelook = false
                FreelookController.stop()
            } else if (
                PerspectiveManager.getCurrentPerspective() ==
                PerspectiveManager.getMaximumPerspectiveIndex()
            ) {
                FreelookController.start()
                hasCycledFreelook = true
                return false
            }
        }

        return true
    }
}

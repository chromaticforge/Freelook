package com.github.chromaticforge.freelook.command

import cc.polyfrost.oneconfig.utils.commands.annotations.Command
import cc.polyfrost.oneconfig.utils.commands.annotations.Main
import com.github.chromaticforge.freelook.Constants
import com.github.chromaticforge.freelook.config.FreelookConfig

@Command(
    value = Constants.ID,
    description = "Access the ${Constants.NAME} GUI.",
    aliases = ["perspective", "snaplook"]
)
object FreelookCommand {
    @Main
    private fun main() {
        FreelookConfig.openGui()
    }
}
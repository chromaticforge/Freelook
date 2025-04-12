package com.github.chromaticforge.freelook.command

import com.github.chromaticforge.freelook.FreelookMod
import org.polyfrost.oneconfig.api.commands.v1.factories.annotated.Command

@Command(value = [FreelookMod.ID, "perspective"])
object FreelookCommand {
    @Command
    private fun main() {
        // FreelookConfig.openGui()
    }
}
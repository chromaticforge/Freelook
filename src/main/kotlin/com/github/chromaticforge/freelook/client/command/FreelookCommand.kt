package com.github.chromaticforge.freelook.client.command

import com.github.chromaticforge.freelook.FreelookConstants
import com.github.chromaticforge.freelook.client.config.FreelookConfig
import org.polyfrost.oneconfig.api.commands.v1.factories.annotated.Command
import org.polyfrost.oneconfig.api.commands.v1.factories.annotated.Handler
import org.polyfrost.oneconfig.utils.v1.dsl.openUI

@Command(value = [FreelookConstants.ID, "perspective", "snaplook"])
object FreelookCommand {
    @Handler
    private fun main() {
        FreelookConfig.openUI()
    }
}
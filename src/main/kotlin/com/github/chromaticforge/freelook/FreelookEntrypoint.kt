package com.github.chromaticforge.freelook

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

import com.github.chromaticforge.freelook.client.command.FreelookCommand
import com.github.chromaticforge.freelook.client.config.FreelookConfig
import com.github.chromaticforge.freelook.client.hook.FreelookEvents
import org.polyfrost.oneconfig.api.commands.v1.CommandManager
import org.polyfrost.oneconfig.api.event.v1.EventManager

@Mod(
    modid = FreelookConstants.ID,
    name = FreelookConstants.NAME,
    version = FreelookConstants.VERSION,
    modLanguageAdapter = "org.polyfrost.oneconfig.utils.v1.forge.KotlinLanguageAdapter"
)
object FreelookEntrypoint {
    private fun initialize() {
        FreelookConfig
        CommandManager.register(FreelookCommand)
        EventManager.INSTANCE.register(FreelookEvents)
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        initialize()
    }
}
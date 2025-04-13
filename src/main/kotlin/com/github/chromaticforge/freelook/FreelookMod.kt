package com.github.chromaticforge.freelook

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.utils.commands.CommandManager
import com.github.chromaticforge.freelook.command.FreelookCommand
import com.github.chromaticforge.freelook.config.FreelookConfig
import com.github.chromaticforge.freelook.hook.FreelookEvents

@Mod(
    modid = Constants.ID,
    name = Constants.NAME,
    version = Constants.VERSION,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter"
)
object FreelookMod {
    private fun initialize() {
        FreelookConfig
        CommandManager.INSTANCE.registerCommand(FreelookCommand)
        EventManager.INSTANCE.register(FreelookEvents)
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        initialize()
    }
}
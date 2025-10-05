package com.github.chromaticforge.freelook

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.utils.commands.CommandManager
import com.github.chromaticforge.freelook.command.FreelookCommand
import com.github.chromaticforge.freelook.config.FreelookConfig
import com.github.chromaticforge.freelook.hook.FreelookEvents

@Mod(
    modid = FreelookMod.ID,
    name = FreelookMod.NAME,
    version = FreelookMod.VERSION,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter"
)
object FreelookMod {
    const val ID = "@MOD_ID@"
    const val NAME = "@MOD_NAME@"
    const val VERSION = "@MOD_VERSION@"

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        FreelookConfig
        CommandManager.INSTANCE.registerCommand(FreelookCommand)
        EventManager.INSTANCE.register(FreelookEvents)
    }
}
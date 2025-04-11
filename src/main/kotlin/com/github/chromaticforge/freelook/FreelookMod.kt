package com.github.chromaticforge.freelook

import cc.polyfrost.oneconfig.utils.commands.CommandManager
import com.github.chromaticforge.freelook.Freelook.perspectiveToggled
import com.github.chromaticforge.freelook.Freelook.togglePerspective
import com.github.chromaticforge.freelook.command.FreelookCommand
import com.github.chromaticforge.freelook.config.FreelookConfig
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

@Mod(
    modid = FreelookMod.MODID,
    name = FreelookMod.NAME,
    version = FreelookMod.VERSION,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter"
)
object FreelookMod {
    const val MODID: String = "@ID@"
    const val NAME: String = "@NAME@"
    const val VERSION: String = "@VER@"

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent?) {
        FreelookConfig
        CommandManager.INSTANCE.registerCommand(FreelookCommand)
        MinecraftForge.EVENT_BUS.register(this)
    }

    @SubscribeEvent
    fun onWorldLoad(event: WorldEvent.Load) {
        if (Freelook.mc.thePlayer != null && Freelook.mc.theWorld != null) {
            if (perspectiveToggled) {
                togglePerspective()
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    fun onTick(event: TickEvent.RenderTickEvent) {
        if (FreelookConfig.hold && perspectiveToggled && !FreelookConfig.keyBind.isActive) {
            togglePerspective()
        }
    }

    @SubscribeEvent
    fun onPerspectiveChange(event: TickEvent) {

    }
}
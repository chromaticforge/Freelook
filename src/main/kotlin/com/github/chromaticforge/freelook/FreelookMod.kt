package com.github.chromaticforge.freelook

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.events.event.RenderEvent
import cc.polyfrost.oneconfig.events.event.TickEvent
import cc.polyfrost.oneconfig.events.event.WorldLoadEvent

import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.libs.universal.UMinecraft

import cc.polyfrost.oneconfig.utils.commands.CommandManager

import com.github.chromaticforge.freelook.hook.FreelookHook.perspectiveToggled
import com.github.chromaticforge.freelook.hook.FreelookHook.togglePerspective
import com.github.chromaticforge.freelook.command.FreelookCommand
import com.github.chromaticforge.freelook.config.FreelookConfig
import com.github.chromaticforge.freelook.hook.FreelookHook.setPerspective

@Mod(
    modid = FreelookMod.ID,
    name = FreelookMod.NAME,
    version = FreelookMod.VERSION,
    modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter"
)
object FreelookMod {
    const val ID: String = "@ID@"
    const val NAME: String = "@NAME@"
    const val VERSION: String = "@VER@"

    val mc = UMinecraft.getMinecraft()

    private fun initialize() {
        FreelookConfig
        CommandManager.INSTANCE.registerCommand(FreelookCommand)
        EventManager.INSTANCE.register(this)
    }

    private var lastPressed = false

    @Subscribe
    fun onTick(event: TickEvent) {
        val active = FreelookConfig.keyBind.isActive
        if (active != lastPressed && FreelookConfig.mode == 0 && FreelookConfig.enabled) {
            lastPressed = active
            setPerspective(active)
        }
    }

    @Subscribe
    fun onWorldLoad(event: WorldLoadEvent) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            if (perspectiveToggled) {
                togglePerspective()
            }
        }
    }

    @Subscribe
    fun onRender(event: RenderEvent) {
        if (FreelookConfig.mode == 0 && perspectiveToggled && !FreelookConfig.keyBind.isActive) {
            togglePerspective()
        }

        if (!FreelookConfig.enabled) {
            togglePerspective()
        }
    }

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        initialize()
    }
}
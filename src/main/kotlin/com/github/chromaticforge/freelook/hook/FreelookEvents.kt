package com.github.chromaticforge.freelook.hook

import cc.polyfrost.oneconfig.events.event.RenderEvent
import cc.polyfrost.oneconfig.events.event.TickEvent
import cc.polyfrost.oneconfig.events.event.WorldLoadEvent
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.utils.dsl.mc
import com.github.chromaticforge.freelook.config.FreelookConfig
import com.github.chromaticforge.freelook.hook.FreelookHook.perspectiveToggled
import com.github.chromaticforge.freelook.hook.FreelookHook.setPerspective
import com.github.chromaticforge.freelook.hook.FreelookHook.togglePerspective

object FreelookEvents {
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
}
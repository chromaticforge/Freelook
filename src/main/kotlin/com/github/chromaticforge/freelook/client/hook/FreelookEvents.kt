package com.github.chromaticforge.freelook.client.hook

import com.github.chromaticforge.freelook.client.config.FreelookConfig
import com.github.chromaticforge.freelook.client.hook.FreelookHook.perspectiveToggled
import com.github.chromaticforge.freelook.client.hook.FreelookHook.togglePerspective
import org.polyfrost.oneconfig.api.event.v1.events.RenderEvent
import org.polyfrost.oneconfig.api.event.v1.events.WorldEvent
import org.polyfrost.oneconfig.api.event.v1.invoke.impl.Subscribe
import org.polyfrost.oneconfig.utils.v1.dsl.mc

object FreelookEvents {
    @Subscribe
    fun onWorldLoad(event: WorldEvent.Load) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            if (perspectiveToggled) {
                togglePerspective()
            }
        }
    }

    @Subscribe
    fun onRender(event: RenderEvent) {
        if (!FreelookConfig.enabled) {
            togglePerspective()
        }
    }
}
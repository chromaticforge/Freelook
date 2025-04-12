package com.github.chromaticforge.freelook

//#if FORGE
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
//#else
//$$ import net.fabricmc.api.ClientModInitializer;
//#endif

import com.github.chromaticforge.freelook.command.FreelookCommand
import com.github.chromaticforge.freelook.config.FreelookConfig
import org.polyfrost.oneconfig.api.commands.v1.CommandManager
import org.polyfrost.oneconfig.api.event.v1.EventManager
import org.polyfrost.oneconfig.api.event.v1.events.WorldEvent
import org.polyfrost.oneconfig.api.event.v1.invoke.impl.Subscribe

//#if FORGE
@Mod(
    modid = FreelookMod.ID,
    name = FreelookMod.NAME,
    version = FreelookMod.VERSION,
    modLanguageAdapter = "org.polyfrost.oneconfig.utils.v1.forge.KotlinLanguageAdapter"
)
//#endif
object FreelookMod
    //#if FABRIC
    //$$ : ClientModInitializer
    //#endif
{
    const val ID: String = "@MOD_ID@"
    const val NAME: String = "@MOD_NAME@"
    const val VERSION: String = "@MOD_VERSION@"

    fun initialize() {
        FreelookConfig.preload()
        CommandManager.register(FreelookCommand)
        EventManager.INSTANCE.register(this)
    }

    //#if FORGE
    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        initialize()
    }
    //#else
    //$$ override fun onInitializeClient() {
    //$$     initialize()
    //$$ }
    //#endif

    @Subscribe
    fun onWorldLoad(event: WorldEvent) {
        if (Freelook.mc.thePlayer != null && Freelook.mc.theWorld != null) {
            Freelook.setPerspective(false)
        }
    }

//    @Subscribe
//    fun onTick(event: RenderEvent) {
//        if (FreelookConfig.mode == 0 && perspectiveToggled && !FreelookConfig.keyBind) {
//            togglePerspective()
//        }
//    }
}
package com.github.chromaticforge.freelook

import com.github.chromaticforge.freelook.client.command.FreelookCommand
import org.polyfrost.oneconfig.api.commands.v1.CommandManager

//#if FABRIC
import net.fabricmc.api.ClientModInitializer
//#endif

//#if FORGE
//$$ import net.minecraftforge.fml.common.Mod
//$$ import net.minecraftforge.fml.common.event.FMLInitializationEvent
//#endif

const val ID: String = "@MOD_ID@"
const val NAME: String = "@MOD_NAME@"
const val VERSION: String = "@MOD_VERSION@"

//#if FORGE
//$$ @Mod(
//$$    modid = ID, name = NAME, version = VERSION,
//$$    modLanguageAdapter = "org.polyfrost.oneconfig.utils.KotlinLanguageAdapter"
//$$ )
//#endif
class FreelookMod
//#if FABRIC
    : ClientModInitializer
//#endif
{
    fun initialize() {
        CommandManager.register(FreelookCommand)
    }

    //#if FABRIC
    override fun onInitializeClient() {
        initialize()
    }
    //#endif

    //#if FORGE
    //$$ @Mod.EventHandler
    //$$ fun onFMLInitialization(event: FMLInitializationEvent) {
    //$$    initialize()
    //$$ }
    //#endif
}
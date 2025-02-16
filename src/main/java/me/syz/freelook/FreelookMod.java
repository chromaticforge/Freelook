package me.syz.freelook;

import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import me.syz.freelook.command.FreelookCommand;
import me.syz.freelook.config.FreelookConfig;
import me.syz.freelook.hooks.FreelookHook;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = FreelookMod.MODID, name = FreelookMod.NAME, version = FreelookMod.VERSION)
public class FreelookMod {
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";

    public static FreelookConfig config;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        config = new FreelookConfig();
        CommandManager.INSTANCE.registerCommand(new FreelookCommand());

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (FreelookHook.mc.thePlayer != null && FreelookHook.mc.theWorld != null) {
            if (event.gui == null) return;
            if (FreelookHook.perspectiveToggled && FreelookConfig.hold) {
                FreelookHook.resetPerspective();
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent event) {
        if (FreelookHook.mc.thePlayer != null && FreelookHook.mc.theWorld != null) {
            if (event.phase.equals(TickEvent.Phase.START)) return;

            boolean down = FreelookConfig.keyBind.isActive();
            if (down != FreelookHook.prevState && FreelookHook.mc.currentScreen == null) {
                FreelookHook.onPressed(down);
                FreelookHook.prevState = down;
            }
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (FreelookHook.mc.thePlayer != null && FreelookHook.mc.theWorld != null) {
            if (FreelookHook.perspectiveToggled) {
                FreelookHook.resetPerspective();
            }
        }
    }
}

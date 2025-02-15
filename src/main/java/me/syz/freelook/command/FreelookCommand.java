package me.syz.freelook.command;

import cc.polyfrost.oneconfig.utils.commands.annotations.Command;
import cc.polyfrost.oneconfig.utils.commands.annotations.Main;
import me.syz.freelook.FreelookMod;

@Command(value = FreelookMod.MODID, description = "Access the " + FreelookMod.NAME + " GUI.")
public class FreelookCommand {
    @Main
    private void handle() {
        FreelookMod.config.openGui();
    }
}

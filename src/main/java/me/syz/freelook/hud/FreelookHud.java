package me.syz.freelook.hud;

import cc.polyfrost.oneconfig.hud.SingleTextHud;
import me.syz.freelook.config.FreelookConfig;
import me.syz.freelook.hooks.FreelookHook;

public class FreelookHud extends SingleTextHud {
    public FreelookHud() {
        super("Freelook", false);
    }

    @Override
    protected String getText(boolean example) {
        return FreelookHook.perspectiveToggled? FreelookConfig.activeText : FreelookConfig.inactiveText;
    }
}

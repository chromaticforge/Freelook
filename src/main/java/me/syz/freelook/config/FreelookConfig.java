package me.syz.freelook.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.KeyBind;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.libs.universal.UKeyboard;
import me.syz.freelook.FreelookMod;

public class FreelookConfig extends Config {
    @KeyBind(name = "Key Bind", subcategory = "Key Bind")
    public static OneKeyBind keyBind = new OneKeyBind(UKeyboard.KEY_LMENU);

    @Switch(name = "Hold", subcategory = "Key Bind")
    public static boolean hold = true;

    @Dropdown(name = "Perspective", options = {"First", "Third", "Reverse"}, subcategory = "Perspective")
    public static int perspective = 1;

    @Switch(name = "Smooth", subcategory = "Perspective")
    public static boolean smooth = false;

    @Slider(name = "FOV", min = 30F, max = 110F, subcategory = "Perspective")
    public static float fov = 70f;

    @Switch(name = "Pitch", subcategory = "Camera")
    public static boolean pitch = true;

    @Switch(name = "Invert Pitch", subcategory = "Camera")
    public static boolean invertPitch = true;

    @Switch(name = "Lock Pitch", subcategory = "Camera")
    public static boolean lockPitch = true;

    @Switch(name = "Yaw", subcategory = "Camera")
    public static boolean yaw = true;

    @Switch(name = "Invert Yaw", subcategory = "Camera")
    public static boolean invertYaw = true;

    public FreelookConfig() {
        super(new Mod(FreelookMod.NAME, ModType.UTIL_QOL, "/freelook.svg"), FreelookMod.MODID + ".json");

        initialize();

        addDependency("invertPitch", "pitch");
        addDependency("lockPitch", "pitch");
        addDependency("invertYaw", "yaw");
    }
}

package me.syz.freelook.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cc.polyfrost.oneconfig.libs.universal.UKeyboard;
import me.syz.freelook.FreelookMod;
import me.syz.freelook.hud.FreelookHud;

public class FreelookConfig extends Config {
    // Controls

    // TODO: "Advanced binds"
    // Separate controls for each perspective & hold/toggle
    @KeyBind(name = "Freelook", subcategory = "Controls")
    public static OneKeyBind keyBind = new OneKeyBind(UKeyboard.KEY_LMENU);

    @Switch(name = "Hold", subcategory = "Controls")
    public static boolean hold = true;

    // Perspective

    @Dropdown(name = "Perspective", options = {"First", "Third", "Reverse"}, subcategory = "Perspective")
    public static int perspective = 1;

    @Checkbox(name = "Custom FOV", subcategory = "Perspective")
    public static boolean useFov = false;

    @Slider(name = "FOV", min = 30F, max = 110F, subcategory = "Perspective")
    public static float fov = 70f;

    // Camera

    @Switch(name = "Pitch", subcategory = "Camera")
    public static boolean pitch = true;

    @Switch(name = "Invert Pitch", subcategory = "Camera")
    public static boolean invertPitch = true;

    @Switch(name = "Pitch Lock", subcategory = "Camera")
    public static boolean pitchLock = true;

    @Switch(name = "Yaw", subcategory = "Camera")
    public static boolean yaw = true;

    @Switch(name = "Invert Yaw", subcategory = "Camera")
    public static boolean invertYaw = true;

    // TODO: Implement
//    @Switch(name = "Cinematic", description = "Unimplemented", subcategory = "Camera")
//    public static boolean smooth = false;

    // HUD

    @Text(name = "Active Text", category = "Display")
    public static String activeText = "Freelook";

    @Text(name = "Inactive Text", category = "Display")
    public static String inactiveText = "";

    @HUD(name = "Display", category = "Display")
    public static FreelookHud hud = new FreelookHud();

    public FreelookConfig() {
        super(new Mod(FreelookMod.NAME, ModType.UTIL_QOL, "/freelook.svg"), FreelookMod.MODID + ".json");

        initialize();

        addDependency("invertPitch", "pitch");
        addDependency("pitchLock", "pitch");
        addDependency("invertYaw", "yaw");

        addDependency("fov", "useFov");
    }
}

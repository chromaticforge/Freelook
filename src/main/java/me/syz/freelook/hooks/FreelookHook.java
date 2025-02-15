package me.syz.freelook.hooks;

import me.syz.freelook.FreelookMod;
import me.syz.freelook.config.FreelookConfig;
import net.minecraft.client.Minecraft;

public class FreelookHook {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean perspectiveToggled = false;
    public static boolean prevState = false;
    public static float cameraYaw = 0.0f;
    public static float cameraPitch = 0.0f;

    private static int previousPerspective = 0;

    public static void onPressed(boolean down) {
        if (FreelookMod.config.enabled) {
            if (down) {
                cameraYaw = mc.thePlayer.rotationYaw;
                cameraPitch = mc.thePlayer.rotationPitch;

                if (perspectiveToggled) {
                    resetPerspective();
                } else {
                    enterPerspective();
                }

                mc.renderGlobal.setDisplayListEntitiesDirty();
            } else if (FreelookConfig.hold) {
                resetPerspective();
            }
        } else if (perspectiveToggled) {
            resetPerspective();
        }
    }

    public static void enterPerspective() {
        perspectiveToggled = true;

        previousPerspective = mc.gameSettings.thirdPersonView;

        mc.gameSettings.thirdPersonView = FreelookConfig.perspective;
    }

    public static void resetPerspective() {
        perspectiveToggled = false;

        mc.gameSettings.thirdPersonView = previousPerspective;

        mc.renderGlobal.setDisplayListEntitiesDirty();
    }

    public static boolean overrideMouse() {
        if (mc.inGameHasFocus) {
            if (!perspectiveToggled) return true;

            mc.mouseHelper.mouseXYChange();

            if (FreelookConfig.yaw) handleYaw();
            if (FreelookConfig.pitch) handlePitch();

            mc.renderGlobal.setDisplayListEntitiesDirty();
        }
        return false;
    }

    public static void handleYaw() {
        float sensitivity = calculateSensitivity();
        float yaw = mc.mouseHelper.deltaX * sensitivity;

        if (FreelookConfig.invertYaw) yaw = -yaw;

        cameraYaw += yaw * 0.15f;
    }

    public static void handlePitch() {
        float sensitivity = calculateSensitivity();
        float pitch = mc.mouseHelper.deltaY * sensitivity;

        if (FreelookConfig.invertPitch) pitch = -pitch;

        cameraPitch += pitch * 0.15f;

        if (FreelookConfig.lockPitch) cameraPitch = Math.max(-90.0f, Math.min(cameraPitch, 90.0f));
    }

    public static float calculateSensitivity() {
        float sensitivity = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        return sensitivity * sensitivity * sensitivity * 8.0f;
    }
}

package com.github.chromaticforge.freelook.mixins;

import com.github.chromaticforge.freelook.client.hook.FreelookHook;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @ModifyExpressionValue(
            method = "orientCamera",
            at = {
                    @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationYaw:F"),
                    @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationYaw:F")
            }
    )
    private float modifyYaw(float original) {
        return FreelookHook.perspectiveToggled ? FreelookHook.cameraYaw : original;
    }

    @ModifyExpressionValue(
            method = "orientCamera",
            at = {
                    @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationPitch:F"),
                    @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationPitch:F")
            }
    )
    private float modifyPitch(float original) {
        return FreelookHook.perspectiveToggled ? FreelookHook.cameraPitch : original;
    }

    @Redirect(
            method = "updateCameraAndRender",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;inGameHasFocus:Z")
    )
    private boolean overrideMouse(Minecraft instance) {
        return FreelookHook.overrideMouse();
    }
}
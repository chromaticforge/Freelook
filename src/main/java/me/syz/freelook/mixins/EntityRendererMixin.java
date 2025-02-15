package me.syz.freelook.mixins;

import me.syz.freelook.hooks.FreelookHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationYaw:F", opcode = Opcodes.GETFIELD))
    private float rotationYawModifier(Entity entity) {
        return FreelookHook.perspectiveToggled ? FreelookHook.cameraYaw : entity.rotationYaw;
    }

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationYaw:F", opcode = Opcodes.GETFIELD))
    private float prevRotationYawModifier(Entity entity) {
        return FreelookHook.perspectiveToggled ? FreelookHook.cameraYaw : entity.prevRotationYaw;
    }

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationPitch:F", opcode = Opcodes.GETFIELD))
    private float rotationPitchModifier(Entity entity) {
        return FreelookHook.perspectiveToggled ? FreelookHook.cameraPitch : entity.rotationPitch;
    }

    @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationPitch:F", opcode = Opcodes.GETFIELD))
    private float prevRotationPitchModifier(Entity entity) {
        return FreelookHook.perspectiveToggled ? FreelookHook.cameraPitch : entity.prevRotationPitch;
    }

    @Redirect(method = "updateCameraAndRender", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;inGameHasFocus:Z", opcode = Opcodes.IFEQ))
    private boolean overrideMouse(Minecraft instance) {
        return FreelookHook.overrideMouse();
    }
}

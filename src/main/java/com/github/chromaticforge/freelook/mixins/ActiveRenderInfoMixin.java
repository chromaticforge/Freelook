package com.github.chromaticforge.freelook.mixins;

import com.github.chromaticforge.freelook.hook.FreelookHook;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.player.EntityPlayer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ActiveRenderInfo.class)
public class ActiveRenderInfoMixin {
    @Redirect(method = "updateRenderInfo", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;rotationPitch:F", opcode = Opcodes.GETFIELD))
    private static float modifyPitch(EntityPlayer player) {
        return FreelookHook.perspectiveToggled ? FreelookHook.cameraPitch : player.rotationPitch;
    }

    @Redirect(method = "updateRenderInfo", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;rotationYaw:F", opcode = Opcodes.GETFIELD))
    private static float modifyYaw(EntityPlayer player) {
        return FreelookHook.perspectiveToggled ? FreelookHook.cameraYaw : player.rotationYaw;
    }
}
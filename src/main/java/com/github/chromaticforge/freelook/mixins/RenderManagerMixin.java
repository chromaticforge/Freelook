package com.github.chromaticforge.freelook.mixins;

import com.github.chromaticforge.freelook.Freelook;
import net.minecraft.client.renderer.entity.RenderManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderManager.class)
public class RenderManagerMixin {
    @Redirect(method = "cacheActiveRenderInfo", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;playerViewX:F", opcode = Opcodes.PUTFIELD))
    private void playerViewXModifier(RenderManager instance, float value) {
        instance.playerViewX = Freelook.perspectiveToggled ? Freelook.cameraPitch : value;
    }

    @Redirect(method = "cacheActiveRenderInfo", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;playerViewY:F", opcode = Opcodes.PUTFIELD))
    private void playerViewYModifier(RenderManager instance, float value) {
        instance.playerViewY = Freelook.perspectiveToggled ? Freelook.cameraYaw : value;
    }
}

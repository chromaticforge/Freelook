package com.github.chromaticforge.freelook.client.mixin;

import org.spongepowered.asm.mixin.Mixin;

//#if MC <= 1.12.2
//$$ import net.minecraft.client.renderer.EntityRenderer;
//$$ import net.minecraft.entity.Entity;
//$$ import org.objectweb.asm.Opcodes;
//$$ import com.github.chromaticforge.freelook.client.CameraStateTracker;
//$$ import com.github.chromaticforge.freelook.client.FreelookController;
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.ModifyArg;
//$$ import org.spongepowered.asm.mixin.injection.Redirect;
//$$
//$$ @Mixin(EntityRenderer.class)
//$$ public class Mixin_EntityRenderer {
//$$
//$$
//$$     @ModifyArg(
//$$             method = "orientCamera",
//$$             at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", ordinal = 2),
//$$             index = 2 // Z is the third argument (0-based index)
//$$     )
//$$     private float modifyZArg(float originalZ) {
//$$         return FreelookController.INSTANCE.applySmoothScale(originalZ);
//$$     }
//$$
//$$     @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationYaw:F", opcode = Opcodes.GETFIELD))
//$$     private float rotationYawModifier(Entity entity) {
//$$         return FreelookController.perspectiveToggled ? CameraStateTracker.INSTANCE.getCameraYaw(entity) : entity.rotationYaw;
//$$     }
//$$
//$$     @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationYaw:F", opcode = Opcodes.GETFIELD))
//$$     private float prevRotationYawModifier(Entity entity) {
//$$         return FreelookController.perspectiveToggled ? CameraStateTracker.INSTANCE.getCameraYaw(entity) : entity.prevRotationYaw;
//$$     }
//$$
//$$     @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationPitch:F", opcode = Opcodes.GETFIELD))
//$$     private float rotationPitchModifier(Entity entity) {
//$$         return FreelookController.perspectiveToggled ? CameraStateTracker.INSTANCE.getCameraPitch(entity) : entity.rotationPitch;
//$$     }
//$$
//$$     @Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;prevRotationPitch:F", opcode = Opcodes.GETFIELD))
//$$     private float prevRotationPitchModifier(Entity entity) {
//$$         return FreelookController.perspectiveToggled ? CameraStateTracker.INSTANCE.getCameraPitch(entity) : entity.prevRotationPitch;
//$$     }
//$$
//$$ }
//#else
import net.minecraft.client.render.entity.EntityRenderer;
@Mixin(EntityRenderer.class)
public class Mixin_EntityRenderer {
}
//#endif

package com.github.chromaticforge.freelook.client.mixin;

import com.github.chromaticforge.freelook.client.CameraStateTracker;
import com.github.chromaticforge.freelook.client.FreelookController;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.deftu.omnicore.client.OmniClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
//#if MC <= 1.12.2
//$$ import net.minecraft.entity.player.EntityPlayer;
//$$ import org.objectweb.asm.Opcodes;
//$$ import org.spongepowered.asm.mixin.injection.Redirect;
//#endif

@Mixin(Camera.class)
public abstract class Mixin_Camera_Rotation {

    //#if MC >= 1.16.5
    @ModifyArgs(
            method = "update",
            at = @At(
                    value  = "INVOKE",
                    target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V",
                    //#if MC >= 1.21.2
                    ordinal = 1
                    //#else
                    //$$ ordinal = 0
                    //#endif
            )
    )
    private void modifyRotationArgs(Args args) {
        ClientPlayerEntity focused = OmniClient.getInstance().player;

        if (FreelookController.perspectiveToggled && focused instanceof ClientPlayerEntity) {
            CameraStateTracker tracker = CameraStateTracker.INSTANCE;

            args.set(0, tracker.getCameraYaw(focused));
            args.set(1, tracker.getCameraPitch(focused));
        }
    }

    @ModifyReturnValue(method = "clipToSpace", at = @At("RETURN"))
    //#if MC >=1.21
    private float adjustClipReturn(float original) {
    //#else
    //$$ private double adjustClipReturn(double original) {
    //#endif
        return FreelookController.INSTANCE.applySmoothScale((float) original);
    }
    //#elseif MC <= 1.12.2
    //$$ @Redirect(
    //$$         method = "updateRenderInfo(Lnet/minecraft/entity/player/EntityPlayer;Z)V",
    //$$         at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;rotationPitch:F", opcode = Opcodes.GETFIELD, ordinal = 0)
    //$$ )
    //$$ private static float modifyPitch(EntityPlayer player) {
    //$$     return FreelookController.perspectiveToggled ? CameraStateTracker.INSTANCE.getCameraPitch(player) : player.rotationPitch;
    //$$ }
    //$$
    //$$ @Redirect(
    //$$         method = "updateRenderInfo(Lnet/minecraft/entity/player/EntityPlayer;Z)V",
    //$$         at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayer;rotationYaw:F", opcode = Opcodes.GETFIELD, ordinal = 0)
    //$$ )
    //$$ private static float modifyYaw(EntityPlayer player) {
    //$$     return FreelookController.perspectiveToggled ? CameraStateTracker.INSTANCE.getCameraYaw(player) : player.rotationYaw;
    //$$ }
    //#endif
}

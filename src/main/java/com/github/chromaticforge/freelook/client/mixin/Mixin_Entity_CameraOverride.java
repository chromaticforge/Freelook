package com.github.chromaticforge.freelook.client.mixin;

import com.github.chromaticforge.freelook.client.CameraStateTracker;
import com.github.chromaticforge.freelook.client.FreelookController;
import com.github.chromaticforge.freelook.client.config.FreelookConfig;
import dev.deftu.omnicore.client.OmniClientPlayer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.polyfrost.oneconfig.api.hypixel.v1.HypixelUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC <= 1.12.2
//$$ import dev.deftu.omnicore.client.OmniClient;
//#endif

@Mixin(Entity.class)
public class Mixin_Entity_CameraOverride {

    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    public void changeCameraLookDirection(
            //#if MC >= 1.16.5
            double yaw, double pitch,
            //#elseif MC <= 1.12.2
            //$$ float yaw, float pitch,
            //#endif
            CallbackInfo ci) {
        if (FreelookController.perspectiveToggled && (Object) this instanceof ClientPlayerEntity) {
            ClientPlayerEntity entity = (ClientPlayerEntity)(Object) this;

            boolean useSnaplook = FreelookConfig.INSTANCE.getSnaplook() || HypixelUtils.isHypixel();
            float pitchDelta = (float) (pitch * 0.15);
            float yawDelta = (float) (yaw * 0.15);

            if (FreelookConfig.Pitch.INSTANCE.getEnabled()) {
                float cameraPitch = FreelookController.INSTANCE.updateCameraValue(CameraStateTracker.INSTANCE.getCameraPitch(entity),
                        // Normal pitch is inverted on <=1.12.2. We must also reflect it here
                        //#if MC <= 1.12.2
                        //$$ -pitchDelta,
                        //#else
                        pitchDelta,
                        //#endif
                        FreelookConfig.Pitch.INSTANCE.getInvert(), FreelookConfig.Pitch.INSTANCE.getLock(), -90.0f, 90.0f);
                CameraStateTracker.INSTANCE.setCameraPitch(entity, useSnaplook ? OmniClientPlayer.getPitch() : cameraPitch);
            }

            if (FreelookConfig.Yaw.INSTANCE.getEnabled()) {
                float cameraYaw = FreelookController.INSTANCE.updateCameraValue(CameraStateTracker.INSTANCE.getCameraYaw(entity), yawDelta,
                        FreelookConfig.Yaw.INSTANCE.getInvert(), FreelookConfig.Yaw.INSTANCE.getLock(), -90.0f, 90.0f);
                CameraStateTracker.INSTANCE.setCameraYaw(entity, useSnaplook ? OmniClientPlayer.getYaw() : cameraYaw);
            }
            //#if MC <= 1.12.2
            //$$ OmniClient.getInstance().renderGlobal.setDisplayListEntitiesDirty();
            //#endif

            if (!useSnaplook) {
                ci.cancel();
            }
        }
    }

}

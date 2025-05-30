package com.github.chromaticforge.freelook.mixins;

import com.github.chromaticforge.freelook.hook.FreelookHook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Redirect(method = "runTick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;thirdPersonView:I", opcode = Opcodes.PUTFIELD))
    private void modifyThirdPerson(GameSettings settings, int value) {
        if (FreelookHook.perspectiveToggled) {
            FreelookHook.togglePerspective();
        } else {
            settings.thirdPersonView = value;
        }
    }

}
package com.yakasov.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayerEntity.class)
public class IsBedWithinRange {
    @Redirect(
            method = "isBedWithinRange*",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;isBedWithinRange(Lnet/minecraft/util/math/BlockPos;)Z"
            )
    )
    private boolean returnTrueForBedWithinRange(ServerPlayerEntity instance, BlockPos pos) {
        return true;
    }
}

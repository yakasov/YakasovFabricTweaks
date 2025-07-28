package com.yakasov.mixin;

import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {
    @ModifyVariable(
            method = "applyPlayerEffects",
            at = @At("STORE"),
            name = "d"
    )
    private static double increaseBeaconRange(double originalRange, World world, BlockPos pos, int beaconLevel) {
        // Before: 20, 30, 40, 50
        // After: 24, 48, 72, 96
        return beaconLevel * 24;
    }

    @ModifyVariable(
            method = "applyPlayerEffects",
            at = @At("STORE"),
            name = "j"
    )
    private static int increaseEffectTime(int originalTime, World world, BlockPos pos, int beaconLevel) {
        // Before: 11s, 13s, 15s, 17s
        // After: 15s, 30s, 45s, 60s
        return beaconLevel * 15 * 20;
    }
}

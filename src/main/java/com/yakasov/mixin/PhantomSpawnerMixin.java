package com.yakasov.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {
    @Inject(
            method = "spawn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/BlockPos;up(I)Lnet/minecraft/util/math/BlockPos;"
            )
    )
    private void returnIfPlayerInMushroomBiome(
            ServerWorld world, boolean spawnMonsters, boolean spawnAnimals,
            CallbackInfoReturnable<Integer> cir, @Local BlockPos blockPos
    ) {
        if (world.getBiome(blockPos).matchesKey(BiomeKeys.MUSHROOM_FIELDS)) {
            return;
        }
    }
}

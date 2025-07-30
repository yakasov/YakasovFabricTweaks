package com.yakasov.mixin;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin {
    @Inject(
            method = "breed(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/AnimalEntity;)V",
            at = @At("TAIL"))
    private void spawnMultipleBabies(ServerWorld world, AnimalEntity other, CallbackInfo ci) {
        AnimalEntity self = (AnimalEntity)(Object)this;

        float multipleChance;
        int maxExtra;

        if (self instanceof PigEntity) {
            multipleChance = 0.25f;
            maxExtra = 3;
        } else {
            multipleChance = 0.05f;
            maxExtra = 1;
        }

        if (world.random.nextFloat() < multipleChance) {
            int extraBabies = world.random.nextInt(maxExtra) + 1;

            for (int i = 0; i < extraBabies; i++) {
                PassiveEntity baby = self.createChild(world, other);
                if (baby != null) {
                    baby.setBaby(true);
                    double offsetX = (world.random.nextDouble() - 0.5) * 2.0;
                    double offsetZ = (world.random.nextDouble() - 0.5) * 2.0;
                    baby.refreshPositionAndAngles(
                            self.getX() + offsetX,
                            self.getY(),
                            self.getZ() + offsetZ,
                            0.0F,
                            0.0F
                    );
                    world.spawnEntityAndPassengers(baby);
                }
            }
        }
    }
}

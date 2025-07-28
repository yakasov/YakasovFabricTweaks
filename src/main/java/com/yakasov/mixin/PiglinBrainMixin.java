package com.yakasov.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    @Invoker("becomeAngryWith")
    private static void invokeBecomeAngryWith(AbstractPiglinEntity piglin, LivingEntity target) {
        // Turns out this won't get run, so might as well throw an error because
        // things would be real bad if it _did_ get run
        throw new AssertionError();
    }

    @Inject(method = "tickActivities", at = @At("HEAD"))
    private static void angerAtWitherSkullWearers(PiglinEntity piglin, CallbackInfo ci) {
        if (piglin.getTarget() == null) {
            List<PlayerEntity> nearbyPlayers = piglin.getWorld().getEntitiesByClass(
                    PlayerEntity.class,
                    piglin.getBoundingBox().expand(16.0),
                    player -> !player.isSpectator() && !player.isCreative()
            );

            for (PlayerEntity player : nearbyPlayers) {
                ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);
                if (head.isOf(Items.WITHER_SKELETON_SKULL)) {
                    invokeBecomeAngryWith(piglin, player);
                    piglin.getBrain().remember(MemoryModuleType.ATTACK_TARGET, player);
                    break;
                }
            }
        }
    }
}

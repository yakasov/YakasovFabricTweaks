package com.yakasov.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ZombifiedPiglinEntity.class)
public class ZombifiedPiglinEntityMixin {
    @Inject(
            method = "mobTick",
            at = @At("HEAD")
    )
    private void checkAngerOnWitherSkullWornByNearbyPlayers(CallbackInfo ci) {
        ZombifiedPiglinEntity self = (ZombifiedPiglinEntity)(Object)this;
        ServerWorld world = (ServerWorld)self.getWorld();

        if (shouldAngerAtNearbyPlayers(self, world)) {
            PlayerEntity targetPlayer = findPlayerToAngerAt(self, world);
            if (targetPlayer != null) {
                self.setTarget(targetPlayer);
                self.setAngryAt(targetPlayer.getUuid());
                self.chooseRandomAngerTime();
            }
        }
    }

    @Unique
    private boolean shouldAngerAtNearbyPlayers(ZombifiedPiglinEntity piglin, ServerWorld world) {
        List<PlayerEntity> nearbyPlayers = world.getEntitiesByClass(
                PlayerEntity.class,
                piglin.getBoundingBox().expand(16.0),
                player -> !player.isSpectator() && !player.isCreative()
        );

        for (PlayerEntity player : nearbyPlayers) {
            ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);
            if (head.isOf(Items.WITHER_SKELETON_SKULL)) {
                return true;
            }
        }
        return false;
    }

    @Unique
    private PlayerEntity findPlayerToAngerAt(ZombifiedPiglinEntity piglin, ServerWorld world) {
        List<PlayerEntity> nearbyPlayers = world.getEntitiesByClass(
                PlayerEntity.class,
                piglin.getBoundingBox().expand(16.0),
                player -> !player.isSpectator() && !player.isCreative()
        );

        for (PlayerEntity player : nearbyPlayers) {
            ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);
            if (head.isOf(Items.WITHER_SKELETON_SKULL)) {
                return player;
            }
        }
        return null;
    }
}
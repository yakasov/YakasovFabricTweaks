package com.yakasov.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.item.Items.ROTTEN_FLESH;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "drop", at = @At("HEAD"))
    private void dropRottenFleshIfKilledByWithEffect(ServerWorld world, DamageSource damageSource, CallbackInfo ci) {
        if (damageSource.isOf(DamageTypes.WITHER) && world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            final int amount = Random.create().nextInt(2);
            final ItemStack flesh = new ItemStack(ROTTEN_FLESH, amount);
            world.spawnEntity(
                    new ItemEntity(world, this.getX(), this.getY(), this.getZ(), flesh)
            );
        }
    }
}

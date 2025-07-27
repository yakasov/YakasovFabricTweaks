package com.yakasov.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageSource.class)
public class DamageSourceMixin {
    @Inject(method = "getDeathMessage", at = @At("HEAD"), cancellable = true)
    private void getCustomDeathMessage(LivingEntity killed, CallbackInfoReturnable<Text> cir) {
        DamageSource damageSource = (DamageSource)(Object)this;

        if (damageSource.getAttacker() instanceof EnderDragonEntity) {
            cir.setReturnValue(Text.translatable("death.attack.mob.ender_dragon", killed.getDisplayName()));
        } else if (damageSource.getAttacker() instanceof PhantomEntity) {
            cir.setReturnValue(Text.translatable("death.attack.mob.phantom", killed.getDisplayName()));
        } else if (damageSource.getAttacker() instanceof WardenEntity) {
            cir.setReturnValue(Text.translatable("death.attack.mob.warden", killed.getDisplayName()));
        }
    }
}

package com.yakasov.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    @Inject(method = "wearsGoldArmor", at = @At("HEAD"), cancellable = true)
    private static void alwaysAngerIfPlayerIsWearingWitherSkull(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        ItemStack head = entity.getEquippedStack(EquipmentSlot.HEAD);

        if (head.isOf(Items.WITHER_SKELETON_SKULL)) {
            cir.setReturnValue(Boolean.FALSE);
        }
    }
}

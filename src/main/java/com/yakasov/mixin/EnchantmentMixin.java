package com.yakasov.mixin;

import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(
            method = "getMaxLevel",
            at = @At("HEAD"),
            cancellable = true
    )
    private void higherMaxLevel(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Integer.valueOf(255));
    }
}
